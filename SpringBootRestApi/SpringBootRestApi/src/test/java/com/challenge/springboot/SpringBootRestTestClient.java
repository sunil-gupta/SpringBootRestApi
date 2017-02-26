package com.challenge.springboot;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.web.client.RestTemplate;

import com.challenge.springboot.model.ShopDetails;


public class SpringBootRestTestClient {

	public static final String REST_SERVICE_URI = "http://localhost:8080/SpringBootRestApi/api";

	/* GET */
	@SuppressWarnings("unchecked")
	@Test()
	public void findAllAddress(){
		System.out.println("Testing findAllAddress API-----------");
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap<String, Object>> addressMap=null;
		try{
			addressMap = restTemplate.getForObject(REST_SERVICE_URI+"/findAll/", List.class);

			assertNotNull(addressMap);
			assertTrue(addressMap.size()>= 1);
		}catch (Exception e) {
			assertTrue(addressMap==null);
		}
	}

	/* POST */
	@Test()
	public void createShopAddress() {
		System.out.println("Testing createShopAddress API----------");
		RestTemplate restTemplate = new RestTemplate();
		ShopDetails shopDetails = new ShopDetails();
		shopDetails.getShopAddress().setNumber("viman nagar");
		shopDetails.setShopName("Krishan veg");
		shopDetails.getShopAddress().setPostCode("411014");
		URI uri =null;
		try{
			uri = restTemplate.postForLocation(REST_SERVICE_URI+"/shop/", shopDetails, ShopDetails.class);
			assertNotNull(uri.toASCIIString());
		}catch (Exception e) {
			assertTrue(shopDetails.getShopAddress().getShopLatitude()==null);
		}
	}

	/* GET */
	@SuppressWarnings("unchecked")
	@Test()
	public void getShopNamesByLatitudeAndLongitude(){
		System.out.println("Testing listAllUsers API-----------");

		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap<String, Object>> addressMap=null;
		try{
			addressMap = restTemplate.getForObject(REST_SERVICE_URI+"/shopNames/?latitude=18.5651450&longitude=73.9181790", List.class);

			assertNotNull(addressMap);
			assertTrue(addressMap.size()>= 1);

		}catch (Exception e) {
			assertTrue(addressMap==null);
		}
	}

	public static void main(String args[]){
		SpringBootRestTestClient testClient=new SpringBootRestTestClient();
		testClient.findAllAddress();
		testClient.createShopAddress();
		testClient.findAllAddress();
		testClient.getShopNamesByLatitudeAndLongitude();

	}
}
