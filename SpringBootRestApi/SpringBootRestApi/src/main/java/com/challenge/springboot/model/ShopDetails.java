package com.challenge.springboot.model;

public class ShopDetails {
	
private String shopName;
private ShopAddress shopAddress;


public String getShopName() {
	return shopName;
}
public void setShopName(String shopName) {
	this.shopName = shopName;
}

public ShopAddress getShopAddress() {
	return shopAddress;
}
public void setShopAddress(ShopAddress shopAddress) {
	this.shopAddress = shopAddress;
}
@Override
public String toString() {
	return "ShopDetails [shopName=" + shopName + ", shopAddress=" + shopAddress + ", getShopName()=" + getShopName()
			+ ", getShopAddress()=" + getShopAddress() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
			+ ", toString()=" + super.toString() + "]";
}



}
