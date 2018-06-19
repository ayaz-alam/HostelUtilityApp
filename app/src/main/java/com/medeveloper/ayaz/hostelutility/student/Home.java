
package com.medeveloper.ayaz.hostelutility.student;

import android.content.Intent;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.medeveloper.ayaz.hostelutility.About;
import com.medeveloper.ayaz.hostelutility.LoginAcitivity;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.CircularTransform;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.MyData;
import com.medeveloper.ayaz.hostelutility.officials.GeneralNotice;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean inHome=true;
    private boolean BackPressedAgain=false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setUpUser();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager fn=getSupportFragmentManager();
        fn.beginTransaction().replace(R.id.fragment_layout, new StudentProfile(), "Profile").commit();
        getSupportActionBar().setTitle("Notice");
        fn.beginTransaction().replace(R.id.fragment_layout, new Notice(), "Notice").commit();

    }

    private void setUpUser() {
        final MyData prefs=new MyData(this);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user==null)
        {
            FirebaseAuth.getInstance().signOut();
            new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Session Expired")
                    .setContentText("Please login again")
                    .setConfirmText("Login")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            startActivity(new Intent(Home.this,LoginAcitivity.class));
                            prefs.clearPreferences();
                            finish();
                        }
                    }).show();
            return;
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        ImageView imageView=headerLayout.findViewById(R.id.display_image);
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fn=getSupportFragmentManager();
                getSupportActionBar().setTitle("Profile");
                fn.beginTransaction().replace(R.id.fragment_layout,new StudentProfile(),"Profile").commit();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        Picasso.get().
                load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                .centerCrop()
                .transform(new CircularTransform())
                .fit()
                .into(imageView);

        ((TextView)headerLayout.findViewById(R.id.display_name)).setText(prefs.getName());
        ((TextView)headerLayout.findViewById(R.id.display_email)).setText(user.getEmail());
        ((TextView)headerLayout.findViewById(R.id.display_room_no)).setText(prefs.getData(MyData.ROOM_NO));

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
        if (id == R.id.action_logout) {



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
            Tag="Your profile";
            fragmentClass = StudentProfile.class;
            inHome=false;
            BackPressedAgain=false;

            // Handle the camera action
        }else if (id == R.id.nav_gen_notice) {
            Tag="General notice";
            fragmentClass = GeneralNotice.class;
            inHome=false;
            BackPressedAgain=false;
        }
        else if (id == R.id.nav_complaint) {
            Tag="Complaint";
            fragmentClass = ComplaintFragment.class;
            inHome=false;
            BackPressedAgain=false;

        } else if (id == R.id.nav_mess_diet_off) {
            Tag="Mess diet off";
            fragmentClass = DietOff.class;
            inHome=false;
            BackPressedAgain=false;


        }
        else if (id == R.id.nav_your_dietoff_requests) {
            Tag="Your requests";
            fragmentClass = YourDietOffRequest.class;
            inHome=false;
            BackPressedAgain=false;

        }

        else if (id == R.id.nav_net_refill) {
            Tag="Net refill";
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
            Tag="Your complaints";
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
        else if(id==R.id.nav_log_out)
        {    new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure ?")
                .setContentText("Hello "+new MyData(this).getData(MyData.NAME)+"\nAre you sure that you want to logout from this device")
                .setConfirmText("Yes")
                .setCancelText("No")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                FirebaseAuth.getInstance().signOut();
                new MyData(getApplication()).clearPreferences();
                Intent intent=new Intent(Home.this,LoginAcitivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        }).show();


        }


        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!(id==R.id.nav_log_out)) {
            // Insert the fragment by replacing any existing fragment
            getSupportActionBar().setTitle(Tag);
            FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction F = fragmentManager.beginTransaction();
            F.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            F.replace(R.id.fragment_layout, fragment, Tag).commit();
        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
