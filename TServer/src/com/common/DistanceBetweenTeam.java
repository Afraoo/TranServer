package com.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.api.SendEndPointPara;
import com.sql.JdbcUtilsImp;

public class DistanceBetweenTeam extends TimerTask{

	 protected final Log logger = LogFactory.getLog(getClass());  
	  
	    private static boolean isRunning = false;  
	  
	    @Override  
	    public void run() {   
	  
	        if (!isRunning) {  
	  
	            isRunning = true;  
	            logger.info("开始检测车队之间的距离");  
	            ComputeDistance();
	            isRunning = false;  
	            logger.info("任务执行结束。");  
	  
	        } else {  
	            logger.info("上一次任务执行还未结束，本次任务不能执行。");  
	        }  
	  
	    }  
	    
	    public void ComputeDistance(){
	    	JdbcUtilsImp imp = new JdbcUtilsImp();
	    	try {
	    		//从第一个车队开始，得到前面车队最后一辆车的行驶距离
	    		long groupId1 = 0;
	    		List<Map<String,Object>> listGroup = imp.listCarGroupId();
	    		for(int i=0;i<listGroup.size(); i++){
	    			Map<String,Object> map1 = listGroup.get(i);
		    		for(Entry<String,Object> entry : map1.entrySet()){
		    			Object a = entry.getValue();
		    			groupId1 = Long.valueOf(a.toString());
		    		}		    		
		    		int carNum1 = 0;
		    		List<Map<String,Object>> listCar1 = imp.listCarNumByGroupId(new Long(groupId1).intValue());
		    		Map<String,Object> mapcar1 = listCar1.get(listCar1.size()-1);
		    		for(Entry<String,Object> entry : mapcar1.entrySet()){
		    			Object a = entry.getValue();
		    			carNum1 = Integer.valueOf(a.toString());
		    		}
		    		long carId1 = imp.getCarIdByCarNum(groupId1, carNum1);
		    		Map<String, Object> firstMap = imp.getCarDistanceMax(groupId1, carId1);
		    		String sfirstdistance = firstMap.get("drive_distance").toString();
		    		double firstdistance = Double.valueOf(sfirstdistance).doubleValue();
		    		
		    		
		    		//得到下一个车队中第一辆车的行驶距离
		    		long groupId2 = 0;
		    		Map<String,Object> map2 = listGroup.get(i+1);
		    		for(Entry<String,Object> entry : map2.entrySet()){
		    			Object a = entry.getValue();
		    			groupId2 = Long.valueOf(a.toString());
		    		}		    		
		    		int carNum2 = 0;
		    		List<Map<String,Object>> listCar2 = imp.listCarNumByGroupId(new Long(groupId2).intValue());
		    		Map<String,Object> mapcar2 = listCar2.get(0);
		    		for(Entry<String,Object> entry : mapcar2.entrySet()){
		    			Object a = entry.getValue();
		    			carNum2 = Integer.valueOf(a.toString());
		    		}
		    		long carId2 = imp.getCarIdByCarNum(groupId2, carNum2);
		    		Map<String, Object> lastMap = imp.getCarDistanceMax(groupId1, carId1);
		    		String slastdistance = lastMap.get("drive_distance").toString();
		    		String sspeed = lastMap.get("speed").toString();
		    		double lastdistance = Double.valueOf(slastdistance);
		    		
		    		double product = firstdistance - lastdistance;
		    		if(0<product && product<0.05){
//		    			List<SendEndPointPara> outputlist = new List<SendEndPointPara>();
		    			SendEndPointPara output = new SendEndPointPara();
		    			
		    			List<String> listM = new ArrayList<String>();
		    			List<String> list = new ArrayList<String>();
		    			List<String> list1 = new ArrayList<String>();
		    			try {
		    				
	    					listM = imp.listMacByGroupId(new Long(groupId2).intValue());	//得到所有的车队中小车的mac地址
	    					
	    					for(int k=0; k<listM.size();k++) {
	    						list.add(k,listM.get(k));
	    					}	    					
		    				for(int k=0;k<list.size();k++){
		    					output.setMacAddress(list.get(k).toString());
		    					output.setSpeed(sspeed);
		    					
//		    					outputlist.add(output);
		    				}
		    			} catch (Exception e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			}
//		    			sendToCarGroup(outputlist);	
		    		}
		    			
	    		}
	    		
	    		
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
//		public void sendToCarGroup(List<SendEndPointPara> outputEndPointParaList){
//		IXbeeMessageConnect conn = new IXbeeMessageConnect();
//		conn.openComPort("COM7", 9600);
//		conn.sendMessages(outputEndPointParaList);
//		conn.closeComPort("COM7");		
//		
//	}
}
