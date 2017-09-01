package com.api;

public class ReceiveEndPointPara {

	// С��mac��ַ
	private String macAddress;
	
	// С����ʱ�ٶ�
	private String speed;
	
	// С����ʻ����
	private String distance;
	
	// С��������Ӧ������
	private int hoare;
	
	// С��ƫ��Ƕ�
	private String swerveAngle;
	
	// С����ʱ
	private long time;
	
	// �Ƿ���복��
	private boolean changeSuccess = false;
	
	// ���ݰ���������
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
