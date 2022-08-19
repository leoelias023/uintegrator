package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

/**
 * Binder for obtain logger factory and match versions of impl-api.
 *
 * @author Leonardo Elias
 */
@SuppressWarnings("unused")
public class StaticLoggerBinder implements LoggerFactoryBinder {

    /**
     * Requested api version that implementation is compatible.
     */
    public static final String REQUESTED_API_VERSION = "1.7";

    private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();
    private static final String loggerFactoryClassStr = IntegratorLoggerFactory.class.getName();
    private final ILoggerFactory loggerFactory;

    public static StaticLoggerBinder getSingleton() {
        return SINGLETON;
    }

    private StaticLoggerBinder() {
        loggerFactory = new IntegratorLoggerFactory();
    }

    @Override
    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    @Override
    public String getLoggerFactoryClassStr() {
        return loggerFactoryClassStr;
    }
}
