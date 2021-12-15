package com.example.mypaginationlibrary.pagingSample1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mypaginationlibrary.R
import com.example.mypaginationlibrary.pagingSample1.retrofit.State

class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(status: State?) {
        val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)
        progressBar.visibility = if (status == State.LOADING) VISIBLE else INVISIBLE

        val error = itemView.findViewById<TextView>(R.id.txt_error)
        error.visibility = if (status == State.ERROR) VISIBLE else INVISIBLE
    }

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_footer, parent, false)
            view.findViewById<TextView>(R.id.txt_error).setOnClickListener { retry() }
            return ListFooterViewHolder(view)
        }
    }
}