package com.erp.mastro.common;

import org.slf4j.Logger;

import java.util.HashMap;

import static org.slf4j.LoggerFactory.getLogger;

public class MastroLogUtils {

    public static HashMap<Class, Logger> loggers = new HashMap<>();
    public static Logger defaultLogger = getLogger(MastroLogUtils.class);

    private MastroLogUtils() {

    }

    private static void putLoggerIfAbsent(Object currentObject) {
        if (!loggers.containsKey(currentObject.getClass())) {
            loggers.put(currentObject.getClass(), getLogger(currentObject.getClass()));
        }
    }

    public static void error(Object currentObject, String message, Throwable e) {
        putLoggerIfAbsent(currentObject);
        loggers.getOrDefault(currentObject.getClass(), defaultLogger).error(message, e);
    }


    public static void error(Object currentObject, String message) {
        putLoggerIfAbsent(currentObject);
        loggers.getOrDefault(currentObject.getClass(), defaultLogger).error(message);
    }

    public static void error(Object currentObject, String message, Object... args) {
        putLoggerIfAbsent(currentObject);
        loggers.getOrDefault(currentObject.getClass(), defaultLogger).debug(message, args);
    }

    public static void debug(Object currentObject, String message, Throwable e) {
        putLoggerIfAbsent(currentObject);
        loggers.getOrDefault(currentObject.getClass(), defaultLogger).debug(message, e);
    }


    public static void debug(Object currentObject, String message) {
        putLoggerIfAbsent(currentObject);
        loggers.getOrDefault(currentObject.getClass(), defaultLogger).debug(message);
    }

    public static void debug(Object currentObject, String message, Object... args) {
        putLoggerIfAbsent(currentObject);
        loggers.getOrDefault(currentObject.getClass(), defaultLogger).debug(message, args);
    }


    public static void warn(Object currentObject, String message, Throwable e) {
        putLoggerIfAbsent(currentObject);
        loggers.getOrDefault(currentObject.getClass(), defaultLogger).warn(message, e);
    }

    public static void warn(Object currentObject, String message) {
        putLoggerIfAbsent(currentObject);
        loggers.getOrDefault(currentObject.getClass(), defaultLogger).warn(message);
    }

    public static void warn(Object currentObject, String message, Object... args) {
        putLoggerIfAbsent(currentObject);
        loggers.getOrDefault(currentObject.getClass(), defaultLogger).debug(message, args);
    }

    public static void info(Object currentObject, String message, Throwable e) {
        putLoggerIfAbsent(currentObject);
        loggers.getOrDefault(currentObject.getClass(), defaultLogger).info(message, e);
    }

    public static void info(Object currentObject, String message) {
        putLoggerIfAbsent(currentObject);
        loggers.getOrDefault(currentObject.getClass(), defaultLogger).info(message);
    }

    public static void info(Object currentObject, String message, Object... args) {
        putLoggerIfAbsent(currentObject);
        loggers.getOrDefault(currentObject.getClass(), defaultLogger).debug(message, args);
    }

}
