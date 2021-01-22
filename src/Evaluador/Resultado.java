package Evaluador;

/*
    Lopez Cortes Jaime Alejandro
    Martinez Martinez Fernando
    2CM3
    Proyecto Final de POO - Aplicador y generador de examenes
*/

import Evaluador.Modelo.Examen;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Resultado extends JFrame implements ActionListener{
    //Se declaran los componentes de la ventana
    JLabel header, pregunta, rect;
    JButton salir, calificar;
    Font fo = new Font("Arial Rounded MT Bold", Font.PLAIN,18);
    int id_examen;
    
    public Resultado(int id_examen){
        this.id_examen = id_examen;
        
        //Se inicia la ventana
        setBounds(20, 20, 1000, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(229,229,229));
        
        header = new JLabel("Resultado", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(81,45,168));
        header.setForeground(Color.WHITE);
        header.setFont(fo);
        header.setBounds(0, 0, 1000, 50);
        add(header);
        
        rect = new JLabel();
        rect.setOpaque(true);
        rect.setBackground(Color.WHITE);
        rect.setBounds(100, 80, 750, 400);
        add(rect,1);
        
        pregunta = new JLabel("Ha terminado el examen, puede revisar su calificacion", SwingConstants.CENTER);
        pregunta.setFont(fo.deriveFont(16f));
        pregunta.setBounds(120,100, 700,50);
        add(pregunta,0);
        
        salir = new JButton("Salir");
        salir.setForeground(Color.WHITE);
        salir.setBackground(new Color(81,45,168));
        salir.setFont(fo);
        salir.setBounds(800, 500, 150, 40);
        salir.addActionListener(this);
        add(salir,0);
        salir.setEnabled(false);
        
        calificar = new JButton("Calificar");
        calificar.setForeground(Color.WHITE);
        calificar.setBackground(new Color(81,45,168));
        calificar.setFont(fo);
        calificar.setBounds(425, 240, 150, 40);
        calificar.addActionListener(this);
        add(calificar,0);
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Calificar")){ //se califica el examen
            Examen ex = new Examen();
            pregunta.setText("Su calificacion fue: " + ex.calificar(id_examen));
            calificar.setEnabled(false);
            salir.setEnabled(true);
        }else{
            if(e.getActionCommand().equals("Salir")){//Se pasa al login
                Login l = new Login();
                dispose();
               
            }
        }
    }
}
