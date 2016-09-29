/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.app.recyclerview;

import java.util.List;

import com.app.glfancycoverflow.R;
import com.app.model.Product;
import com.app.recyclerview.listener.onItemClickListener;
import com.gl.glfancycoverflow.UILApplication;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private List<Product> mDataSet;
    private static onItemClickListener mItemClickListener;
    private ImageLoader mImageLoader;
    private boolean blDelete=false;
    
    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public CustomAdapter(List<Product> dataSet,ImageLoader imageLoader) {
        mDataSet = dataSet;
        mImageLoader = imageLoader;
    }

    public void setonItemClickListener( onItemClickListener onItemClickListener){
    	mItemClickListener = onItemClickListener;
    }
    
    public void addItem(Product product){
    	mDataSet.add(product);
    	notifyDataSetChanged();
    }
    
    public void deleteItem(int position){
    	if(position>=0&&position<mDataSet.size()){
	    	mDataSet.remove(position);
	    	notifyItemRemoved(position);
    	}
    }
    
    public void addItemRange(List<Product>products ,int startIndex){
    	int len = products.size();
    	for(int i=0;i<len;i++){
    		mDataSet.add(startIndex+i,products.get(i));
    	}
    	notifyDataSetChanged();
    }
    
    
    public void deleteItemRange(int startIndex,int count){
    	for(int i=startIndex;i<count;i++){
    		mDataSet.remove(i);
    	}
    	notifyItemRangeRemoved(startIndex,count);
    }
    
    public void  refreshDeleteBtn( boolean blDelete){
    	this.blDelete =blDelete;
    	notifyDataSetChanged();
    }
    
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        viewHolder.getTextView().setText(mDataSet.get(position).getProName()); 
        mImageLoader.displayImage(mDataSet.get(position).getProUrl(), viewHolder.getUrlImv(), 
        		UILApplication.getDisplayImageOptions());
        viewHolder.getUrlImv().setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		        if(mItemClickListener!=null){
		        	mItemClickListener.OnItemSubViewClick(viewHolder.delImv, position);
		        	Log.d(TAG, "item " + position + " deleted.");
		        }
			}
        });
        
        viewHolder.getUrlImv().setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 if(mItemClickListener!=null && blDelete){
			        	mItemClickListener.OnItemSubViewClick(viewHolder.delImv, position);
			        	Log.d(TAG, "item " + position + " deleted.");
			     }
			}
        });
        
        if(blDelete){
        	viewHolder.getDelImv().setVisibility(View.VISIBLE);
        }else{
        	viewHolder.getDelImv().setVisibility(View.GONE);
        }
        
    }

   
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
    
   
    
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView textView;
        private  ImageView urlImv=null;
        private  ImageView delImv=null;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
      /*      v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Log.d(TAG, "Element " + getPosition() + " clicked.");
                   if(mItemClickListener!=null){
                	   mItemClickListener.OnItemClick(v, getPosition());
                   }
                }
            }); */
            
            v.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					Log.d(TAG, "Element " + getPosition() + " longclicked.");
	                if(mItemClickListener!=null){
	                	   mItemClickListener.OnItemLongClick(v, getPosition());
	                }
					return false;
				}
			});
            
            textView = (TextView) v.findViewById(R.id.item_rv_tv);
            urlImv =(ImageView) v.findViewById(R.id.item_rv_imv);
            delImv = (ImageView) v.findViewById(R.id.item_rv_delimv);
        }

        public TextView getTextView(){
            return textView;
        }
        
        public ImageView getUrlImv(){
        	return urlImv;
        }
        
        public ImageView getDelImv(){
        	return delImv;
        }
        
    }
}
