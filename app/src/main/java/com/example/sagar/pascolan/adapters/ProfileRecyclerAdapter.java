package com.example.sagar.pascolan.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sagar.pascolan.R;
import com.example.sagar.pascolan.model.Profile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileRecyclerAdapter extends RecyclerView.Adapter<ProfileRecyclerAdapter.ProfileViewHolder>
{

    public interface onItemClickListener
    {
        void onClick(int position);
    }
    ProfileRecyclerAdapter.onItemClickListener listener;
    private ArrayList<Profile> profiles;
    private Context context;
    public ProfileRecyclerAdapter(Context context, ArrayList<Profile> profiles,ProfileRecyclerAdapter.onItemClickListener listener)
    {
        this.profiles = profiles;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view=layoutInflater.inflate(R.layout.profile_layout,parent,false);
        ProfileViewHolder viewHolder=new ProfileViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileViewHolder holder, int position)
    {
        Profile profile=profiles.get(position);
        holder.name.setText(profile.getName());
        if(profile.getVerified().equals("1"))
        {
            holder.verified.setChecked(true);
            holder.verified.setEnabled(false);
        }
        else
        {
            holder.verified.setChecked(false);
            holder.verified.setEnabled(false);
        }
        if(Picasso.get().load(profile.getUserImage())!=null) {
            Picasso.get().load(profile.getUserImage()).into(holder.profilePic);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder
    {
        View itemView;
        ImageView profilePic;
        TextView name;
        CheckBox verified;
        public ProfileViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            profilePic=itemView.findViewById(R.id.circleImageView);
            name=itemView.findViewById(R.id.userName);
            verified=itemView.findViewById(R.id.verified);
        }
    }
}
