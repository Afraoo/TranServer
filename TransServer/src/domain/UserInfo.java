package domain;

import java.io.Serializable;

public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 下面为将要从数据库输出的属性名，必须与数据库中的属性名完全一样
	 */
	private long id;
	private String mac_adress;
	private int car_num;
	private int group_num;
	
	
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", macAdress=" + mac_adress
				+ ", carNum=" + car_num + ", groupNum=" + group_num + "]";
	}


	
	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getMac_adress() {
		return mac_adress;
	}



	public void setMac_adress(String mac_adress) {
		this.mac_adress = mac_adress;
	}



	public int getCar_num() {
		return car_num;
	}



	public void setCar_num(int car_num) {
		this.car_num = car_num;
	}



	public int getGroup_num() {
		return group_num;
	}



	public void setGroup_num(int group_num) {
		this.group_num = group_num;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public UserInfo() {
		// TODO Auto-generated constructor stub
	}

}
