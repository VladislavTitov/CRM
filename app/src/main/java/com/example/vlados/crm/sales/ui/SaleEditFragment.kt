package com.example.vlados.crm.sales.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.example.vlados.crm.R
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.sales.data.Sale
import kotlinx.android.synthetic.main.fragment_sales_edit.view.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Daria Popova on 26.04.18.
 */


fun Context.getSaleEditFragment(sale: Sale? = null): DialogFragment {
    val fragment = SaleEditFragment()
    val args = Bundle()
    args.putParcelable(SaleEditFragment.SALE_KEY, sale)
    fragment.arguments = args
    return fragment
}

class SaleEditFragment : MvpAppCompatDialogFragment() {

    companion object {
        const val SALE_KEY = "sale_key"
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

    private fun bindAccount(view: View) {
        val sale = arguments?.getParcelable<Sale>(SALE_KEY)

        onChecked(R.id.editSalePercentRadio, view)
    }

    private fun onChecked(id: Int, view: View) {
        when (id) {
            R.id.editSalePercentRadio -> {
                view.editSalePercent.setEnabled(true)
                view.editSalePercent.requestFocus()
                view.editSaleForAmount.setEnabled(false)
            }

            R.id.editSaleForAmountRadio -> {
                view.editSalePercent.setEnabled(false)
                view.editSaleForAmount.setEnabled(true)
                view.editSaleForAmount.requestFocus()
            }
        }
    }

    //    todo change this for something more elegant
    private fun showDateDialog(editText: EditText) {
        val salesCalendar = Calendar.getInstance()
        val date = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                salesCalendar.set(Calendar.YEAR, year)
                salesCalendar.set(Calendar.MONTH, month)
                salesCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateEditTextDate(editText, salesCalendar)
            }
        }
        DatePickerDialog(context, date, salesCalendar.get(Calendar.YEAR), salesCalendar.get(Calendar.MONTH),
                salesCalendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun updateEditTextDate(editText: EditText, salesCalendar: Calendar) {
        val format = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(format)
        editText.setText(dateFormat.format(salesCalendar.time))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.fragment_sales_edit, null)

        view.editSaleType2RG.setOnCheckedChangeListener { group, checkedId -> onChecked(checkedId, view) }


        view.editSaleToDate.setOnClickListener { showDateDialog(view.editSaleToDate) }
        view.editSaleFromDate.setOnClickListener { showDateDialog(view.editSaleFromDate) }


        val builder = AlertDialog.Builder(activity)
        with(builder) {
            setView(view)
            setPositiveButton(R.string.offer_label, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    saveSale()
                }
            })
            setNegativeButton(R.string.cancel_label, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    this@SaleEditFragment.dialog.cancel()
                }
            })
        }

        bindAccount(view)
        return builder.create()
    }

    private fun saveSale() {
        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
    }


}