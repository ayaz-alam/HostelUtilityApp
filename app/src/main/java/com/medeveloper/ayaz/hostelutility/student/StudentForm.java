package com.medeveloper.ayaz.hostelutility.student;

import android.content.DialogInterface;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentDetailsClass;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_form);

        enrollNo=findViewById(R.id.enrollment_no);
        adhaarNo=findViewById(R.id.adhaar_no);
        nameofStudent=findViewById(R.id.your_name);
        //Spinners
        category=findViewById(R.id.student_category);
        bloodGroup=findViewById(R.id.student_blood_group);
        year=findViewById(R.id.year);
        branch=findViewById(R.id.branch);
        class_=findViewById(R.id.class_);


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
        mAuth.signInAnonymously();
        mRef= FirebaseDatabase.getInstance().getReference();
        String hostelID = "MVHostel";
        mRef=mRef.child(hostelID).child("EnrolledStudentList");
       /* for(int i=1;i<=100;i++)
        {
            mRef.child("2016CTAE"+String.format("%03d",i)).child("AdhaarNo").setValue("123456789"+String.format("%03d", i));

        }*/

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.exists())
                    studentList=dataSnapshot;
                    else {
                        Toast.makeText(getBaseContext(),"Warden Not Uploaded the list yet",Toast.LENGTH_SHORT).show();

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

                StudentDetailsClass thisStudent=new StudentDetailsClass(
                        EnrollNo,AdhaarNo,Name,Category,
                        BloodGroup,FatherName,Class,
                        Year,Branch,RoomNo,MobileNo,Email,FatherContact,
                        LocalGuardianNo,CompleteAddress
                        );
                if(CheckDetails())
                {

                    String Password=createDialogForPassword();



                }







            }
        });


    }

    private String createDialogForPassword() {

        final EditText input=new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(4,4,4,4);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(4);
        input.setFilters(filterArray);
        input.setLayoutParams(lp);



        final AlertDialog.Builder DialogueForPassWord = new AlertDialog.Builder(this);
        DialogueForPassWord.setTitle("Create your PassKey");
        DialogueForPassWord.setMessage("Enter Your 4 digit passkey");
        DialogueForPassWord.setIcon(R.drawable.ic_menu_camera);
        DialogueForPassWord.setView(input);

        DialogueForPassWord.setCancelable(false);
        DialogueForPassWord.setPositiveButton("Set Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(input.getText().length()<4)
                {

                    createDialogForPassword();
                    Toast.makeText(getBaseContext(),"Invalid Password",Toast.LENGTH_SHORT).show();
                }

            }
        });


        DialogueForPassWord.create();
        DialogueForPassWord.show();

        return input.getText().toString();

    }



    private boolean CheckDetails() {
        return true;
    }


}
