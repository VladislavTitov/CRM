package com.example.vlados.crm.db.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Daria Popova on 15.05.18.
 */
data class Message(val id: Long? = null,
                   var sender: User? = null,
                   var receiver: User? = null,
                   var body: String? = null,
                   @SerializedName("sender_id") var senderId: Long? = null,
                   @SerializedName("receiver_id") var receiverId: Long? = null) : Parcelable {
    
    constructor(source: Parcel) : this(
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readParcelable<User>(User::class.java.classLoader),
            source.readParcelable<User>(User::class.java.classLoader),
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readValue(Long::class.java.classLoader) as Long?
    )
    
    override fun describeContents() = 0
    
    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeParcelable(sender, 0)
        writeParcelable(receiver, 0)
        writeString(body)
        writeValue(senderId)
        writeValue(receiverId)
    }
    
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Message> = object : Parcelable.Creator<Message> {
            override fun createFromParcel(source: Parcel): Message = Message(source)
            override fun newArray(size: Int): Array<Message?> = arrayOfNulls(size)
        }
    }
}