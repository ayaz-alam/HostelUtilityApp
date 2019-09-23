package com.code_base_update.view.adapters;

import android.content.Context;

import androidx.cardview.widget.CardView;

import com.medeveloper.ayaz.hostelutility.R;

import java.util.List;

public class SingleCheckBoxAdapter extends BaseRecyclerAdapter<String> {

    private Context context;
    public SingleCheckBoxAdapter(Context context, int layoutResId, List<String> data) {
        super(context, layoutResId, data);
        this.context =context;
    }

    @Override
    void bindData(BaseViewHolder viewHolder, String item, int position) {
        viewHolder.setText(R.id.tv_problem_title, item);
        CardView cardView = viewHolder.itemView.findViewById(R.id.card_background);

        if(getSelectedPosition()==position){
            viewHolder.setTextColor(R.id.tv_problem_title,R.color.white);
            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.light_green));
        }else{
            viewHolder.setTextColor(R.id.tv_problem_title,R.color.card_default_text_color);
            //viewHolder.setBackgroundColor(R.id.card_background,R.color.card_grey_background);
            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.card_grey_background));
        }
    }

    @Override
    void updateDataOnTouch(int position) {
        notifyDataSetChanged();
    }

}
