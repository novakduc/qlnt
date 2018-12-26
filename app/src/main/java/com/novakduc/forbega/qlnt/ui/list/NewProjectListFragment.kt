package com.novakduc.forbega.qlnt.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.novakduc.forbega.qlnt.databinding.FragmentProjectListBinding
import com.novakduc.forbega.qlnt.databinding.NewFragmentProjectListBinding
import com.novakduc.forbega.qlnt.utilities.InjectorUtils

class NewProjectListFragment: Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = NewFragmentProjectListBinding.inflate(inflater, container, false)
        val adapter = NewProjectListAdapter(binding.root.context)
        binding.rvProjectList.adapter = adapter
        subscribeUi(adapter, binding)
        return binding.root
    }

    private fun subscribeUi(adapter: NewProjectListAdapter, binding: NewFragmentProjectListBinding?) {
        val factory = InjectorUtils.provideProjectListViewModelFactory(requireContext())
        val viewModel = ViewModelProviders.of(this, factory)
                .get(ProjectListFragmentViewModel::class.java)
        viewModel.projects.observe(this, Observer { projects ->
            if (projects != null && projects.isNotEmpty()) {
                adapter.submitList(projects)
            }
        })
    }
}