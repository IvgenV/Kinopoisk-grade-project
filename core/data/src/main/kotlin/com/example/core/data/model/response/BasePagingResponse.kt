package com.example.core.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class BasePagingResponse(
    @Json(name = "total")
    val total: Int? = null,
    @Json(name = "totalPages")
    val totalPages: Int? = null
)