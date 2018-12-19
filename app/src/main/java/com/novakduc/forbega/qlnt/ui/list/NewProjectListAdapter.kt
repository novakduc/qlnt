package com.novakduc.forbega.qlnt.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.novakduc.forbega.qlnt.R
import com.novakduc.forbega.qlnt.databinding.ActivityProjectListBinding
import com.novakduc.forbega.qlnt.databinding.ProjectItemBinding

class NewProjectListAdapter(
        val context: Context
): ListAdapter<ListViewProjectItem, NewProjectListAdapter.ViewHolder>(ProjectDiffCallBack()) {


    override fun onBindViewHolder(holder: NewProjectListAdapter.ViewHolder, position: Int) {
        TODO("Bind")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewProjectListAdapter.ViewHolder {
        return ViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                        R.layout.project_item, parent, false)
        )
    }

    class ViewHolder(
            private val binding: ProjectItemBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, list: List<ListViewProjectItem>) {
            with(binding) {
                TODO("need to implement")//do something
            }
        }
    }
}