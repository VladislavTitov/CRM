package com.example.vlados.crm.ui.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.vlados.crm.R
import com.example.vlados.crm.common.GenericDiffUtilsCallback
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.common.NavMvpAppCompatFragment
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Message
import com.example.vlados.crm.db.models.User
import com.example.vlados.crm.presenters.MessagesPresenter
import com.example.vlados.crm.utils.getCurrentUser
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.fragment_dialogs.*
import kotlinx.android.synthetic.main.item_dialog.*

/**
 * Created by Daria Popova on 15.05.18.
 */

fun Context.getDialogsFragmnet(): Fragment {
    val fragment = DialogsFragment()
    return fragment
}

class DialogsFragment : NavMvpAppCompatFragment(), ItemInterface<Message> {
    
    var navigator: Navigator? = null
    var currentUser: User? = null
    val messageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "com.example.vlados.crm.MESSAGES_RECEIVED") {
                presenter.onDialogReady(currentUser?.id)
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        context.registerReceiver(messageReceiver,
                IntentFilter("com.example.vlados.crm.MESSAGES_RECEIVED"))
    }
    
    override fun onPause() {
        super.onPause()
        context.unregisterReceiver(messageReceiver)
    }
    
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MessageService.receive(context)
    }
    
    @InjectPresenter
    lateinit var presenter: MessagesPresenter
    
    @ProvidePresenter
    fun providePresenter(): MessagesPresenter {
        return MessagesPresenter(navigator)
    }
    
    lateinit var messageAdapter: DialogsAdapter
    override fun onDetach() {
        super.onDetach()
        navigator = null
    }
    
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Navigator) {
            navigator = context
        }
    }
    
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dialogs, container, false)
        return view
    }
    
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.toolbar?.title = getString(R.string.title_dialogs)
        init()
    }
    
    var layoutManager: RecyclerView.LayoutManager? = null
    fun init() {
        currentUser = context.getCurrentUser()
        
        messageAdapter = DialogsAdapter()
        dialogsRecycleView.adapter = messageAdapter
        layoutManager = LinearLayoutManager(context)
        dialogsRecycleView.layoutManager = layoutManager
        presenter.onDialogReady(context.getCurrentUser()?.id)
    }
    
    override fun changeFab() {
        navigator?.setFabClickListener {
            context.getUserChooserFragment().show(childFragmentManager, "")
        }
    }
    
    
    override fun setItems(items: List<Message>) {
        messageAdapter.setItems(items)
    }
    
    
    fun onClick(dialog: Message) {
        val dialogUser = when (currentUser?.id) {
            dialog.receiver?.id -> dialog.sender
            else -> dialog.receiver
        }
        val tag = MessagesFragment::class.java.name
        var fragment = activity.supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null && fragment.isAdded) {
            return
        }
        fragment = context.getMessagesFragment(dialogUser)
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, tag)
                .addToBackStack(DialogsFragment::class.java.name)
                .commit()
        activity.toolbar?.title = dialogUser?.fullName
    }
    
    
    inner class DialogsAdapter() : RecyclerView.Adapter<DialogsAdapter.DialogsHolder>() {
        
        var dialogs = listOf<Message>()
        
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DialogsHolder {
            val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_dialog, parent, false)
            return DialogsHolder(view)
        }
        
        override fun getItemCount(): Int {
            return dialogs.size
        }
        
        override fun onBindViewHolder(holder: DialogsHolder?, position: Int) {
            holder?.bind(dialogs[position], position)
        }
        
        
        fun setItems(aNew: List<Message>) {
            val diffUtilsCallback = GenericDiffUtilsCallback<Message>(dialogs, aNew,
                    { oldPosition, newPosition ->
                        dialogs[oldPosition].id == aNew[newPosition].id
                    },
                    { oldPosition, newPosition ->
                        val oldItem = dialogs[oldPosition]
                        val newItem = aNew[newPosition]
                        oldItem.receiver == newItem.receiver &&
                                oldItem.body == newItem.body &&
                                oldItem.sender == newItem.sender
                    }
            )
            val result = DiffUtil.calculateDiff(diffUtilsCallback, false)
            dialogs = aNew
            result.dispatchUpdatesTo(this)
        }
        
        inner class DialogsHolder(override val containerView: View) :
                RecyclerView.ViewHolder(containerView), LayoutContainer {
            
            
            fun bind(dialog: Message, position: Int) {
                
                
                var temp: String?
                when (dialog.sender?.id) {
                    currentUser?.id -> {
                        temp = "Я"
                        interlocutorName.text = dialog.receiver?.fullName
                    }
                    else -> {
                        temp = dialog.sender?.fullName
                        interlocutorName.text = dialog.sender?.fullName
                    }
                }
                lastMessage.text = temp + ": ${dialog.body}"
                
                containerView.setOnClickListener {
                    onClick(dialog)
                }
            }
            
            
        }
        
    }
}