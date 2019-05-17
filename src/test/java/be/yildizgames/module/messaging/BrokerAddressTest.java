/*
 *
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 *
 */

package be.yildizgames.module.messaging;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Grégory Van den Borre
 */
public class BrokerAddressTest {

    @Nested
    public class Tcp {

        @Test
        public void happyFlow() {
            BrokerAddress address = BrokerAddress.tcp("localhost", 61616);
            Assertions.assertEquals("tcp://localhost:61616", address.getUri());
        }
    }

    @Nested
    public class Failover {

        @Test
        public void happyFlow() {
            BrokerAddress address = BrokerAddress.failover(List.of(BrokerAddress.tcp("localhost", 61616)));
            Assertions.assertEquals("failover:(tcp://localhost:61616)", address.getUri());
        }

        @Test
        public void happyFlow2Addresses() {
            BrokerAddress address = BrokerAddress.failover(List.of(BrokerAddress.tcp("localhost", 61616), BrokerAddress.tcp("test", 5555)));
            Assertions.assertEquals("failover:(tcp://localhost:61616,tcp://test:5555)", address.getUri());
        }

    }

    @Nested
    public class Discovery {

        @Test
        public void happyFlow() {

        }

    }

}
