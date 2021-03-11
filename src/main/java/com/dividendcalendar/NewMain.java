/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dividendcalendar;

import com.graphics.Gui;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

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
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {        
//        JSONParser parser = new JSONParser();
//        Object object = parser.parse(new FileReader(NewMain.class.getResource("companies.json").getPath()));
//        JSONObject jsonObject = (JSONObject)object;      
//        JSONArray companies = (JSONArray)jsonObject.get("companies");
        
        
        InputStream inputStream = NewMain.class.getResourceAsStream("../companies.json");
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(new InputStreamReader(inputStream, "UTF-8")); 
        JSONArray companies = (JSONArray)jsonObject.get("companies");
             
        
        
        List<Company> listOfCompaniesInPortfolio = new ArrayList<>();
        Stock stock;
        for(Object objectCompany:companies){
            stock = YahooFinance.get(objectCompany.toString());
            listOfCompaniesInPortfolio.add(new Company(stock));
        }
        
        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        int currentMonth = calendar.get(Calendar.MONTH);
        
        List<Company> listCompaniesForThisMonth = new ArrayList<>();
        for(Company company:listOfCompaniesInPortfolio){
            if(company.getDividendPaymentDate().get(Calendar.MONTH) == currentMonth){
                listCompaniesForThisMonth.add(company);
            }
        }
        Gui gui = new Gui(calendar, listCompaniesForThisMonth);
        gui.runGui();   
    }    
}
