package com.medeveloper.ayaz.hostelutility.student;

import android.content.Intent;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.MyData;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentDetailsClass;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class StudentForm extends AppCompatActivity {


    private String EnrollNo,AdhaarNo,
            Name,Category,BloodGroup,
            FatherName,Class,Year,Branch,
            RoomNo,MobileNo,Email,FatherContact,
            LocalGuardianNo, CompleteAddress;

    private EditText enrollNo,adhaarNo,nameofStudent,
            fatherName,roomNo,mobileNo,
            email,fatherContact,localGuardianNo,
            address;
    private Spinner category,bloodGroup,year,branch,class_;

    DatabaseReference mRef;
    DataSnapshot studentList;
    private Button Submit;
    SweetAlertDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_form);

        getSupportActionBar().hide();
        enrollNo=findViewById(R.id.enrollment_no);
        adhaarNo=findViewById(R.id.adhaar_no);
        nameofStudent=findViewById(R.id.your_name);
        //Spinners
        category=findViewById(R.id.student_category);
        bloodGroup=findViewById(R.id.student_blood_group);
        year=findViewById(R.id.year);
        branch=findViewById(R.id.branch);
        class_=findViewById(R.id.class_);
        final LinearLayout ll=findViewById(R.id.form_container);
        ll.setEnabled(false);
        ll.setVisibility(View.GONE);
        pDialog=new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Please wait..").setContentText("Please wait while we register you").setCancelable(false);


        fatherName=findViewById(R.id.student_father);
        roomNo=findViewById(R.id.room_no);
        mobileNo=findViewById(R.id.mobile_no);
        email=findViewById(R.id.email_address);
        fatherContact=findViewById(R.id.father_contact);
        localGuardianNo=findViewById(R.id.local_guardian_no);
        address=findViewById(R.id.permanent_address);

        Submit=findViewById(R.id.submit);
        Authenticated();

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //TODO write some way to implement the temporary data access of enrolled student list
        if(mAuth!=null)
        mAuth.signInAnonymously();
        mRef=FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id)).child(getString(R.string.enroll_student_list_ref));
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.exists())
                    {
                        studentList=dataSnapshot;
                        mAuth.signOut();
                    }
                    else {
                        new SweetAlertDialog(getApplicationContext(),SweetAlertDialog.ERROR_TYPE).setTitleText("Warden Not Uploaded the list yet").show();//,Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mAuth.signOut();
        adhaarNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {

                adhaarNo.setError(null);
                if(s.length()<12)
                {
                    if(adhaarNo.hasFocus())
                        ;
                    else {
                        adhaarNo.setError("Please enter 12 digit number");
                    }
                }
                else if(!(enrollNo.getText().toString().equals("")||enrollNo.getText().length()<11))
                {
                    if(studentList.hasChild(enrollNo.getText().toString()))
                    {
                        String Adh_no=studentList.child(enrollNo.getText().toString()).child("AdhaarNo").getValue(String.class);
                        if(Adh_no.equals(adhaarNo.getText().toString()))
                        {
                            adhaarNo.setError(null);
                            Authenticated();
                            ll.setEnabled(true);
                            ll.setVisibility(View.VISIBLE);

                            adhaarNo.setVisibility(View.GONE);
                            enrollNo.setVisibility(View.GONE);
                            Toast.makeText(getBaseContext(),"Can Proceed",Toast.LENGTH_SHORT).show();
                        }
                        else adhaarNo.setError("Cannot Find Student");
                    }

                }
                else
                {
                    if(enrollNo.length()<11)
                        enrollNo.setError("Please give 11 char. Enrollment No.");
                    else
                    enrollNo.setError("Please fill this First");
                    enrollNo.requestFocus();
                }

            }
        });






    }

    private String Password="123456789";
    private void Authenticated() {



        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EnrollNo=enrollNo.getText().toString();
                AdhaarNo=adhaarNo.getText().toString();
                Name=nameofStudent.getText().toString();
                Category=category.getSelectedItem().toString();
                BloodGroup=bloodGroup.getSelectedItem().toString();
                FatherName=fatherName.getText().toString();
                Class=class_.getSelectedItem().toString();
                Year=year.getSelectedItem().toString();
                Branch=branch.getSelectedItem().toString();
                RoomNo=roomNo.getText().toString();
                MobileNo=mobileNo.getText().toString();
                Email=email.getText().toString();
                FatherContact=fatherContact.getText().toString();
                LocalGuardianNo=localGuardianNo.getText().toString();
                CompleteAddress =address.getText().toString();

                final StudentDetailsClass thisStudent=new StudentDetailsClass(
                        EnrollNo,AdhaarNo,Name,Category,
                        BloodGroup,FatherName,Class,
                        Year,Branch,RoomNo,MobileNo,Email,FatherContact,
                        LocalGuardianNo,CompleteAddress
                        );

              /*  final StudentDetailsClass Student=new StudentDetailsClass(
                        EnrollNo,AdhaarNo,"Name","Category",
                        "Blood Group","Father Name","Class",
                        "Year","Branch","Room No.","9509126582",Email,"Father Contact",
                        "LG","Address"
                );
                */

                if(CheckDetails())
                {
                    pDialog.show();
                    if(Password!=null)
                    {
                        Password="000000";
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).
                                            child(getString(R.string.hostel_id)).child(getString(R.string.student_list_ref))
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(thisStudent).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                /**
                                                 * Saving some data in the SharedPrefrences so that they can Accessed easily
                                                 * For now, saving only HostelID,EnrollmentNo,Name and RoomNo
                                                 * */
                                                savePrefs(getString(R.string.pref_hostel_id),getString(R.string.hostel_id));
                                                savePrefs(getString(R.string.pref_enroll),thisStudent.EnrollNo);
                                                savePrefs(getString(R.string.pref_name),thisStudent.Name);
                                                savePrefs(getString(R.string.pref_room),thisStudent.RoomNo);
                                                createCredentials(thisStudent.Name);

                                                pDialog.dismiss();
                                               final SweetAlertDialog d=ShowDialog("Successfully Registered",3).
                                                        setContentText("Your default password is "+Password+" \nPlease change it once we log you in");
                                               d.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        d.dismiss();
                                                        startActivity(new Intent(StudentForm.this,Home.class));
                                                        finish();
                                                    }
                                                });
                                               d.show();


                                            }
                                        }
                                    });


                                }
                                else
                                {
                                    pDialog.dismiss();
                                    ShowDialog("Can't Login:", 0).setContentText(""+task.getException()).show();
                                }
                            }
                        });
                    }
                    else {
                        pDialog.dismiss();
                        ShowDialog("Password not detected",0).show();
                    }



                }







            }
        });


    }

    private void createCredentials(String Name) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(Name).build();
            user.updateProfile(profileUpdates);
            new MyData(this).setFirstTimeUser(true);
        }
    }

    void savePrefs(String Key,String Value)
    {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString(Key,Value).apply();
        Log.d("Ayaz","Preference Saved :"+getPrefs(Key,"-1"));
    }
    String getPrefs(String Key,String defaultValue)
    {
        return PreferenceManager.getDefaultSharedPreferences(this).getString(Key, defaultValue);
    }

    private SweetAlertDialog ShowDialog(String msg,int code)
    {
        /*
         * code = 0 : Normal Message
         * code = 1 : Error Message
         * code = 3 : ProgressBar
         * code = 4 : Success Dialog
         * */

        SweetAlertDialog myDialog=null;
        if(code==0)
        {
            myDialog=new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE).setTitleText(msg);


        }
        else if(code==1)
        {
            myDialog=new SweetAlertDialog(this,SweetAlertDialog.NORMAL_TYPE).setTitleText(msg);

        }
        else if(code==2)
        {
            myDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText(msg);
            myDialog.setCancelable(false);
        }
        else if(code==3)
        {
            myDialog=new SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE).setTitleText(msg);
        }
        else if(code==4)
        {
            myDialog=new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE).setTitleText(msg);
        }



        return myDialog;
    }



    private boolean CheckDetails() {
        boolean isOkay=true;
        enrollNo.setError(null);
        adhaarNo.setError(null);
        nameofStudent.setError(null);
        fatherName.setError(null);
        roomNo.setError(null);
        mobileNo.setError(null);
        email.setError(null);
        fatherContact.setError(null);
        localGuardianNo.setError(null);
        address.setError(null);
        nameofStudent.setError(null);

        if(Name.equals(""))
        {
            nameofStudent.setError("Required");
            nameofStudent.requestFocus();
            isOkay=false;
        }
        else if(Email.equals(""))
        {
            email.setError("Required");
            email.requestFocus();
            isOkay=false;
        }
        else if(fatherName.getText().toString().equals(""))
        {
            fatherName.setError("Required");
            fatherName.requestFocus();
            isOkay=false;
        }
        else if(mobileNo.getText().toString().equals(""))
        {
            mobileNo.setError("Required");
            mobileNo.requestFocus();
            isOkay=false;

        }
        else if(roomNo.getText().toString().equals(""))
        {
            roomNo.setError("Required");
            roomNo.requestFocus();
            isOkay=false;
        }
        else if(localGuardianNo.getText().toString().equals(""))
        {
            localGuardianNo.setError("Required");
            localGuardianNo.requestFocus();
            isOkay=false;
        }
        else if(category.getSelectedItem().equals("Select Category"))
        {
            Toast.makeText(this,"Please select category",Toast.LENGTH_SHORT).show();
            isOkay=false;
            category.requestFocus();
        }
        else if(bloodGroup.getSelectedItem().equals("Select Blood Group"))
        {
            Toast.makeText(this,"Please select blood group",Toast.LENGTH_SHORT).show();
            isOkay=false;
            bloodGroup.requestFocus();
        }
        else if(year.getSelectedItem().equals("Year")||branch.getSelectedItem().equals("Branch")||class_.getSelectedItem().equals("Class"))
        {
            Toast.makeText(this,"Please select valid class details",Toast.LENGTH_SHORT).show();
            isOkay=false;
            year.requestFocus();

        }
        return isOkay;
    }

}
