package com.challenge.springboot.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.challenge.springboot.model.ShopDetails;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

	private static List<ShopDetails> shopDetails;
	
	@Override
	public ShopDetails findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public List<ShopDetails> findAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void saveShopAddress(ShopDetails shopReq) {
		// TODO Auto-generated method stub
		
	}
	
}
