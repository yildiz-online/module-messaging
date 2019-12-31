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

import be.yildizgames.module.messaging.exception.MessagingException;

import javax.jms.Connection;
import javax.jms.JMSException;
import java.util.Objects;
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
        Objects.requireNonNull(p);
        return getBrokerProvider().initialize(p);
    }

    /**
     * Retrieve the broker provider from the service.
     * @return The found broker provider.
     */
    private static BrokerProvider getBrokerProvider() {
        ServiceLoader<BrokerProvider> provider = ServiceLoader.load(BrokerProvider.class);
        return provider.findFirst().orElseThrow(() -> new IllegalStateException("Missing broker implementation"));
    }

    /**
     * Register a queue destination, a queue contains message that are removed once consumed.
     * @param name Queue unique name.
     * @return The queue destination.
     */
    public final BrokerMessageDestination registerQueue(String name) {
        return new BrokerMessageDestination(this.connection, name, false);
    }

    /**
     * Register a topic destination, a topic is a public subscribe channel..
     * @param name Topic unique name.
     * @return The topic destination.
     */
    public final BrokerMessageDestination registerTopic(String name) {
        return new BrokerMessageDestination(this.connection, name, true);
    }

    /**
     * Prepare the connection to the broker.
     */
    protected final void initializeConnection(final Connection connection) {
        this.connection = connection;
    }

    /**
     * Close the connection to the broker.
     * @throws JMSException If the action fails.
     */
    protected final void closeConnection() throws JMSException {
        this.connection.close();
    }

    /**
     * Start the broker connection.
     * @throws JMSException If the connection fails.
     */
    protected final void start() throws JMSException {
        this.connection.start();
    }

    /**
     * Close the connection to the broker.
     */
    public abstract void close() throws MessagingException;

    public abstract void configure(BrokerProperties config);

}
