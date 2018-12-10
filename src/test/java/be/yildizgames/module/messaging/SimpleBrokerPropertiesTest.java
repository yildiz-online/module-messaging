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
import be.yildizgames.common.util.PropertiesException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Properties;

class SimpleBrokerPropertiesTest {

    @Nested
    class Constructor {

        @Test
        void happyFlow() {

            BrokerProperties bp = new SimpleBrokerProperties(new DummyProperties().p);
            Assertions.assertEquals("value1", bp.getBrokerHost());
            Assertions.assertEquals(1, bp.getBrokerPort());
            Assertions.assertEquals("value3", bp.getBrokerDataFolder());
        }

        @Test
        void nullParameter() {
            Assertions.assertThrows(ImplementationException.class, () -> new SimpleBrokerProperties(null));
        }

        @Test
        void noHost() {
            Properties p = new Properties();
            p.put("broker.port", "1");
            p.put("broker.data", "value");
            Assertions.assertThrows(PropertiesException.class, () -> new SimpleBrokerProperties(p));
        }

        @Test
        void noPort() {
            Properties p = new Properties();
            p.put("broker.host", "value");
            p.put("broker.data", "value");
            Assertions.assertThrows(PropertiesException.class, () -> new SimpleBrokerProperties(p));
        }

        @Test
        void invalidPort() {
            Properties p = new Properties();
            p.put("broker.host", "value1");
            p.put("broker.port", "v");
            p.put("broker.data", "value3");
            Assertions.assertThrows(PropertiesException.class, () -> new SimpleBrokerProperties(p));
        }

        @Test
        void noData() {
            Properties p = new Properties();
            p.put("broker.host", "value");
            p.put("broker.port", "1");
            Assertions.assertThrows(PropertiesException.class, () -> new SimpleBrokerProperties(p));
        }

    }
}
