package com.example.mypaginationlibrary.pagingSample2.mvvm

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mypaginationlibrary.pagingSample2.model.Repo
import kotlinx.coroutines.flow.Flow

class GithubViewModel: ViewModel() {
    fun getPagingData(): Flow<PagingData<Repo>> {
        return GithubRepository.getPagingData().cachedIn(viewModelScope)

    }
}