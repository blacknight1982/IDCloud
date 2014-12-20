package com.id.cloud.inspiration.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class IPLocationInfo{

	private int ipLocationID;
	private String ip;
	private String longitude;
	private String latitude;
	private String postal_code;
	private String city;
	private String region;
	private String region_code;
	private String country;
	private String country_code;
	private String isp;
	
	public IPLocationInfo(){
	}
	
	public IPLocationInfo(String ip, String longitude, String latitude, String postal_code, String city, String region, String region_code, String country, String country_code, String isp){
		this.ip = ip;
		this.longitude = longitude;
		this.latitude = latitude;
		this.postal_code = postal_code;
		this.city = city;
		this.region = region;
		this.region_code = region_code;
		this.country = country;
		this.country_code = country_code;
		this.isp = isp;
		
	}
	
	public int getIpLocationID() {
		return ipLocationID;
	}
	public void setIpLocationID(int ipLocationID) {
		this.ipLocationID = ipLocationID;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRegion_code() {
		return region_code;
	}
	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public String getIsp() {
		return isp;
	}
	public void setIsp(String isp) {
		this.isp = isp;
	}
	
	
}
