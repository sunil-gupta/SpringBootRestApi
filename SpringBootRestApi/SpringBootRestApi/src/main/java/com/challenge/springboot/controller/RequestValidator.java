package com.challenge.springboot.controller;

import com.challenge.springboot.model.ShopDetails;

public class RequestValidator {

	/**
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public boolean isRequestValid(String latitude, String longitude) {
		boolean isValidReqFlag=false;
		if(null !=latitude && !latitude.isEmpty() 
				&&
				null !=longitude && !longitude.isEmpty()){
			isValidReqFlag=true;
		} 

		return isValidReqFlag;
	}
	

	/**
	 * @param addressReq
	 * @return
	 */
	public boolean isRequestValid(ShopDetails addressReq) {
		boolean isValidReqFlag=false;
		if(null !=addressReq.getShopName() && !addressReq.getShopName().isEmpty()){
			isValidReqFlag=true;
		} 

		if(null !=addressReq.getShopAddress() && !isValidReqFlag){
			if(null !=addressReq.getShopAddress().getPostCode() && addressReq.getShopAddress().getPostCode().isEmpty()){
				isValidReqFlag=true;
			}

			if(!isValidReqFlag && null !=addressReq.getShopAddress().getNumber() && addressReq.getShopAddress().getNumber().isEmpty()){
				isValidReqFlag=true;
			}
		}
		return isValidReqFlag;
	}
}
