package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.medeveloper.ayaz.hostelutility.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;


/**
 * Created by Ayaz Alam on 12/05/2018.
 */

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<Complaint> mDataSource;
    private int code;


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView complaintTitle;
        TextView complaintDetails;
        TextView complaintDate;
        ImageView resolvedImage;
        ImageView callButton;
        private TextView studentName,roomNo,staffName,staffContact;
        private TextView resolvedButton;
        ViewHolder(View v) {
            super(v);

            complaintDetails = v.findViewById(R.id.card_text);
            complaintTitle = v.findViewById(R.id.card_title);
            resolvedImage = v.findViewById(R.id.resolve_image);
            complaintDate = v.findViewById(R.id.card_date);
            callButton= v.findViewById(R.id.phone_the_staff);
            if(code==0) {
                resolvedButton=v.findViewById(R.id.resolved_button);
                resolvedButton.setOnClickListener(this);
            }
            if(code!=0)
            {
                studentName=v.findViewById(R.id.card_student_name);

                roomNo=v.findViewById(R.id.card_student_room);

                staffName=v.findViewById(R.id.card_staff_name);

                staffContact=v.findViewById(R.id.card_staff_contact);
            }



            callButton.setOnClickListener(this);

            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {

            if(view==callButton)
            {
                Complaint complaint=mDataSource.get(getAdapterPosition());
                String Phone = complaint.StaffContact;

                String uri = "tel:" + Phone.trim() ;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                 mContext.startActivity(intent);

            }
            else if(view==resolvedButton)
            {

               SweetAlertDialog alrt= new SweetAlertDialog(mContext,SweetAlertDialog.WARNING_TYPE).setTitleText("Are you sure?")
                        .setContentText("Are you sure that you want to mark this complaint as 'Resolved'")
                        .setConfirmText("Confirm").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                         resolveComplaint();
                    }
                });
               alrt.setCancelable(true);
               alrt.show();


            }
        }












        private void resolveComplaint() {
            String complaintID=mDataSource.get(getAdapterPosition()).complaintUID;
            FirebaseDatabase.getInstance().getReference(mContext.getString(R.string.college_id))
                    .child(mContext.getString(R.string.hostel_id))
                    .child(mContext.getString(R.string.complaint_ref))
                    .child(new MyData(mContext).getData(MyData.ENROLLMENT_NO))
                    .child(complaintID)
                    .child("Resolved")
                    .setValue(true)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                    new SweetAlertDialog(mContext,SweetAlertDialog.SUCCESS_TYPE).
                                            setTitleText("Successfull").
                                            show();


                        }
                    });


        }


    }

    public ComplaintAdapter(Context context, ArrayList<Complaint> items,int code) {
        mContext = context;
        mDataSource = items;
        this.code=code;
        // mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(code==0) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_complaint_student, parent, false);
        }
        else
        {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_complaint_official, parent, false);
        }

        return new ViewHolder(itemView);
    }






    @Override
    public void onBindViewHolder(ComplaintAdapter.ViewHolder holder, int position) {

        // - get element from arraylist at this position
        // - replace the contents of the view with that element

        Complaint complaint = mDataSource.get(position);



        holder.complaintTitle.setText(complaint.Field);
        holder.complaintDetails.setText(complaint.ComplaintDescription);
        holder.complaintDate.setText(complaint.ComplaintDate);

        if(code==0)
        {
            if(complaint.Resolved)
            {
                holder.resolvedImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_done));
                holder.resolvedButton.setEnabled(false);
                holder.resolvedButton.setText("Resolved");
                holder.resolvedButton.setClickable(false);
                holder.resolvedButton.setBackground(mContext.getDrawable(R.drawable.success_green_button));

            }
            else
            {

                holder.resolvedButton.setText("Resolve ? ");
                holder.resolvedButton.setBackground(mContext.getDrawable(R.drawable.pending_button));
            }
        }
        if(complaint.Resolved)
        {
            holder.resolvedImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_done));

        }
        else
        {

            holder.resolvedImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cancel));
        }

        if(code!=0)
        {
            holder.studentName.setText(complaint.StudentName);
            holder.roomNo.setText(complaint.RoomNo);
            //TODO Something to do with the staff contact
            holder.staffName.setText("Staff contact");
            holder.staffContact.setText(complaint.StaffContact);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }
}



