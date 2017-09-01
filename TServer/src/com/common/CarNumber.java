package com.common;

public class CarNumber {

	public String getCarNumber(String teamNum, String carNum){
		String carNumber = "";
		if(teamNum.length()<2){
			if(carNum.length()<2){
				carNumber = "0"+teamNum+"0"+carNum;
			} else {
				carNumber = "0"+teamNum +carNum;
			}
		} else {
			if(carNum.length()<2){
				carNumber = teamNum+"0"+carNum;
			} else {
				carNumber = teamNum + carNum;
			}
		}
		
		return carNumber;
	}

}
