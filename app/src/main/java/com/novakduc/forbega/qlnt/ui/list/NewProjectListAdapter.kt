package com.novakduc.forbega.qlnt.ui.list

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.novakduc.forbega.qlnt.databinding.ActivityProjectListBinding
import com.novakduc.forbega.qlnt.databinding.ProjectItemBinding

class NewProjectListAdapter(
        val context: Context
): ListAdapter<List<ListViewProjectItem>, NewProjectListAdapter.ViewHolder>(ProjectDiffCallBack()) {


    override fun onBindViewHolder(holder: NewProjectListAdapter.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewProjectListAdapter.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class ViewHolder(
            private val binding: ProjectItemBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, list: List<ListViewProjectItem>) {
            with(binding) {
                TODO("not implement") //do something
            }
        }
    }
}