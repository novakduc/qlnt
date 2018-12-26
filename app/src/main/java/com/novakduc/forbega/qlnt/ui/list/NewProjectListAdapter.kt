package com.novakduc.forbega.qlnt.ui.list

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.novakduc.forbega.qlnt.R
import com.novakduc.forbega.qlnt.databinding.ProjectItemBinding
import kotlinx.android.synthetic.main.project_item.view.*

class NewProjectListAdapter(
        val context: Context
) : ListAdapter<ListViewProjectItem, NewProjectListAdapter.ViewHolder>(ProjectDiffCallBack()) {

    private val TAG: String? = NewProjectListAdapter::class.java.simpleName as String

    override fun onBindViewHolder(holder: NewProjectListAdapter.ViewHolder, position: Int) {

        holder.bind(createOnClickListener(getItemId(position)), getItem(position))
        Log.d(TAG, getItem(position).toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewProjectListAdapter.ViewHolder {
        return ViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                        R.layout.project_item, parent, false)
        )
    }

    private fun createOnClickListener(projectId: Long) : View.OnClickListener{
        return View.OnClickListener { TODO("not implemented")  }
    }

    class ViewHolder(
            private val binding: ProjectItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, projectItem: ListViewProjectItem) {
            bindInternal(listener, projectItem)
        }

        private fun bindInternal(listener: View.OnClickListener, projectItem: ListViewProjectItem) {
            itemView.textViewProjectName.setText(projectItem.name)
            itemView.textViewProjectDuration.setText(projectItem.yearDuration)
            itemView.setOnClickListener(listener)
        }
    }
}