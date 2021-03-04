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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author pablo
 */
public class Gui extends JFrame{
 
    public Gui(int numberOfButtons){
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(MAXIMIZED_BOTH);
        setTitle("Dividend Calendar");
        ButtonsFrame buttonsFrame = new ButtonsFrame(numberOfButtons);
        add(buttonsFrame);
       
        
    }
    
    
}

class  ButtonsFrame extends JPanel implements ActionListener{
    
    public ButtonsFrame(int numberOfButtons){
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        JPanel jPanel = new JPanel();
        jPanel.setSize(screenDimension);
        jPanel.setLayout(null);
        JButton button;
        List<JButton> listOfButtons = new ArrayList<>();

        for(int i=0;i<numberOfButtons;i++){

            button = new JButton(Integer.toString(i + 1));
            button.setBounds(i*200, 300, 200, 100);            
            listOfButtons.add(button);                



        }
        
        
        for(JButton jButton:listOfButtons){
            add(jButton, BorderLayout.WEST);
        }

        
//        JButton button = new JButton("Mi bototn");
//        add(button);
        //button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        System.out.println("Hola");
    }
}
