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
import android.widget.Toast
import com.example.vlados.crm.R
import com.example.vlados.crm.common.EditMvpAppCompatDialogFragment
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Account
import com.example.vlados.crm.mockCompanies
import com.example.vlados.crm.mockJobs
import kotlinx.android.synthetic.main.fragment_account_edit.view.*

fun Context.getAccountEditFragment(account: Account? = null): DialogFragment {
    val fragment = AccountEditFragment()
    val args = Bundle()
    args.putParcelable(AccountEditFragment.ACCOUNT_KEY, account)
    fragment.arguments = args
    return fragment
}

class AccountEditFragment : EditMvpAppCompatDialogFragment() {

    override fun checkCorrectness(view: View): Boolean {
        var result = true
        val message = getString(R.string.empty_error_text)
        if (isEmpty(view.accountEditName)) {
            setEmptyError(view.accountEditName, message)
            result = false
        }
        if (isEmpty(view.accountEditLogin)) {
            setEmptyError(view.accountEditLogin, message)
            result = false
        }
        if (isEmpty(view.accountEditSurname)) {
            setEmptyError(view.accountEditSurname, message)
            result = false
        }
        if (isEmpty(view.accountEditPassword)) {
            setEmptyError(view.accountEditPassword, message)
            result = false
        }
        if (isEmpty(view.accountEditAddress)) {
            setEmptyError(view.accountEditAddress, message)
            result = false
        }
        if (isEmpty(view.accountEditStore)) {
            setEmptyError(view.accountEditStore, message)
            result = false
        }

        return result
    }


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

    companion object {
        const val ACCOUNT_KEY = "account_key"
    }


    override fun onStart() {
        super.onStart()
    }

    private fun bindAccount(view: View) {
        val account = arguments?.getParcelable<Account>(ACCOUNT_KEY)
        view.accountEditName.setText(account?.name)
        view.accountEditSurname.setText(account?.surname)
        view.accountEditAddress.setText(account?.address)
        view.accountEditLogin.setText(account?.login)
        view.accountEditPassword.setText(account?.password)
        view.accountEditStore.setText(account?.store)

        val companyAdapter = ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_dropdown_item, mockCompanies)
        view.accountEditCompany.adapter = companyAdapter
        view.accountEditCompany.setSelection(mockCompanies.indexOf(account?.company))

        val statusAdapter = ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_dropdown_item, mockJobs)
        view.accountEditJob.adapter = statusAdapter
        view.accountEditJob.setSelection(mockJobs.indexOf(account?.status))
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.fragment_account_edit, null)

        val builder = AlertDialog.Builder(activity)
        with(builder) {
            setView(view)
            setPositiveButton(R.string.edit_label, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                }
            })
            setNegativeButton(R.string.cancel_label, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    this@AccountEditFragment.dialog.cancel()
                }
            })
        }

        bindAccount(view)
        val dialog = builder.create()
        dialog.setOnShowListener { changePosButton(view) }
        return dialog
    }

    override fun save(view: View) {
        if (checkCorrectness(view)) {
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }


}