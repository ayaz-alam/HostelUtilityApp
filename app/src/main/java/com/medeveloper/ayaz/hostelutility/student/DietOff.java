package com.medeveloper.ayaz.hostelutility.student;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

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
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.DietOffRequestClass;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.MyData;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentDetailsClass;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class DietOff extends Fragment implements DatePickerDialog.OnDateSetListener{


    StudentDetailsClass myDetails;
    public DietOff() {
        // Required empty public constructor
    }

    View rootView;
    Boolean from=false;
    int DaysLeft=7;
    int FromDate;
    int Month;
    int DaysRequested;
    Date choosenDate;
    DatabaseReference baseRef;
    SweetAlertDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Calendar mCurrentTime = Calendar.getInstance();
        int year = mCurrentTime.get(Calendar.YEAR);
        int month = mCurrentTime.get(Calendar.MONTH);
        final int day = mCurrentTime.get(Calendar.DAY_OF_MONTH);
        rootView=inflater.inflate(R.layout.student_diet_off, container, false);
        pDialog=ShowDialog("Please wait,,",2);

        final Button submit=rootView.findViewById(R.id.submit_request);
        final TextView daysLeft=rootView.findViewById(R.id.days_left);
        final EditText details=rootView.findViewById(R.id.reason_diet_off);

        baseRef= FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id));
        baseRef.child(getString(R.string.mess_days_left_ref)).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    DaysLeft=dataSnapshot.getValue(Integer.class);
                else DaysLeft=0;

                daysLeft.setText((7-DaysLeft)+" Days");
                if(DaysLeft>6)
                {
                    daysLeft.setText("0 Days");
                    DaysLeft=7;
                    ShowDialog("Sorry",1).setContentText("You don't have any diet off days left").show();
                    submit.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),DietOff.this, year, month, day);
        (rootView.findViewById(R.id.from
        ))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Date today = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(today);// Subtract 6 months
                        long minDate = c.getTime().getTime(); // Twice!

                        datePickerDialog.show();
                        datePickerDialog.getDatePicker().setMinDate(minDate);
                    }
                });

        (rootView.findViewById(R.id.to
        )).setEnabled(false);

        final DatePickerDialog datePicker = new DatePickerDialog(getActivity(),DietOff.this, year, month, day);
        (rootView.findViewById(R.id.to
        )).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar c = Calendar.getInstance();
                        c.setTime(choosenDate);
                        c.add(Calendar.DAY_OF_MONTH,+1);
                        long minDate=c.getTime().getTime();
                        c.add(Calendar.DAY_OF_MONTH,+(6-DaysLeft));
                        long maxDate=c.getTime().getTime();

                        datePicker.getDatePicker().setMinDate(minDate);
                        datePicker.getDatePicker().setMaxDate(maxDate);//*/
                        datePicker.show();

                    }
                });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String To = ((TextView) rootView.findViewById(R.id.to
                )).getText().toString();
                final String From = ((TextView) rootView.findViewById(R.id.from
                )).getText().toString();
                final String Reason = details.getText().toString();


                if (isOkay(To, From, Reason)) {
                    pDialog.show();
                    final MyData prefs = new MyData(getContext());

                    String PushID = baseRef.child(getString(R.string.diet_off_req_ref)).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().getKey();
                    DietOffRequestClass myRequest = new DietOffRequestClass(
                            prefs.getData(MyData.NAME),
                            prefs.getData(MyData.ROOM_NO),
                            prefs.getData(MyData.ENROLLMENT_NO),
                            To, From, Reason,
                            Calendar.getInstance().getTime(),
                            false, PushID,prefs.getData(MyData.MOBILE),false);

                    baseRef.child(getString(R.string.diet_off_req_ref)).child(prefs.getData(MyData.ENROLLMENT_NO))
                            .child(PushID).setValue(myRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                 baseRef.child(getString(R.string.mess_days_left_ref))
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue((DaysLeft+DaysRequested));
                                details.setText(null);
                                ShowDialog("Successfull",3).show();
                                pDialog.dismiss();
                                ((TextView)(rootView.findViewById(R.id.to))).setText("Select Date");
                                ((TextView)(rootView.findViewById(R.id.from))).setText("Select Date");
                                ((rootView.findViewById(R.id.to))).setEnabled(false);


                            }
                            else {
                                pDialog.dismiss();
                                new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Error")
                                        .setContentText("Can't submit your request, please login again\nContact Admin if problem persisits")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                getActivity().finish();
                                                startActivity(new Intent(getContext(), LoginAcitivity.class));
                                            }
                                        });
                            }
                        }
                    });
                }
            }

        });




        return rootView;
    }

    private boolean isOkay(String to, String from, String reason) {
        boolean okay=true;

        if(to.equals("Select Date"))
        {
            okay=false;
            ShowDialog("Date Missing",4).setContentText("Please tell from when you want diet off").show();
    }
        else if(from.equals("Select Date"))
        {
            okay=false;
            ShowDialog("Date Missing",4).setContentText("Please tell until when you need diet off").show();
        }
        else if(reason.equals(""))
        {
            okay=false;
            ShowDialog("No Reason",4).setContentText("Please provide a reason for the diet of and it should be atleast 50 words").show();
        }
        else if(reason.length()<50)
        {
            okay=false;
            ShowDialog("Too Short",4).setContentText("Reason for diet off should be atleast 50 charactors").show();
        }

    return okay;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        String selectedDate = dayOfMonth + " " + getMonthForInt(month) + " " + year;

        if(!from)
        {
            Calendar c=Calendar.getInstance();
            FromDate=dayOfMonth;
            Month=month;
            c.set(year,month-1,dayOfMonth,0,0);
            choosenDate=c.getTime();

        ((TextView)rootView.findViewById(R.id.from)).setText(selectedDate);
            (rootView.findViewById(R.id.to)).setEnabled(true);
            from=!from;
        }
        else
        {
            if(Month!=month)
            DaysRequested=(30-FromDate)+dayOfMonth;
            else DaysRequested=dayOfMonth-FromDate;

            ((TextView)rootView.findViewById(R.id.to)).setText(selectedDate);
        }


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
