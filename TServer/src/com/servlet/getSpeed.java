package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.SendEndPointPara;
import com.common.CarNumber;
import com.sql.JdbcUtilsImp;

public class getSpeed extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public getSpeed() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		this.doPost(request, response);
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String steamNumber = request.getParameter("teamNumber");
		String scarNumber = request.getParameter("carNumber");
		String sspeed = request.getParameter("speed");
		int carNumber;
		int teamNumber;
		
		SendEndPointPara sendEnd = new SendEndPointPara();
		CarNumber carN = new CarNumber();
		List<SendEndPointPara> outputlist = new List<SendEndPointPara>();
		JdbcUtilsImp imp = new JdbcUtilsImp();
		
		if(!steamNumber.equals("") && steamNumber != null && !scarNumber.equals("") && scarNumber != null
				&& !sspeed.equals("") && sspeed != null) {
			
			if(scarNumber.equals("all")){
				if(steamNumber.equals("all")){//对所有车进行操作
					
					try {
						List<String> list = imp.listMacAdress();
						String st = "";
						String sc ="";
						for(int i=0; i<list.size();i++) {
							sendEnd.setMacAddress(list.get(i));
							sendEnd.setSpeed(sspeed);
							sc = String.valueOf(imp.getCarNumByMacAddress(list.get(i))).toString();
							st = String.valueOf(imp.getGroupNumByGroupId(imp.getGroupIdByMacAddress(list.get(i)))).toString();
							sendEnd.setCarNumber(carN.getCarNumber(st, sc));
							
							outputlist.add(sendEnd);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					SendSpeedGroup(outputlist);
					
				} else {//对一个车队进行操作
					teamNumber = Integer.valueOf(steamNumber).intValue();
					
					try {
						long groupId = imp.getGroupIdbyGroupNum(teamNumber);
						int a = (int) groupId;
						List<String> list = imp.listMacByGroupId(a);
						String sc = "";
						for(int i=0; i<list.size();i++){
							sc = String.valueOf(imp.getCarNumByMacAddress(list.get(i))).toString();
							sendEnd.setMacAddress(list.get(i));
							sendEnd.setSpeed(sspeed);
							sendEnd.setCarNumber(carN.getCarNumber(steamNumber,sc));
							
							outputlist.add(sendEnd);
						}
					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					SendSpeedGroup(outputlist);
					
				}
			} else {//对单个小车进行操作
				teamNumber = Integer.valueOf(steamNumber).intValue();
				carNumber = Integer.valueOf(scarNumber).intValue();
				
				try {
					sendEnd.setMacAddress(imp.getMacAddress(teamNumber, carNumber));
					sendEnd.setSpeed(sspeed);
					sendEnd.setCarNumber(carN.getCarNumber(steamNumber, scarNumber));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				SendSpeedSingle(sendEnd);
				
			}		
			
		} else {
			out.print("get nothing form APP");
		}
		out.flush();
		out.close();
	}
	
	public void SendSpeedGroup(List<SendEndPointPara> outputEndPointParaList){
		IXbeeMessageConnect conn = new IXbeeMessageConnect();
		conn.openComPort("COM7", 9600);
		conn.sendMessages(outputEndPointParaList);
		conn.closeComPort("COM7");		
		
	}
	
	public void SendSpeedSingle(SendEndPointPara outputEndPointPara){
	IXbeeMessageConnect conn = new IXbeeMessageConnect();
	conn.openComPort("COM7", 9600);
	conn.sendMessage(outputEndPointPara);
	conn.closeComPort("COM7");
}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
