/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author pablo
 */
public class Gui{
    
    public Gui(int numberOfButtons) {
        this.numberOfButtons = numberOfButtons;
    } 
    
    private JPanel mainPanel;
    private JPanel calendarPanel;
    private JPanel imagePanel;
    private final int numberOfButtons;

    
    
    public void runGui(){
        JFrame frame = new JFrame();
        frame.setTitle("RBM Test Application");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);       
        createMainPanel(numberOfButtons);
        frame.add(mainPanel);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(dimension.width, dimension.height);
        frame.setVisible(true);        
    }
    
    private void createMainPanel(int numberOfButtons) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(100));
        createCalendarPanel(numberOfButtons);
        mainPanel.add(calendarPanel);
        createImagePanel();
        mainPanel.add(imagePanel);
    }
    
    private void createCalendarPanel(int numberOfButtons){
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        calendarPanel = new JPanel();
        calendarPanel.setLayout(new GridLayout(5, 7, 0, 0));
        JButton button;
        List<JButton> listOfButtons = new ArrayList<>();
        for(int i=0;i<numberOfButtons;i++){
            button = new JButton(Integer.toString(i + 1)); 
            button.setPreferredSize(new Dimension(screenDimension.width/14, screenDimension.width/28));
            listOfButtons.add(button);                
        }                
        for(JButton jButton:listOfButtons){
            calendarPanel.add(jButton, BorderLayout.WEST);
        }        
    }
    
    private void createImagePanel(){
        imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(4, 7, 50, 50));
    }    
    
}


