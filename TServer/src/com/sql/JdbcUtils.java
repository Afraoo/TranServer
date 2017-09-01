package com.sql;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.entity.CarBasicInfo;


import zuojie.esql.Esql;


public class JdbcUtils extends BaseDao 
{

	//�������ݿ���û���
	private final String USERNAME = "transuser";
	//�������ݿ������
	private final String PASSWORD = "123";
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
	//���ݿ������װ
	//private Esql esql;
	
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
			e.printStackTrace();
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
		if(params != null && !params.isEmpty())
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
	
	/**
	 * 
	 * ��ѯ���ص�����¼
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findSimpleResult(String sql, List<Object>params) 
			throws SQLException{
		
		Map<String, Object> map = new HashMap<String,Object>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if(params != null && !params.isEmpty()){
			for(int i = 0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();//���ز�ѯ���
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();//����еĳ���
		while(resultSet.next()){
			for(int i=0;i<col_len;i++){
				String cols_name = metaData.getColumnName(i+1);//��õ�ǰ�е�����
				Object cols_value = resultSet.getObject(cols_name);
				if(cols_value == null){
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
		}
		return map;		
	}
	
	
	/**
	 * 
	 * ��ѯ���ض��м�¼
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>> findMoreResult(String sql,
			List<Object>params) throws SQLException {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if(params != null && !params.isEmpty()){
			for(int i = 0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery(); //���ز�ѯ���
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();//����г���
		while (resultSet.next()) {
			Map<String,Object> map = new HashMap<String, Object>();
			for(int i=0;i<cols_len;i++){
				String cols_name = metaData.getColumnName(i+1);
				Object cols_value = resultSet.getObject(cols_name);
				if(cols_value == null){
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}
		return list;		
	}
	
	//jdbc�ķ�װ�����÷����������װ
	public <T> T findSimpleRefResult(String sql, List<Object> params, Class<T> cls) throws Exception{
		
		T resultObject = null;
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if(params != null && !params.isEmpty()){
			for(int i = 0; i<params.size(); i++){
				pstmt.setObject(index, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();//��������
		while(resultSet.next()){
			//ͨ��������ƴ���ʵ��
			resultObject = cls.newInstance();
			for(int i=0;i<cols_len;i++){
				String cols_name = metaData.getColumnName(i+1);
				Object cols_value = resultSet.getObject(cols_name);//ȡ��������Ӧ������
				if(cols_value == null){
					cols_value = "";
				}
				//ͨ����������ȡjava BEAN���е������ֶ�
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true);//��javabean �ķ���privateȨ��
				field.set(resultObject, cols_value);
			}
		}
		return resultObject;
		
	}
	
	/**
	 * 
	 * ͨ��������Ʒ������ݿ�
	 * 
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> findMoreRefResult(String sql, List<Object> params, 
			Class<T> cls) throws Exception {
		List<T> list = new ArrayList<T>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i=0; i<params.size(); i++) {
				pstmt.setObject(index++,  params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			T resultObject = cls.newInstance();
			for (int i=0; i<cols_len; i++) {
				String cols_name = metaData.getColumnName(i+1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true);
				field.set(resultObject, cols_value);
			}
			list.add(resultObject);
		}
		return list;		
	}
	
	/**
	 * �ر����ݿ������
	 */
	public void releaseConn() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * ����С��������Ϣ��
	 * 
	 * @param carInfo
	 * @throws Exception 
	 */
	public void updateCarInfo(long carId,CarBasicInfo carInfo) throws Exception 
	{		
		String sql = "update car_basic_info set mac_adress=? car_num=? group_id=? create_time=? update_time=? where id=?";
		
		esql.update(sql, carInfo.getMac_adress(),carInfo.getCar_num(),carInfo.getGroup_num(),carInfo.getCreateTime(),
				carInfo.getUpdateTime(),carId);	
	}
	
	/**
	 * 
	 * ʵ��С��������Ϣ�������¼��
	 * 
	 * @param carInfo
	 * @return
	 * @throws Exception
	 */
	public long addCarInfo(CarBasicInfo carInfo) throws Exception
	{
		String sql = "insert into car_basic_info (mac_adress, car_num, group_id, create_time, update_time) values(?,?,?,?,?,?)";
		esql.update(sql, carInfo.getMac_adress(),carInfo.getCar_num(),carInfo.getGroup_num(),carInfo.getCreateTime(),
				carInfo.getUpdateTime());
		return getGeneratedId("car_basic_info");
	}
	
	


}
