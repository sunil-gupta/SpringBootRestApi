package com.challenge.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.springboot.model.ShopDetails;
import com.challenge.springboot.service.AddressService;

@RestController
@RequestMapping("/api")
public class RetailRestController {

	public static final Logger logger = LoggerFactory.getLogger(RetailRestController.class);

	@Autowired
	AddressService addressService; //Service which will do all data retrieval/manipulation work

	

	// -------------------Create a User-------------------------------------------

	@RequestMapping(value = "/shop/", method = RequestMethod.POST)
	public ResponseEntity<?> createShopAddress(@RequestBody ShopDetails shopReq) {
		logger.info("Creating Shop Address : {}", shopReq.toString());

		/*if (userService.isUserExist(user)) {
			logger.error("Unable to create. A User with name {} already exist", user.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
			user.getName() + " already exist."),HttpStatus.CONFLICT);
		}*/
		addressService.saveShopAddress(shopReq);

		
		return new ResponseEntity<ShopDetails>(shopReq, HttpStatus.CREATED);
	}

	

}