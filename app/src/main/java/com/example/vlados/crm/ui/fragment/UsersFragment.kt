package com.example.vlados.crm.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.vlados.crm.*
import com.example.vlados.crm.common.GenericDiffUtilsCallback
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.common.NavMvpAppCompatFragment
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.User
import com.example.vlados.crm.presenters.UsersPresenter
import com.example.vlados.crm.ui.edit.getUserEditFragment
import com.example.vlados.crm.utils.getCurrentUser
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.android.synthetic.main.item_user.*

class UserFragment : NavMvpAppCompatFragment(), ItemInterface<User> {
    
    
    override fun changeFab() {
        navigator?.setFabClickListener { onClick() }
    }
    
    
    lateinit var usersAdapter: UsersAdapter
    
    var navigator: Navigator? = null
    
    @InjectPresenter
    lateinit var presenter: UsersPresenter
    
    @ProvidePresenter
    fun providePresenter(): UsersPresenter {
        return UsersPresenter(navigator)
    }
    
    
    public fun onClick(user: User? = null) {
        context.getUserEditFragment(user).show(childFragmentManager, "");
    }
    
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Navigator) {
            navigator = context
        }
    }
    
    
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_users, container, false)
        return view
    }
    
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    
    fun init() {
        usersAdapter = UsersAdapter()
        usersRecycleView.adapter = usersAdapter
        usersRecycleView.layoutManager = LinearLayoutManager(context)
        presenter.onReady()
    }
    
    
    override fun onDetach() {
        super.onDetach()
        navigator = null
    }
    
    
    override fun setItems(items: List<User>) {
        val current = context.getCurrentUser()
        when (current?.role) {
            MANAGER -> usersAdapter.setItems(items.filter { user -> user.role == CASHIER })
            ADMIN -> usersAdapter.setItems(items)
        }
    }
    
    inner class UsersAdapter() : RecyclerView.Adapter<UsersAdapter.UserHolder>() {
        
        var users = listOf<User>()
        
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UserHolder {
            val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_user, parent, false)
            return UserHolder(view)
        }
        
        override fun getItemCount(): Int {
            return users.size
        }
        
        override fun onBindViewHolder(holder: UserHolder?, position: Int) {
            holder?.bind(users[position], position)
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
                                oldItem.shop == newItem.shop &&
                                oldItem.blocked == newItem.blocked
                    }
            )
            val result = DiffUtil.calculateDiff(diffUtilsCallback, false)
            users = aNew
            result.dispatchUpdatesTo(this)
        }
        
        inner class UserHolder(override val containerView: View) :
                RecyclerView.ViewHolder(containerView), LayoutContainer {
            
            
            fun bind(user: User, position: Int) {
                
                userInstanceName.text = user.fullName
                userInstanceStatus.text = getRoleTitle(context, user.role)
                
                
                when (user.blocked) {
                    true -> blockUserImage.visibility = View.VISIBLE
                    else -> blockUserImage.visibility = View.GONE
                }
                
                containerView.setOnClickListener {
                    onClick(user)
                }
            }
            
            
        }
        
    }
}

fun Context.getUsersFragment(): Fragment {
    val fragment = UserFragment()
    return fragment
}
