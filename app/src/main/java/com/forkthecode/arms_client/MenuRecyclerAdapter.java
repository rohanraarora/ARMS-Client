package com.forkthecode.arms_client;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;
import com.squareup.picasso.Picasso;

/**
 * Created by Rohan on 3/10/2016.
 */
public class MenuRecyclerAdapter extends FirebaseRecyclerAdapter<MenuItem,MenuRecyclerAdapter.MenuViewHolder> {

    static ItemClickListener mListener;
    Context mContext;

    public interface ItemClickListener{
        void onClick(int position);
    }

    public MenuRecyclerAdapter(Context context,ItemClickListener listener,Class<MenuItem> modelClass, int modelLayout, Class<MenuViewHolder> viewHolderClass, Query ref){
        super(modelClass,modelLayout,viewHolderClass,ref);
        mListener = listener;
        mContext = context;
    }
    @Override
    protected void populateViewHolder(MenuViewHolder viewHolder, MenuItem model, int position) {
        viewHolder.getNameTextView().setText(model.getName());
        viewHolder.getCategoryTextView().setText(model.getCategory());
        viewHolder.getPriceTextView().setText(model.getPrice());
        Picasso.with(mContext).load(model.getImageUrl()).into(viewHolder.getImageView());
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            mListener.onClick(position);
        }

        private ImageView imageView;
        private TextView nameTextView;
        private TextView priceTextView;
        private TextView categoryTextView;

        public MenuViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            imageView = (ImageView)view.findViewById(R.id.rowMenuImageView);
            nameTextView = (TextView)view.findViewById(R.id.rowMenuNameTextView);
            priceTextView = (TextView)view.findViewById(R.id.rowMenuPriceTextView);
            categoryTextView = (TextView)view.findViewById(R.id.rowMenuCategory);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getCategoryTextView() {
            return categoryTextView;
        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public TextView getPriceTextView() {
            return priceTextView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

    }
}
