package com.example.vlados.crm.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.vlados.crm.R
import com.example.vlados.crm.common.GenericDiffUtilsCallback
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.common.NavMvpAppCompatFragment
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Review
import com.example.vlados.crm.presenters.ReviewsPresenter
import com.example.vlados.crm.utils.getCurrentUser
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_reviews.*
import kotlinx.android.synthetic.main.item_review.*

fun Context.getReviewFragment(): Fragment {
    val fragment = ReviewsFragment()
    return fragment
}

class ReviewsFragment : NavMvpAppCompatFragment(), ItemInterface<Review> {
    
    
    override fun setItems(items: List<Review>) {
        reviewsAdapter.setItems(items)
    }
    
    @InjectPresenter
    lateinit var presenter: ReviewsPresenter
    
    @ProvidePresenter
    fun providePresenter(): ReviewsPresenter {
        return ReviewsPresenter(navigator)
    }
    
    override fun changeFab() {
        navigator?.hibeFab()
    }
    
    
    var navigator: Navigator? = null
    var layoutManager: LinearLayoutManager? = null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Navigator) {
            navigator = context
        }
    }
    
    lateinit var reviewsAdapter: ReviewsAdapter
    
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_reviews, container, false)
    }
    
    
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    
    fun init() {
        reviewsAdapter = ReviewsAdapter()
        reviewsRecycleView.adapter = reviewsAdapter
        layoutManager = LinearLayoutManager(context)
        reviewsRecycleView.layoutManager = layoutManager
        presenter.onReady()
        reviewAddButton.setOnClickListener { sendReview() }
    }
    
    fun clearReview() {
        val input = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(view?.windowToken, 0)
        reviewAddContent.clearFocus()
        reviewAddContent.setText("")
    }
    
    private fun sendReview() {
        val content = reviewAddContent.text.toString()
        if (TextUtils.isEmpty(content))
            reviewAddContent.setError(getString(R.string.empty_error_text))
        else {
            val current = context.getCurrentUser()
            val review = Review(content = content, userId = current?.id, reviewUserId = current?.id,
                    reviewedUser = current, reviewer = current)
            presenter.save(review)
            clearReview()
        }
    }
    
    override fun onDetach() {
        super.onDetach()
        navigator = null
    }
    
    inner class ReviewsAdapter() : RecyclerView.Adapter<ReviewsAdapter.ReviewHolder>() {
        
        var reviews = listOf<Review>()
        
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ReviewHolder {
            val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_review, parent, false)
            return ReviewHolder(view)
        }
        
        override fun getItemCount(): Int {
            return reviews.size
        }
        
        override fun onBindViewHolder(holder: ReviewHolder?, position: Int) {
            holder?.bind(reviews[position], position)
        }
        
        fun addItem(item: Review) {
            val temp = ArrayList<Review>(reviews)
            temp.add(item)
            setItems(temp)
        }
        
        fun setItems(aNew: List<Review>) {
            
            val diffUtilsCallback = GenericDiffUtilsCallback<Review>(reviews, aNew,
                    { oldPosition, newPosition ->
                        reviews[oldPosition].id == aNew[newPosition].id
                    },
                    { oldPosition, newPosition ->
                        val oldItem = reviews[oldPosition]
                        val newItem = aNew[newPosition]
                        oldItem.content == newItem.content &&
                                oldItem.reviewer == newItem.reviewer &&
                                oldItem.reviewedUser == newItem.reviewedUser &&
                                oldItem.userId == newItem.userId &&
                                oldItem.reviewUserId == newItem.reviewUserId
                    }
            )
            val result = DiffUtil.calculateDiff(diffUtilsCallback, false)
            reviews = aNew
            result.dispatchUpdatesTo(this)
            layoutManager?.scrollToPosition(reviews.size - 1)
        }
        
        inner class ReviewHolder(override val containerView: View) :
                RecyclerView.ViewHolder(containerView), LayoutContainer {
            
            
            fun bind(review: Review, position: Int) {
                
                reviewUserName.text = review.reviewer?.fullName
                reviewContent.text = review.content
                when (position % 5) {
                    0 -> reviewAvatar.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_24dp)
                    1 -> reviewAvatar.setImageResource(R.drawable.ic_sentiment_neutral_24dp)
                    2 -> reviewAvatar.setImageResource(R.drawable.ic_sentiment_satisfied_24dp)
                    3 -> reviewAvatar.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
                    else -> reviewAvatar.setImageResource(R.drawable.ic_sentiment_very_satisfied_black_24dp)
                }
                
                containerView.setOnClickListener {
                }
            }
            
        }
        
    }
}