package com.example.vlados.crm.ui.edit

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
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.vlados.crm.ADMIN
import com.example.vlados.crm.MANAGER
import com.example.vlados.crm.R
import com.example.vlados.crm.common.EditMvpAppCompatDialogFragment
import com.example.vlados.crm.common.GoodsArrayAdapter
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Discount
import com.example.vlados.crm.db.models.Good
import com.example.vlados.crm.presenters.DiscountEditInterface
import com.example.vlados.crm.presenters.DiscountEditPresenter
import com.example.vlados.crm.utils.getCurrentUser
import kotlinx.android.synthetic.main.fragment_discounts_edit.view.*
import java.text.SimpleDateFormat
import java.util.*


const val SALE_KEY = "sale_key"
private const val APPROVED_KEY = "approved_key"

fun Context.getDiscountEditFragment(discount: Discount? = null, approved: Boolean = false): DialogFragment {
    val fragment = DiscountEditFragment()
    val args = Bundle()
    args.putBoolean(APPROVED_KEY, approved)
    args.putParcelable(SALE_KEY, discount)
    fragment.arguments = args
    return fragment
}

class DiscountEditFragment : EditMvpAppCompatDialogFragment(), DiscountEditInterface {
    
    @InjectPresenter
    lateinit var presenter: DiscountEditPresenter
    
    @ProvidePresenter
    fun providePresenter(): DiscountEditPresenter {
        return DiscountEditPresenter(navigator, parentFragment.parentFragment)
    }
    
    override fun setGoods(items: List<Good>, dialogView: View) {
        val goodsAdapter = GoodsArrayAdapter(context, items)
        dialogView.discountEditGood.adapter = goodsAdapter
        
        dialogView.discountEditGood.setSelection(goodsAdapter.getPosition(discount?.good))
    }
    
    override fun checkCorrectness(view: View): Boolean {
        var result = true
        val message = getString(R.string.empty_error_text)
        if (isEmpty(view.editSaleToDate)) {
            setEmptyError(view.editSaleToDate, message)
            result = false
        }
        if (isEmpty(view.editSaleFromDate)) {
            setEmptyError(view.editSaleFromDate, message)
            result = false
        }
        when (view.editSaleType2RG.checkedRadioButtonId) {
            R.id.editSalePercentRadio -> {
                if (isEmpty(view.editSalePercent)) {
                    setEmptyError(view.editSalePercent, message)
                    result = false
                }
            }
            
            R.id.editSaleForAmountRadio -> {
                if (isEmpty(view.editSaleForAmount)) {
                    setEmptyError(view.editSaleForAmount, message)
                    result = false
                }
            }
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
    
    private fun bindDiscount(view: View) {
        discount = arguments?.getParcelable<Discount>(SALE_KEY)
        when (discount?.approved) {
            true -> view.editDiscountApproved.setChecked(true)
            false -> view.editDiscountApproved.setChecked(false)
        }
        
        
        when (discount?.type) {
            "percents" -> {
                view.editSaleType2RG.check(R.id.editSalePercentRadio)
                view.editSalePercent.setText(discount?.value)
                onTypeChecked(R.id.editSalePercentRadio, view)
            }
            else -> {
                view.editSaleType2RG.check(R.id.editSaleForAmountRadio)
                view.editSaleForAmount.setText(discount?.value)
                onTypeChecked(R.id.editSaleForAmountRadio, view)
             }
        }
        
        
        
    }
    
    private fun onTypeChecked(id: Int, view: View) {
        when (id) {
            R.id.editSalePercentRadio -> {
                view.editSalePercent.setEnabled(true)
                view.editSalePercent.requestFocus()
                view.editSaleForAmount.error = null
                view.editSaleForAmount.setEnabled(false)
            }
            
            R.id.editSaleForAmountRadio -> {
                view.editSalePercent.setEnabled(false)
                view.editSaleForAmount.setEnabled(true)
                view.editSalePercent.error = null
                view.editSaleForAmount.requestFocus()
            }
        }
    }
    
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
    
    private fun onGoodAllChecked(checkedId: Int, view: View) {
        when (checkedId) {
            R.id.editSaleForOne -> {
                view.discountEditGood.setEnabled(true)
            }
            
            R.id.editSaleForAll -> {
                view.discountEditGood.setEnabled(false)
            }
        }
    }
    
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.fragment_discounts_edit, null)
        
        view.editSaleType2RG.setOnCheckedChangeListener { group, checkedId -> onTypeChecked(checkedId, view) }
        view.editSaleType1RG.setOnCheckedChangeListener { group, checkedId -> onGoodAllChecked(checkedId, view) }
        
        
        
        view.editSaleToDate.setOnClickListener { showDateDialog(view.editSaleToDate) }
        view.editSaleFromDate.setOnClickListener { showDateDialog(view.editSaleFromDate) }
        
        
        val builder = AlertDialog.Builder(activity)
        with(builder) {
            setView(view)
            if (context.getCurrentUser()?.role == ADMIN || !arguments.getBoolean(APPROVED_KEY)) {
                setPositiveButton(R.string.offer_label, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                    }
                })
            }
            setNegativeButton(R.string.cancel_label, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    this@DiscountEditFragment.dialog.cancel()
                }
            })
        }
        
        bindDiscount(view)
        val dialog = builder.create()
        if (context.getCurrentUser()?.role == ADMIN || !arguments.getBoolean(APPROVED_KEY)) {
            dialog.setOnShowListener { changePosButton(view) }
        }
        presenter.onItemsReady(view)
        return dialog
    }
    
    var discount: Discount? = null
    
    private fun updateDiscount(view: View) {
        if (discount == null)
            discount = Discount()
        
        var good = view.discountEditGood.selectedItem as Good
        discount?.good = good.id
        discount?.approved = view.editDiscountApproved.isChecked
        
        
        when (view.editSaleType1RG.checkedRadioButtonId) {
        
        }
        
        when (view.editSaleType2RG.checkedRadioButtonId) {
            R.id.editSalePercentRadio -> {
                discount?.type = "percents"
                discount?.value = view.editSalePercent.text.toString()
            }
            R.id.editSaleForAmountRadio -> {
                discount?.type = "amount"
                discount?.value = view.editSaleForAmount.text.toString()
            }
        }
        
    }
    
    
    override fun save(view: View) {
        if (checkCorrectness(view)) {
            updateDiscount(view)
            
            when (discount?.id) {
                null -> presenter.save(discount)
                else -> presenter.update(discount)
            }
            
            dismiss()
        }
    }
    
    
}