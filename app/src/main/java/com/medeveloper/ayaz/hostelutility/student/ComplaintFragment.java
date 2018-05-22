package com.medeveloper.ayaz.hostelutility.student;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.Complaint;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentDetailsClass;

import java.text.DateFormatSymbols;
import java.util.Calendar;


public class ComplaintFragment extends Fragment {


    public ComplaintFragment() {
        // Required empty public constructor
    }

    StudentDetailsClass myDetails;
    DatabaseReference ref;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.student_complaint, container, false);




        //Declaring the views
        final Spinner complaint=rootView.findViewById(R.id.complaint_spinner);
        final EditText complaintDetails=rootView.findViewById(R.id.complaint_details);

        //On Click on submit button
        ((Button)rootView.findViewById(R.id.submit_complaint)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowProgressDialog();//Progress Bar


                String ComplaintType,ComplaintDetails;
                ComplaintType=complaint.getSelectedItem().toString();
                ComplaintDetails=complaintDetails.getText().toString();
                final Calendar mCurrentTime = Calendar.getInstance();
                int year = mCurrentTime.get(Calendar.YEAR);
                int month = mCurrentTime.get(Calendar.MONTH);
                int day = mCurrentTime.get(Calendar.DAY_OF_MONTH);
                //Date of the complaint creation
                final String complaintDate = day + " " + getMonthForInt(month) + " " + year;
                //StudentDetailsClass tempDetails=new StudentDetailsClass("2016CTAE001","123456789001","adfa","adfa","adfa","adfa","adfa","adfa","adfa","123","9509126582","ayazalam922@gmail.com","adf","adfa","a");
                //Checking if complaint fields are filled
                if(isOkay(ComplaintType,ComplaintDetails)) {

                    Toast.makeText(getActivity(),"Came",Toast.LENGTH_LONG).show();





                    //Getting Student details
                    ref=FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id));
                    ref.child(getString(R.string.student_list_ref)).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists())
                                    {
                                        myDetails = dataSnapshot.getValue(StudentDetailsClass.class);
                                        ShowDialog("Came in Prepare First "+myDetails.Name,0);
                                        ref.child(getString(R.string.staff_ref)).child(complaint.getSelectedItem().toString()).
                                                addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                String StaffToContact;
                                                if(dataSnapshot.exists())
                                                {
                                                    StaffToContact=dataSnapshot.child("Contact").getValue(String.class);
                                                    ShowDialog("Came in Prepare "+StaffToContact,0);

                                                    Complaint newCmpnt = new Complaint(myDetails.EnrollNo, myDetails.Name,
                                                            getString(R.string.hostel_id), myDetails.RoomNo, complaint.getSelectedItem().toString()
                                                            , StaffToContact, getString(R.string.wardenId), complaintDate, complaintDetails.getText().toString(),
                                                            false);

                                                    //Refering to the complaint section in the database
                                                    ref.child("Complaints").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().
                                                            setValue(newCmpnt).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful())
                                                            {
                                                                Toast.makeText(getContext(),"Successfull",Toast.LENGTH_LONG).show();
                                                                complaint.setSelection(0);
                                                                complaintDetails.setText(null);
                                                            }
                                                            else Toast.makeText(getContext(),"not Successfull"+task.getException(),Toast.LENGTH_LONG).show();
                                                        }
                                                    });


                                                }

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                    else ShowDialog("DataSnapshot does not exist",0);
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });



                }







            }
        });







        return rootView;
    }

    private void ShowProgressDialog() {

    }

    //Function to check the fields are valid or not
    private boolean isOkay(String complaintType, String complaintDetails) {
        boolean Okay=true;
        if(complaintType.equals("Select complaint field"))
        {
            Toast.makeText(getContext(),"Please select a problem",Toast.LENGTH_LONG).show();
            Okay=false;
        }
        else if(complaintDetails.equals(""))
        {
            Toast.makeText(getContext(),"Please fill the complaint details",Toast.LENGTH_LONG).show();
            Okay=false;
        }
        else if(complaintDetails.length()<50)
        {
            Toast.makeText(getContext(),"Complaint details cannot be less than 50 letters",Toast.LENGTH_LONG).show();
            Okay=false;
        }

        return Okay;
    }
    private void ShowDialog(String msg, int code) {

        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    Complaint newComplaint;

    private Complaint prepareComplaintData(final String complaintType, final String complaintDetails, final String complaintDate)
    {







     return newComplaint;
    }


    //Function returns name of the function for integer value of the month
    String getMonthForInt(int num) {
        String month = "wrong";
        num--;
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }
}
