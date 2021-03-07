/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphics;

import com.dividendcalendar.Company;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author pablo
 */
public class Gui implements ActionListener{
    
    public Gui(Calendar calendar, List<Company> listCompaniesForThisMonth) {
        this.numberOfButtons = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        this.listCompaniesForThisMonth = listCompaniesForThisMonth;
        this.calendar = calendar;
    } 
    
    private Calendar calendar;
    private JFrame frame;
    private JPanel tableAndCalendarPanel;
    private JPanel mainPanel;
    private JPanel calendarPanel;
    private JPanel imagePanel;
    private final int numberOfButtons;
    private final List<Company> listCompaniesForThisMonth;
    private List<Company> listCompaniesDisplayed = new ArrayList<>();
    private Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
    
    
    public void runGui() throws IOException{
        frame = new JFrame();
        frame.setTitle("RBM Test Application");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);       
        createMainPanel(numberOfButtons);
        frame.add(mainPanel);
        frame.setSize(screenDimension.width, screenDimension.height);
        frame.setVisible(true);        
    }
    
    private void createMainPanel(int numberOfButtons) throws IOException {
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(100));
        //createCalendarPanel(numberOfButtons);
        createPanelForTableAndCalendar(numberOfButtons);
        mainPanel.add(calendarPanel);
        createImagePanel();
        mainPanel.add(imagePanel);
    }
    
    private void createPanelForTableAndCalendar(int numberOfButtons){
        tableAndCalendarPanel = new JPanel();
        tableAndCalendarPanel.setLayout(new BoxLayout(tableAndCalendarPanel, BoxLayout.Y_AXIS));
        createCalendarPanel(numberOfButtons);
        createTablePanel();
        tableAndCalendarPanel.add(calendarPanel);
        //add table panel
    }
    
    private void createTablePanel(){
        //toDo
    }
    
    private void createCalendarPanel(int numberOfButtons){
        calendarPanel = new JPanel();
        calendarPanel.setLayout(new GridLayout(6, 7, 0, 0));
        setButtonsForDayNames(calendarPanel);
        synchronizeWeekDay(calendarPanel);
        JButton button;
        List<JButton> listOfButtons = new ArrayList<>();
        for(int i=0;i<numberOfButtons;i++){
            button = new JButton(Integer.toString(i + 1)); 
            button.setName(Integer.toString(i + 1));
            button.addActionListener(this);
            if(getListOfDividendDays(listCompaniesForThisMonth).contains(i + 1)){
                button.setBackground(Color.green);
            }            
            button.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/28));
            listOfButtons.add(button);                
        }                
        for(JButton jButton:listOfButtons){
            calendarPanel.add(jButton, BorderLayout.WEST);
        }        
    }
    
    private void setButtonsForDayNames(JPanel calendarPanel){
        JButton mondayButton = new JButton("Monday");
        mondayButton.setEnabled(false);
        mondayButton.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/35));
        JButton tuesdayButton = new JButton("Tuesday");
        tuesdayButton.setEnabled(false);
        mondayButton.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/35));
        JButton wednesdayButton = new JButton("Wednesday");
        wednesdayButton.setEnabled(false);
        mondayButton.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/35));
        JButton thursdayButton = new JButton("Thursday");
        thursdayButton.setEnabled(false);
        mondayButton.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/35));
        JButton fridayButton = new JButton("Friday");
        fridayButton.setEnabled(false);
        mondayButton.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/35));
        JButton saturdayButton = new JButton("Saturday");
        saturdayButton.setEnabled(false);
        mondayButton.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/35));
        JButton sundayButton = new JButton("Sunday");
        sundayButton.setEnabled(false);
        mondayButton.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/35));
        calendarPanel.add(mondayButton);
        calendarPanel.add(tuesdayButton);
        calendarPanel.add(wednesdayButton);
        calendarPanel.add(thursdayButton);
        calendarPanel.add(fridayButton);
        calendarPanel.add(saturdayButton);
        calendarPanel.add(sundayButton);
    }
    
    private void synchronizeWeekDay(JPanel calendarPanel){
        Calendar today = Calendar.getInstance(Locale.GERMANY);
        today.set(Calendar.DAY_OF_MONTH, 1);        
        JButton button;
        for(int i=1;i<today.get(Calendar.DAY_OF_WEEK)-1;i++){
            button = new JButton();
            button.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/28));
            calendarPanel.add(button);
        }        
    }
    
    private List<Integer> getListOfDividendDays(List<Company> listOfCompanies){
        List<Integer> listOfInt = new ArrayList<>();
        for(Company company:listOfCompanies){
            listOfInt.add(company.getDayOfMonth());
        }
        return listOfInt;        
    }
    
    private void createImagePanel() throws IOException{
        imagePanel = new JPanel();
        double numberOfRowsDouble = listCompaniesForThisMonth.size()/4;
        int numberOfRowsInteger = (int)numberOfRowsDouble;      
        imagePanel.setLayout(new GridLayout(numberOfRowsInteger, 4, 0, 0));  
//        for(Company company:listCompaniesForThisMonth){
//            JLabel label = company.getLabel();            
//            imagePanel.add(label);
//            }              
        }
    

    @Override
    public void actionPerformed(ActionEvent e) {        
        for(Company company:listCompaniesDisplayed){
            imagePanel.remove(company.getLabel());
        }
        JButton clickedButton = (JButton)e.getSource();
        int dayOfMonth = Integer.valueOf(clickedButton.getName());
        List<Company> listOfCompaniesPayingToday = new ArrayList<>();
        for(Company company:listCompaniesForThisMonth){
            if(company.getDayOfMonth()==dayOfMonth){
                listOfCompaniesPayingToday.add(company);
            }
        }        
        for(Company company:listOfCompaniesPayingToday){
            imagePanel.add(company.getLabel());
        }            
        mainPanel.revalidate();
        mainPanel.repaint();
        listCompaniesDisplayed = new ArrayList<>(listOfCompaniesPayingToday);
    }   
}


