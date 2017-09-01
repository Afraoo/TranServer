package com.common;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestTask extends TimerTask {  
    protected final Log logger = LogFactory.getLog(getClass());  
  
    private static boolean isRunning = false;  
  
    @Override  
    public void run() {   
  
        if (!isRunning) {  
  
            isRunning = true;  
            logger.info("开始执行任务。");  
  
            int i = 0;  
            while (i++ < 10) {  
                logger.info("已完成任务的" + i + "/" + 10);  
            }  
  
            isRunning = false;  
            logger.info("任务执行结束。");  
  
        } else {  
            logger.info("上一次任务执行还未结束，本次任务不能执行。");  
        }  
  
    }  
}
