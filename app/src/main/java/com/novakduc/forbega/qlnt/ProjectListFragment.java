package com.novakduc.forbega.qlnt;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.novakduc.forbega.qlnt.model.Project;
import com.novakduc.forbega.qlnt.model.Qlnt;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by n.thanh on 9/29/2016.
 */

public class ProjectListFragment extends Fragment {
    // TODO: 9/29/2016
    public static final String PREF_QLNT = "com.novak.forbequ.qlnt";
    private static final String ACTIVE_PROJECT_ID = "active_project_id";
    private Long mActiveProject = Long.valueOf(-1);
    private ArrayList<Project> mProjects;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjects = Qlnt.getInstance(getActivity()).getProjectList();

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
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        final ActionBar ab = activity.getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_view_list);
        ab.setDisplayHomeAsUpEnabled(true);

        final ImageView imageView = (ImageView) view.findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.pic).centerCrop().into(imageView);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_project_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(activity, mProjects));

        Log.d("Check", "RC has set");

        return view;
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private ArrayList<Project> mValues;

        public SimpleStringRecyclerViewAdapter(Context context, ArrayList<Project> items) {
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

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = "Item " + position + " clicked.";
                    Log.d("rv", s);
                }
            });

            Glide.with(holder.mImageView.getContext())
                    .load(getRandomCheeseDrawable())
                    .fitCenter()
                    .into(holder.mImageView);
        }

        private int getRandomCheeseDrawable() {
            switch (new Random().nextInt(5)) {
                default:
                case 0:
                    return R.drawable.cheese_1;
                case 1:
                    return R.drawable.cheese_2;
                case 2:
                    return R.drawable.cheese_3;
                case 3:
                    return R.drawable.cheese_4;
                case 4:
                    return R.drawable.cheese_5;
            }
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;
            public String mBoundString;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.project_avatar);
                mTextView = (TextView) view.findViewById(R.id.textViewProjectName);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }
    }
}
