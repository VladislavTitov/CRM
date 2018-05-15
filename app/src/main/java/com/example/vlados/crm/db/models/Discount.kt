package com.example.vlados.crm.db.models


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*


data class Discount(val id: Int? = null,
                    @SerializedName("valid_from") var from: Date? = null,
                    @SerializedName("valid_to") var to: Date? = null,
                    @SerializedName("discount_type") var type: String = "percents",
                    var value: String = "0",
                    var approved: Boolean = false,
                    @SerializedName("good_id") var good: Long? = null) : Parcelable {
    
    fun formTitle(): String {
        var result = "Скидка $value"
        result += when (type) {
            "percents" -> "%"
            else -> " руб."
        }
        return result
    }
    
    fun getInfo(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        if (from == null || to == null )
            return ""
        return "С ${dateFormat.format(from)} до ${dateFormat.format(to)}"
    }
    
    constructor(source: Parcel) : this(
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readSerializable() as Date?,
            source.readSerializable() as Date?,
            source.readString(),
            source.readString(),
            1 == source.readInt(),
            source.readValue(Long::class.java.classLoader) as Long?
    )
    
    override fun describeContents() = 0
    
    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeSerializable(from)
        writeSerializable(to)
        writeString(type)
        writeString(value)
        writeInt((if (approved) 1 else 0))
        writeValue(good)
    }
    
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Discount> = object : Parcelable.Creator<Discount> {
            override fun createFromParcel(source: Parcel): Discount = Discount(source)
            override fun newArray(size: Int): Array<Discount?> = arrayOfNulls(size)
        }
    }
}