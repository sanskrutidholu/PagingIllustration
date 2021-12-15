package com.example.mypaginationlibrary.pagingSample1.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mypaginationlibrary.pagingSample1.paging.NewsDataSource
import com.example.mypaginationlibrary.pagingSample1.retrofit.NetworkService
import com.example.mypaginationlibrary.pagingSample1.retrofit.News
import io.reactivex.disposables.CompositeDisposable

class NewsDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService
) : DataSource.Factory<Int,News>(){

    val newsDataSourceLiveData = MutableLiveData<NewsDataSource>()

    override fun create(): DataSource<Int, News> {
        val newsDataSource = NewsDataSource(networkService, compositeDisposable)
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}