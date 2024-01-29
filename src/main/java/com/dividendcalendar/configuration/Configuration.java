package com.dividendcalendar.configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Configuration {

    @XmlElement
    private BrokerData brokerData;

    public BrokerData getBrokerData() {
        return brokerData;
    }
}