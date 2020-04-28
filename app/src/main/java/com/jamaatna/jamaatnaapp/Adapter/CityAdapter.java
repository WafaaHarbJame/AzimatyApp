package com.jamaatna.jamaatnaapp.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jamaatna.jamaatnaapp.Model.DataCallback;
import com.jamaatna.jamaatnaapp.Model.SubItem;
import com.jamaatna.jamaatnaapp.R;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.SubItemViewHolder> {

    Context context;
    int selectedPosition = 0;
    private List<SubItem> subItemList;
    DataCallback dataCallback;

    public CityAdapter( Context context,List<SubItem> subItemList, DataCallback dataCallback) {
        this.subItemList = subItemList;
        this.context = context;
        this.dataCallback=dataCallback;

    }


    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubItemViewHolder subItemViewHolder, final int i) {
        final SubItem subItem = subItemList.get(i);
        subItemViewHolder.tvSubItemTitle.setText(subItem.getSubItemTitle());


        if (selectedPosition == i) {
            subItemViewHolder.tvSubItemTitle.setBackground(ContextCompat.getDrawable(context, R.drawable.citystyle));
            subItemViewHolder.tvSubItemTitle.setTextColor(ContextCompat.getColor(context, R.color.white));

        } else {
            subItemViewHolder.tvSubItemTitle.setBackground(null);
            subItemViewHolder.tvSubItemTitle.setTextColor(ContextCompat.getColor(context, R.color.greycity));


        }


    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }

    class SubItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubItemTitle;

        SubItemViewHolder(View itemView) {
            super(itemView);
            tvSubItemTitle = itemView.findViewById(R.id.tv_sub_item_title);

            itemView.setOnClickListener(
                    new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SubItem subItem=subItemList.get(getAdapterPosition());
                    selectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    if(dataCallback!=null){
                        dataCallback.dataResult(subItem,"Ameer",true);
                    }
                }
            });
        }
    }
}
