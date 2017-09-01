//package com.Main;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class PCServer implements Runnable {  
//    public static final String SERVERIP = "192.168.1.203";  
//    public static final int SERVERPORT = 8080;  
//  
//    @SuppressWarnings("resource")
//	public void run() {  
//        try {  
//            System.out.println("S: Connecting...");  
//  
//            ServerSocket serverSocket = new ServerSocket(SERVERPORT);  
//            while (true) {  
//                // 等待接受客户端请求   
//                Socket client = serverSocket.accept();  
//  
//                System.out.println("S: Receiving...");  
//                  
//                try {  
//                    // 接受客户端信息  
//                    BufferedReader in = new BufferedReader(  
//                            new InputStreamReader(client.getInputStream()));  
//                      
//                    // 发送给客户端的消息   
//                    PrintWriter out = new PrintWriter(new BufferedWriter(
//                    		new OutputStreamWriter(client.getOutputStream())),true);  
//                      
//                    System.out.println("S: 111111");  
//                    String str = in.readLine(); // 读取客户端的信息  
//                    System.out.println("S: 222222");  
//                    if (str != null ) {  
//                        // 设置返回信息，把从客户端接收的信息再返回给客户端  
//                        out.println("You sent to server message is:" + str);  
//                        out.flush();  
//                          
//                        // 把客户端发送的信息保存到文件中  
//                        File file = new File ("C://android.txt");  
//                        FileOutputStream fops = new FileOutputStream(file);   
//                        byte [] b = str.getBytes();  
//                        for ( int i = 0 ; i < b.length; i++ )  
//                        {  
//                            fops.write(b[i]);  
//                        }  
//                        System.out.println("S: Received: '" + str + "'");  
//                    } else {  
//                        System.out.println("Not receiver anything from client!");  
//                    }  
//                } catch (Exception e) {  
//                    System.out.println("S: Error 1");  
//                    e.printStackTrace();  
//                } finally {  
//                    client.close();  
//                    System.out.println("S: Done.");  
//                }  
//            }  
//        } catch (Exception e) {  
//            System.out.println("S: Error 2");  
//            e.printStackTrace();  
//        }  
//    }  
//      
//    public static void main(String [] args ) {  
//        Thread desktopServerThread = new Thread(new PCServer());  
//        desktopServerThread.start();  
//  
//    }  
//}  
////package com.Main;
////
////import java.io.DataInputStream;
////import java.io.DataOutputStream;
////import java.io.IOException;
////import java.io.InputStream;
////import java.io.OutputStream;
////import java.net.ServerSocket;
////import java.net.Socket;
////
////public class PCServer {
////    private ServerSocket mServer;
////    private int port = 5566;
////    private boolean mRunning = false;
////
////
////    public PCServer() throws IOException {
////        mServer = new ServerSocket(port);
////    }
////
////
////    public void startServer() {
////        mRunning = true;
////        while (mRunning) {
////            try {
////                Socket client = mServer.accept();
////                System.out.println("已经有一个客户端连接上了");
////                CommniucationThread thread = new CommniucationThread(client);
////                thread.start();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        }
////    }
////
////
////    public void setRunning(boolean running) {
////        mRunning = running;
////    }
////
////
////    public boolean getRunning() {
////        return mRunning;
////    }
////
////
////    public void stopServer() {
////        mRunning = false;
////        if (mServer != null) {
////            try {
////                mServer.close();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        }
////    }
////
////
////    public static void main(String[] args) {
////        try {
////            PCServer server = new PCServer();
////            server.startServer();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
////
////
////    private class CommniucationThread extends Thread {
////
////
////        private Socket mSocket;
////
////
////        public CommniucationThread(Socket socket) {
////            mSocket = socket;
////        }
////
////
////        public void run() {
////                try {
////                    InputStream is = mSocket.getInputStream();
////                    DataInputStream dis = new DataInputStream(is);
////                    String result = null;
////                    while ((result = dis.readUTF()) != null) {
////                        System.out.println("我是客户端，我传来的数据:" + result);
////                        //服务端回答客户端
////                        String messageToClient=new String("You are right.".getBytes(),"utf-8");
////                        sendMessageToClient(messageToClient);
////                    }
////                    System.out.println("这次会话完成，关闭连接了。");
////                } catch (IOException e) {
////                    e.printStackTrace();
////                    System.out.println("客户端异常关闭");
////                }finally{
////                    closeCommniucation();
////                }
////        }
////
////
////        private void sendMessageToClient (String message) throws IOException {
////            OutputStream os = mSocket.getOutputStream();
////            DataOutputStream dos = new DataOutputStream(os);
////            dos.writeUTF(message);
////            dos.flush();
////        }
////
////
////        public void closeCommniucation() {
////            if (mSocket != null) {
////                try {
////                    mSocket.close();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////                mSocket=null;
////            }
////        }
////    }
////}