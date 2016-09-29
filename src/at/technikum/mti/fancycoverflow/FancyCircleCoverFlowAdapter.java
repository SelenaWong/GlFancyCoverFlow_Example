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

package at.technikum.mti.fancycoverflow;

import java.util.List;

import com.app.model.Product;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public abstract class FancyCircleCoverFlowAdapter extends BaseAdapter {

    // =============================================================================
    // Supertype overrides
    // =============================================================================
	
	 private List<Product>products;
	 private boolean isLoops=false;
	 
	 
	 public List<Product> getProducts(){
		 return products;
	 }
	 
	 public void setProducts(List<Product> products){
		 this.products = products;
	 }
	 
	 public boolean getIsLoops(){
		 return isLoops;
	 }
	 
	 public void setIsLoops(boolean isLoops){
		 this.isLoops = isLoops;
	 }
	 
	 
	 public FancyCircleCoverFlowAdapter( List<Product> products){
		 this.products = products;
	 }
	
     @Override
     public int getCount() {
     	if(isLoops){
     		return Integer.MAX_VALUE;
     	}
        return products.size();
     }

      @Override
      public  Product getItem(int i) {
          return products.get(i%products.size());
      }

      @Override
      public long getItemId(int i) {
          return i%products.size();
      }
	

    @Override
    public final View getView(int i, View reusableView, ViewGroup viewGroup) {
        FancyCoverFlow coverFlow = (FancyCoverFlow) viewGroup;

        View wrappedView = null;
        FancyCoverFlowItemWrapper coverFlowItem;

        if (reusableView != null) {
            coverFlowItem = (FancyCoverFlowItemWrapper) reusableView;
            wrappedView = coverFlowItem.getChildAt(0);
            coverFlowItem.removeAllViews();
        } else {
            coverFlowItem = new FancyCoverFlowItemWrapper(viewGroup.getContext());
        }

        wrappedView = this.getCoverFlowItem(i%products.size(), wrappedView, viewGroup);

        if (wrappedView == null) {
            throw new NullPointerException("getCoverFlowItem() was expected to return a view, but null was returned.");
        }

        final boolean isReflectionEnabled = coverFlow.isReflectionEnabled();
        coverFlowItem.setReflectionEnabled(isReflectionEnabled);

        if(isReflectionEnabled) {
            coverFlowItem.setReflectionGap(coverFlow.getReflectionGap());
            coverFlowItem.setReflectionRatio(coverFlow.getReflectionRatio());
        }

        coverFlowItem.addView(wrappedView);
        coverFlowItem.setLayoutParams(wrappedView.getLayoutParams());
        return coverFlowItem;
    }

    // =============================================================================
    // Abstract methods
    // =============================================================================

    public abstract View getCoverFlowItem(int position, View reusableView, ViewGroup parent);
}
