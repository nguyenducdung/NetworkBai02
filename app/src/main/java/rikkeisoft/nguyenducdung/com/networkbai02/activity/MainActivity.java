package rikkeisoft.nguyenducdung.com.networkbai02.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rikkeisoft.nguyenducdung.com.networkbai02.custom.AlbumAdapter;
import rikkeisoft.nguyenducdung.com.networkbai02.R;
import rikkeisoft.nguyenducdung.com.networkbai02.model.Album;
import rikkeisoft.nguyenducdung.com.networkbai02.retro.ApiUntil;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private List<Album> albums;
    private RecyclerView rcList;
    private ImageView imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        checkInternetConnection();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcList.setLayoutManager(linearLayoutManager);
        rcList.setHasFixedSize(true);

        ApiUntil.getServiceClass().getAllPost().enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (response.isSuccessful()) {
                    final List<Album> albums = response.body();
                    Log.d(TAG, "Returned count " + albums.size());
                    AlbumAdapter albumAdapter = new AlbumAdapter(albums, getApplicationContext(), new AlbumAdapter.ItemClick() {
                        @Override
                        public void onClickItem(int i) {
                            rcList.setVisibility(View.GONE);
                            imgUrl.setVisibility(View.VISIBLE);
                            Glide.with(getApplicationContext())
                                    .load(albums.get(i).getUrl())
                                    .into(imgUrl);
                        }
                    });
                    rcList.setAdapter(albumAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Log.d(TAG, "error loading from API");
            }
        });

    }

    private void init() {
        rcList = (RecyclerView) findViewById(R.id.rc_list);
        imgUrl = (ImageView) findViewById(R.id.img_url);
    }

    private boolean checkInternetConnection() {
        ConnectivityManager connManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            Toast.makeText(this, "No default network is currently active", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!networkInfo.isConnected()) {
            Toast.makeText(this, "Network is connected", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!networkInfo.isAvailable()) {
            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(this, "Network OK", Toast.LENGTH_LONG).show();
        return true;
    }

}
