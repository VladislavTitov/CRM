package com.example.vlados.crm.db.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class User(val id: Long? = null,
                var shop: String? = null,
                var username: String? = null,
                var address: String? = null,
                var email: String? = null,
                @SerializedName("password_digest") var password: String? = null,
                var name: String? = null,
                var surname: String? = null,
                var role: String? = null) : Parcelable {

    public fun getFullName(): String {
        return name + " " + surname;
    }

    constructor(source: Parcel) : this(
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(shop)
        writeString(username)
        writeString(address)
        writeString(email)
        writeString(password)
        writeString(name)
        writeString(surname)
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