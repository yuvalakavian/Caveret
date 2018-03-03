package com.yuvalmetal.caveret.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.ApiObjects.Employee;
import com.yuvalmetal.caveret.ApiObjects.Item;
import com.yuvalmetal.caveret.R;

/**
 * Created by yuvalmetal on 11/11/2017.
 */

public class EditEmployeeAdapter extends RecyclerViewAdapter<Employee> {
    private EditItemActions<Employee> mEditEmployeeActions;

    public EditEmployeeAdapter(Context context,EditItemActions<Employee> actionsHolder, OnViewHolderClick<Employee> listener) {
        super(context, listener);
        mEditEmployeeActions = actionsHolder;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.edit_employee_view, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters

        return v;
    }

    @Override
    protected void bindView(final Employee item, RecyclerViewAdapter.ViewHolder viewHolder) {
        if (item != null) {
            TextView mEmployeeName = (TextView) viewHolder.getView(R.id.edit_employee_name);
            ImageButton mEditButton = (ImageButton) viewHolder.getView(R.id.edit_employee_edit_button);
            ImageButton mDeleteButton = (ImageButton) viewHolder.getView(R.id.edit_employee_delete_button);

            mEmployeeName.setText(item.getFirstName() + " " + item.getLastName());

            mEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditEmployeeActions.switchToUpdateItemFragment(item);
                }
            });

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditEmployeeActions.showDeleteItemDialog(item);
                }
            });


        }
    }
}
