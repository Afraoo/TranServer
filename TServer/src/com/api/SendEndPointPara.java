package com.api;

public class SendEndPointPara {
	
	// С��MAC��ַ
	private String macAddress;
	
	// С����ʱ�ٶ�
	private String speed;
	
	// С��ת��0 ֱ�У�1 ��ת��2 ��ת
	private String swervePara;
	
	// С��ת��Ƕ�
	private String swerveAngle;
	
	// С����ȡ�Ļ�������
	private String hoare;
	
	// ����������Ӧ�ľ���
	private String hoareDisatnce;
	
	//0��������ʻ��1��������2������
	private String swerveFlow;
	
	private String carNumber;
	
	//����+����
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
