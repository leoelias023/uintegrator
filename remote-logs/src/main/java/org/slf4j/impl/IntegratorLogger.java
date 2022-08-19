package org.slf4j.impl;

import br.com.dbaonline.uintegrator.entity.transients.LogLevel;
import br.com.dbaonline.uintegrator.logs.submitter.LogSubmitter;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * Custom logger with multithreading-http and local logging.
 *
 * @author Leonardo Elias
 */
public class IntegratorLogger implements Logger {
    
    private static final LogSubmitter logSubmitter = LogSubmitter.submitters();

    @Override
    public String getName() {
        return "integrator-logger";
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void trace(String msg) {
        logSubmitter.submit(msg, LogLevel.TRACE);
    }

    @Override
    public void trace(String format, Object arg) {
        logSubmitter.submit(String.format(format, arg), LogLevel.TRACE);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        logSubmitter.submit(String.format(format, arg1, arg2), LogLevel.TRACE);
    }

    @Override
    public void trace(String format, Object... arguments) {
        logSubmitter.submit(String.format(format, arguments), LogLevel.TRACE);
    }

    @Override
    public void trace(String msg, Throwable t) {
        logSubmitter.submit(msg, LogLevel.TRACE);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return false;
    }

    @Override
    public void trace(Marker marker, String msg) {
        logSubmitter.submit(msg, LogLevel.TRACE);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        trace(format, arg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        trace(format, arg1, arg2);
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        trace(format, argArray);
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        trace(msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void debug(String msg) {
        if (isDebugEnabled()) {
            logSubmitter.submit(msg, LogLevel.DEBUG);
        }
    }

    @Override
    public void debug(String format, Object arg) {
        if (isDebugEnabled())
            logSubmitter.submit(
                    String.format(format, arg), LogLevel.DEBUG);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        if (isDebugEnabled())
            logSubmitter.submit(
                String.format(format, arg1, arg2), LogLevel.DEBUG);
    }

    @Override
    public void debug(String format, Object... arguments) {
        if (isDebugEnabled())
            logSubmitter.submit(
                String.format(format, arguments), LogLevel.DEBUG);
    }

    @Override
    public void debug(String msg, Throwable t) {
        if (isDebugEnabled())
            logSubmitter.submit(msg, LogLevel.DEBUG);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return false;
    }

    @Override
    public void debug(Marker marker, String msg) {
        if (isDebugEnabled())
        debug(msg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        if (isDebugEnabled())
        debug(format, arg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        if (isDebugEnabled())
        debug(format, arg1, arg2);
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        if (isDebugEnabled())
        debug(format, arguments);
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        if (isDebugEnabled())
        debug(msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public void info(String msg) {
        logSubmitter.submit(msg, LogLevel.INFO);
    }

    @Override
    public void info(String format, Object arg) {
        logSubmitter.submit(
                String.format(format, arg), LogLevel.INFO);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        logSubmitter.submit(
                String.format(format, arg1, arg2), LogLevel.INFO);
    }

    @Override
    public void info(String format, Object... arguments) {
        logSubmitter.submit(
                String.format(format, arguments), LogLevel.INFO);
    }

    @Override
    public void info(String msg, Throwable t) {
        logSubmitter.submit(msg, LogLevel.INFO);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return true;
    }

    @Override
    public void info(Marker marker, String msg) {
        info(msg);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        info(format, arg);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        info(format, arg1, arg2);
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        info(format, arguments);
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        info(msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void warn(String msg) {
        logSubmitter.submit(msg, LogLevel.WARNING);
    }

    @Override
    public void warn(String format, Object arg) {
        logSubmitter.submit(
                String.format(format, arg), LogLevel.WARNING);
    }

    @Override
    public void warn(String format, Object... arguments) {
        logSubmitter.submit(
                String.format(format, arguments), LogLevel.WARNING);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        logSubmitter.submit(
                String.format(format, arg1, arg2), LogLevel.WARNING);
    }

    @Override
    public void warn(String msg, Throwable t) {
        logSubmitter.submit(msg, LogLevel.WARNING);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return true;
    }

    @Override
    public void warn(Marker marker, String msg) {
        warn(msg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        warn(format, arg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        warn(format, arg1, arg2);
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        warn(format, arguments);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        warn(msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void error(String msg) {
        logSubmitter.submit(msg, LogLevel.ERROR);
    }

    @Override
    public void error(String format, Object arg) {
        logSubmitter.submit(
                String.format(format, arg), LogLevel.ERROR);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        logSubmitter.submit(
                String.format(format, arg1, arg2), LogLevel.ERROR);
    }

    @Override
    public void error(String format, Object... arguments) {
        logSubmitter.submit(
                String.format(format, arguments), LogLevel.ERROR);
    }

    @Override
    public void error(String msg, Throwable t) {
        logSubmitter.submit(msg, LogLevel.ERROR);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return true;
    }

    @Override
    public void error(Marker marker, String msg) {
        error(msg);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        error(format, arg);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        error(format, arg1, arg2);
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        error(format, arguments);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        error(msg, t);
    }
}
