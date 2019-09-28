package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.student.PhotoViewer;
import com.medeveloper.ayaz.hostelutility.utility.DateUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NoticeClassAdapter extends RecyclerView.Adapter<NoticeClassAdapter.ViewHolder> {

    private Context mContext;
    // private LayoutInflater mInflater;
    private ArrayList<NoticeClass> mDataSource;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //each data item is just a string in this case
        TextView noticeTitle;
        TextView noticeBody;
        TextView noticeDate;
        TextView noticeGenerator;
        ImageView noticeImage;
        ProgressBar mProgressBar;
        TextView expand_colapse;
        private boolean expanded=false;

        public ViewHolder(View v) {
            super(v);

            noticeTitle =v.findViewById(R.id.notice_title);
            noticeBody = v.findViewById(R.id.notice_body);
            noticeDate = v.findViewById(R.id.notice_date);
            noticeGenerator=v.findViewById(R.id.generated_by);
            noticeImage = v.findViewById(R.id.image_notice);
            mProgressBar=v.findViewById(R.id.photo_loading);
            expand_colapse=v.findViewById(R.id.expand_colapse);
            expand_colapse.setOnClickListener(this);
            noticeImage.setOnClickListener(this);


            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(view==expand_colapse) {
                        if (expanded) {
                            noticeBody.setMaxLines(2);
                            expand_colapse.setText("Read more..");
                        } else {
                            noticeBody.setMaxLines(100);
                            expand_colapse.setText("Read less..");
                        }
                        expanded = !expanded;
            }
            else if(view==noticeImage) {

                Intent in1 = new Intent(mContext, PhotoViewer.class);
                in1.putExtra("NoticeTitle",mDataSource.get(getAdapterPosition()).noticeTitle);
                in1.putExtra("NoticeBody",mDataSource.get(getAdapterPosition()).noticeBody);
                in1.setData(Uri.parse(mDataSource.get(getAdapterPosition()).photoUrl));
                mContext.startActivity(in1);
            }


        }


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
        holder.noticeDate.setText((""+ DateUtils.getDate(notice.noticeDate)));
        if(notice.noticeBody.length()>80)
        holder.expand_colapse.setVisibility(View.VISIBLE);
        else holder.expand_colapse.setVisibility(View.GONE);


        //if Image is present then load it from internet and display it.
        if(notice.photoUrl!=null)
        {
            holder.noticeImage.setVisibility(View.INVISIBLE);
            Picasso.get().
                    load(notice.photoUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_notdatafound)
                    .fit()
                    .into(holder.noticeImage, new Callback() {
                        @Override
                        public void onSuccess() {

                            holder.noticeImage.setVisibility(View.VISIBLE);
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
