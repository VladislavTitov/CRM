package com.example.vlados.crm.network.shop

import com.google.gson.annotations.SerializedName

data class ShopCreateUpdateRequest(val id: Long? = null,
                                   var name: String,
                                   var address: String,
                                   @SerializedName("admin_id") var adminId: Long? = null) {
}