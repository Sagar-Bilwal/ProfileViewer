package com.example.sagar.pascolan;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sagar.pascolan.adapters.ProfileRecyclerAdapter;
import com.example.sagar.pascolan.model.Profile;
import com.example.sagar.pascolan.responses.UserResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity implements ProfileRecyclerAdapter.onItemClickListener {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    ProfileRecyclerAdapter profileRecyclerAdapter;
    ArrayList<Profile> Profiles=new ArrayList<>();
    ArrayList<Profile> sortedProfiles=new ArrayList<>();
    Boolean isScrolling=false;
    int currentItems,totalItems,scrollOutItems;
    LinearLayoutManager layoutManager;
    int pageNo=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        recyclerView=findViewById(R.id.profileRecyclerView);
        profileRecyclerAdapter=new ProfileRecyclerAdapter(this,sortedProfiles,this);
        progressBar=findViewById(R.id.progressbar);
        fetchProfiles();
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(profileRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(lastItemDisplaying())
                {
                    pageNo++;
                    fetchProfiles();
                }
            }
        });
    }

    private boolean lastItemDisplaying()
    {
        boolean answer=false;
        if(recyclerView.getAdapter().getItemCount()!=0)
        {
            int lastVisibleItem=((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if(lastVisibleItem==(recyclerView.getAdapter().getItemCount()-1))
            {
                answer= true;
            }
        }
        return answer;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.sort)
        {
            Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show();
            fetchSortedProfiles();
            profileRecyclerAdapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchSortedProfiles()
    {
        Toast.makeText(this, "Reached", Toast.LENGTH_SHORT).show();
        sortedProfiles.clear();
        for(int y=6;y>=0;y--)
        {
            if(Profiles.size()==0)
            {
                return;
            }
            for (int x = 0; x < Profiles.size(); x++)
            {
                if(Profiles.get(x).getPriority().equals(y + ""))
                {
                    sortedProfiles.add(Profiles.get(x));
                }
            }
        }
        profileRecyclerAdapter.notifyDataSetChanged();
        layoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
    }

    private void fetchProfiles()
    {
        Call<UserResponse> call=ApiClient.getInstance().getPascolanApi().getProfiles("/users.php?page="+pageNo) ;
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                UserResponse profiles=response.body();
                progressBar.setVisibility(View.GONE);
                if(profiles!=null) {
                    Profiles.addAll(profiles.getSampleUsers());
                    sortedProfiles.addAll(profiles.getSampleUsers());
                    profileRecyclerAdapter.notifyDataSetChanged();
                    layoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Log.d("error", t.getMessage());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ListActivity.this,"No Connection",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(final int position)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
        builder.setTitle("Delete Profile");
        builder.setMessage("Are you sure you want to delete this Profile.");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Profiles.remove(position);
                profileRecyclerAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
