/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dividendcalendar;

import java.util.Calendar;
import yahoofinance.Stock;

/**
 *
 * @author pablo
 */
public class Company {
    public Company(Stock stock) {
        this.ticker = stock.getSymbol();
        this.companyName = stock.getName();
        this.dividendPaymentDate = stock.getDividend().getPayDate();
        this.dayOfMonth = stock.getDividend().getPayDate().get(Calendar.DAY_OF_MONTH);
        this.imagePath = "src\\main\\ressources\\images\\" + getTicker() + ".png";
    }
    
    private final String companyName;
    private final String ticker;
    private final Calendar dividendPaymentDate;
    private final int dayOfMonth;
    private final String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getTicker() {
        return ticker;
    }
    
    public Calendar getDividendPaymentDate() {
        return dividendPaymentDate;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }    
}
