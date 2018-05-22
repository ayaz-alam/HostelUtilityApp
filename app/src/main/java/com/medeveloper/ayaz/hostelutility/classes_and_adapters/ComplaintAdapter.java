package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;


/**
 * Created by ESIDEM jnr on 12/10/2016.
 */

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder>{

    private Context mContext;
    // private LayoutInflater mInflater;
    private ArrayList<Complaint> mDataSource;
    OnItemClickListener mItemClickListener;
    private int code;


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //each data item is just a string in this case
        public TextView complaintTitle;
        public TextView complaintDetails;
        public TextView complaintDate;
        public ImageView resolvedImage;
        public ImageView callButton;
        private TextView studentName,roomNo,staffName,staffContact;
        private TextView resolvedButton;
        public ViewHolder(View v) {
            super(v);

            complaintDetails = (TextView) v.findViewById(R.id.card_text);
            complaintTitle = (TextView) v.findViewById(R.id.card_title);
            resolvedImage = (ImageView) v.findViewById(R.id.resolve_image);
            complaintDate = (TextView) v.findViewById(R.id.card_date);
            callButton=v.findViewById(R.id.phone_the_staff);
            if(code==0)
            {
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
                Toast.makeText(mContext,"Call the Staff",Toast.LENGTH_LONG).show();
            else if(view==resolvedButton)
                Toast.makeText(mContext,"resolved",Toast.LENGTH_LONG).show();


            Log.d("Ayaz Alam","Item Clicked"+getItemCount());
            //TODO OnClick Item


        }





    }
    public interface OnItemClickListener{
        public void onItemClick(View view, int Position);
    }

    public void SetOnItemClickListner(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
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
        if(complaint.Resolved)
        {
            holder.resolvedImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_resolved));
        }
        else
        {
            holder.resolvedImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_waiting));
        }

        if(code!=0)
        {
            holder.studentName.setText(complaint.StudentName);
            holder.roomNo.setText(complaint.RoomNo);
            holder.staffName.setText("Staff Name");
            holder.staffContact.setText(complaint.StaffContact);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }
}



