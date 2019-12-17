package com.capfsd.stock.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class LogUtils {
    private static String logPrefix = "LogUtils->";
    private static LogUtils instance;
    private static Logger logger = null;
    private static Map<Class, Logger> loggerList = new HashMap<Class, Logger>();

    private LogUtils() {
    }


    public synchronized static LogUtils getInst(Object obj) {
        if (instance == null) {
            instance = new LogUtils();
        }
        LogUtils.logger = loggerList.get(obj.getClass());
        if (LogUtils.logger == null) {
            LogUtils.logger = LoggerFactory.getLogger(obj.getClass());
            //Log.logger = Logger.getLogger(obj.getClass());
            loggerList.put(obj.getClass(), LogUtils.logger);
        }
        return instance;
    }

    public synchronized static LogUtils getInst(Class clazz) {
        if (instance == null) {
            instance = new LogUtils();
        }
        LogUtils.logger = loggerList.get(clazz);
        if (LogUtils.logger == null) {
            LogUtils.logger = LoggerFactory.getLogger(clazz);
            loggerList.put(clazz, LogUtils.logger);
        }
        return instance;
    }


    public synchronized static LogUtils getInst() {
        if (instance == null) {
            instance = new LogUtils();
        }
        LogUtils.logger = loggerList.get(LogUtils.class);
        if (LogUtils.logger == null) {
            LogUtils.logger = LoggerFactory.getLogger(LogUtils.class);
            loggerList.put(LogUtils.class, LogUtils.logger);
        }
        return instance;
    }

    public void trace(String message) {
        LogUtils.logger.trace(logPrefix + message);
    }

    public void trace(String message, Throwable t) {
        LogUtils.logger.trace(logPrefix + message, t);
    }

    public void debug(String message) {
        LogUtils.logger.debug(logPrefix + message);
    }

    public void debug(String message, Throwable t) {
        LogUtils.logger.debug(logPrefix + message, t);
    }

    public void info(String message) {
        LogUtils.logger.info(logPrefix + message);
    }

    public void info(String message, Throwable t) {
        LogUtils.logger.info(logPrefix + message, t);
    }

    public void warn(String message) {
        LogUtils.logger.warn(logPrefix + message);
    }

    public void warn(String message, Throwable t) {
        LogUtils.logger.warn(logPrefix + message, t);
    }

    public void error(String message,Object... arguments) {
        LogUtils.logger.error(logPrefix + message,arguments);
    }

    public void error(String message, Throwable t, Object... arguments) {
        LogUtils.logger.error(logPrefix + message,t,arguments);
    }
}