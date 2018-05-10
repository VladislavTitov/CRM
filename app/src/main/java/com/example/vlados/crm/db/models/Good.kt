package com.example.vlados.crm.db.models

import android.os.Parcel
import android.os.Parcelable

data class Good(val id : Long,
                val name : String,
                val price : Int,
                val kind : String,
                val sizes : List<String> = listOf()) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.createStringArrayList())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeInt(price)
        parcel.writeString(kind)
        parcel.writeStringList(sizes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Good> {
        override fun createFromParcel(parcel: Parcel): Good {
            return Good(parcel)
        }

        override fun newArray(size: Int): Array<Good?> {
            return arrayOfNulls(size)
        }
    }

}