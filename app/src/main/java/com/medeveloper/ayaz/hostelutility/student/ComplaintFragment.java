package com.medeveloper.ayaz.hostelutility.student;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
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
import com.medeveloper.ayaz.hostelutility.LoginAcitivity;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.Complaint;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StaffDetailsClass;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentDetailsClass;
import com.medeveloper.ayaz.hostelutility.officials.StaffDetails;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;


public class ComplaintFragment extends Fragment {


    private SweetAlertDialog pDialog;
    private int MY_PERMISSIONS_REQUEST_SEND_SMS=142;

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
        pDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE).setTitleText("Sending request..");

        //Declaring the views
        final Spinner complaint=rootView.findViewById(R.id.complaint_spinner);
        final EditText complaintDetails=rootView.findViewById(R.id.complaint_details);

        //On Click on submit button
        ((Button)rootView.findViewById(R.id.submit_complaint)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //Progress Bar


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

                    pDialog.show();
                    //Getting Student details
                    ref=FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id));
                    ref.child(getString(R.string.staff_ref)).child(complaint.getSelectedItem().toString()).
                            addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    StaffDetailsClass StaffToContact;
                                    if(dataSnapshot.exists())

                                    {
                                        StaffToContact=dataSnapshot.getValue(StaffDetailsClass.class);
                                        Log.d("Ayaz","Staff to contact: "+StaffToContact.ContactNumber);
                                        // ShowDialog("Came in Prepare "+StaffToContact,0);
                                                    //Refering to the complaint section in the database
                                        String EnrollNumber=getPrefs(getString(R.string.pref_enroll),"NULL");
                                        String Name=getPrefs(getString(R.string.pref_name),"NULL");
                                        String Room=getPrefs(getString(R.string.pref_room),"NULL");
                                        if(EnrollNumber.equals("NULL")||Name.equals("NULL")||Room.equals("NULL"))
                                        {
                                            new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).setTitleText("Session Expired!!").setConfirmText("Please login again..").setConfirmText("Login").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    startActivity(new Intent(getActivity(), LoginAcitivity.class));
                                                    sweetAlertDialog.dismiss();
                                                    getActivity().finish();
                                                }
                                            });
                                        }
                                        else
                                            {

                                            DatabaseReference temp = ref.child(getString(R.string.complaint_ref)).child(EnrollNumber);
                                            final String pushID = temp.push().getKey();

                                            //Creating complaint class object to be pushed on firebase
                                            final Complaint newCmpnt = new Complaint(EnrollNumber, Name,
                                                    getString(R.string.hostel_id), Room, complaint.getSelectedItem().toString()
                                                    , StaffToContact.ContactNumber, getString(R.string.warden_number),
                                                    complaintDate,
                                                    complaintDetails.getText().toString()
                                                    , pushID, false);


                                            temp.child(pushID).setValue(newCmpnt).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        pDialog.dismiss();
                                                        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE).
                                                                setTitleText("Successfull").
                                                                show();

                                                        /*
                                                         * Here we are sending the SMS to the warden and to the respective staff member to be contacted
                                                         * We are using in-built messaging services to send the message and custom notification to all the app users
                                                         * */
                                                        sendSMSMessage(newCmpnt.StaffContact, "Complaint Reported,\n" + "Hostel: " + newCmpnt.HostelID + "\nRoom Number: " + newCmpnt.RoomNo + "\nRegarding: " + newCmpnt.Field +
                                                                        "\nDescription: " + newCmpnt.ComplaintDescription + "\nName: " + newCmpnt.StudentName + "\nStudent Contact: " + newCmpnt.StaffContact
                                                                , getContext());
                                                        sendSMSMessage(getString(R.string.warden_number), "To warden,\nComplaint Reported, Hostel: " + newCmpnt.HostelID + "\nRoom Number: " + newCmpnt.RoomNo + "\nRegarding: " + newCmpnt.Field +
                                                                        "\nDescription: " + newCmpnt.ComplaintDescription + "\nName: " + newCmpnt.StudentName + "\nStudent Contact: " + newCmpnt.StaffContact
                                                                , getContext()
                                                        );
                                                        complaint.setSelection(0);
                                                        complaintDetails.setText(null);
                                                    } else
                                                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE).
                                                                setTitleText("not Successfull").setContentText(task.getException().toString()).
                                                                show();
                                                    ;
                                                }
                                            });
                                        }


                                                }
                                                else {
                                                    pDialog.dismiss();
                                                    new SweetAlertDialog(getContext(),SweetAlertDialog.WARNING_TYPE).setTitleText("Error").show();


                                                }

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                    else ShowDialog("DataSnapshot does not exist",1);


                                }

                            });





        return rootView;
    }


    protected void sendSMSMessage(String phoneNo, String message, Context context) {

        SmsManager sm = SmsManager.getDefault();
        ArrayList<String> parts =sm.divideMessage(message);
        int numParts = parts.size();

        ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();

        sm.sendMultipartTextMessage(phoneNo,null,parts,null,null);

    }

    void savePrefs(String Key,String Value)
    {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString(Key,Value).apply();
        Log.d("Ayaz","Preference Saved :"+getPrefs(Key,"-1"));
    }
    String getPrefs(String Key,String defaultValue)
    {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Key, defaultValue);
    }
    //Function to check the fields are valid or not
    private boolean isOkay(String complaintType, String complaintDetails) {
        boolean Okay=true;
        if(complaintType.equals("Select complaint field"))
        {
            ShowDialog("Please select a complaint field",4).show();
            Okay=false;
        }
        else if(complaintDetails.equals(""))
        {
            ShowDialog("Please give details",4).show();
            Okay=false;
        }
        else if(complaintDetails.length()<50)
        {
            ShowDialog("Atleast 50 letters",4).show();
            Okay=false;
        }

        return Okay;
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
            myDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.NORMAL_TYPE).setTitleText(msg);

        }
        else if(code==1)
        {
            myDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE).setTitleText(msg);

        }
        else if(code==2)
        {
            myDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText(msg);
            myDialog.setCancelable(false);
        }
        else if(code==3)
        {
            myDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.SUCCESS_TYPE).setTitleText(msg);
        }
        else if(code==4)
        {
            myDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.WARNING_TYPE).setTitleText(msg);
        }



        return myDialog;
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
