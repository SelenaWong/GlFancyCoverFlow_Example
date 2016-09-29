/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.gl.glfancycoverflow;

import java.io.File;

import com.app.glfancycoverflow.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;


import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com) 
 *  
 *  鍒濆鍖朓mageLoader
 */
public class UILApplication extends Application {


	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader(getApplicationContext());
	}

	


	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"universalimageloader/Cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
			    .diskCache(new UnlimitedDiskCache(cacheDir))
			    .diskCacheSize(50 * 1024 * 1024)
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.diskCacheFileCount(100) 
				.writeDebugLogs() // Remove for release app
				.build();
	
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
	
	
	public static DisplayImageOptions getDisplayImageOptions() {

		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.logo)
				.showImageForEmptyUri(R.drawable.logo)
				.showImageOnFail(R.drawable.ic_error)
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		return displayImageOptions;
	}

}