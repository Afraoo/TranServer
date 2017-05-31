package com.jdbc.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcUtils 
{

	//定义数据库的用户名
	private final String USERNAME = "root";
	//定义数据库的密码
	private final String PASSWORD = "admin";
	//定义数据库的驱动信息
	private final String DRIVER = "org.postgresql.Driver";
	//定义数据库的访问地址
	private final String URL = "jdbc:postgresql://localhost:5432/Trans";
	//定义数据库的链接
	private Connection connection;
	//定义sql语句的执行对象
	private PreparedStatement pstmt;
	//定义查询返回的结果集合
	private ResultSet resultSet;
	
	public JdbcUtils() 
	{
		// 
		try
		{
			Class.forName(DRIVER);
			System.out.println("注册驱动成功！！");
		}
		catch(Exception e)
		{			
			//TODO:handle exception
		}

	}
	//定义获得数据库的链接
	public Connection getConnection()
	{
		try{
			connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
		}catch(Exception e){
			//TODO:handle exception
		}
		return connection;
	}
	
	/**
	 * 完成对数据库的表的添加、删除和修改的操作
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	
	public boolean updateByPreparedStatement(String sql, List<Object>params) throws SQLException
	{
		boolean flag = false;
		int result = -1; //当用户执行添加、修改、删除操作的时候所影响数据库的行数
		pstmt = connection.prepareStatement(sql);
		int index = 1; //表示占位符的第一个位置
		if(params != null && params.isEmpty())
		{
			for(int i=0;i<params.size();i++)
			{
				pstmt.setObject(index++, params.get(i));
			}
		}
		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;//result大于0的情况下为true,否则为false
		return flag;		
	}
	
	public static void main(String[] args)
	{
		//TODO Auto-generated method stub
		JdbcUtils jdbcUtils = new JdbcUtils();
	}

}
