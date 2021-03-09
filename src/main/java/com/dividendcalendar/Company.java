/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dividendcalendar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import yahoofinance.Stock;

/**
 *
 * @author pablo
 */
public class Company {
    
    public Company(Stock stock) throws IOException {
        this.ticker = stock.getSymbol();
        this.companyName = stock.getName();
        this.dividendPaymentDate = stock.getDividend().getPayDate();
        this.dayOfMonth = stock.getDividend().getPayDate().get(Calendar.DAY_OF_MONTH);
        this.imagePath = "src\\main\\resources\\images\\" + this.ticker + ".png";  
        BufferedImage img = ImageIO.read(new File(this.imagePath));
        ImageIcon icon = new ImageIcon(img);        
        this.label = new JLabel(icon);    
        this.currency = stock.getCurrency();
    }
    
    private final String companyName;
    private final String ticker;
    private final Calendar dividendPaymentDate;
    private final int dayOfMonth;
    private final String imagePath;
    private final JLabel label;
    private final String currency;

    public String getCurrency() {
        return currency;
    }    
    
    public JLabel getLabel() {        
        return label;
    }

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
