package com.api;

import java.util.List;

public interface IXbeeMessageConnect {

	/**
	 * API模式获取小车信息 
	 */
	public void receiveMessageForAPI();
	
	/**
	 * 广播模式获取小车信息 
	 */
	public void receiveMessageForBroadcast();
	
	/**
	 * 操作单个小车
	 * @param outputEndPointPara
	 */
	public void sendMessage(SendEndPointPara outputEndPointPara);

	/**
	 * 操作复数小车
	 * @param outputEndPointParaList
	 */
	public void sendMessages(List<SendEndPointPara> outputEndPointParaList);

	/**
	 * 开启端口
	 * @param portName
	 * @param baudRate
	 */
	public void openComPort(String portName, int baudRate);
	
    /**
     * 关闭端口	
     * @param portName
     */
	public void closeComPort(String portName);
	
	public void clearComPort(String portName);
	
}
