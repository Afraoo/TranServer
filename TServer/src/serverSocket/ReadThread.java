package serverSocket;

import java.io.BufferedReader;
import java.net.Socket;

public class ReadThread extends Thread{

	private Socket socket;
	public ReadThread(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
	}
//	
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		BufferedReader br = null;
//		try {
//			//构建流对象
//			br = IOUtil.getReader(socket);
//		} 
//		super.run();
//	}
//	
	
}
