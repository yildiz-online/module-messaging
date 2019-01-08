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

import javax.jms.Connection;
import javax.jms.JMSException;
import java.util.ServiceLoader;

/**
 * The broker is the application to connect to, in order to use messaging.
 * @author Grégory Van den Borre
 */
public abstract class Broker {

    /**
     * Connection to the broker system.
     */
    private Connection connection;

    /**
     * Protected constructor.
     */
    protected Broker() {
        super();
    }

    /**
     * Create a new Broker connection from properties configuration.
     * @param p Properties for the broker connection configuration.
     * @return The created instance.
     */
    public static Broker getBroker(BrokerProperties p) {
        ImplementationException.throwForNull(p);
        return getBrokerProvider().initialize(p);
    }

    @Deprecated
    public static Broker getBroker(String host, int port) {
        return getBrokerProvider().initialize(host, port);
    }

    @Deprecated
    public static Broker getBroker(String name, BrokerProperties p) {
        ImplementationException.throwForNull(name);
        ImplementationException.throwForNull(p);
        return getBrokerProvider().initializeInternal(name, p);
    }

    private static BrokerProvider getBrokerProvider() {
        ServiceLoader<BrokerProvider> provider = ServiceLoader.load(BrokerProvider.class);
        return provider.findFirst().orElseThrow(() -> ImplementationException.missingImplementation("broker"));
    }

    /**
     * Register a queue destination, a queue contains message that are removed once consumed.
     * @param name Queue unique name.
     * @return The queue destination.
     */
    public final BrokerMessageDestination registerQueue(String name) {
        return new BrokerMessageDestination(this.connection, name, false);
    }

    public final BrokerMessageDestination registerTopic(String name) {
        return new BrokerMessageDestination(this.connection, name, true);
    }

    protected final void initializeConnection(final Connection connection) {
        this.connection = connection;
    }

    protected final void closeConnection() throws JMSException {
        this.connection.close();
    }

    protected final void start() throws JMSException {
        this.connection.start();
    }

    public abstract void close() throws Exception;
}
