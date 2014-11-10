package com.id.cloud.inspiration.utility.webshare;

import org.springframework.stereotype.Repository;

@Repository
public class WebShareRepositoryImpl implements WebShareRepository{

	private WebShare webShare;

	@Override
	public WebShare create(String shareContent) {
		if (webShare == null)
		{
			webShare = new WebShare();
			webShare.setShareContent(shareContent);
		}
		else{
			update(shareContent);
		}
		return webShare;
	}

	@Override
	public void update(String shareContent) {
		if (webShare == null)
		{
			webShare = new WebShare();
			
		}
		webShare.setShareContent(shareContent);
	}

	@Override
	public WebShare getWebShare() {
		// TODO Auto-generated method stub
		return webShare;
	}
}
