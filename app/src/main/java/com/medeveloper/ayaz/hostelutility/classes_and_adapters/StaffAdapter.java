package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medeveloper.ayaz.hostelutility.R;
import java.util.ArrayList;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder>{

    private Context mContext;
    // private LayoutInflater mInflater;
    private ArrayList<StaffDetailsClass> mDataSource;
    OnItemClickListener mItemClickListener;


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //each data item is just a string in this case
        public TextView staffName;
        public TextView staffContact;
        public TextView staffJoinDate;
        private TextView staffDepartment;

        public ViewHolder(View v) {
            super(v);

            staffName = (TextView) v.findViewById(R.id.card_staff_name);
            staffContact = (TextView) v.findViewById(R.id.card_staff_contact);
            staffJoinDate = (TextView) v.findViewById(R.id.card_staff_join);
            staffDepartment=v.findViewById(R.id.staff_department);

            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            //OnClick Listener
        }





    }
    public interface OnItemClickListener{
        public void onItemClick(View view, int Position);
    }

    public void SetOnItemClickListner(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }
    public StaffAdapter(Context context, ArrayList<StaffDetailsClass> items) {
        mContext = context;
        mDataSource = items;

        // mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_staff_details, parent, false);

        return new ViewHolder(itemView);
    }






    @Override
    public void onBindViewHolder(StaffAdapter.ViewHolder holder, int position) {

        // - get element from arraylist at this position
        // - replace the contents of the view with that element

        StaffDetailsClass staffDetails = mDataSource.get(position);
        holder.staffName.setText(staffDetails.NameOfStaff);
        holder.staffContact.setText(staffDetails.ContactNumber);
        holder.staffJoinDate.setText(staffDetails.JoiningDate);
        holder.staffDepartment.setText(staffDetails.Department);

    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }
}




