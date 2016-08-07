package com.example.c.photogallery;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by c on 2016-08-07.
 */
public class PhotoGalleryFragment extends Fragment {

    RecyclerView mPhotoRecyclerView;
    private ArrayList<GalleryItem> mItems = new ArrayList<GalleryItem>();

    public static PhotoGalleryFragment newInstance() {
        PhotoGalleryFragment fragment = new PhotoGalleryFragment();
        return fragment;
    }

    //키:61d85f40793d1fef143ada94ecc2bdd4
    class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<GalleryItem>> {
        @Override
        protected void onPostExecute(ArrayList<GalleryItem> galleryItems) {
            super.onPostExecute(galleryItems);
            mItems = galleryItems;
            //네트워크로 데이터를.. 다받아왔을때 처리 하기 때문에 메소드로 정리한다
            setupAdapter();
        }

        @Override
        protected ArrayList<GalleryItem> doInBackground(Void... voids) {
            return new FlickrFetchr().fetchItems();

        }
    }

    class PhotoHolder extends RecyclerView.ViewHolder {
        private ImageView mItemImageView;

        public PhotoHolder(View itemView) {
            super(itemView);
            mItemImageView = (ImageView) itemView;
        }

        public void bindDrawable(Drawable drawable) {
            mItemImageView.setImageDrawable(drawable);
        }


//        public void bindGalleryItem(GalleryItem item) {
//
//        }
    }

    class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        private ArrayList<GalleryItem> mGalleryItems;

        public PhotoAdapter(ArrayList<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.gallery_item,parent,false);
            return new PhotoHolder(v);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {
            GalleryItem item = mGalleryItems.get(position);
            Drawable d= getResources().getDrawable(R.mipmap.ic_launcher);
            holder.bindDrawable(d);
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchItemsTask().execute();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        mPhotoRecyclerView = (RecyclerView) v.findViewById(R.id.photo_gallery_recyclerview);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setupAdapter();
        return v;
    }

    private void setupAdapter() {
        //다른곳에서호출되기 때문에 메소드로 만들었고 , 다른곳에 호출시 프레그먼트가 add 되었는지 확인뒤 아답터를.. 만들어준다
        if (isAdded()) {
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }
}
