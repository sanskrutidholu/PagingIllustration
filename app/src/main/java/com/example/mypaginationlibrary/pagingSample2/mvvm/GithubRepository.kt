package com.example.mypaginationlibrary.pagingSample2.mvvm

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mypaginationlibrary.pagingSample2.api.GithubService
import com.example.mypaginationlibrary.pagingSample2.model.Repo
import com.example.mypaginationlibrary.pagingSample2.paging.RepoSource
import kotlinx.coroutines.flow.Flow

object GithubRepository {
    private const val PAGE_SIZE = 50

    private val githubService = GithubService.create()

    fun getPagingData(): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = {
                RepoSource(githubService)
            }
        ).flow
    }

}