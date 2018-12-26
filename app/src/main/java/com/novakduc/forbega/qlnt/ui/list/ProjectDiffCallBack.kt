package com.novakduc.forbega.qlnt.ui.list

class ProjectDiffCallBack: DiffUtil.ItemCallback<ListViewProjectItem>() {

    override fun areItemsTheSame(oldItem: ListViewProjectItem, newItem: ListViewProjectItem): Boolean {
        return oldItem.projectId == newItem.projectId
    }

    override fun areContentsTheSame(oldItem: ListViewProjectItem, newItem: ListViewProjectItem): Boolean {
        return newItem == oldItem
    }
}