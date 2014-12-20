package com.id.cloud.utility.iplocation;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.id.cloud.inspiration.entities.IPLocationInfo;

/**
 * RESTFul implementation of query location information from IP address 
 * from http://www.telize.com/
 * @author John
 *
 */
@Service("QueryLocationInfoTelizeImpl")
public class QueryLocationInfoTelizeImpl implements QueryLocationInfo {

	
	@Override
	public IPLocationInfo queryByIP(String ip) {
		// TODO Auto-generated method stub
		RestTemplate restTemplate = new RestTemplate();
		IPLocationInfo ipLocationInfo = restTemplate.getForObject("http://www.telize.com/geoip/"+ip, IPLocationInfo.class);
		return ipLocationInfo;
	}
	
	public static void main(String args[]) {
		QueryLocationInfoTelizeImpl queryLocationInfoTelizeImpl = new QueryLocationInfoTelizeImpl();
		IPLocationInfo ipLocationInfo = queryLocationInfoTelizeImpl.queryByIP("70.192.140.143");
        System.out.println("getLatitude:    " + ipLocationInfo.getLatitude());
        System.out.println("About:   " + ipLocationInfo.getLongitude());
        System.out.println("Phone:   " + ipLocationInfo.getCountry());
        System.out.println("Website: " + ipLocationInfo.getCity());
    }

}
