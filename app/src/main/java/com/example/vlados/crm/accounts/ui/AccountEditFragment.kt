package com.example.vlados.crm.accounts.ui

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
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.example.vlados.crm.R
import com.example.vlados.crm.accounts.data.Account
import com.example.vlados.crm.accounts.mockCompanies
import com.example.vlados.crm.accounts.mockJobs
import com.example.vlados.crm.common.Navigator
import kotlinx.android.synthetic.main.fragment_account_edit.view.*

fun Context.AccountEdit(): DialogFragment {
    val fragment = AccountEditFragment()
    return fragment
}

fun Context.AccountEdit(account: Account): DialogFragment {
    val fragment = AccountEditFragment()
    val args = Bundle()
    args.putParcelable(AccountEditFragment.ACCOUNT_KEY, account)
    fragment.arguments = args
    return fragment
}

class AccountEditFragment : MvpAppCompatDialogFragment() {


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

        val jobAdapter = ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_dropdown_item, mockJobs)
        view.accountEditJob.adapter = jobAdapter
        view.accountEditJob.setSelection(mockJobs.indexOf(account?.job))
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.fragment_account_edit, null)

        val builder = AlertDialog.Builder(activity)
        with(builder) {
            setView(view)
            setPositiveButton(R.string.edit_label, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    saveAccount()
                }
            })
            setNegativeButton(R.string.cancel_label, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    this@AccountEditFragment.dialog.cancel()
                }
            })
        }

        bindAccount(view)
        return builder.create()
    }

    private fun saveAccount() {
        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
    }


}