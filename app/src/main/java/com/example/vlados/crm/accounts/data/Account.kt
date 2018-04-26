package com.example.vlados.crm.accounts.data

import android.os.Parcel
import android.os.Parcelable
import com.example.vlados.crm.accounts.getRandomCompany
import com.example.vlados.crm.accounts.getRandomJob
import java.util.*

/**
 * Created by Daria Popova on 25.04.18.
 */
//todo remove random
data class Account(var company: String = getRandomCompany(),
                   var store: String = UUID.randomUUID().toString(),
                   var address: String = UUID.randomUUID().toString(),
                   var login: String = UUID.randomUUID().toString(),
                   var password: String = UUID.randomUUID().toString(),
                   var name: String = UUID.randomUUID().toString(),
                   var surname: String = UUID.randomUUID().toString(),
                   var job: String = getRandomJob()) : Parcelable {

    public fun getFullName(): String {
        return name + " " + surname;
    }

    constructor(source: Parcel) : this(
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
        writeString(company)
        writeString(store)
        writeString(address)
        writeString(login)
        writeString(password)
        writeString(name)
        writeString(surname)
        writeString(job)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Account> = object : Parcelable.Creator<Account> {
            override fun createFromParcel(source: Parcel): Account = Account(source)
            override fun newArray(size: Int): Array<Account?> = arrayOfNulls(size)
        }
    }
}