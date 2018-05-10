package com.example.vlados.crm.ui.edit

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.vlados.crm.R
import com.example.vlados.crm.common.EditMvpAppCompatDialogFragment
import com.example.vlados.crm.db.models.Good
import com.example.vlados.crm.ui.fragment.GoodsFragment
import kotlinx.android.synthetic.main.dialog_edit_good.view.*

private const val GOOD_KEY = "good"

fun GoodsFragment.getGoodEditDialog(good: Good? = null) : GoodEditFragment {
    val bundle = Bundle()
    bundle.putParcelable(GOOD_KEY, good)
    val fragment = GoodEditFragment()
    fragment.arguments = bundle
    return fragment
}

class GoodEditFragment: EditMvpAppCompatDialogFragment() {

    override fun checkCorrectness(view: View): Boolean {
        return true
    }

    override fun save(view: View) {
        Toast.makeText(context, "SAveD!", Toast.LENGTH_SHORT).show()
        dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_good, null)

        val builder = AlertDialog.Builder(context)
        with(builder, {
            setView(view)
            setCancelable(false)
            setPositiveButton(R.string.save_label, DialogInterface.OnClickListener { _, _ -> save(view) })
            setNegativeButton(R.string.cancel_label, DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })
        })
        bind(view)
        return builder.create()
    }

    fun bind(view: View) {
        val good = arguments?.getParcelable<Good>(GOOD_KEY)
        view.name.setText(good?.name)
        view.price.setText(good?.price?.toString())
        view.kind.setText(good?.kind)
        view.sizes.setText(good?.sizes?.joinToString())
    }
}