package com.challenge.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.springboot.model.ShopDetails;
import com.challenge.springboot.service.AddressService;
import com.challenge.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RetailRestController {

	public static final Logger logger = LoggerFactory.getLogger(RetailRestController.class);

	@Autowired
	AddressService addressService; //Service which will do all data retrieval/manipulation work

	// -------------------Create a Shop Address-------------------------------------------

	@RequestMapping(value = "/shop/", method = RequestMethod.POST)
	public ResponseEntity<?> createShopAddress(@RequestBody ShopDetails shopReq) {
		logger.info("Creating Shop Address : {}", shopReq.toString());

		/*if (userService.isUserExist(user)) {
			logger.error("Unable to create. A User with name {} already exist", user.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
			user.getName() + " already exist."),HttpStatus.CONFLICT);
		}*/
		
		try {
			addressService.saveShopAddress(shopReq);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return new ResponseEntity<ShopDetails>(shopReq, HttpStatus.CREATED);
	}
	
	
	/**
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	@RequestMapping(value = "/shopNames/", method = RequestMethod.GET)
	public ResponseEntity<?> getShopNamesByLatitudeAndLongitude(@RequestParam("latitude") String latitude,@RequestParam("longitude") String longitude) {

		logger.info("Fetching Shops Name with latitude and longitude", latitude +" "+longitude);
		String[] shopNames = null;
		try {
			shopNames = addressService.findById(latitude, longitude);
			if (shopNames == null || shopNames.length==0) {
				logger.error("Shops with latitude and longitude not found.", latitude +" "+longitude);
				
				return new ResponseEntity<CustomErrorType>(new CustomErrorType("Shops with latitude and longitude" + latitude +" "+longitude 
						+ " not found"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<String[]>(shopNames, HttpStatus.OK);
	}
}
