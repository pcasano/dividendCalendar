package com.dividendcalendar.configuration;

import com.dividendcalendar.NewMain;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class ConfigurationReader {

    public Configuration unmarshal() throws JAXBException {
        InputStream inputStream = NewMain.class.getResourceAsStream("/config.xml");
        JAXBContext context = JAXBContext.newInstance(Configuration.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Configuration) unmarshaller.unmarshal(inputStream);
    }
}