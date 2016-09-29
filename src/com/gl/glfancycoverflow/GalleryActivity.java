/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gl.glfancycoverflow;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.CustomFancyCoverFlowAdpater;
import java.util.ArrayList;
import java.util.List;
import com.app.glfancycoverflow.R;
import com.app.model.Product;
import com.app.recyclerview.CustomAdapter;
import com.app.recyclerview.SpaceItemDecoration;
import com.app.recyclerview.listener.*;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GalleryActivity extends Activity implements OnClickListener{

	private final static int ImageLayoutWidth =160;//280
    private final static int ImageLayoutHeight =280;//500
    private final static int UPPERLIMIT= 5;
    private ImageLoader imageLoader;
    private Button mAddBtn,mPurchaseBtn,mCartDeleteBtn;
    private ImageView mCartImv;
	private FancyCoverFlow fancyCoverFlow;
	private RelativeLayout rlCoverFlowParent;
	private CustomFancyCoverFlowAdpater mAdapter;
    protected RecyclerView mRecyclerView;
    protected CustomAdapter mListAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
	private List<Product>mProducts=new ArrayList<Product>();
    protected List<Product> mAddList=new ArrayList<Product>();
    private PathMeasure mPathMeasure;
    private float mCurrentPosition[] = new float[2];
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       this.setContentView(R.layout.activity_main);
       fancyCoverFlow = (FancyCoverFlow)findViewById(R.id.glfancyCoverFlow);
       rlCoverFlowParent = (RelativeLayout) findViewById(R.id.rl_coverFlowParent);
       mCartImv = (ImageView) findViewById(R.id.imv_cart);
       mAddBtn = (Button)findViewById(R.id.btn_add);
       mPurchaseBtn = (Button)findViewById(R.id.btn_purchase);
       mCartDeleteBtn = (Button) findViewById(R.id.btn_cart_operate);
       mRecyclerView = (RecyclerView)findViewById(R.id.rlv_cart_product);
       mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
       mRecyclerView.setLayoutManager(mLayoutManager);
       mRecyclerView.addItemDecoration( new SpaceItemDecoration(5));
       mAddBtn.setOnClickListener(this);
       mPurchaseBtn.setOnClickListener(this);
       mCartDeleteBtn.setOnClickListener(this);
       imageLoader =ImageLoader.getInstance();
       loadView();
    }
    
    public void loadView(){
    	fancyCoverFlow.recycleWrapper();
    	if(mAdapter!=null){ 
    		mProducts=mProducts.subList(0, 5);
    		mAdapter.notifyDataSetChanged();
    	}else{
    		mProducts = ProductTest.getProducts();
            mAdapter= new CustomFancyCoverFlowAdpater(mProducts,GalleryActivity.this);
    	}
	    ViewTreeObserver vto2 = fancyCoverFlow.getViewTreeObserver();
	         vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
	              @Override
	              public void onGlobalLayout() {
	                  fancyCoverFlow.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	                  int pageCapacity = fancyCoverFlow.getWidth()/ImageLayoutWidth;
	                  int len = mProducts.size();
	                  if(pageCapacity<len){
	                  	mAdapter.setIsLoops(true);
	                  	fancyCoverFlow.setAdapter(mAdapter);
	                  	fancyCoverFlow.setSelection(len);
	                  }else{
	                  	fancyCoverFlow.setAdapter(mAdapter);
	                  	fancyCoverFlow.setSelection(len/2);
	                  }
	                  if(mListAdapter!=null){
	                	  mListAdapter.deleteItemRange(0, mAddList.size());
	                  }else{
	                	  initRecyclerView();
	                  }
	              }
	          });
	         fancyCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub
					 if(mAddList.size()==UPPERLIMIT){
						 return;
					 }
					 int position =arg2%mProducts.size();
					 Log.i("TAG", " item click pos="+position);
				     ImageView v = (ImageView) arg1.findViewById(R.id.coverflow_image);
					 mListAdapter.refreshDeleteBtn(false);
					 mAddList.add(mProducts.get(position));
				     addToCart(v,position);
				 }
	         });
    }
    
    public void addToCart( ImageView imv,int position ){
    	
    	int parentPoint[] = new int[2];
    	int startPoint[] = new int[2]; 
    	int toPoint[] = new int[2];
        rlCoverFlowParent.getLocationInWindow(parentPoint);
    	imv.getLocationInWindow(startPoint);
        mCartImv.getLocationInWindow(toPoint);
        
        float startX = startPoint[0]-parentPoint[0] + imv.getWidth()/2;
        float startY = startPoint[1]-parentPoint[1] + imv.getHeight()/2;
        float toX = toPoint[0]-parentPoint[0]+mCartImv.getWidth()/3;
        float toY = toPoint[1]-parentPoint[1];
        
        Log.i("TAG", "startX="+startX +"startY="+startY +"toX="+toX +"toY="+toY);
        
        final ImageView moveImv = new ImageView(GalleryActivity.this);
        moveImv.setImageDrawable(imv.getDrawable());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(60, 60);
        rlCoverFlowParent.addView(moveImv, params);
        
        Path path = new Path();
        path.moveTo(startX, startY);
        path.quadTo((startX+toX)/2, startY, toX, toY);
        
        mPathMeasure = new PathMeasure(path,false);
        ValueAnimator vAnimation = ValueAnimator.ofFloat(mPathMeasure.getLength());
        vAnimation.setDuration(1000);
        vAnimation.addUpdateListener(new AnimatorUpdateListener(){
			@Override
			public void onAnimationUpdate(ValueAnimator arg0) {
				// TODO Auto-generated method stub
				float distance = (Float) arg0.getAnimatedValue();
				mPathMeasure.getPosTan(distance, mCurrentPosition, null);
				moveImv.setTranslationX(mCurrentPosition[0]);
				moveImv.setTranslationY(mCurrentPosition[1]);
			}
        });
        
        vAnimation.start();
        vAnimation.addListener(new Animator.AnimatorListener() {
        	
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				rlCoverFlowParent.removeView(moveImv);
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});
        
       /* PathAnimation animation = new PathAnimation(path);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setAnimationListener( new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				rlCoverFlowParent.removeView(moveImv);
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
        });
        moveImv.startAnimation(animation);*/
        
   	    Log.i("TAG", " moveImv");
    }
    

    public void initRecyclerView(){
    	 mListAdapter = new CustomAdapter(mAddList,imageLoader);
    	 mRecyclerView.setAdapter(mListAdapter);
    	 mListAdapter.setonItemClickListener(new onItemClickListener(){

			@Override
			public void OnItemLongClick(View view, int position) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void OnItemClick(View view, int position) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void OnItemSubViewClick(View view, int position) {
				// TODO Auto-generated method stub
				mListAdapter.deleteItem(position);
			}
    	 });
    }
    
	/**
	 * Description 
	 * @param arg0 
	 * @see android.view.View.OnClickListener#onClick(android.view.View) 
	 */ 
		
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		switch(arg0.getId()){
		case R.id.btn_add:
			mListAdapter.refreshDeleteBtn(false);
			if(mAddList.size()==UPPERLIMIT){
				Toast.makeText(getApplicationContext(), "¹ºÎï³µÂúÀ²À²~", Toast.LENGTH_LONG).show();
				return;
			}
			Product pro= (Product) fancyCoverFlow.getSelectedItem();
			Log.w("TAG", "onClick pro "+pro.getProName());
			mListAdapter.addItem(pro);		
			//setViewPagerAndZoom( fancyCoverFlow.getSelectedView(),fancyCoverFlow.getSelectedItemPosition());
			break;
		case R.id.btn_purchase:
			Product pro2= (Product) fancyCoverFlow.getSelectedItem();
			break;
		case R.id.btn_cart_operate:
			mListAdapter.refreshDeleteBtn(true);
			break;
		default:
			break;
		}
	}   
    
	

	
	
	
	
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	fancyCoverFlow.recycleWrapper();
    }

   }