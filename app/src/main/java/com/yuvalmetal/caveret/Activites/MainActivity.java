package com.yuvalmetal.caveret.Activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView _loginUserTitle;
    TextView _loginUserName;
    DrawerLayout _drawer;
    NavigationView _navigationView;
    boolean _isLoggedIn;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updateLoginStatus(false);

        _drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, _drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        _drawer.setDrawerListener(toggle);

        toggle.syncState();

        _navigationView = (NavigationView) findViewById(R.id.nav_view);
        _navigationView.setNavigationItemSelectedListener(this);

        View hView =  _navigationView.getHeaderView(0);

        _loginUserTitle = (TextView)hView.findViewById(R.id.login_user_title);

        _loginUserName = (TextView)hView.findViewById(R.id.login_user_name);

        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

    }

    public void updateLoginStatus(boolean status){
        _isLoggedIn = status;
        if(!_isLoggedIn){
            startLoginActivity();
        }
    }


    @Override
    public void onBackPressed() {

        if (_drawer.isDrawerOpen(Gravity.START)) {
            _drawer.closeDrawer(Gravity.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void updateNavigationMenu() {
        Menu nav_menu =   _navigationView.getMenu();

        _navigationView.getMenu().findItem(R.id.nav_login).setVisible(!_isLoggedIn);
        _navigationView.getMenu().findItem(R.id.nav_logout).setVisible(_isLoggedIn);
        _navigationView.getMenu().findItem(R.id.nav_shop_cart).setVisible(_isLoggedIn);

        if(ApiResources.getInstance(this).getmEmployee().EmployeePermission.getId() == 1) {
            _navigationView.getMenu().findItem(R.id.nav_manager_menu).setVisible(_isLoggedIn);
        }
    }

    public void updateNavigation(String title, String fullName,boolean status) {
        _loginUserTitle.setText(title);
        _loginUserName.setText(fullName);
        updateLoginStatus(status);
        updateNavigationMenu();
    }

    public void startLoginActivity(){
        Intent intent ;
        intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, 1);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent ;
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(!_isLoggedIn){
            if (id == R.id.nav_login) {
                startLoginActivity();
            }
        }
        else {

            if (id == R.id.nav_shop_cart) {

                intent = new Intent(this,ShoppingCartActivity.class);
                startActivity(intent);
            }
            /*

            else if (id == R.id.nav_add_item) {
                intent = new Intent(this, AddItemActivity.class);
                startActivity(intent);

            } else if (id == R.id.nav_order_supplier) {
                intent = new Intent(this,OrderSupplierActivity.class);
                startActivity(intent);

            } else if (id == R.id.nav_add_employee) {
                intent = new Intent(this,AddEmployeeActivity.class);
                startActivity(intent);

            } else if (id == R.id.nav_fire_employee) {
                intent = new Intent(this,DeleteEmployeeActivity.class);
                startActivity(intent);

            } else if (id == R.id.nav_delete_item) {
                intent = new Intent(this,DeleteItemActivity.class);
                startActivity(intent);
                */
            else if (id == R.id.nav_logout) {
                updateNavigation(getString(R.string.app_name),getString(R.string.nav_sub_title),false);
            }
            else if (id == R.id.nav_manage_items) {
                intent = new Intent(this,ManageItemsActivity.class);
                startActivity(intent);
            }
            else if(id == R.id.nav_manage_employees){
                intent = new Intent(this,ManageEmployeesActivity.class);
                startActivity(intent);
            }
        }



       _drawer.closeDrawer(Gravity.START);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                updateNavigation(data.getStringExtra("login_user_title"),data.getStringExtra("login_user_name"),true);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

}