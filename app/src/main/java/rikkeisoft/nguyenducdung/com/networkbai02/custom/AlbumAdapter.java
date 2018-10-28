package rikkeisoft.nguyenducdung.com.networkbai02.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import rikkeisoft.nguyenducdung.com.networkbai02.R;
import rikkeisoft.nguyenducdung.com.networkbai02.model.Album;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>  {
    private List<Album> albums;
    private Context context;
    private ItemClick itemClick;

    public AlbumAdapter(List<Album> albums, Context context, ItemClick itemClick) {
        this.albums = albums;
        this.context = context;
        this.itemClick = itemClick;
    }

    public AlbumAdapter(List<Album> albums, Context context) {
        this.albums = albums;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.photo_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context)
                .load(albums.get(position).getThumbnailUrl())
                .into(holder.imgThumbnaiUrl);
        holder.tvTitle.setText(albums.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onClickItem(position);
                notifyItemChanged(position);
            }
        });
        return;
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgThumbnaiUrl;
        private TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnaiUrl = itemView.findViewById(R.id.img_thumbnaiurl);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
    public interface ItemClick {
        void onClickItem(int i);
    }
}
