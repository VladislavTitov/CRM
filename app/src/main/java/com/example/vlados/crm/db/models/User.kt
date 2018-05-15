package com.example.vlados.crm.db.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class User(val id: Long? = null,
                var shop: Shop? = null,
                var username: String? = null,
                var address: String? = null,
                var email: String? = null,
                @SerializedName("password_digest") var password: String? = null,
                @SerializedName("full_name") var fullName: String? = null,
                var blocked: Boolean? = null,
                var role: String? = null) : Parcelable {
    
    constructor(source: Parcel) : this(
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readParcelable<Shop>(Shop::class.java.classLoader),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            source.readString()
    )
    
    override fun describeContents() = 0
    
    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeParcelable(shop, 0)
        writeString(username)
        writeString(address)
        writeString(email)
        writeString(password)
        writeString(fullName)
        writeValue(blocked)
        writeString(role)
    }
    
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}