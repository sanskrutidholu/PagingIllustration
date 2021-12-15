package com.example.mypaginationlibrary.pagingSample1.retrofit

import com.google.gson.annotations.SerializedName

data class News(
    val title:String,

    @SerializedName("urlToImage")
    val image: String
)

data class Response(
    @SerializedName("articles")
    val news:List<News>
)