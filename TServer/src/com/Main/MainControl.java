package com.Main;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import serverSocket.ReadThread;

import com.common.JudgeCongestion;
import com.sql.JdbcUtils;
import com.sql.JdbcUtilsImp;

public class MainControl {

	private String flag="";
	
	public MainControl() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
////		JudgeCongestion jc = new JudgeCongestion();
////		jc.Judge();
//	
//		JdbcUtilsImp jdbc = new JdbcUtilsImp();
//		int a =1;
////		List<Map<String, Object>> list  =  jdbc.getMacAddress(1,1);
//		System.out.println(jdbc.listCarGroupId());
//		
//		JudgeCongestion judge = new JudgeCongestion();
//		JudgeChannel judgec = new JudgeChannle();//判断内外道接口
		
//		Runnable runnable = new Runnable() {  
//            public void run() {  
//                // task to run goes here
//            	try {
////                	judgec.judgeC(); //调用判断内外道接口方法
////            		judge.Judge();
//                    System.out.println("Hello !!");  
//            	} catch(Throwable t) {
//            		
//            	}
//            }  
//        };  
//        ScheduledExecutorService service = Executors  
//                .newSingleThreadScheduledExecutor();  
//        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
//        service.scheduleAtFixedRate(runnable, 10, 60, TimeUnit.SECONDS);
		
//		ServerSocket server = new ServerSocket(9999);
//		System.out.println("服务器启动OK！");
//		Socket socket = null;
//		
//		while(true) {
//			socket = server.accept();
//			System.out.println("客户端已连接");
//			//读
//			new ReadThread(socket).start();
//		}
	}
	

		
}
