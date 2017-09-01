package com.sql;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Bean.PostgreSQL;

import com.api.ReceiveEndPointPara;
import com.entity.CarBasicInfo;
import com.entity.CarBasicInfo;

import zuojie.esql.Esql;


public class JdbcUtilsImp extends JdbcUtils implements JdbcUtilsDao,PostgreSQL
{		
	public List<Map<String,Object>> listCarGroupId() throws Exception {
		// TODO Auto-generated method stub
		super.getConnection();
		String sql ="select group_num from t_car_group ";
		List<Map<String,Object>> list = findMoreResult(sql, null);		
		
		return list;
	}	
	
	public List<Map<String,Object>> listCarNumByGroupId(int groupNum) throws Exception{
		
		super.getConnection();	 
		 
		String sql ="select car_num from car_basic_info where group_id =? ";
		List<Object> params1 = new ArrayList<Object>();
		int num = groupNum;
		params1.add(getGroupIdbyGroupNum(num));
		List<Map<String,Object>> list = findMoreResult(sql, params1);
		return list;
		
	}
	
	public long getGroupIdbyGroupNum(int groupNum) throws Exception{
		
		super.getConnection();	
		String sql = "select id from t_car_group where group_num = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(groupNum);
		int c=0;
		Map<String,Object> map = findSimpleResult(sql, params);
		for (Entry<String, Object> entry : map.entrySet()) {  
	           Object a=entry.getValue();  
	           String b = a.toString();
	           c = Integer.valueOf(b).intValue();	          
	        }     
		return c;
	}
	
	public String getMacAddress(int teamNum, int carNum) throws Exception{
		super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		long groupId = getGroupIdbyGroupNum(teamNum);		
		String sql = "select mac_adress from car_basic_info where car_num=? and group_id =?";
		params.add(carNum);
		params.add(groupId);
		
		Map<String,Object> map = findSimpleResult(sql, params);
		String macAddress = null;
		for (Entry<String, Object> entry : map.entrySet()) {  			
	           Object a=entry.getValue();  
	           macAddress = a.toString();
	        }     
		return macAddress;
	}
	
	public List<String> listMacByGroupId(int groupId) throws Exception{
		super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		String sql = "select mac_adress from car_basic_info where group_id =?";
		params.add(groupId);
		List<Map<String,Object>> list = findMoreResult(sql, params);
		
		List<String> list1 = new ArrayList<String>();
		
		for(int i =0; i<list.size(); i++) {
			Map<String,Object> map = list.get(i);
			for(Entry<String, Object> entry : map.entrySet()) {
				Object a = entry.getValue();
				list1.add(i,a.toString());
			}
		}
		return list1;
		
	}
	
//	public boolean JudgeCongestionByTime() throws Exception{
//		jdbcUtils.getConnection();
//		
//		String sql = "select group_id from car_history_data where create_time";
//		boolean flag = false;
//		return flag;
//	}
	
	public boolean addCarHistory(long carId, ReceiveEndPointPara input) throws Exception{
		super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		String sql = "insert into car_history_data (speed,drive_distance,history_hall_count,car_id,send_time,"
				+ "create_time,heading_degree,group_id,change_success) values(?,?,?,?,?,?,?,?,?)";
		
		long currTime = System.currentTimeMillis();
        Timestamp timeObj = new Timestamp(currTime);            //yyyy-MM-dd HH:mm:ss:mis     
        String createTime = timeObj.toString().substring(0,19);       //yyyy-MM-dd HH:mm:ss    
		long groupId = getGroupIdByCarId(carId);
		
		params.add(input.getSpeed());
		params.add(input.getDistance());
		params.add(input.getHoare());
		params.add(carId);
		params.add(input.getTime());
		params.add(createTime);
		params.add(input.getSwerveAngle());
		params.add(groupId);
		params.add(input.isChangeSuccess());
		
		boolean flag = updateByPreparedStatement(sql, params);
		if(flag){
			System.out.println("小车历史数据添加成功！");  
			
		} else{
			System.out.println("小车历史数据添加失败！");
		}
		
		return flag;
	}
	
	public long getCarIdByMac(String macAddress) throws Exception {
		super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		String sql = "select id from car_basic_info where mac_adress=?";
		params.add(macAddress);
		
		long carId=0;
		Map<String, Object> map = findSimpleResult(sql,params);
		for (Entry<String, Object> entry : map.entrySet()) {  	
			Object a=entry.getValue();  
			String b = a.toString();   
			carId = Integer.valueOf(b).intValue();
		}
		return carId;
	}
	
	public double getSingleCarDistance(int teamNumber, int carNumber) throws Exception {
		
		super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		String sql = "select drive_distance from car_history_data where group_id=? and car_id=?";
		params.add(teamNumber);
		params.add(carNumber);
		
		double distance = 0;
		Map<String, Object> map = findSimpleResult(sql, params);
		for(Entry<String, Object> entry : map.entrySet()) {
			Object a = entry.getValue();
			String b = a.toString();
			distance = Double.valueOf(b).doubleValue();
		}
		
		return distance;
	}
	
	public List<Map<String,Object>> listCarIdForFollowing(int teamNumber, double distance) throws Exception {
		super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		String sql = "select car_id from car_history_data where group_id=? and drive_distance<?";
		params.add(getGroupIdbyGroupNum(teamNumber));
		params.add(distance);
		
		List<Map<String, Object>> list = findMoreResult(sql, params);
		
		return list;	
		
	}
	
	public String getMacByCarId(Map<String, Object> map) throws Exception{
		super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		String sql = "select mac_adress from car_basic_info where id=?";
		params.add(map);
		String macAddress ="";
		Map<String, Object> map1 = findSimpleResult(sql, params);
		
		for(Entry<String, Object> entry : map1.entrySet()) {
			Object a  = entry.getValue();
			macAddress = a.toString();
		}
		return macAddress;
	}
	
	public List<Map<String,Object>> listCoorByCarId(long carId) throws Exception {
		super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		String sql = "select coordinate_x,coordinate_y,direction from car_coordinate_info where car_id=?";
		params.add(carId);
		List<Map<String,Object>> list = findMoreResult(sql, params);
		
		return list;
	}
	
	public List<Map<String,Object>> listCoorGroup() throws Exception {
		super.getConnection();
		
		String sql ="select coordinate_x,coordinate_y,direction from car_coordinate_info";		
		List<Map<String,Object>> list = findMoreResult(sql, null);
		
		return list;
		
	}
	
//	public List<Map<String, Object>> listCarIdByGroupId(long groupId) throws Exception {
//		super.getConnection();
//		
//		List<Object> params = new ArrayList<Object>();
//		String sql = "select car_id from";
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		return list;
//	}
	
	public long getGroupIdByCarId(long carId) throws Exception {
		super.getConnection();
		
		String sql = "select group_id from car_basic_info where id=?";
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		
		long groupId = 0;
		Map<String, Object> map = findSimpleResult(sql,params);
		for(Entry<String, Object> entry : map.entrySet()) {
			Object a = entry.getValue();
			groupId = Long.valueOf(a.toString()).longValue();
		}
		return groupId;
	}
	
	public List<String> listMacAdress() throws Exception {
		super.getConnection();
		
		String sql = "select mac_adress from car_basic_info";
		
		List<String> list1 = new ArrayList();
		List<Map<String, Object>> list = findMoreResult(sql, null);
		for(int i=0;i<list.size();i++) {
			Map<String,Object> map = list.get(i);
			for(Entry<String, Object> entry : map.entrySet()) {
				Object a = entry.getValue();
				list1.add(a.toString());
			}
		}
		
		return list1;
	}
	
	public Map<String, Object> getCarDistanceMax(long groupId, long carId) throws Exception{
		super.getConnection();
		
		List<Object> params = new ArrayList<Object>();
		String sql = "select speed,drive_distance from car_history_data where id= ("
				+ "select max(id) from car_history_data where group_id=? and car_id=?)";
		params.add(groupId);
		params.add(carId);
		Map<String, Object> map = findSimpleResult(sql, params);
		return map;
	}
	public long getCarIdByCarNum(long groupId, int carNum) throws Exception{
		super.getConnection();
		
		String sql = "select id from car_basic_info where group_id=? and car_num =?";
		List<Object> params = new ArrayList<Object>();
		params.add(groupId);
		params.add(carNum);
		Map<String, Object> map = findSimpleResult(sql,params);
		
		String scarId = "";
		for(Entry<String, Object> entry : map.entrySet()){
			Object a = entry.getValue();
			scarId = a.toString();			
		}
		long carId = Long.valueOf(scarId).longValue();
		return carId;
	}
	
	public int getCarNumByCarId(long carId) throws Exception{
		super.getConnection();
		
		String sql = "select car_num from car_basic_info where id=?";
		List<Object> params = new ArrayList<Object>();
		params.add(carId);
		Map<String, Object> map = findSimpleResult(sql, params);
		int carNumber = 0;
		for(Entry<String, Object> entry : map.entrySet()){
			Object a = entry.getValue();
			carNumber = Integer.valueOf(a.toString()).intValue();			
		}
		return carNumber;
	}
	
	public int getCarNumByMacAddress(String macAddress) throws Exception{
		super.getConnection();
		
		String sql = "select car_num from car_basic_info where mac_adress=?";
		List<Object> params = new ArrayList<Object>(0);
		params.add(macAddress);
		Map<String, Object> map = findSimpleResult(sql, params);
		int carNumber = 0;
		for(Entry<String, Object> entry : map.entrySet()){
			Object a = entry.getValue();
			carNumber = Integer.valueOf(a.toString()).intValue();
		}
		return carNumber;
	}
	
	public long getGroupIdByMacAddress(String macAddress) throws Exception{
		super.getConnection();
		
		String sql = "select group_id from car_basic_info where mac_adress=?";
		List<Object> params = new ArrayList<Object>();
		params.add(macAddress);
		Map<String, Object> map = findSimpleResult(sql, params);
		long groupId = 0;
		for(Entry<String, Object> entry : map.entrySet()){
			Object a = entry.getValue();
			groupId = Long.valueOf(a.toString());
		}
		return groupId;
	}
	
	public int getGroupNumByGroupId(long groupId) throws Exception{
		super.getConnection();
		
		String sql = "select group_num from t_car_group where id=?";
		List<Object> params = new ArrayList<Object>();
		params.add(groupId);
		Map<String, Object> map = findSimpleResult(sql, params);
		int groupNum = 0;
		for(Entry<String,Object> entry : map.entrySet()){
			Object a = entry.getValue();
			groupNum = Integer.valueOf(a.toString());
		}
		return groupNum;
	}
	
}
