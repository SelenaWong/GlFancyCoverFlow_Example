
/**   
 * @Title: Product.java 
 * @Package: com.app.test 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年9月26日 上午11:02:34 
 * @version 1.3.1 
 */


package com.app.model;

/** 
 * @Description 
 * @author Selena Wong
 * @date 2016年9月26日 上午11:02:34 
 * @version V1.3.1
 */

public class Product {
	private String proName;
	private String proUrl;
	
	public Product(){
		
	}

	public Product(String name,String url){
		this.proName = name;
		this.proUrl = url;
	}
	
	/**
	 * @return proName
	 */
	
	public String getProName() {
		return proName;
	}

	
	/** 
	 * @param proName 要设置的 proName 
	 */
	
	public void setProName(String proName) {
		this.proName = proName;
	}

	
	/**
	 * @return proUrl
	 */
	
	public String getProUrl() {
		return proUrl;
	}

	
	/** 
	 * @param proUrl 要设置的 proUrl 
	 */
	
	public void setProUrl(String proUrl) {
		this.proUrl = proUrl;
	}
}
