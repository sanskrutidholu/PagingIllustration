package com.example.mypaginationlibrary.pagingSample2.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mypaginationlibrary.pagingSample2.api.GithubService
import com.example.mypaginationlibrary.pagingSample2.model.Repo

class RepoSource (private val githubService: GithubService) : PagingSource<Int, Repo>() {

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return try {
            val page = params.key ?: 1 // set page 1 as default
            val pageSize = params.loadSize
            val repoResponse = githubService.searchRepos(page,pageSize)
            val repoItems = repoResponse.items
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}