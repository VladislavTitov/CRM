package com.example.vlados.crm.db.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by Daria Popova on 04.05.18.
 */
data class Shop(val id: Long = Random().nextLong(),
                var name: String = UUID.randomUUID().toString(),
                var address: String = UUID.randomUUID().toString()) : Parcelable {

    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(name)
        writeString(address)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Shop> = object : Parcelable.Creator<Shop> {
            override fun createFromParcel(source: Parcel): Shop = Shop(source)
            override fun newArray(size: Int): Array<Shop?> = arrayOfNulls(size)
        }
    }
}