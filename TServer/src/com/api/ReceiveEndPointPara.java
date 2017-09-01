package com.api;

public class ReceiveEndPointPara {

	// 小车mac地址
	private String macAddress;
	
	// 小车即时速度
	private String speed;
	
	// 小车行驶距离
	private String distance;
	
	// 小车霍尔感应器计数
	private int hoare;
	
	// 小车偏向角度
	private String swerveAngle;
	
	// 小车计时
	private long time;
	
	// 是否进入车道
	private boolean changeSuccess = false;
	
	// 数据包解析正常
	private boolean isNormal = false;
	
	public boolean isChangeSuccess() {
		return changeSuccess;
	}

	public void setChangeSuccess(boolean changeSuccess) {
		this.changeSuccess = changeSuccess;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public boolean isNormal() {
		return isNormal;
	}

	public void setNormal(boolean isNormal) {
		this.isNormal = isNormal;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public int getHoare() {
		return hoare;
	}

	public void setHoare(int hoare) {
		this.hoare = hoare;
	}

	public String getSwerveAngle() {
		return swerveAngle;
	}

	public void setSwerveAngle(String swerveAngle) {
		this.swerveAngle = swerveAngle;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
