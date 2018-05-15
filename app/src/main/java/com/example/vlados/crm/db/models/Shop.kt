package com.example.vlados.crm.db.models

import android.os.Parcel
import android.os.Parcelable


data class Shop(val id: Long?,
                var name: String,
                var address: String,
                var admin: User? = null) : Parcelable {

    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString(),
            source.readString(),
            source.readValue(User::class.java.classLoader) as User?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        if (id != null) {
            writeLong(id)
        }
        writeString(name)
        writeString(address)
        writeValue(admin)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Shop> = object : Parcelable.Creator<Shop> {
            override fun createFromParcel(source: Parcel): Shop = Shop(source)
            override fun newArray(size: Int): Array<Shop?> = arrayOfNulls(size)
        }
    }
}