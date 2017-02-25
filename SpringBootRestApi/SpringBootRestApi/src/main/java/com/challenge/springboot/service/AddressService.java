package com.challenge.springboot.service;


import java.util.List;

import com.challenge.springboot.model.ShopDetails;

public interface AddressService {
	String[] findById(String latitude, String longitude) throws Exception;
	
	void saveShopAddress(ShopDetails shopReq) throws Exception;
	
	List<ShopDetails> findAllUsers();
}
