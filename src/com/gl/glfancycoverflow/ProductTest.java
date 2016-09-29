package com.gl.glfancycoverflow;

import java.util.ArrayList;
import java.util.List;

import com.app.model.Product;

/**
 * @author LittleLiByte 
 * 测试数据
 */
public class ProductTest {

	public static String[] urls = new String[] {
			"http://img.cxdq.com/d1/img/070609/20076962820825.jpg",
			"http://www.ocn.com.cn/Upload/userfiles/25(128).jpg",
			"http://image11.m1905.cn/uploadfile/2012/1025/20121025115127717.jpg",
			"http://www.sndu.com.cn/resource/images/201511/20151125132158625.jpeg",
			"http://img31.mtime.cn/mg/2014/09/30/145438.41392832_270X405X4.jpg",
			"http://img31.mtime.cn/mg/2014/09/09/095439.24895990_270X405X4.jpg",
			"http://img31.mtime.cn/mg/2014/10/13/151034.85474901_270X405X4.jpg",
			"http://img31.mtime.cn/mg/2014/09/23/084444.96794628_270X405X4.jpg",
			"http://img31.mtime.cn/mg/2014/08/15/104026.33444853_270X405X4.jpg",
			"http://img31.mtime.cn/mg/2014/09/26/151251.44963343_270X405X4.jpg"

	};

	public static String[] names = new String[] { "哈利波特", "饥饿鸟", "暮光",
			"剑雨", "3D食人虫", "心花怒放", "忍者神龟","移动迷宫"	};

	
	public static List<Product> getProducts() {
		List<Product> proList = new ArrayList<Product>();

		for (int i = 0; i < 8; i++) {
			Product film = new Product(names[i], urls[i]);
			proList.add(film);
		}
		return proList;
	}
}
