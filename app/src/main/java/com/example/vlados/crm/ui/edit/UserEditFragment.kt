package com.example.vlados.crm.ui.edit

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.vlados.crm.R
import com.example.vlados.crm.common.EditMvpAppCompatDialogFragment
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.common.ShopArrayAdapter
import com.example.vlados.crm.db.models.Shop
import com.example.vlados.crm.db.models.User
import com.example.vlados.crm.getRole
import com.example.vlados.crm.getRoleIndex
import com.example.vlados.crm.getRoles
import com.example.vlados.crm.presenters.UserEditInterface
import com.example.vlados.crm.presenters.UsersEditPresenter
import com.example.vlados.crm.utils.getCurrentUser
import kotlinx.android.synthetic.main.fragment_user_edit.view.*

const val USER_KEY = "user_key"

fun Context.getUserEditFragment(user: User? = null): DialogFragment {
    val fragment = UserEditFragment()
    val args = Bundle()
    args.putParcelable(USER_KEY, user)
    fragment.arguments = args
    return fragment
}

class UserEditFragment : EditMvpAppCompatDialogFragment(), UserEditInterface {
    
    @InjectPresenter
    lateinit var presenter: UsersEditPresenter
    
    @ProvidePresenter
    fun providePresenter(): UsersEditPresenter {
        return UsersEditPresenter(navigator, parentFragment)
    }
    
    
    private var shops: List<Shop>? = null
    
    
    override fun setShops(items: List<Shop>, dialogView: View) {
        val shopsAdapter = ShopArrayAdapter(context, items)
        dialogView.userEditShop.adapter = shopsAdapter
        dialogView.userEditShop.setSelection(shopsAdapter.getPosition(user?.shop?.id))
    }
    
    override fun checkCorrectness(view: View): Boolean {
        var result = true
        val message = getString(R.string.empty_error_text)
        
        if (isEmpty(view.userEditEmail)) {
            setError(view.userEditEmail, message)
            result = false
        }
        if (isEmpty(view.userEditFullName)) {
            setError(view.userEditFullName, message)
            result = false
        }
        if (isEmpty(view.userEditPassword)) {
            setError(view.userEditPassword, message)
            result = false
        }
        if (isEmpty(view.userEditUsername)) {
            setError(view.userEditUsername, message)
            result = false
        }
        
        return result
    }
    
    var user: User? = null
    
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
    
    
    private fun bindUser(view: View) {
        user = arguments?.getParcelable<User>(USER_KEY)
        view.userEditFullName.setText(user?.fullName)
        view.userEditEmail.setText(user?.email)
        view.userEditPassword.setText(user?.password)
        view.userEditUsername.setText(user?.username)
        
        val roleAdapter = ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_dropdown_item, getRoles(context,
                context.getCurrentUser()?.role))
        view.userEditRole.adapter = roleAdapter
        view.userEditRole.setSelection(getRoleIndex(user?.role))
        when (user?.blocked){
            true -> view.userEditBlocked.setChecked(true)
        }
    }
    
    
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.fragment_user_edit, null)
        presenter.onItemsReady(view)
        val builder = AlertDialog.Builder(activity)
        with(builder) {
            setView(view)
            setPositiveButton(R.string.save_label, null)
            setNegativeButton(R.string.cancel_label, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    this@UserEditFragment.dialog.cancel()
                }
            })
        }
        
        bindUser(view)
        val dialog = builder.create()
        dialog.setOnShowListener { changePosButton(view) }
        return dialog
    }
    
    private fun updateUser(view: View) {
        if (user == null)
            user = User()
        user?.email = getFromEdit(view.userEditEmail)
        user?.fullName = getFromEdit(view.userEditFullName)
        user?.password = getFromEdit(view.userEditPassword)
        user?.username = getFromEdit(view.userEditUsername)
        user?.role = getRole(context, view.userEditRole.selectedItem.toString())
        user?.blocked = view.userEditBlocked.isChecked
        val shop = view.userEditShop.selectedItem as Shop
        user?.shop = shop
        
    }
    
    
    override fun save(view: View) {
        if (checkCorrectness(view)) {
            updateUser(view)
            
            when (user?.id) {
                null -> presenter.save(user)
                else -> presenter.update(user)
            }
            
            dismiss()
        }
    }
    
    
}