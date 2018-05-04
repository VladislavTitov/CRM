package com.example.vlados.crm.db.models


import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by Daria Popova on 26.04.18.
 */
data class Sale(val id: Long = 4L,
                var title: String = UUID.randomUUID().toString(),
                var from: Date = Date(),
                var to: Date = Date(),
                var info: String = UUID.randomUUID().toString(),
                var approved: Boolean = false) : Parcelable {

    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString(),
            source.readSerializable() as Date,
            source.readSerializable() as Date,
            source.readString(),
            1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
        writeSerializable(from)
        writeSerializable(to)
        writeString(info)
        writeInt((if (approved) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Sale> = object : Parcelable.Creator<Sale> {
            override fun createFromParcel(source: Parcel): Sale = Sale(source)
            override fun newArray(size: Int): Array<Sale?> = arrayOfNulls(size)
        }
    }
}