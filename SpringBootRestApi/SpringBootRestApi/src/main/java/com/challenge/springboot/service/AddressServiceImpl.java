package com.challenge.springboot.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.challenge.springboot.model.ShopDetails;

@Service("addressService")
public class AddressServiceImpl implements AddressService {
	public static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

	private static List<ShopDetails> shopDetails=new ArrayList<ShopDetails>();

	/* (non-Javadoc)
	 * @see com.challenge.springboot.service.AddressService#findByLatitudeAndLongitude(java.lang.String, java.lang.String)
	 */
	@Override
	public String[] findByLatitudeAndLongitude(String latitude, String longitude) throws Exception {

		String shopList[] = this.getAddressByLatLongPositions(latitude, longitude);
		logger.info("Shop Names for provided latitude and longitude :: "+shopList);
		return shopList;
	}

	/* (non-Javadoc)
	 * @see com.challenge.springboot.service.AddressService#addShopAddress(com.challenge.springboot.model.ShopDetails)
	 */
	@Override
	public void addShopAddress(ShopDetails shopReq) throws Exception {
		StringBuilder address= new StringBuilder();

		if(null !=shopReq.getShopName()){
			address.append(shopReq.getShopName());
		}
		if(null !=shopReq.getShopAddress().getNumber()){
			address.append(shopReq.getShopAddress().getNumber());
		}

		if(null !=shopReq.getShopAddress().getPostCode()){
			address.append(shopReq.getShopAddress().getPostCode());
		}

		String latLongs[] = this.getLatLongPositions(address.toString());

		if(null !=latLongs){
			logger.info("Latitude: "+latLongs[0]+" and Longitude: "+latLongs[1]);

			shopReq.getShopAddress().setShopLatitude(latLongs[0]);
			shopReq.getShopAddress().setShopLongitude(latLongs[1]);
		}

		shopDetails.add(shopReq);
	}

	/**
	 * @param address
	 * @return
	 * @throws Exception
	 */
	private String[] getLatLongPositions(String address) throws Exception
	{
		int responseCode = 0;
		HttpURLConnection httpConnection = null;
		try{
			String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=true";
			logger.info("URL for get LatLongPositions : "+api);

			URL url = new URL(api);
			httpConnection = (HttpURLConnection)url.openConnection();
			httpConnection.connect();
			responseCode = httpConnection.getResponseCode();
			if(responseCode == 200)
			{
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
				Document document = builder.parse(httpConnection.getInputStream());
				XPathFactory xPathfactory = XPathFactory.newInstance();
				XPath xpath = xPathfactory.newXPath();
				XPathExpression expr = xpath.compile("/GeocodeResponse/status");
				String status = (String)expr.evaluate(document, XPathConstants.STRING);
				if(status.equals("OK"))
				{
					expr = xpath.compile("//geometry/location/lat");
					String latitude = (String)expr.evaluate(document, XPathConstants.STRING);
					expr = xpath.compile("//geometry/location/lng");
					String longitude = (String)expr.evaluate(document, XPathConstants.STRING);
					return new String[] {latitude, longitude};
				}
				else
				{
					throw new Exception("Error from the API - response status: "+status);
				}
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally{
			if(null!=httpConnection){
				httpConnection.disconnect();
			}
		}
		
		return null;
	}


	/**
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws Exception
	 */
	private String[] getAddressByLatLongPositions(String latitude, String longitude) throws Exception
	{
		int responseCode = 0;
		HttpURLConnection httpConnection = null;
		try{
			String api = "http://api.geonames.org/findNearbyPOIsOSM?lat=" + URLEncoder.encode(latitude, "UTF-8")+"&lng="+URLEncoder.encode(longitude, "UTF-8") + "&username=demo";
			logger.info("URL for getting Address list by latitude and longitude : "+api);

			URL url = new URL(api);
			httpConnection = (HttpURLConnection)url.openConnection();
			httpConnection.connect();
			responseCode = httpConnection.getResponseCode();

			if(responseCode == 200){
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
				Document document = builder.parse(httpConnection.getInputStream());

				NodeList nList = document.getElementsByTagName("poi");
				String shopsList[]=new String[nList.getLength()];

				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					logger.info("\nCurrent Element :" + nNode.getNodeName());

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						logger.info("Name : "+ eElement.getElementsByTagName("name").item(0).getTextContent());
						shopsList[temp]=eElement.getElementsByTagName("name").item(0).getTextContent();
					}
				}
				return shopsList;
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally{
			if(null!=httpConnection){
				httpConnection.disconnect();
			}
		}
		return null;
	}


	/* (non-Javadoc)
	 * @see com.challenge.springboot.service.AddressService#findAllAddress()
	 */
	@Override
	public List<ShopDetails> findAllAddress() {
		return shopDetails;
	}

}
