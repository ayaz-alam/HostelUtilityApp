package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.medeveloper.ayaz.hostelutility.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NoticeClassAdapter extends RecyclerView.Adapter<NoticeClassAdapter.ViewHolder> {

    private Context mContext;
    // private LayoutInflater mInflater;
    private ArrayList<NoticeClass> mDataSource;
    NoticeClassAdapter.OnItemClickListener mItemClickListener;



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //each data item is just a string in this case
        public TextView noticeTitle;
        public TextView noticeBody;
        public TextView noticeDate;
        public TextView noticeGenerator;
        public ImageView noticeImage;
        public ProgressBar mProgressBar;

        public ViewHolder(View v) {
            super(v);

            noticeTitle =v.findViewById(R.id.notice_title);
            noticeBody = v.findViewById(R.id.notice_body);
            noticeDate = v.findViewById(R.id.notice_date);
            noticeGenerator=v.findViewById(R.id.generated_by);
            noticeImage = v.findViewById(R.id.image_notice);
            mProgressBar=v.findViewById(R.id.photo_loading);


            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {



        }


    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int Position);
    }

    public void SetOnItemClickListner(final NoticeClassAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public NoticeClassAdapter(Context context, ArrayList<NoticeClass> items) {
        mContext = context;
        mDataSource = items;


    }


    @Override
    public NoticeClassAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_notice, parent, false);


        return new NoticeClassAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final NoticeClassAdapter.ViewHolder holder, int position) {

        // - get element from arraylist at this position
        // - replace the contents of the view with that element

        NoticeClass notice = mDataSource.get(position);


        holder.noticeTitle.setText(notice.noticeTitle);
        holder.noticeBody.setText(notice.noticeBody);
        holder.noticeGenerator.setText("by: "+notice.noticeGenerator);
        holder.noticeDate.setText((""+notice.noticeDate.getTime()));


        //if Image is present then load it from internet and display it.
        if(notice.photoUrl!=null)
        {
            Picasso.get().
                    load(notice.photoUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_face_white_24dp)
                    .fit()
                    .into(holder.noticeImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.mProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            holder.mProgressBar.setVisibility(View.GONE);
                            holder.noticeImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.error_circle));
                        }
                    });

        }
        else {
            holder.noticeImage.setVisibility(View.GONE);
            holder.mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }
}
