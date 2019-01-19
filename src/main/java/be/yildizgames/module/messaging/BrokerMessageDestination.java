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
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * A message destination wis the channel where the asynchronous messages go.
 * @author Grégory Van den Borre
 */
public class BrokerMessageDestination {

    /**
     * Session to create the destination.
     */
    private final Session session;

    /**
     * JMS destination.
     */
    private final Destination destination;

    /**
     * Create a new destination.
     * @param connection Connection to use to create a session.
     * @param name Destination name.
     * @param topic True will create a topic, false will create a queue.
     */
    BrokerMessageDestination(Connection connection, String name, boolean topic) {
        super();
        try {
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            if(topic) {
                this.destination = this.session.createTopic(name);
            } else {
                this.destination = this.session.createQueue(name);
            }
        } catch (JMSException e) {
            throw new MessagingException(e);
        }
    }

    /**
     * Create a new producer to send messages to this destination.
     * @return The created producer.
     */
    public JmsMessageProducer createProducer() {
        return new JmsMessageProducer(this.session, this.destination);
    }

    /**
     * Create a new consumer to retrieve messages from this destination.
     * @param listener Logic to invoke when a message is received.
     * @return The created consumer.
     */
    public MessageConsumer createConsumer(BrokerMessageListener listener) {
        return new MessageConsumer(this.session, this.destination, listener);
    }
}
