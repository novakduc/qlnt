package com.novakduc.forbega.qlnt.ui.list

import androidx.recyclerview.widget.DiffUtil
import com.novakduc.forbega.qlnt.R.id.name

class ProjectDiffCallBack: DiffUtil.ItemCallback<ListViewProjectItem>() {

    override fun areItemsTheSame(oldItem: ListViewProjectItem, newItem: ListViewProjectItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ListViewProjectItem, newItem: ListViewProjectItem): Boolean {
        return newItem.equals(oldItem)
    }
}