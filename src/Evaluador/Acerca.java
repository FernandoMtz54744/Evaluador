package Evaluador;

/*
    Cortes Lopez Jaime Alejandro
    Martinez Martinez Fernando
    2CM3
    Proyecto Final de POO - Aplicador y generador de examenes
*/

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Acerca extends JFrame implements ActionListener{
    //Se declaran los componentes de la ventana
    JLabel header, info, rect;
    JButton back;
    Font fo = new Font("Arial Rounded MT Bold", Font.PLAIN,18);
    
    public Acerca(){
        //Se inicia la ventana
        setBounds(20, 20, 1000, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(229,229,229));
        
        header = new JLabel("Acerca de", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(81,45,168));
        header.setForeground(Color.WHITE);
        header.setFont(fo);
        header.setBounds(0, 0, 1000, 50);
        add(header);
        
        rect = new JLabel();
        rect.setOpaque(true);
        rect.setBackground(Color.WHITE);
        rect.setBounds(100, 100, 800, 400);
        add(rect,1);
        
        info = new JLabel("<html><p>Este es un sistema que permite Generar y Evaluar exámenes<br><br>"
                + "<li type=\"disc\"><b>Usuario:</b> Solo puede responder examenes y ver su calificacion</li>"
                + "<li type=\"disc\"><b>Administrador:</b> Puede crear y editar reactivos</li><br><br><br>"
                + "Cortes Lopez Jaime Aleandro<br>"
                + "Martinez Martinez Fernando<br>"
                + "2CM3 Programación Orientada a Objetos<br><br>"
                + "<b>Escuela Superior de Cómputo<br>"
                + "Instituto Politécnico Nacional</b>"
                + "</p></html>", SwingConstants.CENTER);
        info.setFont(fo.deriveFont(16f));
        info.setBounds(150,80, 700,400);
        add(info,0);
        
        back = new JButton("Regresar");
        back.setForeground(Color.WHITE);
        back.setBackground(new Color(81,45,168));
        back.setFont(fo);
        back.setBounds(800, 500, 150, 40);
        back.addActionListener(this);
        add(back,0);
        
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            Login l = new Login();
            dispose();
    }
}
