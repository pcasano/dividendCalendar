/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dividendcalendar;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author pablo
 */
public class Gui extends JFrame{
 
    public Gui(){
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(MAXIMIZED_BOTH);
        setTitle("Dividend Calendar");
        
        ButtonsFrame buttonsFrame = new ButtonsFrame();
        add(buttonsFrame);
       
        
    }
    
    
}

class  ButtonsFrame extends JPanel implements ActionListener{
    JButton button = new JButton("Mi bototn");
    public ButtonsFrame(){
        add(button);
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        System.out.println("Hola");
    }
}
