package com.medeveloper.ayaz.hostelutility.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medeveloper.ayaz.hostelutility.R;
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

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(mAuth!=null)
        mAuth.signInAnonymously();
        mRef=FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id)).child(getString(R.string.enroll_student_list_ref));
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.exists())
                    studentList=dataSnapshot;
                    else {
                        new SweetAlertDialog(getApplicationContext(),SweetAlertDialog.ERROR_TYPE).setTitleText("Warden Not Uploaded the list yet").show();//,Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        enrollNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enrollNo.setError(null);
               if(s.length()<11)
               {
                   if(!enrollNo.hasFocus())
                   enrollNo.setError("Please Enter a Valid Enroll Number");

               }
               else {
                   if(!studentList.hasChild(s.toString()))
                       enrollNo.setError("Can't find your Enrollment Number");
               }

            }
        });

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
                                                pDialog.dismiss();
                                                ShowDialog("Successfully Registered",3).
                                                        setContentText("Your default password is "+Password+" \nPlease change it once we log you in").
                                                        setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        startActivity(new Intent(StudentForm.this,Home.class));
                                                    }
                                                }).show();

                                            }
                                        }
                                    });


                                }
                                else
                                {
                                    pDialog.dismiss();
                                    ShowDialog("Cannot Log you in :"+task.getException(), 0).show();
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
            myDialog=new SweetAlertDialog(getApplicationContext(),SweetAlertDialog.ERROR_TYPE).setTitleText(msg);


        }
        else if(code==1)
        {
            myDialog=new SweetAlertDialog(this,SweetAlertDialog.NORMAL_TYPE).setTitleText(msg);

        }
        else if(code==2)
        {
            myDialog = new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText(msg);
            myDialog.setCancelable(false);
        }
        else if(code==3)
        {
            myDialog=new SweetAlertDialog(getApplicationContext(),SweetAlertDialog.SUCCESS_TYPE).setTitleText(msg);
        }
        else if(code==4)
        {
            myDialog=new SweetAlertDialog(getApplicationContext(),SweetAlertDialog.WARNING_TYPE).setTitleText(msg);
        }



        return myDialog;
    }

    private boolean CheckDetails() {
        return true;
    }


}
