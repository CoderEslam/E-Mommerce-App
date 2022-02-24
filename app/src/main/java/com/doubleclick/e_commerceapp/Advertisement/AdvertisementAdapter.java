package com.doubleclick.e_commerceapp.Advertisement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.e_commerceapp.Model.Advertisement;
import com.doubleclick.e_commerceapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.doubleclick.e_commerceapp.Advertisement.QDB_Advertisement.advertisements;

public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.AdvertisementViewHolder> {

    List<Advertisement> advertisementList = advertisements;

    @NonNull
    @NotNull
    @Override
    public AdvertisementAdapter.AdvertisementViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_advertisement,parent,false);
        return new AdvertisementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdvertisementAdapter.AdvertisementViewHolder holder, int position) {
        Advertisement advertisement  =advertisementList.get(position);
        holder.NameAdmin.setText(advertisement.getAdminName());
        holder.AdminID.setText(advertisement.getIdAdmin());
        holder.ProductID.setText(advertisement.getIDProducts());
        holder.TypeAd.setText(advertisement.getTypeAd());
        Glide.with(holder.itemView.getContext()).load(advertisement.getImageAd()).placeholder(R.drawable.parson).into(holder.imageAdvertisement);

    }

    @Override
    public int getItemCount() {
        return advertisementList.size();
    }

    public class AdvertisementViewHolder extends RecyclerView.ViewHolder {

        private TextView NameAdmin ,TypeAd,ProductID ,AdminID;
        private CircleImageView imageAdvertisement;
        public AdvertisementViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            NameAdmin = itemView.findViewById(R.id.NameAdmin);
            TypeAd = itemView.findViewById(R.id.TypeAd);
            ProductID = itemView.findViewById(R.id.ProductID);
            AdminID = itemView.findViewById(R.id.AdminID);
            imageAdvertisement = itemView.findViewById(R.id.imageAdvertisement);

        }


    }
}
