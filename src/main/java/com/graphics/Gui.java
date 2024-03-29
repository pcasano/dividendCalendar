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
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author pablo
 */
public class Gui implements ActionListener{
    
    public Gui(Calendar calendar, List<Company> listCompaniesForThisMonth, List<Company> listOfCompaniesInPortfolio) {
        this.numberOfButtons = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        this.listCompaniesForThisMonth = listCompaniesForThisMonth;
        this.listCompaniesInPortfolio = listOfCompaniesInPortfolio;
        this.calendar = calendar;
    } 
    
    private Calendar calendar;
    private JTextArea jTextArea;
    private JScrollPane jScrollPane;
    private JFrame frame;
    private JPanel logAndCalendarPanel;
    private JPanel mainPanel;
    private JPanel calendarPanel;
    private JPanel logPanel;
    private JPanel imagePanel;    
    private JPanel imagePanelSeparator;        
    private JPanel showButtonsPanel;
    private JButton showAllCompaniesButton;
    private JButton showPortfolioButton;
    private final int numberOfButtons;
    private final List<Company> listCompaniesForThisMonth;
    private final List<Company> listCompaniesInPortfolio;
    private List<Company> listCompaniesDisplayed = new ArrayList<>();
    private final Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
    
    
    public void runGui() throws IOException{
        frame = new JFrame();
        frame.setTitle("RBM Test Application");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);       
        createMainPanel(numberOfButtons);
        frame.add(mainPanel);
        frame.setSize(screenDimension.width, screenDimension.height);
        frame.setVisible(true); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    private void createMainPanel(int numberOfButtons) throws IOException {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        createPanelForLogAndCalendar(numberOfButtons);
        mainPanel.add(logAndCalendarPanel);
        createImagePanel();
        mainPanel.add(imagePanelSeparator);
    }
    
    private void createPanelForLogAndCalendar(int numberOfButtons){
        logAndCalendarPanel = new JPanel();
        logAndCalendarPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        createCalendarPanel(numberOfButtons);
        createLogPanel();
        logAndCalendarPanel.add(calendarPanel);
        logAndCalendarPanel.add(logPanel);
        logAndCalendarPanel.setMaximumSize(new Dimension(
                screenDimension.width,
                screenDimension.height/2)
        );
    }
    
    private void createLogPanel(){
        logPanel = new JPanel();
        logPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 50, 0));
        jTextArea = new JTextArea(20,40);
        jTextArea.setBackground(Color.WHITE);
        jScrollPane = new JScrollPane(jTextArea); 
        logPanel.add(jScrollPane);
        this.createShowCompanyButtons();
        logPanel.add(showButtonsPanel);
    }
    
    private void createShowCompanyButtons(){
        showButtonsPanel = new JPanel();
        showButtonsPanel.setLayout(new BoxLayout(showButtonsPanel, BoxLayout.Y_AXIS));              
        this.createShowAllButton();
        this.createShowPortfolioButton();
        showButtonsPanel.add(showAllCompaniesButton);
        showButtonsPanel.add(Box.createVerticalStrut(15));  
        showButtonsPanel.add(showPortfolioButton);        
    }
    
    private void createCalendarPanel(int numberOfButtons){
        calendarPanel = new JPanel();
        int numberOfDays = this.calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Calendar cal = Calendar.getInstance(Locale.GERMANY);
        cal.set(Calendar.DAY_OF_MONTH, 1); 
        int numberOfWhiteDays;

        if(cal.get(Calendar.DAY_OF_WEEK) == 1){
            numberOfWhiteDays = 6;
        }
        else{
            numberOfWhiteDays = cal.get(Calendar.DAY_OF_WEEK) - 2;
        }
        float rows = (float)(numberOfDays + numberOfWhiteDays + 7)/7;
        int formattedRows;
        if(rows>6.0){
            formattedRows = 7;
        }
        else{
            formattedRows = 6;
        }
        calendarPanel.setLayout(new GridLayout(formattedRows, 7, 0, 0));
        setButtonsForDayNames(calendarPanel);
        synchronizeWeekDay(calendarPanel);
        JButton button;
        for(int i=0;i<numberOfButtons;i++){
            button = new JButton(Integer.toString(i + 1)); 
            button.setName(Integer.toString(i + 1));
            button.addActionListener(this);

            if(getListOfDividendDays(listCompaniesForThisMonth).contains(i + 1)){
                button.setBackground(Color.green);
            }
            else{
                if(isWeekend(i + 1)){
                    button.setEnabled(false);
                }                 
            }           
            button.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/28));
            calendarPanel.add(button, BorderLayout.WEST);
        }
        addWhiteDaysAtEnd(
                formattedRows * 7 - numberOfDays - numberOfWhiteDays - 7,
                calendarPanel
        );
    }
    
    private boolean isWeekend(int day){
        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        if(calendar.get(Calendar.DAY_OF_WEEK)==1 || calendar.get(Calendar.DAY_OF_WEEK)==7){
            return true;
        }
        else{
            return false;
        }
    }
    
    private void setButtonsForDayNames(JPanel calendarPanel){
        JButton mondayButton = new JButton("Monday");
        mondayButton.setEnabled(false);
        mondayButton.setBackground(Color.LIGHT_GRAY);
        mondayButton.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/35));
        JButton tuesdayButton = new JButton("Tuesday");
        tuesdayButton.setEnabled(false);
        tuesdayButton.setBackground(Color.LIGHT_GRAY);
        tuesdayButton.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/35));
        JButton wednesdayButton = new JButton("Wednesday");
        wednesdayButton.setEnabled(false);
        wednesdayButton.setBackground(Color.LIGHT_GRAY);
        wednesdayButton.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/35));
        JButton thursdayButton = new JButton("Thursday");
        thursdayButton.setEnabled(false);
        thursdayButton.setBackground(Color.LIGHT_GRAY);
        thursdayButton.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/35));
        JButton fridayButton = new JButton("Friday");
        fridayButton.setEnabled(false);
        fridayButton.setBackground(Color.LIGHT_GRAY);
        fridayButton.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/35));
        JButton saturdayButton = new JButton("Saturday");
        saturdayButton.setEnabled(false);
        saturdayButton.setBackground(Color.LIGHT_GRAY);
        saturdayButton.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/35));
        JButton sundayButton = new JButton("Sunday");
        sundayButton.setEnabled(false);
        sundayButton.setBackground(Color.LIGHT_GRAY);
        sundayButton.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/35));
        calendarPanel.add(mondayButton);
        calendarPanel.add(tuesdayButton);
        calendarPanel.add(wednesdayButton);
        calendarPanel.add(thursdayButton);
        calendarPanel.add(fridayButton);
        calendarPanel.add(saturdayButton);
        calendarPanel.add(sundayButton);
    }
    
    private int getNumberOfWhiteDaysToInsert(Calendar today){
        if(today.get(Calendar.DAY_OF_WEEK)==1){
            return 7;
        }
        else{
            return today.get(Calendar.DAY_OF_WEEK) - 1;
        }
    }
    
    private void synchronizeWeekDay(JPanel calendarPanel){
        Calendar today = Calendar.getInstance(Locale.GERMANY);
        today.set(Calendar.DAY_OF_MONTH, 1);        
        JButton button;
        for(int i=1;i<this.getNumberOfWhiteDaysToInsert(today);i++){
            button = new JButton();
            button.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/28));
            button.setEnabled(false);
            calendarPanel.add(button);
        }        
    }

    private void addWhiteDaysAtEnd(int daysToAdd, JPanel calendarPanel){
        JButton button;
        for(int i=0;i<daysToAdd;i++){
            button = new JButton();
            button.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/28));
            button.setEnabled(false);
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
        imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        imagePanelSeparator = new JPanel(); 
        imagePanelSeparator.setLayout(new BoxLayout(imagePanelSeparator, BoxLayout.Y_AXIS));
        imagePanelSeparator.add(Box.createVerticalStrut(20));
        imagePanelSeparator.add(imagePanel);
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
        jTextArea.setText("");
        for(Company company:listOfCompaniesPayingToday){
            imagePanel.add(company.getLabel());            
        }  
        jTextArea.setText(getLogTextWithPayDates(listOfCompaniesPayingToday));
        mainPanel.revalidate();
        mainPanel.repaint();
        listCompaniesDisplayed = new ArrayList<>(listOfCompaniesPayingToday);
    }
    
    private String getLogTextWithPayDates(List<Company> listOfCompanies){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append(listOfCompanies.size())
                    .append(" dividend(s) in total")
                    .append(System.getProperty("line.separator"));
        listOfCompanies.forEach((company) -> {
            sb.append("- ");
            if(company.getCompanyName().length()>30){
                sb.append(company.getCompanyName().substring(0, 30))
                  .append("...");                
            }
            else{
                sb.append(company.getCompanyName());                                 
            }
                sb.append(" pays on ")  
                  .append(format.format(company.getDividendPaymentDate().getTime()))
                  .append(System.getProperty("line.separator"));
        });
        return sb.toString();
    }
    
    private String getLogTextNoPaydates(List<Company> listOfCompanies){
        StringBuilder sb = new StringBuilder();
        sb.append(listOfCompanies.size())
                    .append(" companies in total")
                    .append(System.getProperty("line.separator"));
        listOfCompanies.forEach((company) -> {
            sb.append("- ");
            if(company.getCompanyName().length()>30){
                sb.append(company.getCompanyName().substring(0, 30))
                  .append("...")
                  .append(System.getProperty("line.separator"));
            }
            else{
                sb.append(company.getCompanyName())
                  .append(System.getProperty("line.separator"));
            }              
        });
        return sb.toString();
    }
    
    private void createShowAllButton(){
        showAllCompaniesButton = new JButton(new AbstractAction("Show all") {
        @Override
            public void actionPerformed( ActionEvent e ) {
                for(Company company:listCompaniesDisplayed){
                    imagePanel.remove(company.getLabel());
                }        

                Collections.sort(listCompaniesForThisMonth, new Comparator<Company>() {
                    public int compare(Company c1, Company c2) {
                    return c1.getDividendPaymentDate().compareTo(c2.getDividendPaymentDate());
                    }
                });         
                
                for(Company company:listCompaniesForThisMonth){
                    imagePanel.add(company.getLabel()); 
                }
                jTextArea.setText("");
                jTextArea.setText(getLogTextWithPayDates(listCompaniesForThisMonth));
                mainPanel.revalidate();
                mainPanel.repaint();
                listCompaniesDisplayed = new ArrayList<>(listCompaniesForThisMonth);            
            }
        });
    }
    
    private void createShowPortfolioButton(){
        showPortfolioButton = new JButton(new AbstractAction("Show portfolio") {
        @Override
            public void actionPerformed( ActionEvent e ) {
                for(Company company:listCompaniesDisplayed){
                    imagePanel.remove(company.getLabel());
                }        
                Collections.sort(
                        listCompaniesInPortfolio, 
                        (Company c1, Company c2) -> c1.getTicker().compareTo(c2.getTicker())
                );                                
                for(Company company:listCompaniesInPortfolio){
                    imagePanel.add(company.getLabel()); 
                }
                jTextArea.setText("");
                jTextArea.setText(getLogTextNoPaydates(listCompaniesInPortfolio));
                mainPanel.revalidate();
                mainPanel.repaint();
                listCompaniesDisplayed = new ArrayList<>(listCompaniesInPortfolio);            
            }
        });
    }    
    
}



