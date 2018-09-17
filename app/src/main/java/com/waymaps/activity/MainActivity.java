package com.waymaps.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.waymaps.MainApplication;
import com.waymaps.app.R;
import com.waymaps.data.model.Task;
import com.waymaps.fragment.AbstractFragment;
import com.waymaps.fragment.MailFragment;
import com.waymaps.fragment.MainFragment;
import com.waymaps.fragment.PhoneFragment;
import com.waymaps.fragment.TaskFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;
import ru.terrakok.cicerone.commands.Command;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int SMS_PERMISSION_CODE = 0;

    private Navigator navigator = new SupportFragmentNavigator(getSupportFragmentManager(),
            R.id.content_main) {
        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            switch (screenKey) {
                case AbstractFragment.FragmentName.SCREEN_MAIN:
                    return AbstractFragment.getNewInstance(MainFragment.class, data);
                case AbstractFragment.FragmentName.SCREEN_PHONE:
                    return AbstractFragment.getNewInstance(PhoneFragment.class, data);
                case AbstractFragment.FragmentName.SCREEN_MAIL:
                    return AbstractFragment.getNewInstance(MailFragment.class, data);
                case AbstractFragment.FragmentName.SCREEN_TASK:
                    return AbstractFragment.getNewInstance(TaskFragment.class, data);
                case AbstractFragment.FragmentName.SCREEN_HOME:
                    return AbstractFragment.getNewInstance(MainFragment.class, data);
                default:
                    throw new RuntimeException("Unknown screen key!");
            }
        }

        @Override
        protected void showSystemMessage(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void exit() {
            finish();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        MainApplication.INSTANCE.getNavigatorHolder().setNavigator(navigator);
        if (!isSmsPermissionGranted()) {
            requestReadAndSendSmsPermission();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainApplication.INSTANCE.getNavigatorHolder().removeNavigator();
    }


    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        MainApplication.INSTANCE.getRouter().newRootScreen(AbstractFragment.FragmentName.SCREEN_HOME);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mail) {
            MainApplication.INSTANCE.getRouter().navigateTo(AbstractFragment.FragmentName.SCREEN_MAIL);
        } else if (id == R.id.nav_phone) {
            MainApplication.INSTANCE.getRouter().navigateTo(AbstractFragment.FragmentName.SCREEN_PHONE);
        } else if (id == R.id.nav_task) {
            MainApplication.INSTANCE.getRouter().navigateTo(AbstractFragment.FragmentName.SCREEN_TASK);
        } else if (id == R.id.nav_home) {
            MainApplication.INSTANCE.getRouter().navigateTo(AbstractFragment.FragmentName.SCREEN_HOME);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, SMS_PERMISSION_CODE);
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

    public void setDrawer(DrawerLayout drawer) {
        this.drawer = drawer;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }
}
