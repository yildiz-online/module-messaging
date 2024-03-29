/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
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

import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

/**
 * Standard broker properties.
 *
 * @author Grégory Van den Borre
 */
public class BrokerPropertiesStandard implements BrokerProperties {

    private final String host;

    private final int port;

    private final String data;

    private final boolean internal;

    private BrokerPropertiesStandard(Properties properties) {
        Objects.requireNonNull(properties);
        this.host = Optional.ofNullable(properties.getProperty("broker.host")).orElseThrow(() -> new IllegalStateException("broker.host not found"));
        this.port = Integer.parseInt(Optional.ofNullable(properties.getProperty("broker.port")).orElseThrow(() -> new IllegalStateException("broker.port not found")));
        this.data = Optional.ofNullable(properties.getProperty("broker.data")).orElseThrow(() -> new IllegalStateException("broker.data not found"));
        this.internal = Optional.ofNullable(properties.getProperty("broker.internal")).orElseThrow(() -> new IllegalStateException("broker.internal not found")).equalsIgnoreCase("true");
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
        return new BrokerPropertiesStandard(properties);
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
