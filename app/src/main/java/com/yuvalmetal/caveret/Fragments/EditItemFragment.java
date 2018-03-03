package com.yuvalmetal.caveret.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yuvalmetal.caveret.ApiObjects.Item;
import com.yuvalmetal.caveret.R;
import com.yuvalmetal.caveret.RecyclerViewAdapters.EditItemActions;
import com.yuvalmetal.caveret.RecyclerViewAdapters.EditItemAdapter;

import java.util.ArrayList;

/**
 * Created by yuvalmetal on 09/11/2017.
 */

public class EditItemFragment extends Fragment implements EditItemActions<Item>{
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private ItemList mItemListFragment;
    private EditItemAdapter mAdapter;

    public ArrayList<Item> mSelectedItems;


    public EditItemFragment() {
        // Required empty public constructor
    }


    public static EditItemFragment newInstance(String searchQuery) {
        EditItemFragment fragment = new EditItemFragment();
        Bundle args = new Bundle();
        args.putString("SEARCH_QUERY",searchQuery);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new EditItemAdapter(getContext(),this, null);

        mItemListFragment = new ItemList(mAdapter);

        mFragmentManager = getChildFragmentManager();

        mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragmentTransaction.add(R.id.edit_item_fragment_container,mItemListFragment,"itemList");

        mFragmentTransaction.commit();

        mSelectedItems = new ArrayList<Item>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_item, container, false);

        return rootView;
    }

    public boolean executeSearchQuery(String text){
        if(mItemListFragment != null) {
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.edit_item_fragment_container,mItemListFragment,"itemList");
            mFragmentTransaction.commit();
            mItemListFragment.executeSearchQuery(text);
        }
        else{
            Log.d("ERROR","IM NULL");
        }
        return true;
    }

    @Override
    public void switchToUpdateItemFragment(Item item) {
        UpdateItemFragment updateItemFragment = UpdateItemFragment.newInstance(item);
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.edit_item_fragment_container,updateItemFragment,"updateItem");
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    @Override
    public void showDeleteItemDialog(Item item) {
        // Create an instance of the dialog fragment and show it
        DeleteItemDialog dialog = new DeleteItemDialog(item);
        dialog.show(mFragmentManager, "מחיקת מוצר");
    }

}
