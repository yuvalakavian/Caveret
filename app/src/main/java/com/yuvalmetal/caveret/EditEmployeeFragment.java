package com.yuvalmetal.caveret;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuvalmetal.caveret.ApiObjects.Employee;
import com.yuvalmetal.caveret.Fragments.DeleteEmployeeDialog;
import com.yuvalmetal.caveret.Fragments.EmployeeList;
import com.yuvalmetal.caveret.RecyclerViewAdapters.EditEmployeeAdapter;
import com.yuvalmetal.caveret.RecyclerViewAdapters.EditItemActions;

import java.util.ArrayList;


public class EditEmployeeFragment extends Fragment implements EditItemActions<Employee>{
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private EmployeeList mEmployeeListFragment;
    private EditEmployeeAdapter mAdapter;

    public ArrayList<Employee> mSelectedItems;


    public EditEmployeeFragment() {
        // Required empty public constructor
    }


    public static EditEmployeeFragment newInstance() {
        EditEmployeeFragment fragment = new EditEmployeeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new EditEmployeeAdapter(getContext(),this, null);

        mEmployeeListFragment = new EmployeeList(mAdapter);

        mFragmentManager = getChildFragmentManager();

        mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragmentTransaction.add(R.id.edit_employee_fragment_container, mEmployeeListFragment,"itemList");

        mFragmentTransaction.commit();

        mSelectedItems = new ArrayList<Employee>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_employee, container, false);
    }

    public boolean executeSearchQuery(String text){
        if(mEmployeeListFragment != null) {
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.edit_employee_fragment_container, mEmployeeListFragment,"itemList");
            mFragmentTransaction.commit();
            mEmployeeListFragment.executeSearchQuery(text);
        }
        else{
            Log.d("ERROR","IM NULL");
        }
        return true;
    }


    @Override
    public void switchToUpdateItemFragment(Employee item) {
        UpdateEmployeeFragment updateEmployeeFragment = UpdateEmployeeFragment.newInstance(item);
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.edit_employee_fragment_container,updateEmployeeFragment,"updateEmployee");
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();

    }

    @Override
    public void showDeleteItemDialog(Employee item) {
        // Create an instance of the dialog fragment and show it
        DeleteEmployeeDialog dialog = new DeleteEmployeeDialog(item);
        dialog.show(mFragmentManager, "מחיקת עובד");
    }
}
