package com.example.vlados.crm.ui.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.vlados.crm.R
import com.example.vlados.crm.common.GenericDiffUtilsCallback
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.User
import com.example.vlados.crm.getRoleTitle
import com.example.vlados.crm.presenters.UsersPresenter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.fragment_dialog_users.view.*
import kotlinx.android.synthetic.main.item_user.*

/**
 * Created by Daria Popova on 15.05.18.
 */
class UsersChooserFragment : MvpAppCompatDialogFragment(), ItemInterface<User> {
    
    
    lateinit var usersChoserChooserAdapter: UsersChooserAdapter
    
    var navigator: Navigator? = null
    
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Navigator) {
            navigator = context
        }
    }
    
    override fun onDetach() {
        super.onDetach()
        navigator = null
    }
    
    @InjectPresenter
    lateinit var presenter: UsersPresenter
    
    @ProvidePresenter
    fun providePresenter(): UsersPresenter {
        return UsersPresenter(navigator)
    }
    

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.fragment_dialog_users, null)
        
        
        val builder = AlertDialog.Builder(activity)
        with(builder) {
            setView(view)
            setNegativeButton(R.string.cancel_label, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    this@UsersChooserFragment.dialog.cancel()
                }
            })
        }
        usersChoserChooserAdapter = UsersChooserAdapter(view)
        view.chooserUserRecycleView.layoutManager =  LinearLayoutManager(context)
        view.chooserUserRecycleView.adapter = usersChoserChooserAdapter
        presenter.onReady()
        return builder.create()
    }
    
    override fun setItems(items: List<User>) {
        usersChoserChooserAdapter.setItems(items)
    }
    
    fun onClick(user: User) {
        val tag = MessagesFragment::class.java.name
        var fragment = activity.supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null && fragment.isAdded) {
            return
        }
        fragment = context.getMessagesFragment(user)
        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, tag)
                .addToBackStack(DialogsFragment::class.java.name)
                .commit()
        activity.toolbar?.title  = user.fullName
        dismiss()
    }
    
    inner class UsersChooserAdapter(val dialogView: View) : RecyclerView.Adapter<UsersChooserAdapter.UserChooserHolder>() {
        
        var users = listOf<User>()
        
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UserChooserHolder {
            val view = LayoutInflater.from(dialogView.context)
                    .inflate(R.layout.item_user, parent, false)
            return UserChooserHolder(view)
        }
        
        override fun getItemCount(): Int {
            return users.size
        }
        
        override fun onBindViewHolder(chooserHolder: UserChooserHolder?, position: Int) {
            chooserHolder?.bind(users[position], position)
        }
        
        
        fun setItems(aNew: List<User>) {
            val diffUtilsCallback = GenericDiffUtilsCallback<User>(users, aNew,
                    { oldPosition, newPosition ->
                        users[oldPosition].id == aNew[newPosition].id
                    },
                    { oldPosition, newPosition ->
                        val oldItem = users[oldPosition]
                        val newItem = aNew[newPosition]
                        oldItem.fullName == newItem.fullName &&
                                oldItem.address == newItem.address &&
                                oldItem.email == newItem.email &&
                                oldItem.password == newItem.password &&
                                oldItem.shop == newItem.shop
                    }
            )
            val result = DiffUtil.calculateDiff(diffUtilsCallback, false)
            users = aNew
            result.dispatchUpdatesTo(this)
            notifyDataSetChanged()
        }
        
        inner class UserChooserHolder(override val containerView: View) :
                RecyclerView.ViewHolder(containerView), LayoutContainer {
            
            
            fun bind(user: User, position: Int) {
                
                userInstanceName.text = user.fullName
                userInstanceStatus.text = getRoleTitle(context, user.role)
                
                
                containerView.setOnClickListener {
                    onClick(user)
                }
            }
            
            
        }
        
    }
    
}

fun Context.getUserChooserFragment():DialogFragment{
    val fragment = UsersChooserFragment()
    return fragment
}