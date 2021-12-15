package com.example.mypaginationlibrary.pagingSample1.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mypaginationlibrary.pagingSample1.retrofit.NetworkService
import com.example.mypaginationlibrary.pagingSample1.retrofit.News
import com.example.mypaginationlibrary.pagingSample1.retrofit.State
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action

class NewsDataSource(
    private val networkService: NetworkService,
    private val compositeDisposable: CompositeDisposable
): PageKeyedDataSource<Int, News>() {

    var state:MutableLiveData<State> = MutableLiveData()
    var retryCompletable: Completable? = null

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            networkService.getNews(params.key, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(
                            response.news,
                            params.key + 1
                        )
                    },
                    {
                        updateState(State.ERROR)
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        TODO("Not yet implemented")
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, News>
    ) {
        updateState(State.LOADING)
        compositeDisposable.add(
            networkService.getNews(1, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(
                            response.news,
                            null,
                            2
                        )
                    },
                    {
                        updateState(State.ERROR)
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}