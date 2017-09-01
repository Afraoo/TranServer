package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.IXbeeMessageConnect;
import com.api.SendEndPointPara;
import com.common.CarFollowing;
import com.common.CarNumber;
import com.sql.JdbcUtilsImp;

public class getText extends HttpServlet implements IXbeeMessageConnect{

	/**
	 * Constructor of the object.
	 */
	public getText() {
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
		String sSpeed = request.getParameter("speed");
		String sDistance = request.getParameter("distance");
		String sAngle = request.getParameter("angle");
		String sDirect = request.getParameter("direction");
		String carNumber = "";
		
//		System.out.println("�����ǣ�"+sDirect);
		System.out.println("�����ǣ�"+sDirect);
		
					
		
		JdbcUtilsImp imp = new JdbcUtilsImp();
		CarNumber carN = new CarNumber();
		
		//�ж��ǶԵ���С�����в������ǶԳ��ӽ��в���
		if(!steamNum.equals("") && steamNum != null && !steamNum.equals("All") && !sCarNum.equals("") 
				&& sCarNum != null &&  sDistance.equals("no")  && !sAngle.equals("") && sAngle != null){			
			//�Ե���С����������
			if( !sSpeed.equals("") && sSpeed != null && !sDirect.equals("") && sDirect != null){
				int car = Integer.valueOf(sCarNum).intValue();
				int angle = Integer.valueOf(sAngle).intValue();
				int team = Integer.valueOf(steamNum).intValue();
				
				SendEndPointPara singleOutput = new SendEndPointPara();
				singleOutput.setSpeed(sSpeed);
				singleOutput.setSwerveAngle(sAngle);
				carNumber = carN.getCarNumber(steamNum, sCarNum);
				singleOutput.setCarNumber(carNumber);
				
				if(sDirect.equals("up"))
				{
					singleOutput.setSwervePara("0");//0 ֱ�У�1 ��ת��2 ��ת
				} else if(sDirect.equals("left")){
					singleOutput.setSwervePara("1");
				} else if(sDirect.equals("right")){
					singleOutput.setSwervePara("2");
				}
				
				try {
					singleOutput.setMacAddress(imp.getMacAddress(team,car));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					out.print("cannot get data from database");
					e.printStackTrace();
				}				
				
				//��ʱ�Ը���С����ĳ������и������
				CarFollowing carfollow = new CarFollowing();
				carfollow.setSpeed(sSpeed);
				carfollow.startFollowing(team,car);
				
				
				sendToCar(singleOutput);//���ýӿ���С����������
				
				out.print("single success");
				
			} else if((sSpeed.equals("") || sSpeed == null) && !sDirect.equals("") && sDirect != null){
				out.print("δ��APP����ٶ�");
			} else if(!sSpeed.equals("") && sSpeed != null && (sDirect.equals("") || sDirect == null)){
				out.print("δ��APP��÷���");
			} else {
				out.print("δ��APP����ٶȺͷ���");
			}		
			
		} else if(!steamNum.equals("") && steamNum != null && steamNum.equals("All") 
				&& sCarNum.equals("no") && sAngle.equals("no") && !sDistance.equals("") 
				&& sDistance != null && !sSpeed.equals("") && sSpeed != null){
			//������С�����в���
			List<SendEndPointPara> outputlist = new List<SendEndPointPara>();
			SendEndPointPara output = new SendEndPointPara();
			
			List<String> listM = new ArrayList<String>();
			List<String> list = new ArrayList<String>();
			List<String> list1 = new ArrayList<String>();
			try {
				List<Map<String,Object>> listG = imp.listCarGroupId();
				for(Map<String, Object> i : listG){//��json�����е�valueȡ��������list1��
					for(Entry entry : i.entrySet()){
						Object a = entry.getValue();
						list1.add(a.toString());//���ӱ��
					}
				}
				
				for(int j=0; j<list1.size(); j++){		
					listM = imp.listMacByGroupId(Integer.valueOf(list1.get(j)).intValue());	//�õ����еĳ�����С����mac��ַ
					
					for(int k=0; k<listM.size();k++) {
						list.add(k,listM.get(k));
					}
					
				}				
				
				String st = "";
				String sc = "";
				for(int i=0;i<list.size();i++){
					st = listG.get(i).toString();
					int a = imp.getCarNumByCarId(imp.getCarIdByMac(list.get(i).toString()));
					sc = String.valueOf(a);
					output.setMacAddress(list.get(i).toString());
					output.setSpeed(sSpeed);
					output.setCarNumber(carN.getCarNumber(st, sc));
					//output.setAngle(0);
					
					outputlist.add(output);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				out.print("cannot get data from DB");
				e.printStackTrace();
			}
			sendToCarGroup(outputlist);		

			
			out.print("complex success");
			
		}else if(!steamNum.equals("") && steamNum != null && !steamNum.equals("All") 
				&& sCarNum.equals("no") && sAngle.equals("no") && !sDistance.equals("") 
				&& sDistance != null && !sSpeed.equals("") && sSpeed != null){
			//��һ�����ӵ�С�����в���
			List<SendEndPointPara> outputlist = new List<SendEndPointPara>();
			SendEndPointPara output = new SendEndPointPara();
			
			int team = Integer.valueOf(steamNum).intValue();
			List<String> list = new ArrayList<String>();			
			try {
				Long groupId = imp.getGroupIdbyGroupNum(team);
				list = imp.listMacByGroupId(groupId.intValue());
				List<Map<String, Object>> listc = imp.listCarNumByGroupId(new Long(groupId).intValue());
				
				
				String sc ="";
				for(int i=0;i<list.size();i++){
					int a = imp.getCarNumByMacAddress(list.get(i).toString());
					sc = String.valueOf(a);
					output.setMacAddress(list.get(i).toString());
					output.setSpeed(sSpeed);
					output.setCarNumber(carN.getCarNumber(steamNum, sc));
					//output.setAngle(0);
					
					outputlist.add(output);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendToCarGroup(outputlist);				
			
		} else {
			out.print("δ��APP����κ�����");;
		}

		out.flush();
		out.close();
	}
	
	public void sendToCar(SendEndPointPara outputEndPointPara){
		IXbeeMessageConnect conn = new IXbeeMessageConnect();
		conn.openComPort("COM7", 9600);
		conn.sendMessage(outputEndPointPara);
		conn.closeComPort("COM7");
	}
	
	public void sendToCarGroup(List<SendEndPointPara> outputEndPointParaList){
		IXbeeMessageConnect conn = new IXbeeMessageConnect();
		conn.openComPort("COM7", 9600);
		conn.sendMessages(outputEndPointParaList);
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

	public void receiveMessageForAPI() {
		// TODO Auto-generated method stub
		
	}

	public void receiveMessageForBroadcast() {
		// TODO Auto-generated method stub
		
	}

	public void sendMessage(SendEndPointPara outputEndPointPara) {
		// TODO Auto-generated method stub
		
	}

	public void sendMessages(List<SendEndPointPara> outputEndPointParaList) {
		// TODO Auto-generated method stub
		
	}

	public void openComPort(String portName, int baudRate) {
		// TODO Auto-generated method stub
		
	}

	public void closeComPort(String portName) {
		// TODO Auto-generated method stub
		
	}

	public void clearComPort(String portName) {
		// TODO Auto-generated method stub
		
	}

}
