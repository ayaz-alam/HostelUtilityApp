
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
import com.medeveloper.ayaz.hostelutility.interfaces.photoChangeListener;
import com.medeveloper.ayaz.hostelutility.officials.GeneralNotice;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean inHome=false;
    private int currentFrag = 0;
    private boolean BackPressedAgain=false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setUpUser();

        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        //If screen rotates or screen turns off
        if(savedInstanceState!=null)
            populateFrag(savedInstanceState.getInt(getString(R.string.fragment_tag)));
        else
            populateFrag(R.id.nav_notice);

    }

    public static photoChangeListener photoListener;

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
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        final ImageView imageView=headerLayout.findViewById(R.id.display_image);
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

        photoListener = new photoChangeListener() {
            @Override
            public void onPhotoChanged() {
                Picasso.get().
                        load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                        .centerCrop()
                        .transform(new CircularTransform())
                        .fit()
                        .into(imageView);
            }
        };


        Picasso.get().
                load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                .centerCrop()
                .transform(new CircularTransform())
                .placeholder(getDrawable(R.drawable.man))
                .fit()
                .into(imageView);

        ((TextView)headerLayout.findViewById(R.id.display_name)).setText(prefs.getName());
        ((TextView)headerLayout.findViewById(R.id.display_email)).setText(user.getEmail());
        ((TextView)headerLayout.findViewById(R.id.display_room_no)).setText(prefs.getData(MyData.ROOM_NO));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(inHome)
            super.onBackPressed();
            else populateFrag(R.id.nav_notice);
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
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void populateFrag(int fragmentNum){
        currentFrag = fragmentNum;
        Fragment fragment = null;
        Class fragmentClass=Home.class;
        String title = null;
        switch (fragmentNum) {
            case R.id.nav_your_profile:
                title = "Your profile";
                fragmentClass = StudentProfile.class;
                inHome = false;
                BackPressedAgain = false;
                // Handle the camera action
                break;
            case R.id.nav_gen_notice:
                title = "General notice";
                currentFrag = 2;
                fragmentClass = GeneralNotice.class;
                inHome = false;
                BackPressedAgain = false;
                break;
            case R.id.nav_complaint:
                title = "Complaint";
                currentFrag = 2;
                fragmentClass = ComplaintFragment.class;
                inHome = false;
                BackPressedAgain = false;

                break;
            case R.id.nav_mess_diet_off:
                title = "Mess diet off";
                currentFrag = 2;
                fragmentClass = DietOff.class;
                inHome = false;
                BackPressedAgain = false;
                break;
            case R.id.nav_your_dietoff_requests:
                title = "Your requests";
                fragmentClass = YourDietOffRequest.class;
                inHome = false;
                BackPressedAgain = false;
                break;
            case R.id.nav_notice:
                if (inHome) {
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    return;
                }
                fragmentClass = Notice.class;
                inHome = true;
                title = "Notice";
                break;
            case R.id.nav_your_complaints:
                title = "Your complaints";
                fragmentClass = YourComplaints.class;
                inHome = false;
                BackPressedAgain = false;
                break;
            case R.id.nav_about:
                title = "About";
                fragmentClass = About.class;
                inHome = false;
                BackPressedAgain = false;
                break;
            case R.id.nav_log_out:
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure ?")
                        .setContentText("Hello " + new MyData(this).getData(MyData.NAME) + "\nAre you sure that you want to logout from this device")
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                FirebaseAuth.getInstance().signOut();
                                new MyData(getApplication()).clearPreferences();
                                Intent intent = new Intent(Home.this, LoginAcitivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                        }).show();
                break;
                default:
                    title = "Notice";
                    fragmentClass = Notice.class;
                    inHome = true;
                    BackPressedAgain = false;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!(fragmentNum==R.id.nav_log_out)) {
            // Insert the fragment by replacing any existing fragment
            getSupportActionBar().setTitle(title);
            FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction F = fragmentManager.beginTransaction();
            F.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            F.replace(R.id.fragment_layout, fragment, title).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        populateFrag(item.getItemId());
        return true;
    }


    /**
     * This function saves the current state of the application, including list of Icon Selected and previous position,
     * this prevents the loss of data during screen orientation change and device lock.
     * @param outState current state of the activity
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(getString(R.string.fragment_tag),currentFrag);
        super.onSaveInstanceState(outState);
    }
}
