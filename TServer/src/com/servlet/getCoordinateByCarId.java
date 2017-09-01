package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.sql.JdbcUtilsImp;

public class getCoordinateByCarId extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public getCoordinateByCarId() {
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
		
		String sCarId = request.getParameter("carId");
		
		JdbcUtilsImp imp = new JdbcUtilsImp();
		if(!sCarId.equals("") && sCarId != null) {
			long carId = Long.valueOf(sCarId).longValue();
			
			try {
				List<Map<String,Object>> list = imp.listCoorByCarId(carId);
				JSONArray jsonArray = JSONArray.fromObject(list);
				out.print(jsonArray);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				System.out.println("fail to get data from DB");
				out.print("fail to get data from DB");
				e.printStackTrace();
			}
		} else {
			System.out.println("fail to get carId from APP");
			out.print("carId is null");
		}	
		
		out.flush();
		out.close();
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
