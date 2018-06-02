package com.example.darthvader.blooddonation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {
    List<User> userList;
    List<User> userListFiltered;
    List<User> userListFiltered1;
    Context context;
    View view;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPhone, tvBg;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvBg = itemView.findViewById(R.id.tv_bg);
            tvPhone = itemView.findViewById(R.id.tv_phone);
        }
    }

    public RecyclerViewAdapter(Context context, List<User> userList) {
        this.userList = userList;
        this.context = context;
        this.userListFiltered = userList;
        this.userListFiltered1 = userList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final User user = userList.get(position);
        holder.tvName.setText(user.getUsername());
        holder.tvBg.setText(user.getBloodGroup());
        holder.tvPhone.setText(Long.toString(user.getPhNo()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserActivity.class);
                intent.putExtra("name", user.getUsername());
                intent.putExtra("email", user.getEmail());
                intent.putExtra("phone", user.getPhNo());
                intent.putExtra("bg", user.getBloodGroup());
                intent.putExtra("pincode", user.getPincode());
                intent.putExtra("city", user.getCity());
                intent.putExtra("state", user.getState());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public void onClick(View view) {

    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    userListFiltered = userListFiltered1;
                } else {
                    List<User> list = new ArrayList<>();
                    for (User user : userList) {
                        if (user.getBloodGroup().toLowerCase().contains(charString.toLowerCase())) {
                            list.add(user);
                        }
                    }
                    userListFiltered = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = userListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userList = (List<User>)filterResults.values;
                notifyDataSetChanged();
            }
        };

    }
}
