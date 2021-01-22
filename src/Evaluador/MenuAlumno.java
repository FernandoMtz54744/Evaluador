package Evaluador;

/*
    Lopez Cortes Jaime Alejandro
    Martinez Martinez Fernando
    2CM3
    Proyecto Final de POO - Aplicador y generador de examenes
*/

import Evaluador.Modelo.Examen;
import Evaluador.Modelo.Usuario;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;

public class MenuAlumno extends JFrame implements ActionListener{
    //Se declaran los componentes de la ventana
    JLabel header, info, rect;
    JButton crearExamen, salir;
    Font fo = new Font("Arial Rounded MT Bold", Font.PLAIN,18);
    int id_usuario;
    JPanel contenedor, buttonPanel; //contenedor de los botones de examenes
    JScrollPane scroll;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public MenuAlumno(int id_usuario){
        //Se acepta guarda el id_usuario
        this.id_usuario = id_usuario;
        //Se inicia la ventana
        setBounds(20, 20, 1000, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(229,229,229));
        
        header = new JLabel("Examenes", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(81,45,168));
        header.setForeground(Color.WHITE);
        header.setFont(fo);
        header.setBounds(0, 0, 1000, 50);
        add(header);
               
        info = new JLabel("Seleccione un examen para ver su respuestas, o bien cree uno nuevo");
        info.setFont(fo.deriveFont(16f));
        info.setBounds(200,60, 800,20);
        add(info,0);
        
        crearExamen = new JButton("+");
        crearExamen.setForeground(Color.WHITE);
        crearExamen.setBackground(new Color(81,45,168));
        crearExamen.setFont(fo.deriveFont(22f));
        crearExamen.setBounds(880, 480, 60, 60);
        crearExamen.addActionListener(this);
        add(crearExamen,0);
        
        salir = new JButton("Salir");
        salir.setForeground(Color.WHITE);
        salir.setBackground(new Color(81,45,168));
        salir.setFont(fo);
        salir.setBounds(50, 510, 100, 40);
        salir.addActionListener(this);
        add(salir,0);
        
        
        //Se cargan los examenes
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        //Se obtienen todos los examnes para un usuario
       List<Examen> examenes = new ArrayList<>();
       Usuario usr = new Usuario();
       examenes = usr.consultarExamenes(id_usuario);
       
        for(int i=0; i<examenes.size(); i++){
            JButton exButton;
            if(examenes.get(i).getCalificacion() != -1){
                exButton = new JButton(examenes.get(i).getTitulo()+" | "+examenes.get(i).getFecha() + " |  Calif " + examenes.get(i).getCalificacion()+"/10");
            }else{
                exButton = new JButton(examenes.get(i).getTitulo() +" | "+examenes.get(i).getFecha() + " |  No terminado");
            }
            exButton.setPreferredSize(new Dimension(730, 50));
            exButton.setMaximumSize(new Dimension(730, 50));
            exButton.setBackground(new Color(81,45,168));
            exButton.setFont(fo);
            exButton.setForeground(Color.WHITE);
            exButton.setName(""+examenes.get(i).getId_examen());
            exButton.putClientProperty("titulo",examenes.get(i).getTitulo());
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
        Examen ex = new Examen();
        if(e.getActionCommand().equals("+")){
            //Se crea un examen
            String titulo = JOptionPane.showInputDialog(null, "Agrege un titulo a su examen");
            ex.setTitulo(titulo);
            LocalDateTime now = LocalDateTime.now();  
            ex.setFecha(dtf.format(now));
            int id_ex = ex.crearExamen(id_usuario, ex);
            int [] reactivos  = ex.reactivosExamen(id_ex);
            ResolverExamen resolve  =new ResolverExamen(titulo, id_ex, 0, reactivos);
            dispose();
        }else{
            if(e.getActionCommand().equals("Salir")){
                Login l = new Login();
                dispose();
            }else{
                //Se pasa a un examen ya creado
                int id_examen = Integer.parseInt((String)(((Component)e.getSource()).getName()));
                String titulo = (String)((JButton)e.getSource()).getClientProperty("titulo");
                int [] reactivos  = ex.reactivosExamen(id_examen);
                ResolverExamen resolve = new ResolverExamen(titulo,id_examen,0, reactivos);
                dispose();
            }
        }
    }
}
