package com.id.cloud.utility.iplocation;

import com.id.cloud.inspiration.entities.IPLocationInfo;

public interface QueryLocationInfo {
	
	public IPLocationInfo queryByIP(String ip);

}
