package be.yildizgames.module.messaging;

import java.util.Properties;

class DummyProperties {

    final Properties p = new Properties();

    DummyProperties() {
        p.put("broker.host", "value1");
        p.put("broker.port", "1");
        p.put("broker.data", "value3");
        p.put("broker.internal", "true");
    }

}
