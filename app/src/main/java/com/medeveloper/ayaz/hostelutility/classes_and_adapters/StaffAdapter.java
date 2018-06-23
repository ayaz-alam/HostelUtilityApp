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

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {

    private Context mContext;
    // private LayoutInflater mInflater;
    private ArrayList<StaffDetailsClass> mDataSource;
    StaffAdapter.OnItemClickListener mItemClickListener;



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //each data item is just a string in this case
        public TextView staffDepartment;
        public TextView staffName;
        public TextView staffContact;
        public TextView staffDateOfJoining;

        public ViewHolder(View v) {
            super(v);

            staffDepartment =v.findViewById(R.id.staff_department);
            staffName = v.findViewById(R.id.card_staff_name);
            staffContact = v.findViewById(R.id.card_staff_contact);
            staffDateOfJoining = v.findViewById(R.id.card_staff_join);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Log.d("Ayaz Alam", "Item Clicked" + getItemCount());
            //TODO OnClick Item


        }


    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int Position);
    }

    public void SetOnItemClickListner(final StaffAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public StaffAdapter(Context context, ArrayList<StaffDetailsClass> items) {
        mContext = context;
        mDataSource = items;


    }


    @Override
    public StaffAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_staff_details, parent, false);


        return new StaffAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(StaffAdapter.ViewHolder holder, int position) {

        // - get element from arraylist at this position
        // - replace the contents of the view with that element

        StaffDetailsClass staff = mDataSource.get(position);
        holder.staffDepartment.setText(staff.Department);
        holder.staffName.setText(staff.NameOfStaff);
        holder.staffContact.setText(staff.ContactNumber);
        holder.staffDateOfJoining.setText(staff.JoiningDate);
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }
}
