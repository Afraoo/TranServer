package com.common;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TimerListener implements ServletContextListener {  
	  
    private Timer timer = null;  
  
    /**
     * TestTask()是执行具体操作的类
     */
    public void contextInitialized(ServletContextEvent event) {  
        timer = new Timer(true);  
        event.getServletContext().log("定时器已启动");  
        timer.scheduleAtFixedRate(new DistanceBetweenTeam(), 7 * 1000, 60 * 1000); // 每小时执行一次run方法    测试5秒5 * 1000;  
    }  
  
    public void contextDestroyed(ServletContextEvent event) {  
        if (timer != null) {  
            timer.cancel();  
            event.getServletContext().log("定时器销毁");  
        }  
    }  
}  