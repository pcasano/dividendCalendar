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

import lombok.Getter;

/**
 *
 * @author pablo
 */

@Getter
public class Company {
    
    private final Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
    
    public Company(StockOverview stockOverview) throws IOException {
        this.ticker = stockOverview.getTicker();
        this.companyName = stockOverview.getCompanyName();
        InputStream inputStream = NewMain.class.getResourceAsStream("/com/images/" + this.ticker+".png");
        Image img = ImageIO.read(inputStream);
        Image formattedImg = img.getScaledInstance(screenDimension.width/16, screenDimension.width/16, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(formattedImg);                
        this.label = new JLabel(icon); 
        try{
            this.dividendPaymentDate = stockOverview.getDividendPaymentDate();
            this.dayOfMonth = stockOverview.getDividendPaymentDate().get(Calendar.DAY_OF_MONTH);
        }catch(NullPointerException e){
            this.dayOfMonth = -1;
        }     
    }

    private final String companyName;
    private final String ticker;
    private Calendar dividendPaymentDate;
    private int dayOfMonth;
    private final JLabel label;

}
