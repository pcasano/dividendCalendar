package com.dividendcalendar.configuration;

import javax.xml.bind.annotation.XmlElement;

public class BrokerData {

    @XmlElement
    private String token;
    @XmlElement
    private String monthlyQueryId;
    @XmlElement
    private String baseUrl;
    @XmlElement
    private String apiVersion;

    public String getToken() {
        return token;
    }

    public String getMonthlyQueryId() {
        return monthlyQueryId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiVersion() {
        return apiVersion;
    }
}
