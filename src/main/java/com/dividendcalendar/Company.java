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
        this.imagePath = getClass().getResource("../images/"+this.ticker+".png").getPath();
        BufferedImage img = ImageIO.read(new File(this.imagePath));
        ImageIcon icon = new ImageIcon(img);        
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
    private final String imagePath;
    private final JLabel label;    
    
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
