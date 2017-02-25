package com.challenge.springboot.model;

public class ShopAddress {

	private String number;
	private String postCode;
	private String shopLongitude;
	private String shopLatitude;
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getShopLongitude() {
		return shopLongitude;
	}
	public void setShopLongitude(String shopLongitude) {
		this.shopLongitude = shopLongitude;
	}
	public String getShopLatitude() {
		return shopLatitude;
	}
	public void setShopLatitude(String shopLatitude) {
		this.shopLatitude = shopLatitude;
	}
	@Override
	public String toString() {
		return "ShopAddress [number=" + number + ", postCode=" + postCode + ", shopLongitude=" + shopLongitude
				+ ", shopLatitude=" + shopLatitude + "]";
	}
	
	
}
