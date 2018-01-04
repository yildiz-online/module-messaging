/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2017 Grégory Van den Borre
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

package be.yildiz.module.messaging;

import be.yildizgames.common.exception.technical.InitializationException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import java.io.File;
import java.nio.file.Path;

/**
 * @author Grégory Van den Borre
 */
public class Broker {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BrokerService brokerService = new BrokerService();
    private Connection connection;

    private Broker() {
        super();
    }

    public static Broker initialize(BrokerProperties properties) {
        return Broker.initialize(properties.getBrokerHost(), properties.getBrokerPort());
    }

    public static Broker initialize(String host, int port) {
        try {
            Broker broker = new Broker();
            broker.logger.info("Preparing the broker...");
            String address = "failover:tcp://" + host + ":" + port;
            broker.logger.info("Connecting to broker: {}", address);
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(address);
            broker.connection = connectionFactory.createConnection();
            broker.connection.start();
            return broker;
        } catch (Exception e) {
            throw new InitializationException(e);
        }
    }

    public static Broker initializeInternal(String name, BrokerProperties properties) {
        return Broker.initializeInternal(name, new File(properties.getBrokerDataFolder()), properties.getBrokerHost(), properties.getBrokerPort());
    }

    public static Broker initializeInternal(String name, Path dataDirectory, String host, int port) {
        return Broker.initializeInternal(name, dataDirectory.toFile(), host, port);
    }

    public static Broker initializeInternal(String name, File dataDirectory, String host, int port) {
        try {
            Broker broker = new Broker();
            broker.brokerService.setBrokerName(name);
            broker.brokerService.setDataDirectoryFile(dataDirectory);
            String address = "tcp://" + host + ":" + port;
            broker.brokerService.addConnector(address);
            broker.brokerService.start();
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(address);
            broker.connection = connectionFactory.createConnection();
            broker.connection.start();
            return broker;
        } catch (Exception e) {
            throw new InitializationException(e);
        }
    }

    public BrokerMessageDestination registerQueue(String name) {
        return new BrokerMessageDestination(this.connection, name, false);
    }

    public BrokerMessageDestination registerTopic(String name) {
        return new BrokerMessageDestination(this.connection, name, true);
    }

    public void close() throws Exception {
        this.connection.close();
        this.brokerService.stop();
    }
}
