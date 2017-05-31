package com.jdbc.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcUtils 
{

	//�������ݿ���û���
	private final String USERNAME = "root";
	//�������ݿ������
	private final String PASSWORD = "admin";
	//�������ݿ��������Ϣ
	private final String DRIVER = "org.postgresql.Driver";
	//�������ݿ�ķ��ʵ�ַ
	private final String URL = "jdbc:postgresql://localhost:5432/Trans";
	//�������ݿ������
	private Connection connection;
	//����sql����ִ�ж���
	private PreparedStatement pstmt;
	//�����ѯ���صĽ������
	private ResultSet resultSet;
	
	public JdbcUtils() 
	{
		// 
		try
		{
			Class.forName(DRIVER);
			System.out.println("ע�������ɹ�����");
		}
		catch(Exception e)
		{			
			//TODO:handle exception
		}

	}
	//���������ݿ������
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
	 * ��ɶ����ݿ�ı����ӡ�ɾ�����޸ĵĲ���
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	
	public boolean updateByPreparedStatement(String sql, List<Object>params) throws SQLException
	{
		boolean flag = false;
		int result = -1; //���û�ִ����ӡ��޸ġ�ɾ��������ʱ����Ӱ�����ݿ������
		pstmt = connection.prepareStatement(sql);
		int index = 1; //��ʾռλ���ĵ�һ��λ��
		if(params != null && params.isEmpty())
		{
			for(int i=0;i<params.size();i++)
			{
				pstmt.setObject(index++, params.get(i));
			}
		}
		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;//result����0�������Ϊtrue,����Ϊfalse
		return flag;		
	}
	
	public static void main(String[] args)
	{
		//TODO Auto-generated method stub
		JdbcUtils jdbcUtils = new JdbcUtils();
	}

}
