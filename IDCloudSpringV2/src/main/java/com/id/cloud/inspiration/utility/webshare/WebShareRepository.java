package com.id.cloud.inspiration.utility.webshare;

public interface WebShareRepository {
	
	public WebShare create(String shareContent);
	public void update(String shareContent);
	public WebShare getWebShare();
}
