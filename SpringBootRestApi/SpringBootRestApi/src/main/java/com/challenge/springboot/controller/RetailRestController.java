package com.challenge.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.challenge.springboot.model.ShopDetails;
import com.challenge.springboot.service.AddressService;
import com.challenge.springboot.util.CustomErrorType;

/**
 * Class : RetailRestController <br>
 * 25 February 2017
 * 
 * The RetailRestController implements 3 operations<br>
 * 	-createShopAddress :Add address provided in request
 *  -getShopNamesByLatitudeAndLongitude	:Find address/shop near by provided 
 *  latitude and longitude.<br>
 * 
 * @author Sunil Gupta
 *
 */

@RestController
@RequestMapping("/api")
public class RetailRestController extends RequestValidator {

	public static final Logger logger = LoggerFactory.getLogger(RetailRestController.class);

	@Autowired
	AddressService addressService; //Service which will do all data retrieval/manipulation work

	// -------------------Create a Shop Address-------------------------------------------

	/**
	 * Method Name <br>
	 * createShopAddress<br>
	 * 25 February 2017 
	 * 
	 * This operation accept request conatins shopname,postalcode and shop number  
	 * @param addressReq
	 * @return
	 */
	@RequestMapping(value = "/shop/", method = RequestMethod.POST)
	public ResponseEntity<?> createShopAddress(@RequestBody ShopDetails addressReq,UriComponentsBuilder ucBuilder) {
		logger.info("Creating Shop Address : ", addressReq.toString());

		if (!this.isRequestValid(addressReq)) {
			logger.error("Invalid request for createShopAddress");
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Invalid request parameter for createShopAddress"),HttpStatus.BAD_REQUEST);
		}

		try {
			addressService.addShopAddress(addressReq);

			if(null !=addressReq.getShopAddress()){ 
				if(null==addressReq.getShopAddress().getShopLatitude() && null==addressReq.getShopAddress().getShopLongitude()){
					logger.error("latitude and longitude not found for given address");
					return new ResponseEntity<CustomErrorType>(new CustomErrorType("latitude and longitude not found for given address"), HttpStatus.NOT_FOUND);
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<CustomErrorType>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
		}
		//http://localhost:8080/SpringBootRestApi/api/shopNames/?latitude=18.5651450&longitude=73.9181790
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/shopNames/{shopName}").buildAndExpand(addressReq.getShopName()).toUri());
		
		return new ResponseEntity<ShopDetails>(addressReq,headers, HttpStatus.CREATED);
	}


	// -------------------Fetch near by shops by providing latitude and longitude-------------------------------------------

	/**
	 * Method Name <br>
	 * getShopNamesByLatitudeAndLongitude<br>
	 * 25 February 2017 
	 * 
	 * This method is used to Fetch near by shops name by providing latitude and longitude.<br>
	 * 
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	@RequestMapping(value = "/shopNames/", method = RequestMethod.GET)
	public ResponseEntity<?> getShopNamesByLatitudeAndLongitude(@RequestParam("latitude") String latitude,@RequestParam("longitude") String longitude) {

		logger.info("Fetching Shops Name with latitude and longitude", latitude +" "+longitude);
		String[] shopNames = null;

		if(!isRequestValid(latitude, longitude)){
			logger.error("Invalid request for getShopNamesByLatitudeAndLongitude");
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Invalid request parameter for getShopNamesByLatitudeAndLongitude"),HttpStatus.BAD_REQUEST);
		}

		try {
			shopNames = addressService.findByLatitudeAndLongitude(latitude, longitude);
			if (shopNames == null || shopNames.length==0) {
				logger.error("Shops with latitude and longitude not found.", latitude +" "+longitude);

				return new ResponseEntity<CustomErrorType>(new CustomErrorType("Shops with latitude and longitude" + latitude +" "+longitude 
						+ " not found"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<CustomErrorType>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String[]>(shopNames, HttpStatus.OK);
	}
	
	// -------------------Fetch all added address-------------------------------------------

		/**
		 * Method Name <br>
		 * findAllAddress<br>
		 * 25 February 2017 
		 * 
		 * This method is used to Fetch all added address.<br>
		 * 
		 * @return
		 */
		@RequestMapping(value = "/findAll/", method = RequestMethod.GET)
		public ResponseEntity<?> findAllAddress() {

			logger.info("Fetching all Shops adderss");
			List<ShopDetails> shopList = null;

			try {
				shopList = addressService.findAllAddress();
				if (shopList == null || shopList.isEmpty()) {
					logger.error("No shops find.");

					return new ResponseEntity<CustomErrorType>(new CustomErrorType("No shops find."), HttpStatus.NOT_FOUND);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				return new ResponseEntity<CustomErrorType>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<List<ShopDetails>>(shopList, HttpStatus.OK);
		}
	
}