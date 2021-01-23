package Evaluador;

/*
    Cortes Lopez Jaime Alejandro
    Martinez Martinez Fernando
    2CM3
    Proyecto Final de POO - Aplicador y generador de examenes
*/

import Evaluador.Modelo.Reactivo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import java.util.ArrayList;

public class MenuAdmin extends JFrame implements ActionListener{
    //Se declaran los componentes de la ventana
    JLabel header, info, rect;
    JButton registraPregunta, salir;
    Font fo = new Font("Arial Rounded MT Bold", Font.PLAIN,18);
    JPanel contenedor, buttonPanel; //contenedor de los botones de los reactivos
    JScrollPane scroll;
    
    public MenuAdmin(){
        //Se inicia la ventana
        setBounds(20, 20, 1000, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(229,229,229));
        
        header = new JLabel("Preguntas registradas", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(81,45,168));
        header.setForeground(Color.WHITE);
        header.setFont(fo);
        header.setBounds(0, 0, 1000, 50);
        add(header);
        
        info = new JLabel("Seleccione una pregunta para editarla, o bien cree una nueva");
        info.setFont(fo.deriveFont(16f));
        info.setBounds(200,60, 800,20);
        add(info,0);
        
        registraPregunta = new JButton("+");
        registraPregunta.setForeground(Color.WHITE);
        registraPregunta.setBackground(new Color(81,45,168));
        registraPregunta.setFont(fo.deriveFont(22f));
        registraPregunta.setBounds(880, 480, 60, 60);
        registraPregunta.addActionListener(this);
        add(registraPregunta,0);
        
        salir = new JButton("Salir");
        salir.setForeground(Color.WHITE);
        salir.setBackground(new Color(81,45,168));
        salir.setFont(fo);
        salir.setBounds(50, 510, 100, 40);
        salir.addActionListener(this);
        add(salir,0);
        
        //Se cargan los reactivos
                //Cargando los componenetes
       buttonPanel = new JPanel(); 
       buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
       buttonPanel.setBackground(Color.WHITE);
       //Recuperando el registro de botones
       java.util.List<Reactivo> reactivos = new ArrayList<>();
       Reactivo r = new Reactivo();
       reactivos = r.allReactivos();
       //Agregando los botones por reactivo
        for(int i=0; i<reactivos.size() ; i++){
            JButton exButton = new JButton(reactivos.get(i).getPregunta());
            exButton.setPreferredSize(new Dimension(730, 50));
            exButton.setMaximumSize(new Dimension(730, 50));
            exButton.setBackground(new Color(81,45,168));
            exButton.setFont(fo);
            exButton.setForeground(Color.WHITE);
            exButton.setName(""+reactivos.get(i).getId_reactivo());
            exButton.addActionListener(this);
            buttonPanel.add(exButton);
            buttonPanel.add(Box.createRigidArea(new Dimension(0,5)));
        }
        scroll = new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(100, 100, 750, 400);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll);
        
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("+")){
            //Se va a registrar preguntas
            EditPreguntas ed = new EditPreguntas(-1);
            dispose();
        }else{
            if(e.getActionCommand().equals("Salir")){
                Login l = new Login();
                dispose();
            }else{//Se revisa una pregunta ya existente
                int id_reactivo = Integer.parseInt((String)(((Component)e.getSource()).getName()));
                EditPreguntas ed = new EditPreguntas(id_reactivo);
                dispose();
            }
        }
    }
}
