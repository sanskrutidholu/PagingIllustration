package com.example.mypaginationlibrary.pagingSample1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mypaginationlibrary.pagingSample1.paging.NewsDataSource
import com.example.mypaginationlibrary.pagingSample1.retrofit.NetworkService
import com.example.mypaginationlibrary.pagingSample1.retrofit.News
import com.example.mypaginationlibrary.pagingSample1.retrofit.State
import io.reactivex.disposables.CompositeDisposable

class NewsViewModel: ViewModel() {
    private val networkService = NetworkService.getService()
    lateinit var newsList: LiveData<PagedList<News>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 5
    private val newsDataSourceFactory: NewsDataSourceFactory =
        NewsDataSourceFactory(compositeDisposable, networkService)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        newsList = LivePagedListBuilder(newsDataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap(
        newsDataSourceFactory.newsDataSourceLiveData,
        NewsDataSource::state
    )

    fun retry() {
        newsDataSourceFactory.newsDataSourceLiveData.value!!.retryCompletable
    }

    fun listIsEmpty(): Boolean {
        return newsList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}