package com.id.cloud.inspiration.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.id.cloud.inspiration.dao.AccessLogDao;
import com.id.cloud.inspiration.dao.IPLocationInfoDao;
import com.id.cloud.inspiration.entities.AccessLog;
import com.id.cloud.inspiration.entities.IPLocationInfo;
import com.id.cloud.utility.iplocation.QueryLocationInfo;

@Repository("inspirationAccessLogRepository")
public class InspirationAccessLocationRepository {
	
	@Autowired
	AccessLogDao accessLogDao;
	
	@Autowired
	QueryLocationInfo queryLocationInfo;
	
	@Autowired
	IPLocationInfoDao iplocationInfoDao;
	
	public List<IPLocationInfo> getAccessIPLocationForToday(){
		List<AccessLog> accessLogs = accessLogDao.findForTodayDistinctIP();
		String[] ips = new String[accessLogs.size()];
		for(int i=0;i<accessLogs.size();i++){
			ips[i] = accessLogs.get(i).getIp();
		}
		return iplocationInfoDao.findByIPs(ips);
	}
	
	public List<IPLocationInfo> getAllAccessIPLocations(){
		return iplocationInfoDao.findAll();
	}
	
	public List<IPLocationInfo> getAccessIPLocationForOneWeek(){
		List<AccessLog> accessLogs = accessLogDao.findForOneWeekDistinctIP();
		String[] ips = new String[accessLogs.size()];
		for(int i=0;i<accessLogs.size();i++){
			ips[i] = accessLogs.get(i).getIp();
		}
		return iplocationInfoDao.findByIPs(ips);
	}
	
	public int recordAccessLocation(AccessLog accessLog){
		if(accessLog != null){
			String ipAddress = accessLog.getIp();
			if(ipAddress.startsWith("0:0:0")||ipAddress.startsWith("192.168")){
				return 0;
			}
			else{
				String[] ips = {ipAddress};
				List <IPLocationInfo> ipLocationInfos = iplocationInfoDao.findByIPs(ips);
				if(ipLocationInfos.size() == 0){
					return iplocationInfoDao.create(queryLocationInfo.queryByIP(ipAddress));
				}
				else return 0;
			}
		}
		else return 0;
	}
	
	/*public void checkAndUpdateIPLocationInfoTable(){
		
		List<AccessLog> accessLogs = accessLogDao.findAllDistinctIP();
		for(int i=0;i<accessLogs.size();i++){
			recordAccessLocation(accessLogs.get(i));
		}
	}*/
}
