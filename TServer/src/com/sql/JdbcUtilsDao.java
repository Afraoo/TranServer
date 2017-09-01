package com.sql;

import java.util.List;
import java.util.Map;

import com.api.ReceiveEndPointPara;
import com.entity.CarBasicInfo;

public interface JdbcUtilsDao {

	public List<Map<String,Object>> listCarGroupId() throws Exception;
	
	public List<Map<String,Object>> listCarNumByGroupId(int groupId) throws Exception;
	
	public long getGroupIdbyGroupNum(int groupNum) throws Exception;
	
	public String getMacAddress(int teamNum, int carNum) throws Exception;
	
	public List<String> listMacByGroupId(int groupId) throws Exception;
	
//	public boolean JudgeCongestionByTime(int carId) throws Exception;
	
	public boolean addCarHistory(long carId,ReceiveEndPointPara input) throws Exception;
	
	public long getCarIdByMac(String macAddress) throws Exception;	
	
	public double getSingleCarDistance(int teamNumber, int carNumber) throws Exception;
	
	public List<Map<String,Object>> listCarIdForFollowing(int teamNumber, double distance) throws Exception;
	
	public String getMacByCarId(Map<String, Object> map) throws Exception;
	
	public List<Map<String,Object>> listCoorByCarId(long carId) throws Exception;
	
	public List<Map<String,Object>> listCoorGroup() throws Exception;
	
//	public List<Map<String, Object>> listCarIdByGroupId(long groupId) throws Exception;
	
	public long getGroupIdByCarId(long carId) throws Exception;
	
	public List<String> listMacAdress() throws Exception;
	
	public Map<String, Object> getCarDistanceMax(long groupId, long carId) throws Exception;
	
	public long getCarIdByCarNum(long groupId, int carNum) throws Exception;
	
	public int getCarNumByCarId(long carId) throws Exception;
	
	public int getCarNumByMacAddress(String macAddress) throws Exception;
	
	public long getGroupIdByMacAddress(String macAddress) throws Exception;
	
	public int getGroupNumByGroupId(long groupId) throws Exception;
	
}
