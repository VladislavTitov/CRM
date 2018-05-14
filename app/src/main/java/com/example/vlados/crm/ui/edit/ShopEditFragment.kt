package com.example.vlados.crm.ui.edit


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.vlados.crm.R
import com.example.vlados.crm.common.EditMvpAppCompatDialogFragment
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Shop
import kotlinx.android.synthetic.main.fragment_shop_edit.view.*


fun Context.getShopEditFragment(shop: Shop? = null): DialogFragment {
    val fragment = ShopEditFragment()
    val args = Bundle()
    args.putParcelable(ShopEditFragment.SHOP_KEY, shop)
    fragment.arguments = args
    return fragment
}

class ShopEditFragment : EditMvpAppCompatDialogFragment() {

    override fun checkCorrectness(view: View): Boolean {
        var result = true
        val message = getString(R.string.empty_error_text)
        if (isEmpty(view.editShopAdminName)) {
            setError(view.editShopAdminName, message)
            result = false
        }
        if (isEmpty(view.editShopName)) {
            setError(view.editShopName, message)
            result = false
        }
        if (isEmpty(view.editShopAddress)) {
            setError(view.editShopAddress, message)
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
        const val SHOP_KEY = "shop_key"
    }


    private fun bindShop(view: View) {
        val account = arguments?.getParcelable<Shop>(SHOP_KEY)

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.fragment_shop_edit, null)

        val builder = AlertDialog.Builder(activity)
        with(builder) {
            setView(view)
            setPositiveButton(R.string.save_label, null)
            setNegativeButton(R.string.cancel_label, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    this@ShopEditFragment.dialog.cancel()
                }
            })

        }

        bindShop(view)
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