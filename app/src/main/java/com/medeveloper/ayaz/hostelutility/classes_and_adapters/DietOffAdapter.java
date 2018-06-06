package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.app.PendingIntent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.medeveloper.ayaz.hostelutility.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;

public class DietOffAdapter extends RecyclerView.Adapter<DietOffAdapter.ViewHolder> {

    private Context mContext;
    // private LayoutInflater mInflater;
    private ArrayList<DietOffRequestClass> mDataSource;
    DietOffAdapter.OnItemClickListener mItemClickListener;



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //each data item is just a string in this case
        public TextView studentName,RoomNo,EnrollmentNo,To,From,Reason,Time;
        private Button Accept,Decline;


        public ViewHolder(View v) {
            super(v);

            studentName =v.findViewById(R.id.diet_off_name);
            RoomNo = v.findViewById(R.id.diet_off_room);
            EnrollmentNo = v.findViewById(R.id.diet_off_enroll);
            To=v.findViewById(R.id.diet_off_to);
            From=v.findViewById(R.id.diet_off_from);
            Reason=v.findViewById(R.id.diet_off_reason);
            Time=v.findViewById(R.id.diet_off_date);
            Accept=v.findViewById(R.id.accept_button);
            Decline=v.findViewById(R.id.decline_button);
            Accept.setOnClickListener(this);
            Decline.setOnClickListener(this);


            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final DietOffRequestClass request=mDataSource.get(getAdapterPosition());
            if(view==Accept)
            {

                new SweetAlertDialog(mContext,SweetAlertDialog.WARNING_TYPE).setTitleText("Accept ?")
                        .setContentText("Are you sure ?\nYou want to accept the request")
                        .setConfirmText("Accept")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(final SweetAlertDialog sweetAlertDialog) {
                                DietOffRequestClass dietOffReq=mDataSource.get(getAdapterPosition());
                                FirebaseDatabase.getInstance().getReference(mContext.getString(R.string.college_id))
                                        .child(mContext.getString(R.string.hostel_id)).child(mContext.getString(R.string.diet_off_req_ref))
                                        .child(new MyData(mContext).getData(MyData.ENROLLMENT_NO))
                                        .child(dietOffReq.requestID)
                                        .child("accepted").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            sweetAlertDialog.dismiss();
                                            sendSMSMessage(request.studentMobile,"Your request for diet off has been accepted by the warden" +
                                                    "\nRequest ID: "+request.requestID+" \nRequest Date"+request.time.getTime()+"\n" +"FROM: "+request.From
                                                            +"\nTO: "+request.To,mContext);

                                            sendSMSMessage(mContext.getString(R.string.mess_contractor_number),"Request for "+
                                            request.Name+" of Hostel "+mContext.getString(R.string.hostel_id)+" has been accepted by the warden" +
                                                    "\nRequest ID: "+request.requestID+" \nRequest Date"+request.time.getTime()+"\n" +
                                                    "FROM: "+request.From
                                            +"\nTO: "+request.To,mContext
                                            );
                                            new SweetAlertDialog(mContext,SweetAlertDialog.SUCCESS_TYPE).setTitleText("Successfull").show();
                                        }
                                    }
                                });
                                Log.d("Ayaz","Item Clicked: "+mDataSource.get(getAdapterPosition()).requestID);
                            }
                        })
                        .setCancelText("Cancel")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                sweetAlertDialog.dismiss();
                            }
                        })
                        .show();

            }

            else if(view==Decline)
            {
                final DietOffRequestClass dietOffReq=mDataSource.get(getAdapterPosition());
                        new SweetAlertDialog(mContext,SweetAlertDialog.WARNING_TYPE).setTitleText("Decline?")
                                .setContentText("Are you sure you want to cancel this request")
                                .setConfirmText("Yes, Cancel")
                                .setCancelText("Back")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(final SweetAlertDialog sweetAlertDialog) {
                                        FirebaseDatabase.getInstance().getReference(mContext.getString(R.string.college_id))
                                                .child(mContext.getString(R.string.hostel_id)).child(mContext.getString(R.string.diet_off_req_ref))
                                                .child(new MyData(mContext).getData(MyData.ENROLLMENT_NO))
                                                .child(dietOffReq.requestID)
                                                .child("seen").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    sendSMSMessage(request.studentMobile,"Your request for diet off has been rejected by the warden" +
                                                            "\nRequest ID: "+request.requestID+" \nRequest Date"+request.time.getTime()+"\n" +"FROM: "+request.From
                                                            +"\nTO: "+request.To,mContext);
                                                    sweetAlertDialog.dismiss();
                                                }
                                                else Toast.makeText(mContext,"Some error occured",Toast.LENGTH_SHORT).show();
                                            }
                                        });



                                    }
                                })
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();
            }



        }


    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int Position);
    }

    public void SetOnItemClickListner(final DietOffAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public DietOffAdapter(Context context, ArrayList<DietOffRequestClass> items) {
        mContext = context;
        mDataSource = items;


    }


    @Override
    public DietOffAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_diet_off, parent, false);


        return new DietOffAdapter.ViewHolder(itemView);
    }


    protected void sendSMSMessage(String phoneNo, String message, Context context) {

        SmsManager sm = SmsManager.getDefault();
        ArrayList<String> parts =sm.divideMessage(message);
        int numParts = parts.size();

        ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();

        sm.sendMultipartTextMessage(phoneNo,null,parts,null,null);

    }


    @Override
    public void onBindViewHolder(final DietOffAdapter.ViewHolder holder, int position) {

        // - get element from arraylist at this position
        // - replace the contents of the view with that element

        DietOffRequestClass notice = mDataSource.get(position);

        if(notice.accepted)
        {
            holder.Accept.setText("Accepted");
            holder.Accept.setEnabled(false);
            holder.Decline.setVisibility(View.GONE);

        }
        else if(notice.seen){
            holder.Decline.setText("Declined");
            holder.Accept.setVisibility(View.GONE);
            holder.Decline.setEnabled(false);
        }

        holder.studentName.setText(""+notice.Name);
        holder.RoomNo.setText("Room: "+notice.RoomNo);
        holder.EnrollmentNo.setText("En. no: "+notice.EnrollmentNo);
        holder.Time.setText((""+notice.time.getTime()));
        holder.From.setText("From date:   "+notice.From);
        holder.To.setText("Upto date:  "+notice.To);
        holder.Reason.setText("\nReason:\n"+notice.Reason);




    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }
}
