
package com.medeveloper.ayaz.hostelutility.student;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.medeveloper.ayaz.hostelutility.About;
import com.medeveloper.ayaz.hostelutility.R;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean inHome=true;
    private boolean BackPressedAgain=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager fn=getSupportFragmentManager();
        fn.beginTransaction().replace(R.id.fragment_layout,new Notice(),"Notice").commit();
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
        getMenuInflater().inflate(R.menu.home, menu);
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

        Fragment fragment = null;
        Class fragmentClass=Home.class;
        String Tag="Notice";
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_your_profile) {
            Tag="YourProfile";
            fragmentClass = YourProfile.class;
            inHome=false;
            BackPressedAgain=false;

            // Handle the camera action
        } else if (id == R.id.nav_complaint) {
            Tag="Complaint";
            fragmentClass = ComplaintFragment.class;
            inHome=false;
            BackPressedAgain=false;

        } else if (id == R.id.nav_mess_diet_off) {
            Tag="MessDietOff";
            fragmentClass = DietOff.class;
            inHome=false;
            BackPressedAgain=false;


        } else if (id == R.id.nav_net_refill) {
            Tag="NetRefill";
            fragmentClass = NetRefill.class;
            inHome=false;
            BackPressedAgain=false;

        } else if (id == R.id.nav_notice) {
            if(inHome)
            {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            fragmentClass = Notice.class;
            inHome=true;
            Tag="Notice";

        } else if (id == R.id.nav_your_complaints) {
            Tag="YourComplaints";
            fragmentClass = YourComplaints.class;
            inHome=false;
            BackPressedAgain=false;

        }
        else if(id==R.id.nav_about)
        {
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
