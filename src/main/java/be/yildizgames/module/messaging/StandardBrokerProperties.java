/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildizgames.module.messaging;

import be.yildizgames.common.exception.implementation.ImplementationException;
import be.yildizgames.common.util.PropertiesHelper;

import java.util.Properties;

/**
 * Standard broker properties.
 *
 * @author Grégory Van den Borre
 */
public class StandardBrokerProperties implements BrokerProperties {

    private final String host;

    private final int port;

    private final String data;

    private final boolean internal;

    private StandardBrokerProperties(Properties properties) {
        ImplementationException.throwForNull(properties);
        this.host = PropertiesHelper.getValue(properties, "broker.host");
        this.port = PropertiesHelper.getIntValue(properties, "broker.port");
        this.data = PropertiesHelper.getValue(properties,"broker.data");
        this.internal = PropertiesHelper.getBooleanValue(properties,"broker.internal");
    }

    /**
     * Build a standard broker properties.
     * Expected keys are:
     * broker.host: String
     * broker.port: int
     * broker.data: String path
     * broker.internal: boolean
     * @param properties Properties for the broker.
     * @return The Broker properties.
     */
    public static BrokerProperties fromProperties(Properties properties) {
        return new StandardBrokerProperties(properties);
    }

    @Override
    public final String getBrokerHost() {
        return this.host;
    }

    @Override
    public final int getBrokerPort() {
        return this.port;
    }

    @Override
    public final String getBrokerDataFolder() {
        return this.data;
    }

    @Override
    public final boolean getBrokerInternal() {
        return this.internal;
    }
}
