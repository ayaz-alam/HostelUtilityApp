package com.medeveloper.ayaz.hostelutility.student;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.NetRefClass;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class NetRefill extends Fragment {


    public NetRefill() {
        // Required empty public constructor
    }


    View rootView;
    SweetAlertDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.student_net_refill, container, false);
        pDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE);
        ((Button)rootView.findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=((EditText)rootView.findViewById(R.id.email)).getText().toString();
                String ID=((EditText)rootView.findViewById(R.id.id)).getText().toString();
                if(ID.equals(""))
                {
                    ((EditText)rootView.findViewById(R.id.id)).setError("Required");
                }
                else if (Email.equals(""))
                {
                    ((EditText)rootView.findViewById(R.id.email)).setError("Required");
                }
                else
                {
                    pDialog.show();
                    FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id))
                            .child("InternetRefills").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(new NetRefClass(ID,Email)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                ((EditText)rootView.findViewById(R.id.email)).setText(null);
                                ((EditText)rootView.findViewById(R.id.id)).setText(null);
                                pDialog.dismiss();
                                new SweetAlertDialog(getContext(),SweetAlertDialog.SUCCESS_TYPE).
                                        setTitleText("Successfull").show();

                            }

                        }
                    });
                }


            }
        });


        return rootView;
    }

}
