package com.example.vlados.crm.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
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
import com.example.vlados.crm.ui.edit.USER_KEY
import com.example.vlados.crm.utils.getCurrentUser
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.item_message.*


/**
 * Created by Daria Popova on 15.05.18.
 */

fun Context.getMessagesFragment(user: User?): Fragment {
    val fragment = MessagesFragment()
    val args = Bundle()
    args.putParcelable(USER_KEY, user)
    fragment.arguments = args
    return fragment
}

class MessagesFragment : NavMvpAppCompatFragment(), ItemInterface<Message> {
    
    var navigator: Navigator? = null
    var currentUser: User? = null
    var dialogUser: User? = null
    var width: Int = 0
    
    @InjectPresenter
    lateinit var presenter: MessagesPresenter
    
    @ProvidePresenter
    fun providePresenter(): MessagesPresenter {
        return MessagesPresenter(navigator)
    }
    
    fun getWidth() {
        val displayMetrics = DisplayMetrics()
        (context as Activity).getWindowManager()
                .getDefaultDisplay().getMetrics(displayMetrics)
        width = displayMetrics.widthPixels * 4 / 7
    }
    
    lateinit var messageAdapter: MessagesAdapter
    
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
        val view = inflater.inflate(R.layout.fragment_messages, container, false)
        return view
    }
    
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    
    var layoutManager: RecyclerView.LayoutManager? = null
    
    fun init() {
        currentUser = context.getCurrentUser()
        dialogUser = arguments.getParcelable(USER_KEY)
        getWidth()
        
        
        messageAdapter = MessagesAdapter()
        layoutManager = LinearLayoutManager(context)
        messagesRecycleView.adapter = messageAdapter
        messagesRecycleView.layoutManager = layoutManager
        presenter.getMessagesWithUser(currentUser?.id, dialogUser?.id)
        messageAddButton.setOnClickListener { save() }
    }
    
    private fun save() {
        val body = messageAddContent.text.toString()
        if (!TextUtils.isEmpty(body)) {
            val message = Message(senderId = currentUser?.id, receiverId = dialogUser?.id, body = body,
                    receiver = dialogUser, sender = currentUser)
            presenter.save(message)
            clearMessage()
        }
    }
    
    fun clearMessage() {
        val input = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(view?.windowToken, 0)
        messageAddContent.clearFocus()
        messageAddContent.setText("")
    }
    
    override fun changeFab() {
        navigator?.hibeFab()
    }
    
    
    override fun setItems(items: List<Message>) {
        messageAdapter.setItems(items)
    }
    
    
    inner class MessagesAdapter() : RecyclerView.Adapter<MessagesAdapter.MessagesHolder>() {
        
        var dialogs = listOf<Message>()
        
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MessagesHolder {
            val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_message, parent, false)
            return MessagesHolder(view)
        }
        
        override fun getItemCount(): Int {
            return dialogs.size
        }
        
        override fun onBindViewHolder(holder: MessagesHolder?, position: Int) {
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
            dialogs.sortedBy { message -> message.id }
            result.dispatchUpdatesTo(this)
            layoutManager?.scrollToPosition(dialogs.size - 1)
        }
        
        inner class MessagesHolder(override val containerView: View) :
                RecyclerView.ViewHolder(containerView), LayoutContainer {
            
            
            fun bind(message: Message, position: Int) {
                
                messageText.minimumWidth = width
                
                messageText.text = message.body
                
                val params = messageText.getLayoutParams() as RelativeLayout.LayoutParams
                
                
                
                when (message.sender?.id) {
    
                    currentUser?.id  -> {
                        messageText.background = ContextCompat.getDrawable(context, R.drawable.receive_message_background)
                        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                    }
                    else-> {
                        messageText.background = ContextCompat.getDrawable(context, R.drawable.sent_message_background)
                        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
                    }
                }
                messageText.layoutParams = params
            }
            
            
        }
        
    }
    
}