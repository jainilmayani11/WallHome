package com.example.wallhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.wallhome.adapter.Adapter;
import com.example.wallhome.models.ImageModel;
import com.example.wallhome.models.SearchModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;


    private ArrayList<ImageModel> modelClassList;
    private RecyclerView recyclerView;
    Adapter adapter;
    CardView mTrending,mTechnology,mCar,mNature,mFlower,mDark,mBike,mGod;
    EditText editText;
    ImageButton search;
    Dialog dgLoading;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerview);
        mTrending = findViewById(R.id.trending);
        mTechnology = findViewById(R.id.technology);
        mCar = findViewById(R.id.car);
        mNature = findViewById(R.id.nature);
        mDark = findViewById(R.id.dark);
        mFlower = findViewById(R.id.flower);
        mBike = findViewById(R.id.bike);
        mGod = findViewById(R.id.god);


        editText = findViewById(R.id.edtSearch);
        search = findViewById(R.id.search);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);



      /*  navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                {
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    break;
                }
                case R.id.about:
                {
                    Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.share:
                {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "WallHome");
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                    //Toast.makeText(getApplicationContext(),"Share",Toast.LENGTH_LONG).show();
                    return true;
                }
                case R.id.rate:
                {
                    rateDialog = new Dialog(MainActivity.this);
                    rateDialog.setContentView(R.layout.custom_ratingbar);

                    if (rateDialog.getWindow()!=null){
                        rateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    rateDialog.show();
                }
                rateDialog.dismiss();
                Toast.makeText(MainActivity.this, "Thank You", Toast.LENGTH_SHORT).show();
                break;

            }
            return false;
        });*/





        dgLoading = new Dialog(this);
        dgLoading.setContentView(R.layout.custom_progreesbar);

        if (dgLoading.getWindow()!=null){
            dgLoading.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }



        modelClassList= new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        adapter = new Adapter(getApplicationContext(),modelClassList);
        recyclerView.setAdapter(adapter);
        findPhotos();

        mTrending.setOnClickListener(view -> {
            String query = "trending";
            getSearchImage(query);
        });

        mTechnology.setOnClickListener(view -> {
            String query = "technology";
            getSearchImage(query);
        });

        mCar.setOnClickListener(view -> {
            String query = "car";
            getSearchImage(query);
        });

        mNature.setOnClickListener(view -> {
            String query = "nature";
            getSearchImage(query);
        });

        mFlower.setOnClickListener(view -> {
            String query = "flower";
            getSearchImage(query);
        });

        mDark.setOnClickListener(view -> {
            String query = "dark";
            getSearchImage(query);
        });

        mBike.setOnClickListener(view -> {
            String query = "bike";
            getSearchImage(query);
        });

        mGod.setOnClickListener(view -> {
            String query = "god";
            getSearchImage(query);
        });


        search.setOnClickListener(view -> {
            String query= editText.getText().toString().trim().toLowerCase();
            if (query.isEmpty()){
                Toast.makeText(MainActivity.this, "Enter Something", Toast.LENGTH_SHORT).show();
            }
            else {
                getSearchImage(query);
            }
        });

        dgLoading.show();

    }

    private void getSearchImage(String query) {
        dgLoading.show();
        ApiUtilities.getApiInterface().getSearchImage(query,1,80).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(@NonNull Call<SearchModel> call, @NonNull Response<SearchModel> response) {
                dgLoading.dismiss();
                modelClassList.clear();
                if (response.isSuccessful()){
                    assert response.body() != null;
                    modelClassList.addAll(response.body().getPhotos());
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(MainActivity.this, "No able to get", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchModel> call, @NonNull Throwable t) {
                dgLoading.dismiss();
            }
        });

    }

    private void findPhotos() {

        dgLoading.show();
        modelClassList.clear();
        ApiUtilities.getApiInterface().getImage(1,80).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(@NonNull Call<SearchModel> call, @NonNull Response<SearchModel> response) {
                dgLoading.dismiss();
                if (response.isSuccessful()){
                    assert response.body() != null;
                    modelClassList.addAll(response.body().getPhotos());
                    adapter.notifyDataSetChanged();
                }
                else {

                    Toast.makeText(MainActivity.this, "No able to get", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchModel> call, @NonNull Throwable t) {
                dgLoading.dismiss();
            }

        });
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }
}