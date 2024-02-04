/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dividendcalendar;

import com.dividendcalendar.configuration.Configuration;
import com.dividendcalendar.reportContents.FlexStatement;
import com.dividendcalendar.reportContents.OpenDividendAccrual;
import com.dividendcalendar.reportContents.StatementOfFundsLine;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.graphics.Gui;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;


/**
 *
 * @author pablo
 */
public class NewMain{

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws org.json.simple.parser.ParseException
     */  

    private static final Logger logger = LogManager.getLogger(NewMain.class);

    // Compile pom.xlm as clean compile assembly:single
    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException, ParserConfigurationException, SAXException, InterruptedException {
        InputStream inputStream = NewMain.class.getResourceAsStream("/com/companies.json");
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(new InputStreamReader(inputStream, "UTF-8")); 
        JSONArray companies = (JSONArray)jsonObject.get("companies");

        logger.info("-------------------- Application starts --------------------");

        File file = new File("simple_bean.xml");
        InputStream configInputStream = NewMain.class.getResourceAsStream("/config.xml");
        XmlMapper xmlMapper = new XmlMapper();
        Configuration configuration  = xmlMapper.readValue(configInputStream, Configuration.class);

        RestAssured.baseURI = configuration.getBrokerData().getBaseUrl();
        RequestSpecification request = RestAssured.given();
        logger.info( "connection to IB");
        Response response = request.queryParam("t", configuration.getBrokerData().getToken())
                .queryParam("q", configuration.getBrokerData().getMonthlyQueryId())
                .queryParam("v", configuration.getBrokerData().getApiVersion())
                .get("FlexStatementService.SendRequest");
        String xmlStringForReferenceCode = response.asString();
        XmlParser xmlParserForReferenceCode = new XmlParser(xmlStringForReferenceCode);

        if(!xmlParserForReferenceCode.getStatus().equals("Success")) {
            throw new AccessDeniedException("connection to IB failed due to: " + xmlParserForReferenceCode.getErrorMessage());
        }
        String referenceCode = xmlParserForReferenceCode.getReferenceCode();

        TimeUnit.SECONDS.sleep(2);
        RequestSpecification requestForContent = RestAssured.given();
        logger.info( "retrieving data from IB");
        Response responseForContent = requestForContent.queryParam("q", referenceCode)
                .queryParam("t", configuration.getBrokerData().getToken())
                .queryParam("v", configuration.getBrokerData().getApiVersion())
                .get("FlexStatementService.GetStatement");

        String xmlStringForContent = responseForContent.asString();

        logger.info( "parsing data from IB response");
        XmlParser xmlParserForContent = new XmlParser(xmlStringForContent);

        List<OpenDividendAccrual> listOfOpenDividendAccrual = xmlParserForContent.getListOfOpenDividendAccrual();
        List<StatementOfFundsLine> listOfStatementOfFundsLine = xmlParserForContent.getListOfStatementOfFundsLine();

        List<OpenDividendAccrual> listFilteredOpenDivsAccrual = getListOpenDividendAccrualCurrentMonth(listOfOpenDividendAccrual);
        List<StatementOfFundsLine> listOfDivsBase = getListOfStatementOfFundsLine(listOfStatementOfFundsLine, "BaseCurrency", "DIV");


        List<Company> listOfCompaniesInPortfolio = new ArrayList<>();
        for(Object objectCompany:companies){
            StockOverview stockOverview = new StockOverview(objectCompany.toString());
            listOfCompaniesInPortfolio.add(new Company(stockOverview));
        }
        List<Company> listCompaniesForThisMonth = new ArrayList<>();
        for (OpenDividendAccrual openDividendAccrual: listFilteredOpenDivsAccrual) {
            StockOverview stockOverview = new StockOverview(openDividendAccrual);
            listCompaniesForThisMonth.add(new Company(stockOverview));
        }
        for(StatementOfFundsLine statementOfFundsLine: listOfDivsBase) {
            StockOverview stockOverview = new StockOverview(statementOfFundsLine);
            listCompaniesForThisMonth.add(new Company(stockOverview));
        }

        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        Gui gui = new Gui(calendar, listCompaniesForThisMonth, listOfCompaniesInPortfolio);
        gui.runGui();   
    }

    private static List<OpenDividendAccrual> getListOpenDividendAccrualCurrentMonth(List<OpenDividendAccrual> openDividendAccrualList) {
        Calendar cal1 = Calendar.getInstance(Locale.GERMANY);
        Calendar now = Calendar.getInstance(Locale.GERMANY);
        now.setTime(new Date());
        return openDividendAccrualList.stream().filter(div -> {
            cal1.setTime(div.getPayDate());
            int month = cal1.get(Calendar.MONTH);
            int year = cal1.get(Calendar.YEAR);
            return Integer.valueOf(month).equals(now.get(Calendar.MONTH)) && Integer.valueOf(year).equals(now.get(Calendar.YEAR));
        }).toList();
    }

    private static List<StatementOfFundsLine> getListOfStatementOfFundsLine(
            List<StatementOfFundsLine> listOfStatementOfFundsLine,
            String levelOfDetail,
            String... activityCode) {
        List<StatementOfFundsLine> losof =  listOfStatementOfFundsLine.stream()
                .filter(entry -> entry.getLevelOfDetail().equals(levelOfDetail) && Arrays.asList(activityCode).contains(entry.getActivityCode())).toList();
        Calendar cal1 = Calendar.getInstance(Locale.GERMANY);
        Calendar now = Calendar.getInstance(Locale.GERMANY);
        now.setTime(new Date());
        return losof.stream().filter(entry -> {
            cal1.setTime(entry.getDate());
            int month = cal1.get(Calendar.MONTH);
            int year = cal1.get(Calendar.YEAR);
            return Integer.valueOf(month).equals(now.get(Calendar.MONTH)) && Integer.valueOf(year).equals(now.get(Calendar.YEAR));
        }).toList();
    }
}
