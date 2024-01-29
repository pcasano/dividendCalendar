package com.dividendcalendar.reportContents;

import lombok.Getter;
import org.w3c.dom.NamedNodeMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class OpenDividendAccrual {

    private final String currency;
    private final String symbol;
    private final String description;
    private final Date payDate;
    private final double grossAmount;
    private final double netAmount;
    private final String actionID;

    public OpenDividendAccrual(NamedNodeMap namedNodeMap) throws ParseException {
        this.currency = namedNodeMap.getNamedItem("currency").getTextContent();
        this.symbol = namedNodeMap.getNamedItem("symbol").getTextContent();
        this.description = namedNodeMap.getNamedItem("description").getTextContent();
        this.payDate = new SimpleDateFormat("dd/MM/yyyy").parse(namedNodeMap.getNamedItem("payDate").getTextContent());;
        this.grossAmount = Double.parseDouble(namedNodeMap.getNamedItem("grossAmount").getTextContent());
        this.netAmount = Double.parseDouble(namedNodeMap.getNamedItem("netAmount").getTextContent());
        this.actionID = namedNodeMap.getNamedItem("actionID").getTextContent();
    }

}
