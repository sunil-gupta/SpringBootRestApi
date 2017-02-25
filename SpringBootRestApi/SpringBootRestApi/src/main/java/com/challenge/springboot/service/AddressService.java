package com.challenge.springboot.service;


import java.util.List;

import com.challenge.springboot.model.ShopDetails;

public interface AddressService {
	
	/**
	 * Method Name <br>
	 * findByLatitudeAndLongitude<br>
	 * 25 February 2017 
	 * 
	 * This method is used to Fetch near by shops name by providing latitude and longitude.<br>
	 * 
	 * @param latitude
	 * @param longitude
	 * 
	 * @return String[]
	 * @throws Exception
	 */
	String[] findByLatitudeAndLongitude(String latitude, String longitude) throws Exception;
	
	/**
	 * Method Name <br>
	 * addShopAddress<br>
	 * 25 February 2017 
	 * 
	 * This method is used to add shop address in memeory.<br>
	 * 
	 * @param shopReq
	 * @throws Exception
	 */
	void addShopAddress(ShopDetails shopReq) throws Exception;
	
	/**
	 * Method Name <br>
	 * findAllAddress<br>
	 * 25 February 2017 
	 * 
	 * This method is used to Fetch all added address.<br>
	 * 
	 * @return List<ShopDetails>
	 */
	List<ShopDetails> findAllAddress();
}
