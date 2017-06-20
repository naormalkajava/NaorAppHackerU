package com.example.ericbell.myapplication;


import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RcyclerCardVIew extends Fragment implements YnetDataSource.OnYnetArrivedListener {
    RecyclerView rvCard;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_rcycler_card_view, container, false);
        rvCard = (RecyclerView) v.findViewById(R.id.rvCard);
        YnetDataSource.getYnet(this);


        return v;
    }

    @Override
    public void ynetArrived(List<YnetDataSource.Ynet> data) {
        if (data != null) {
            rvCard.setLayoutManager(new LinearLayoutManager(getContext()));
            rvCard.setAdapter(new RecyclerAdapter(getContext(), data));
        }

    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        Context context;
        LayoutInflater inflater;
        List<YnetDataSource.Ynet> data;

        public RecyclerAdapter(Context context, List<YnetDataSource.Ynet> data) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.item_ynet, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            YnetDataSource.Ynet ynet = data.get(position);
            String title = ynet.getTitle();
            String imageSource = ynet.getImageSource();
            String description = ynet.getDescription();
            String link = ynet.getLink();
            holder.tvTitle.setText(title);
            holder.tvDescription.setText(description);
            Picasso.with(context).load(imageSource).placeholder(R.drawable.error1).error(R.drawable.placeholder).into(holder.ivImage);

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivImage;
            TextView tvTitle;
            TextView tvDescription;

            public ViewHolder(View itemView) {
                super(itemView);
                ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        YnetDataSource.Ynet ynet = data.get(position);
                        String link = ynet.getLink();
                        FragmentActivity activity = (FragmentActivity) context;
                        activity.getSupportFragmentManager().beginTransaction().addToBackStack("error").replace(R.id.container, WebViewFragment.newInstance(link)).commit();
                    }
                });
            }
        }
    }

}
