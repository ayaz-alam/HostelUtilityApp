package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medeveloper.ayaz.hostelutility.R;
import java.util.ArrayList;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    private Context mContext;
    // private LayoutInflater mInflater;
    private ArrayList<StudentDetailsClass> mDataSource;
    StaffAdapter.OnItemClickListener mItemClickListener;



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //each data item is just a string in this case
        public TextView studentName;
        public TextView studentContact;
        public TextView studentRoom;
        public TextView studentClass;

        public ViewHolder(View v) {
            super(v);

            studentName =v.findViewById(R.id.card_student_name);
            studentRoom = v.findViewById(R.id.student_room_no);
            studentContact = v.findViewById(R.id.card_student_contact);
            studentClass = v.findViewById(R.id.card_student_class);
            studentContact.setOnClickListener(this);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(view==studentContact)
            {
                StudentDetailsClass student=mDataSource.get(getAdapterPosition());
                String Phone = student.MobileNo;

                String uri = "tel:" + Phone.trim() ;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                mContext.startActivity(intent);
            }



        }


    }


    public StudentListAdapter(Context context, ArrayList<StudentDetailsClass> items) {
        mContext = context;
        mDataSource = items;


    }


    @Override
    public StudentListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_student_list, parent, false);


        return new StudentListAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(StudentListAdapter.ViewHolder holder, int position) {

        // - get element from arraylist at this position
        // - replace the contents of the view with that element

        StudentDetailsClass student = mDataSource.get(position);


        holder.studentName.setText(student.Name);
        holder.studentRoom.setText(student.RoomNo);
        holder.studentContact.setText(student.MobileNo+" (Call)");
        holder.studentClass.setText(student.Branch+" "+student.Year+" Yr.");
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }
}
