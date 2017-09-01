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

	//定义数据库的用户名
	private final String USERNAME = "transuser";
	//定义数据库的密码
	private final String PASSWORD = "123";
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
	//数据库操作封装
	//private Esql esql;
	
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
			e.printStackTrace();
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
		if(params != null && !params.isEmpty())
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
	
	/**
	 * 
	 * 查询返回单条记录
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
		resultSet = pstmt.executeQuery();//返回查询结果
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();//获得列的长度
		while(resultSet.next()){
			for(int i=0;i<col_len;i++){
				String cols_name = metaData.getColumnName(i+1);//获得当前列的名称
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
	 * 查询返回多行记录
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
		resultSet = pstmt.executeQuery(); //返回查询结果
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();//获得列长度
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
	
	//jdbc的封装可以用反射机制来封装
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
		int cols_len = metaData.getColumnCount();//声明长度
		while(resultSet.next()){
			//通过反射机制创建实例
			resultObject = cls.newInstance();
			for(int i=0;i<cols_len;i++){
				String cols_name = metaData.getColumnName(i+1);
				Object cols_value = resultSet.getObject(cols_name);//取出列所对应的属性
				if(cols_value == null){
					cols_value = "";
				}
				//通过列名来获取java BEAN中列的属性字段
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true);//打开javabean 的访问private权限
				field.set(resultObject, cols_value);
			}
		}
		return resultObject;
		
	}
	
	/**
	 * 
	 * 通过反射机制访问数据库
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
	 * 关闭数据库的链接
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
	 * 更新小车基本信息表
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
	 * 实现小车基本信息表的数据录入
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
