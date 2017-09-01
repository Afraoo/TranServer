package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.IXbeeMessageConnect;
import com.api.SendEndPointPara;
import com.common.CarNumber;
import com.sql.JdbcUtilsImp;

public class toStop extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public toStop() {
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

		response.setContentType("text/html;charset=utf-8");
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

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String steamNum = request.getParameter("teamNumber");
		String sCarNum = request.getParameter("carNumber");		
		
		JdbcUtilsImp imp = new JdbcUtilsImp();
		CarNumber carNumber = new CarNumber();
		if(!steamNum.equals("") && steamNum != null && !sCarNum.equals("") && sCarNum != null){
			
			int team = Integer.valueOf(steamNum).intValue();
			int car = Integer.valueOf(sCarNum).intValue();
			
			System.out.println("这是运行toStop:");		
			SendEndPointPara singleOutput = new SendEndPointPara();
			try {
				singleOutput.setMacAddress(imp.getMacAddress(team,car));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				out.print("fail to get macAddress from DB");
				e.printStackTrace();
			}
			singleOutput.setCarNumber(carNumber.getCarNumber(steamNum, sCarNum));
			singleOutput.setSpeed("0");
			
			sendStop(singleOutput);
			out.print("successfully stop");
		} else if(steamNum.equals("") || steamNum == null || sCarNum.equals("") || sCarNum == null){
			out.print("teamNumber or carNumber is null");
		} else {
			out.print("fail to stop");
		}
		out.flush();
		out.close();
	}

	public void sendStop(SendEndPointPara outputEndPointPara) {
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
