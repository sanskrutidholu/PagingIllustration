package com.example.mypaginationlibrary.pagingSample2.model

import com.google.gson.annotations.SerializedName

class RepoResponse(
    @SerializedName("items")
    val items: List<Repo> = emptyList()
)