package com.example.mypaginationlibrary.pagingSample1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mypaginationlibrary.pagingSample1.adapter.NewsListAdapter
import com.example.mypaginationlibrary.databinding.ActivityMainBinding
import com.example.mypaginationlibrary.pagingSample1.retrofit.State
import com.example.mypaginationlibrary.pagingSample1.viewModel.NewsViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NewsViewModel
    lateinit var newsListAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        initAdapter()
        initState()
    }

    private fun initState() {
        binding.txtError.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer{
            binding.progressBar.visibility =
                if (viewModel.listIsEmpty() && it == State.LOADING) VISIBLE else GONE
            binding.txtError.visibility = if (viewModel.listIsEmpty() && it == State.ERROR) VISIBLE else GONE
            if (!viewModel.listIsEmpty()) {
                newsListAdapter.setState(it?: State.DONE)
            }
        })

    }

    private fun initAdapter() {
        newsListAdapter = NewsListAdapter { viewModel.retry() }
        binding.recyclerView.adapter = newsListAdapter
        viewModel.newsList.observe(this, Observer {
            newsListAdapter.submitList(it)
        })
    }
}