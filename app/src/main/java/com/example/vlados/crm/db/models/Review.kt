package com.example.vlados.crm.db.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Daria Popova on 12.05.18.
 */
data class Review(val id: Int? = null,
                  var content: String,
                  @SerializedName("user_id") var userId: Long? = null,
                  @SerializedName("reviewer_id") var reviewUserId: Long? = null,
                  var reviewer: User? = null,
                  @SerializedName("reviewed_user") var reviewedUser: User? = null) : Parcelable {
    
    constructor(source: Parcel) : this(
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readParcelable<User>(User::class.java.classLoader),
            source.readParcelable<User>(User::class.java.classLoader)
    )
    
    override fun describeContents() = 0
    
    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(content)
        writeValue(userId)
        writeValue(reviewUserId)
        writeParcelable(reviewer, 0)
        writeParcelable(reviewedUser, 0)
    }
    
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Review> = object : Parcelable.Creator<Review> {
            override fun createFromParcel(source: Parcel): Review = Review(source)
            override fun newArray(size: Int): Array<Review?> = arrayOfNulls(size)
        }
    }
}