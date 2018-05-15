package com.example.vlados.crm.ui.edit

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.vlados.crm.R
import com.example.vlados.crm.common.EditMvpAppCompatDialogFragment
import com.example.vlados.crm.common.EditObserver
import com.example.vlados.crm.db.models.Good
import com.example.vlados.crm.presenters.edit.good.GoodEditInterface
import com.example.vlados.crm.presenters.edit.good.GoodEditPresenter
import com.example.vlados.crm.ui.fragment.GoodsFragment
import kotlinx.android.synthetic.main.dialog_edit_good.view.*

private const val GOOD_KEY = "good"
private const val NEW_KEY = "is_new"

fun GoodsFragment.getGoodEditDialog(good: Good? = null): GoodEditFragment {
    val bundle = Bundle()
    bundle.putBoolean(NEW_KEY, good == null)
    bundle.putParcelable(GOOD_KEY, good)
    val fragment = GoodEditFragment()
    fragment.arguments = bundle
    return fragment
}

class GoodEditFragment : EditMvpAppCompatDialogFragment(), GoodEditInterface {
    
    @InjectPresenter
    lateinit var presenter: GoodEditPresenter
    lateinit var layout: View
    var editObserver: EditObserver? = null
    var good: Good? = null
    
    @ProvidePresenter
    fun providePresenter(): GoodEditPresenter {
        return GoodEditPresenter(arguments.getBoolean(NEW_KEY))
    }
    
    override fun checkCorrectness(view: View): Boolean {
        var result = true
        val message = getString(R.string.empty_error_text)
        
        if (isEmpty(view.name)) {
            setError(view.name, message)
            result = false
        }
        if (isEmpty(view.price)) {
            setError(view.price, message)
            result = false
        }
        if (isEmpty(view.kind)) {
            setError(view.kind, message)
            result = false
        }
        if (isEmpty(view.sizes)) {
            setError(view.sizes, message)
            result = false
        }
        return result
    }
    
    override fun save(view: View) {
        if (checkCorrectness(view)) {
            presenter.onSave(good?.id,
                    view.name.text.toString(),
                    view.price.text.toString().toInt(),
                    view.kind.text.toString(),
                    view.sizes.text.toString())
        }
        
    }
    
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        editObserver = (parentFragment as EditObserver)
    }
    
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        layout = LayoutInflater.from(context).inflate(R.layout.dialog_edit_good, null)
        
        val builder = AlertDialog.Builder(context)
        with(builder, {
            setView(layout)
            setCancelable(false)
            /*setPositiveButton(R.string.save_label, DialogInterface.OnClickListener { _, _ -> save(layout) })
            setNegativeButton(R.string.cancel_label, DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })*/
        })
        layout.save.setOnClickListener {
            save(layout)
        }
        layout.cancel.setOnClickListener {
            dialog.dismiss()
        }
        bind(layout)
        return builder.create()
    }
    
    fun bind(view: View) {
        good = arguments?.getParcelable<Good>(GOOD_KEY)
        view.name.setText(good?.name)
        view.price.setText(good?.price?.toString())
        view.kind.setText(good?.kind)
        view.sizes.setText(good?.sizes)
    }
    
    override fun showLoading(isShown: Boolean) {
        if (isShown) {
            layout.form.visibility = View.GONE
            layout.progress.visibility = View.VISIBLE
        } else {
            layout.form.visibility = View.VISIBLE
            layout.progress.visibility = View.GONE
        }
    }
    
    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    
    override fun notifyObserver() {
        editObserver?.onEditEnd()
        
    }
}