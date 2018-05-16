package com.example.vlados.crm.ui.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.arellomobile.mvp.MvpAppCompatFragment
import com.example.vlados.crm.R
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.network.BASE_URL
import com.example.vlados.crm.presenters.ReportsInterace
import com.example.vlados.crm.ui.getWebActivityIntent
import kotlinx.android.synthetic.main.fragment_reports.*
import java.text.SimpleDateFormat
import java.util.*

fun Context.getReportsFragment(): ReportsFragment {
    return ReportsFragment()
}

class ReportsFragment : MvpAppCompatFragment(), ReportsInterace {

    private var navigator: Navigator? = null
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Navigator) {
            navigator = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_reports, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        navigator?.hibeFab()

        val today = Calendar.getInstance()
        toDate.setText(dateFormat.format(today.time))
        today.add(Calendar.DAY_OF_MONTH, -1)
        fromDate.setText(dateFormat.format(today.time))
        show.setOnClickListener {
            startActivity(context.getWebActivityIntent("${BASE_URL}reports/?from=${fromDate.text}&to=${toDate.text}"))
        }

        fromDate.setOnClickListener { showDateDialog(fromDate) }
        toDate.setOnClickListener { showDateDialog(toDate) }
    }

    private fun showDateDialog(editText: EditText) {
        val chooseDate = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            chooseDate.set(Calendar.YEAR, year)
            chooseDate.set(Calendar.MONTH, month)
            chooseDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateEditTextDate(editText, chooseDate)
        }
        val dialog = DatePickerDialog(context, date, chooseDate.get(Calendar.YEAR), chooseDate.get(Calendar.MONTH),
                chooseDate.get(Calendar.DAY_OF_MONTH))
        dialog.datePicker.maxDate = chooseDate.timeInMillis
        dialog.show()
    }

    private fun updateEditTextDate(editText: EditText, salesCalendar: Calendar) {
        editText.setText(dateFormat.format(salesCalendar.time))
        editText.error = null
    }

    override fun onDetach() {
        super.onDetach()
        navigator = null
    }

}