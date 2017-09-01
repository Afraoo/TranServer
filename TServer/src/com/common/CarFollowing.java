package com.common;

import java.util.List;
import java.util.Map;

import com.api.IXbeeMessageConnect;
import com.api.SendEndPointPara;
import com.sql.JdbcUtilsImp;

public class CarFollowing implements IXbeeMessageConnect{
	
	private boolean flag = false;
	private double distance ;
	private String macAdress;
	private String speed;	

	private List<SendEndPointPara> outputlist;
	private SendEndPointPara output;
	
	public boolean startFollowing(int teamNumber, int carNumber){
		
		JdbcUtilsImp imp = new JdbcUtilsImp();
		try {
			distance = imp.getSingleCarDistance(teamNumber, carNumber);
			List<Map<String,Object>> list = imp.listCarIdForFollowing(teamNumber, distance);
			
			for(int i = 0; i<list.size(); i++) {
				macAdress = imp.getMacByCarId(list.get(i));
				output.setMacAddress(macAdress);
				output.setSpeed(speed);		
				outputlist.set(i, output);
			}
//			IXbeeMessageConnect conn = new IXbeeMessageConnect();
//			conn.openComPort("COM7", 9600);
//			conn.writeMessages(outputlist);
//			conn.closeComPort("COM7");		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("从历史数据表中单个小车行驶距离获取失败！");
			e.printStackTrace();
		}
		
		return flag;		
	}
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getMacAdress() {
		return macAdress;
	}

	public void setMacAdress(String macAdress) {
		this.macAdress = macAdress;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public SendEndPointPara getOutput() {
		return output;
	}

	public void setOutput(SendEndPointPara output) {
		this.output = output;
	}

	public void receiveMessageForAPI() {
		// TODO Auto-generated method stub
		
	}

	public void receiveMessageForBroadcast() {
		// TODO Auto-generated method stub
		
	}

	public void sendMessage(SendEndPointPara outputEndPointPara) {
		// TODO Auto-generated method stub
		
	}

	public void sendMessages(List<SendEndPointPara> outputEndPointParaList) {
		// TODO Auto-generated method stub
		
	}

	public void openComPort(String portName, int baudRate) {
		// TODO Auto-generated method stub
		
	}

	public void closeComPort(String portName) {
		// TODO Auto-generated method stub
		
	}

	public void clearComPort(String portName) {
		// TODO Auto-generated method stub
		
	}

}
