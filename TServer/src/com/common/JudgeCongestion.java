package com.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;





import java.util.Map.Entry;

import com.sql.JdbcUtilsImp;

public class JudgeCongestion {

	private int teamNum;
	private int carNum;
	public JudgeCongestion() {
		// TODO Auto-generated constructor stub
	}
	
	JdbcUtilsImp imp = new JdbcUtilsImp();
	public void Judge(){
		try {
			List<Map<String, Object>> group = imp.listCarGroupId();
			List<String> list = new ArrayList<String>();
			for(int i = 0; i<group.size(); i++) {						
				Map<String,Object> a = group.get(i);
				for (Entry<String, Object> entry : a.entrySet()){
					Object b = entry.getValue();
					list.add(i, b.toString());
				}				
			}
			
			for(int k=0; k<list.size(); k++){
				String groupNum = list.get(k);
				List<Map<String, Object>> car = imp.listCarNumByGroupId(Integer.valueOf(groupNum).intValue());
				for(int j = 0; j<car.size(); j++){
					Map<String, Object> carId = car.get(j);
					System.out.println(carId);
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("从数据库获取数据失败");
			e.printStackTrace();
		}    	
	}

}
