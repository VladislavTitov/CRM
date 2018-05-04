package com.example.vlados.crm.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.vlados.crm.R
import com.example.vlados.crm.common.GenericDiffUtilsCallback
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.common.NavMvpAppCompatFragment
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Good
import com.example.vlados.crm.presenters.GoodsPresenter
import kotlinx.android.synthetic.main.fragment_goods.*

fun Fragment.getGoodsFragment(): Fragment {
    val fragment = GoodsFragment()
    return fragment
}

class GoodsFragment : NavMvpAppCompatFragment(), ItemInterface<Good> {

    @InjectPresenter
    lateinit var presenter: GoodsPresenter

    @ProvidePresenter
    fun providePresenter() : GoodsPresenter {
        return GoodsPresenter()
    }

    var navigator: Navigator? = null
    var adapter : GoodsListAdapter? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Navigator) {
            navigator = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_goods, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        adapter = GoodsListAdapter { presenter.onItemClick(it) }
        rv_goods.adapter = adapter
        rv_goods.layoutManager = LinearLayoutManager(context)
        presenter.onGoodsRVReady()
    }



    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
           changeFab()
        }
    }

    override fun changeFab() {
        navigator?.setFabClickListener {
            Snackbar.make(it, "Hi from Goods!", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigator = null
    }

    override fun setItems(items: List<Good>) {
        adapter?.setGoodsAndCalculateDiff(items)
    }

    inner class GoodsListAdapter(private val onItemClickListener : (item : Good) -> Boolean) : RecyclerView.Adapter<GoodHolder>() {

        var goods = listOf<Good>()
            private set

        fun setGoodsAndCalculateDiff(new: List<Good>) {
            val diffUtilsCallback = GenericDiffUtilsCallback<Good>(goods, new,
                    { oldPosition, newPosition ->
                        goods[oldPosition].id == new[newPosition].id    //TODO change on most necessary property
                    },
                    { oldPosition, newPosition ->
                        val oldItem = goods[oldPosition]
                        val newItem = new[newPosition]
                        oldItem.name == newItem.name && oldItem.price == newItem.price && oldItem.sizes == newItem.sizes //TODO change accordingly change model
                    }
            )
            val result = DiffUtil.calculateDiff(diffUtilsCallback, false)
            goods = new
            result.dispatchUpdatesTo(this)
        }

        override fun getItemCount(): Int {
            return goods.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GoodHolder {
            return GoodHolder(LayoutInflater.from(context).inflate(R.layout.item_goods, parent, false),
                    onItemClickListener = onItemClickListener,
                    onItemChange = { pos -> notifyItemChanged(pos)})
        }

        override fun onBindViewHolder(holder: GoodHolder?, position: Int) {
            holder?.bind(goods[position], position)
        }

    }

    inner class GoodHolder(itemView: View?, private val onItemClickListener : (item : Good) -> Boolean,
                           private val onItemChange: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

        private val nameTv : TextView? = itemView?.findViewById(R.id.good_name)

        fun bind(item : Good, position: Int) {
            nameTv?.text = item.name
            itemView.setOnClickListener {
                if (onItemClickListener(item)) {
                    onItemChange(position)
                }
            }
        }


    }
}