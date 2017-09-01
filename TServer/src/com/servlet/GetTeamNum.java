package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.sql.JdbcUtilsImp;

public class GetTeamNum extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetTeamNum() {
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

		System.out.println("这是运行doGET");
		this.doPost(request, response);
//		response.setContentType("text/html;charset=UTF-8");
//		response.getWriter().write("收到请求");
	
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
		System.out.println("这是运行dopost");
			
		PrintWriter writer = null;
		JdbcUtilsImp imp = new JdbcUtilsImp();
		
		//int c= 0;
		try {
			List<Map<String,Object>> list = imp.listCarGroupId();
			JSONArray jsonArray = JSONArray.fromObject(list);

			writer = response.getWriter();
			writer.print(jsonArray);			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			writer.print("fail to get data form DB");
			e.printStackTrace();
		}
		writer.flush();
		writer.close();
		
		
	
		
        //doResponse(response);
//		PrintWriter out = response.getWriter();
//		String str = request.getParameter("Ask");
//		
//		if (!str.equals("")) {
//			out.println("OK");
//		}else {
//			out.println("Wrong");
//		}
//		
//		out.flush();
//		out.close();
	
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
