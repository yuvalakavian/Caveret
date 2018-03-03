package com.yuvalmetal.caveret.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.ApiObjects.Employee;
import com.yuvalmetal.caveret.ApiObjects.Item;
import com.yuvalmetal.caveret.R;

/**
 * Created by yuvalmetal on 31/10/2017.
 */

public class DeleteEmployeesAdapter extends  RecyclerViewAdapter<Employee> {
    private AdapterDataPasser<Employee> deleteEmployeeeDataPasser;

    public DeleteEmployeesAdapter(Context context, OnViewHolderClick<Employee> listener) {
        super(context, listener);
        deleteEmployeeeDataPasser = (AdapterDataPasser<Employee>) context;

    }


    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.delete_employee_view, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters

        return v;
    }

    @Override
    protected void bindView(final Employee item, RecyclerViewAdapter.ViewHolder viewHolder) {
        if (item != null) {
            TextView mItemName = (TextView) viewHolder.getView(R.id.delete_employee_name);

            CheckBox mCheckbox = (CheckBox) viewHolder.getView(R.id.delete_employee_checkbox);

            mCheckbox.setChecked(deleteEmployeeeDataPasser.isItemAdded(item));


            mCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = ((CheckBox) v).isChecked();
                    if (isChecked) {
                        deleteEmployeeeDataPasser.addItem(item);
                    } else {
                        deleteEmployeeeDataPasser.removeItem(item);
                    }
                    deleteEmployeeeDataPasser.updateSelectedQuantity();
                }
            });

            mItemName.setText(item.getFirstName() + " " + item.getLastName());

        }
    }
}

