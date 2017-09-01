package com.api;

public class SendEndPointPara {
	
	// 小车MAC地址
	private String macAddress;
	
	// 小车即时速度
	private String speed;
	
	// 小车转向：0 直行，1 左转，2 右转
	private String swervePara;
	
	// 小车转向角度
	private String swerveAngle;
	
	// 小车获取的霍尔读数
	private String hoare;
	
	// 霍尔读数对应的距离
	private String hoareDisatnce;
	
	//0：正常行驶；1：分流；2：合流
	private String swerveFlow;
	
	private String carNumber;
	
	//车队+车号
	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getSwerveFlow() {
		return swerveFlow;
	}

	public void setSwerveFlow(String swerveFlow) {
		this.swerveFlow = swerveFlow;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getSwervePara() {
		return swervePara;
	}

	public void setSwervePara(String swervePara) {
		this.swervePara = swervePara;
	}

	public String getSwerveAngle() {
		return swerveAngle;
	}

	public void setSwerveAngle(String swerveAngle) {
		this.swerveAngle = swerveAngle;
	}

	public String getHoare() {
		return hoare;
	}

	public void setHoare(String hoare) {
		this.hoare = hoare;
	}

	public String getHoareDisatnce() {
		return hoareDisatnce;
	}

	public void setHoareDisatnce(String hoareDisatnce) {
		this.hoareDisatnce = hoareDisatnce;
	}
}
