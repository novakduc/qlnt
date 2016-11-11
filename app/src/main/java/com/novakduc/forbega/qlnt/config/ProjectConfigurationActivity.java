package com.novakduc.forbega.qlnt.config;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.novakduc.baselibrary.SimpleFragmentActivity;
import com.novakduc.forbega.qlnt.R;
import com.novakduc.forbega.qlnt.config.base.ProjectBaseConfigFragment;
import com.novakduc.forbega.qlnt.model.Project;

public class ProjectConfigurationActivity extends SimpleFragmentActivity
        implements ProjectBaseConfigFragment.DiscardListener {
    private static final int PROJECT_DISCARD_CONFIRMATION = 0;
    private Project mTempProject;

    @Override
    protected Fragment createFragment() {
        mTempProject = (Project) getIntent()
                .getSerializableExtra(ProjectBaseConfigFragment.TEMP_PROJECT);
        return ProjectBaseConfigFragment.newInstance(mTempProject);
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getFragmentManager();
        int count = manager.getBackStackEntryCount();
        Log.i("count", String.valueOf(count));
        if (count == 0) {

        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //RESULT FROM PROJECT DISCARD DIALOG
        if (requestCode == PROJECT_DISCARD_CONFIRMATION) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void discardConfirmation() {
        DialogFragment dialogFragment = new DiscardConfirmation();
        dialogFragment.show(getFragmentManager(), "discardConfirm");
    }

    //Project cancel confirmation dialog
    public static class DiscardConfirmation extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.project_create_discard)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onActivityResult(PROJECT_DISCARD_CONFIRMATION,
                                    AppCompatActivity.RESULT_OK, new Intent());
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            return builder.create();
        }
    }
}
