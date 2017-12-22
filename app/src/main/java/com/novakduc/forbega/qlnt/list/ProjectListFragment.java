package com.novakduc.forbega.qlnt.list;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.config.ProjectConfigurationActivity;
import com.novakduc.forbega.qlnt.model.Project;
import com.novakduc.forbega.qlnt.model.Qlnt;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by n.thanh on 9/29/2016.
 */

public class ProjectListFragment extends Fragment {
    // TODO: 9/29/2016
    public static final String PREF_QLNT = "com.novak.forbequ.qlnt";
    private static final String ACTIVE_PROJECT_ID = "active_project_id";
    private static final int PROJECT_CONFIG_RESULT = 0;
    private long mActiveProject = -1;
    private ArrayList<Project> mProjects;
    ProjectsRecyclerViewAdapter mProjectsRecyclerViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjects = Qlnt.getInstance(getActivity().getApplicationContext()).getProjectList();

        //Load active project ID
        SharedPreferences preferences = getActivity().getSharedPreferences(PREF_QLNT, 0);
        Long id = preferences.getLong(ACTIVE_PROJECT_ID, -1);
        Qlnt.getInstance(getActivity().getApplicationContext()).setActiveProjectId(id);
    }

    @Override
    public void onStop() {
        super.onStop();

        //Save active project ID
        SharedPreferences preferences = getActivity().getSharedPreferences(PREF_QLNT, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(ACTIVE_PROJECT_ID, mActiveProject);
        editor.apply();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar ab = activity.getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_view_list);
            ab.setDisplayHomeAsUpEnabled(false);
        }

        final ImageView imageView = view.findViewById(R.id.backdrop);
        Glide.with(this)
                .load(R.drawable.pic)
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);

        mProjectsRecyclerViewAdapter = new ProjectsRecyclerViewAdapter(activity, mProjects);
        RecyclerView recyclerView = view.findViewById(R.id.rv_project_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mProjectsRecyclerViewAdapter);

        Log.d("Check", "RC has set");

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProjectConfigurationActivity.class);
                startActivityForResult(intent, PROJECT_CONFIG_RESULT);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: 4/19/2017 Update result from project configuration activity
        mProjectsRecyclerViewAdapter.notifyDataSetChanged();
    }

    public static class ProjectsRecyclerViewAdapter
            extends RecyclerView.Adapter<ProjectsRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private ArrayList<Project> mValues;

        public ProjectsRecyclerViewAdapter(Context context, ArrayList<Project> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        public Project getValueAt(int position) {
            return mValues.get(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.project_item_layout, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mBoundString = mValues.get(position).getName();
            holder.mTextView.setText(mValues.get(position).getName());
            // TODO: 11/24/2016 update project information

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = "Item " + position + " clicked.";
                    Log.d("rv", s);
                }
            });

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mTextView;
            public String mBoundString;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTextView = view.findViewById(R.id.textViewProjectName);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }
    }
}
