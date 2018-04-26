package com.medeveloper.ayaz.hostelutility.officials;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.medeveloper.ayaz.hostelutility.About;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.student.DietOff;
import com.medeveloper.ayaz.hostelutility.student.Home;
import com.medeveloper.ayaz.hostelutility.student.Notice;

public class OfficialsHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean inHome=true;
    private boolean BackPressedAgain=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.officials_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FragmentManager fn=getSupportFragmentManager();
        fn.beginTransaction().replace(R.id.fragment_layout,new Notice(),"Notice").commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.officials_home, menu);
        return true;
    }

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

        Fragment fragment = null;
        Class fragmentClass=Home.class;
        String Tag="Notice";

        int id = item.getItemId();

        if (id == R.id.nav_notice) {
            if(inHome)
            {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            fragmentClass = Notice.class;
            inHome=true;
            Tag="Notice";
            // Handle the camera action
        } else if (id == R.id.nav_gen_notice) {
            Tag="GeneralNotice";
            fragmentClass = GeneralNotice.class;
            inHome=false;
            BackPressedAgain=false;
        } else if (id == R.id.nav_send_notice) {
            Tag="SendNotice";
            fragmentClass = SendNotice.class;
            inHome=false;
            BackPressedAgain=false;
        } else if (id == R.id.nav_complaints) {
            Tag="Complaint";
            fragmentClass = Complaints.class;
            inHome=false;
            BackPressedAgain=false;

        } else if (id == R.id.nav_diet_off_requests) {
            Tag="DietOffRequests";
            fragmentClass = DietOffRequests.class;
            inHome=false;
            BackPressedAgain=false;
        } else if (id == R.id.nav_staff_details) {
            Tag="StaffDetails";
            fragmentClass = StaffDetails.class;
            inHome=false;
            BackPressedAgain=false;
        }
        else if (id == R.id.nav_your_profile) {
            Tag="YourProfile";
            fragmentClass = YourProfile.class;
            inHome=false;
            BackPressedAgain=false;
        }
        else if (id == R.id.nav_about) {
            Tag="About";
            fragmentClass = About.class;
            inHome=false;
            BackPressedAgain=false;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction F=fragmentManager.beginTransaction();
        F.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        F.replace(R.id.fragment_layout, fragment,Tag).commit();






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
