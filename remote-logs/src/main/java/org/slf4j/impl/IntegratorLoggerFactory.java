package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * Factory of {@link Logger} for obtain a new {@link IntegratorLogger} instace.
 *
 * @author Leonardo Elias
 */
public class IntegratorLoggerFactory implements ILoggerFactory {
    @Override
    public Logger getLogger(String name) {
        return new IntegratorLogger();
    }
}
