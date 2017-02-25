package com.challenge.springboot.service;


import java.util.List;

import com.challenge.springboot.model.ShopDetails;

public interface AddressService {
	ShopDetails findById(long id);
	
	void saveShopAddress(ShopDetails shopReq);
	
	List<ShopDetails> findAllUsers();
}
