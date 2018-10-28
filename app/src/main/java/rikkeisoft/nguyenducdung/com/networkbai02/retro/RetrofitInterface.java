package rikkeisoft.nguyenducdung.com.networkbai02.retro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import rikkeisoft.nguyenducdung.com.networkbai02.model.Album;

public interface RetrofitInterface {
    @GET("photos")
    public Call<List<Album>> getAllPost();
}
