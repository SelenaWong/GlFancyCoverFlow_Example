
/**   
 * @Title: OnItemClickListener.java 
 * @Package: com.example.android.recyclerview.listener 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年9月26日 下午5:29:53 
 * @version 1.3.1 
 */


package com.app.recyclerview.listener;

import android.view.View;

/** 
 * @Description 
 * @author Selena Wong
 * @date 2016年9月26日 下午5:29:53 
 * @version V1.3.1
 */

public interface onItemClickListener {
	
	public void OnItemLongClick(View view,int position);
	
	public void OnItemClick(View view,int position);
	
	public void OnItemSubViewClick(View view,int position);
	
}
