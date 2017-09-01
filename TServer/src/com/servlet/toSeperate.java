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

public class toSeperate extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public toSeperate() {
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
		String sOrder = request.getParameter("order");
		System.out.println("分流/合流："+sOrder);
		
		SendEndPointPara sendEnd = new SendEndPointPara();
		CarNumber carNumber = new CarNumber();
		if(!sOrder.equals("") && sOrder != null){
			int order = Integer.valueOf(sOrder).intValue();
			if(order == 1){   //0：正常行驶， 1：分流; 2:合流
				sendEnd.setSwerveFlow(sOrder);
//				sendEnd.setCarNumber(carNumber.getCarNumber(teamNum, carNum));
				
				out.print("seperate success");
			} else if(order == 2) {
				sendEnd.setSwerveFlow(sOrder);
				
				out.print("interflow success");
			} else {
				
			}
		} else {
			out.print("fail to seperate");
		}
		
		out.flush();
		out.close();
	}
	
	public void sendSperate(SendEndPointPara sendEndPointPara) {
		IXbeeMessageConnect conn = new IXbeeMessageConnect();
		conn.openComPort("COM7", 9600);
		conn.sendMessage(sendEndPointPara);
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
