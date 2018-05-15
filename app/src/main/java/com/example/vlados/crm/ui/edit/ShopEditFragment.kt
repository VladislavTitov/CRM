package com.example.vlados.crm.ui.edit


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.vlados.crm.R
import com.example.vlados.crm.common.EditMvpAppCompatDialogFragment
import com.example.vlados.crm.common.EditObserver
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.common.UsersArrayAdapter
import com.example.vlados.crm.db.models.Shop
import com.example.vlados.crm.db.models.User
import com.example.vlados.crm.presenters.edit.shop.ShopEditInterface
import com.example.vlados.crm.presenters.edit.shop.ShopEditPresenter
import kotlinx.android.synthetic.main.fragment_shop_edit.view.*

private const val SHOP_KEY = "shop_key"
private const val NEW_KEY = "new_key"

fun Context.getShopEditFragment(shop: Shop? = null): DialogFragment {
    val fragment = ShopEditFragment()
    val args = Bundle()
    args.putBoolean(NEW_KEY, shop == null)
    args.putParcelable(SHOP_KEY, shop)
    fragment.arguments = args
    return fragment
}

class ShopEditFragment : EditMvpAppCompatDialogFragment(), ShopEditInterface {

    @InjectPresenter
    lateinit var presenter: ShopEditPresenter
    var editObserver: EditObserver? = null

    var navigator: Navigator? = null
    lateinit var layout: View
    var shop: Shop? = null

    var spin: Spinner? = null

    @ProvidePresenter
    fun providePresenter(): ShopEditPresenter {
        return ShopEditPresenter(arguments.getBoolean(NEW_KEY), navigator!!)
    }

    override fun checkCorrectness(view: View): Boolean {
        var result = true
        val message = getString(R.string.empty_error_text)
        if (isEmpty(view.editShopName)) {
            setEmptyError(view.editShopName, message)
            result = false
        }
        if (isEmpty(view.editShopAddress)) {
            setEmptyError(view.editShopAddress, message)
            result = false
        }
        if (spin == null) {
            result = false
        }

        return result
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Navigator) {
            navigator = context
        }
        editObserver = (parentFragment as EditObserver)
    }

    override fun onDetach() {
        super.onDetach()
        navigator = null
    }

    private fun bindShop(view: View) {
        shop = arguments?.getParcelable<Shop>(SHOP_KEY)
        view.editShopName.setText(shop?.name)
        view.editShopAddress.setText(shop?.address)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = LayoutInflater.from(context)
        layout = inflater.inflate(R.layout.fragment_shop_edit, null)

        val builder = AlertDialog.Builder(activity)
        with(builder) {
            setView(layout)
            /*setPositiveButton(R.string.save_label, null)
            setNegativeButton(R.string.cancel_label, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    this@ShopEditFragment.dialog.cancel()
                }
            })*/

        }

        layout.save.setOnClickListener { save(layout) }
        layout.cancel.setOnClickListener { dismiss() }

        bindShop(layout)
        presenter.onSpinnerReady()
        val dialog = builder.create()
        return dialog
    }

    override fun setSpinnerItems(users: List<User>) {
        spin = Spinner(context)
        val adapter = UsersArrayAdapter(context, users)
        spin?.adapter = adapter
        spin?.setSelection(adapter.getPosition(shop?.id))
        spin?.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layout.users.addView(spin)
    }

    override fun save(view: View) {
        if (checkCorrectness(view)) {
            val manager = spin?.selectedItem as User
            presenter.onSave(shop?.id, view.editShopName.text.toString(), view.editShopAddress.text.toString(), manager.id!!)
        }
    }

    override fun showLoading(isShown: Boolean) {
        if (isShown) {
            layout.form.visibility = View.GONE
            layout.loading.visibility = View.VISIBLE
        } else {
            layout.form.visibility = View.VISIBLE
            layout.loading.visibility = View.GONE
        }
    }

    override fun showMessage(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun notifyObserver() {
        editObserver?.onEditEnd()
    }
}