package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
        public TextView Name,RoomNo,EnrollmentNo,To,From,Reason,Time;
        private Button Accept,Decline;


        public ViewHolder(View v) {
            super(v);

            Name =v.findViewById(R.id.diet_off_name);
            RoomNo = v.findViewById(R.id.diet_off_room);
            EnrollmentNo = v.findViewById(R.id.diet_off_enroll);
            To=v.findViewById(R.id.diet_off_to);
            From=v.findViewById(R.id.diet_off_from);
            Reason=v.findViewById(R.id.diet_off_reason);
            Time=v.findViewById(R.id.diet_off_date);
            Accept=v.findViewById(R.id.accept_button);
            Decline=v.findViewById(R.id.decline_button);


            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {



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
                .inflate(R.layout.card_notice, parent, false);


        return new DietOffAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final DietOffAdapter.ViewHolder holder, int position) {

        // - get element from arraylist at this position
        // - replace the contents of the view with that element

        DietOffRequestClass notice = mDataSource.get(position);


        holder.Name.setText(notice.Name);
        holder.RoomNo.setText("Room: "+notice.RoomNo);
        holder.EnrollmentNo.setText("En. no: "+notice.EnrollmentNo);
        holder.Time.setText((""+notice.time.getTime()));
        holder.From.setText("From date:"+notice.From);
        holder.To.setText("Upto date:"+notice.To);
        holder.Reason.setText("Reason \n:"+notice.From);
        holder.From.setText("From date:"+notice.From);
        holder.Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(mContext,SweetAlertDialog.SUCCESS_TYPE).setTitleText("Accepted").show();
               /* FirebaseDatabase.getInstance().getReference(mContext.getString(R.string.college_id)).
                child(mContext.getString(R.string.hostel_id)).child(mContext.getString(R.string.diet_off_complaints_ref));
*/

            }
        });

        holder.Decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(mContext,SweetAlertDialog.WARNING_TYPE).setTitleText("Decline?").show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }
}
