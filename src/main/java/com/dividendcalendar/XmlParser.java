package com.dividendcalendar;

import com.dividendcalendar.reportContents.FlexStatement;
import com.dividendcalendar.reportContents.OpenDividendAccrual;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.dividendcalendar.reportContents.StatementOfFundsLine;

public class XmlParser {

    private final Document document;

    public XmlParser(String xmlString) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlString));
        this.document = builder.parse(is);
        this.document.getDocumentElement().normalize();

    }

    public String getReferenceCode() {
        return this.document.getElementsByTagName("ReferenceCode").item(0).getTextContent();
    }

    public String getStatus() {
        return this.document.getElementsByTagName("Status").item(0).getTextContent();
    }

    public String getErrorMessage() {
        return this.document.getElementsByTagName("ErrorMessage").item(0).getTextContent();
    }

    public String getErrorCode() {
        return this.document.getElementsByTagName("ErrorCode").item(0).getTextContent();
    }

    public FlexStatement getFlexStatement() throws ParseException {
        return new FlexStatement(this.document.getElementsByTagName("FlexStatement").item(0).getAttributes());
    }

    public List<OpenDividendAccrual> getListOfOpenDividendAccrual() throws ParseException {
        List<OpenDividendAccrual> listOpenDividendAccrual = new ArrayList<>();
        NodeList nodeList = this.document.getElementsByTagName("OpenDividendAccruals").item(0).getChildNodes();
        for (int index=0; index<nodeList.getLength(); index++) {
            if (nodeList.item(index) instanceof Element) {
                listOpenDividendAccrual.add(new OpenDividendAccrual(nodeList.item(index).getAttributes()));
            }
        }
        return listOpenDividendAccrual;
    }

    public List<StatementOfFundsLine> getListOfStatementOfFundsLine() throws ParseException {
        List<StatementOfFundsLine> listStatementOfFundsLine = new ArrayList<>();
        NodeList nodeList = this.document.getElementsByTagName("StmtFunds").item(0).getChildNodes();
        for (int index=0; index<nodeList.getLength(); index++) {
            if (nodeList.item(index) instanceof Element) {
                listStatementOfFundsLine.add(new StatementOfFundsLine(nodeList.item(index).getAttributes()));
            }
        }
        return listStatementOfFundsLine;
    }
}
