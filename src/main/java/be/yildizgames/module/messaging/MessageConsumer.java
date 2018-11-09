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

import be.yildizgames.common.logging.LogFactory;
import be.yildizgames.module.messaging.exception.MessagingException;
import org.slf4j.Logger;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Grégory Van den Borre
 */
public class MessageConsumer {

    private final Logger logger = LogFactory.getInstance().getLogger(this.getClass());

    private final List<Message> messageReceived = new ArrayList<>();

    MessageConsumer(Session session, Destination destination, BrokerMessageListener listener) {
        try (javax.jms.MessageConsumer consumer = session.createConsumer(destination)){
            consumer.setMessageListener(m -> {
                        try {
                            Message message = new Message(
                                    ((TextMessage) m).getText(),
                                    m.getJMSCorrelationID());
                            this.messageReceived.add(message);
                            listener.messageReceived(message);
                        } catch (JMSException e) {
                            logger.error("Error retrieving JMS message", e);
                        }
                    }
            );
        } catch (JMSException e) {
            throw new MessagingException(e);
        }
    }

    public final List<Message> getMessageReceived() {
        return Collections.unmodifiableList(this.messageReceived);
    }

    public final boolean hasMessage() {
        return !this.messageReceived.isEmpty();
    }
}
