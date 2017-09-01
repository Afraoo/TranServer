package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sql.JdbcUtilsImp;

import net.sf.json.JSONArray;

public class getCarNum extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public getCarNum() {
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

		response.setContentType("text/html");
		System.out.println("这是运行doGET");
		this.doPost(request, response);
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
		System.out.println("这是运行GetCarNum的dopost");
				
		PrintWriter writer = response.getWriter();
		JdbcUtilsImp imp = new JdbcUtilsImp();
		
		String idget = request.getParameter("teamNumber");
		//String ss = new String(request.getParameter("hello").getBytes("ISO-8859-1"),"UTF-8");
//		System.out.println(request.getParameter("hello"));

		
		if(!idget.equals("") && idget !=null){
			
			int groupNum = Integer.valueOf(idget).intValue();
			try {
				List<Map<String, Object>> list = imp.listCarNumByGroupId(groupNum);
				JSONArray jsonArray = JSONArray.fromObject(list);			
				writer.print(jsonArray);				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				writer.print("fail to get data from DB");
				e.printStackTrace();
			}
		} else{
			writer.print("teamNumber is null");
		}

		writer.flush();
		writer.close();
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
