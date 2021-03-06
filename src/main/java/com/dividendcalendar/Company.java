/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dividendcalendar;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
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
    
    private final Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
    
    public Company(Stock stock) throws IOException {
        this.ticker = stock.getSymbol();
        this.companyName = stock.getName();        
        InputStream inputStream = NewMain.class.getResourceAsStream("/com/images/" + this.ticker+".png");
        Image img = ImageIO.read(inputStream);
        Image formattedImg = img.getScaledInstance(screenDimension.width/16, screenDimension.width/16, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(formattedImg);                
        this.label = new JLabel(icon); 
        try{
            this.dividendPaymentDate = stock.getDividend().getPayDate();
            this.dayOfMonth = stock.getDividend().getPayDate().get(Calendar.DAY_OF_MONTH);               
        }catch(NullPointerException e){
            this.dayOfMonth = -1;
        }     
    }
    
    private final String companyName;
    private final String ticker;
    private Calendar dividendPaymentDate;
    private int dayOfMonth;
    private final JLabel label;    
    
    public JLabel getLabel() {        
        return label;
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
