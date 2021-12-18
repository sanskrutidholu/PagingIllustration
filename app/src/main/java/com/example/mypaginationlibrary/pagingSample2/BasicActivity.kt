package com.example.mypaginationlibrary.pagingSample2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypaginationlibrary.R
import com.example.mypaginationlibrary.databinding.ActivityBasicBinding
import com.example.mypaginationlibrary.pagingSample2.adapter.FooterAdapter
import com.example.mypaginationlibrary.pagingSample2.adapter.RepoAdapter
import com.example.mypaginationlibrary.pagingSample2.mvvm.GithubViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BasicActivity : AppCompatActivity() {

    lateinit var binding: ActivityBasicBinding

    private val viewModel by lazy {
        ViewModelProvider(this).get(GithubViewModel::class.java)
    }

    private val repoAdapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRV()
        loadData()

        repoAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                }
                is LoadState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                }
                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, "Load Error: ${state.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.getPagingData().collect {
                repoAdapter.submitData(it)
            }
        }
    }

    private fun setRV() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter.withLoadStateFooter(FooterAdapter {
                repoAdapter.retry()
            })
        }
    }
}