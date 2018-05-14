package com.example.vlados.crm.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Review
import com.example.vlados.crm.network.ApiMethods
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class ReviewsPresenter(val navigator: Navigator?) : MvpPresenter<ItemInterface<Review>>() {
    
    var reviews: List<Review> = mutableListOf()
    fun onReady() {
        ApiMethods.get.getAllReviews().observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    reviews = it
                    viewState.setItems(it)
                }, {
                    it.printStackTrace()
                    navigator?.showSnack("Error!")
                })
    }
    
    fun save(review: Review) {
        ApiMethods.post.postReview(review.userId, review).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    addItem(review)
                }, {
                    it.printStackTrace()
                    navigator?.showSnack("Error!")
                })
    }
    
    fun addItem(review: Review) {
        reviews += review
        viewState.setItems(reviews)
    }
}