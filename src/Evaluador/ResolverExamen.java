package Evaluador;

/*
    Cortes Lopez Jaime Alejandro
    Martinez Martinez Fernando
    2CM3
    Proyecto Final de POO - Aplicador y generador de examenes
*/

import Evaluador.Modelo.Examen;
import Evaluador.Modelo.Reactivo;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class ResolverExamen extends JFrame implements ActionListener, ItemListener{
    //Se declaran los componentes de la ventana
    JLabel header, pregunta, rect, tiempo;
    ButtonGroup group;
    JButton siguiente, abandonar;
    JRadioButton opcA, opcB, opcC, opcD;
    Font fo = new Font("Arial Rounded MT Bold", Font.PLAIN,18);
    int [] reactivos;
    int numPregunta;
    int id_examen;
    String respuestaUser, titulo;
    Timer timer;
    int contador = 10;
    boolean stop = false;
      
    public ResolverExamen(String titu, int id_examen, int numPregunta, int[] reactivos){
        if(numPregunta == 10){
            Resultado  r = new Resultado(id_examen);
            dispose();
        }else{

        this.reactivos = reactivos; //Id's de los reactivos de un examen
        this.numPregunta = numPregunta; //Numero actual de la pregunta
        this.id_examen = id_examen;
        this.titulo = titu;
        //Se inicia la ventana
        setBounds(20, 20, 1000, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(229,229,229));
        
        header = new JLabel(titu, SwingConstants.CENTER);
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
        
        pregunta = new JLabel("");
        pregunta.setFont(fo.deriveFont(16f));
        pregunta.setBounds(120,100, 700,50);
        add(pregunta,0);
        
        tiempo = new JLabel("");
        tiempo.setFont(fo.deriveFont(16f));
        tiempo.setBounds(860, 60, 150, 40);
        add(tiempo,0);
        
        group  = new ButtonGroup();
        opcA = new JRadioButton("Opcion A");
        opcA.setFont(fo.deriveFont(16f));
        opcA.setBounds(150, 200, 600, 30);
        opcA.setBackground(Color.WHITE);
        opcA.addItemListener(this);
        add(opcA,0);
        group.add(opcA);
        
        opcB = new JRadioButton("Opcion B");
        opcB.setFont(fo.deriveFont(16f));
        opcB.setBounds(150, 250, 600, 30);
        opcB.setBackground(Color.WHITE);
        opcB.addItemListener(this);
        add(opcB,0);
        group.add(opcB);
        
        opcC = new JRadioButton("Opcion C");
        opcC.setFont(fo.deriveFont(16f));
        opcC.setBounds(150, 300, 600, 30);
        opcC.setBackground(Color.WHITE);
        opcC.addItemListener(this);
        add(opcC,0);
        group.add(opcC);
        
        opcD = new JRadioButton("Opcion D");
        opcD.setFont(fo.deriveFont(16f));
        opcD.setBounds(150, 350, 600, 30);
        opcD.setBackground(Color.WHITE);
        opcD.addItemListener(this);
        add(opcD,0);
        group.add(opcD);
               
        siguiente = new JButton("Responder");
        siguiente.setForeground(Color.WHITE);
        siguiente.setBackground(new Color(81,45,168));
        siguiente.setFont(fo);
        siguiente.setBounds(800, 500, 150, 40);
        siguiente.addActionListener(this);
        add(siguiente,0);
        
        abandonar = new JButton("Abandonar");
        abandonar.setForeground(Color.WHITE);
        abandonar.setBackground(new Color(81,45,168));
        abandonar.setFont(fo);
        abandonar.setBounds(800, 100, 150, 40);
        abandonar.addActionListener(this);
        add(abandonar,0);
        
        cargarPregunta(id_examen, reactivos[numPregunta]);
        setVisible(true);
        //Se inicia el contador
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         stop = true;
        if(respuestaUser.equals("")){
            respuestaUser = "s"; //si el usuario deja pregunta en blanco la respuesta es incorrecta
        }
        if(e.getActionCommand().equals("Responder")){
            Examen ex = new Examen();
            responder(id_examen, reactivos[numPregunta], respuestaUser);
            numPregunta++;
            ResolverExamen r = new ResolverExamen(titulo, id_examen, numPregunta, reactivos);
            dispose();
        }else{
            if(e.getActionCommand().equals("Siguiente")){//Se pasa a la siguiente pregunta
                numPregunta++;
                ResolverExamen r = new ResolverExamen(titulo, id_examen, numPregunta, reactivos);
                dispose();
            }else{
                if(e.getActionCommand().equals("Abandonar")){
                    Login l = new Login();
                    dispose();
                }
            }
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent ie) {
        if(opcA.isSelected()){
            respuestaUser = "a";
        }else{
            if(opcB.isSelected()){
                respuestaUser="b";
            }else{
                if(opcC.isSelected()){
                    respuestaUser="c";
                }else{
                    if(opcD.isSelected()){
                        respuestaUser="d";
                    }
                }
            }
        }
    }
    
    public void responder(int id_examen, int id_reactivo, String opc){
            Examen ex = new Examen();
            if(ex.Responde(id_examen, id_reactivo, opc)){
                System.out.println("Respuesta contestada");
            }else{
                System.out.println("Error al contestar");
            }
    }
    
    public void cargarPregunta(int id_ex, int id_reac){
        Examen ex = new Examen();
        Reactivo r = ex.reactivoRespondido(id_ex, id_reac);
        pregunta.setText("<html><p>"+r.getPregunta()+"</p></html>");
        opcA.setText(r.getOpcionA());
        opcB.setText(r.getOpcionB());
        opcC.setText(r.getOpcionC());
        opcD.setText(r.getOpcionD());      
        respuestaUser = r.getOpc_user();
        System.out.println(respuestaUser);
        if(!respuestaUser.equals("n") || respuestaUser.equals("s")){ //Si la respuesta ya fue contestada alguna vez ya no puede cambiarla
          opcA.setEnabled(false);
          opcB.setEnabled(false);
          opcC.setEnabled(false);
          opcD.setEnabled(false);
          siguiente.setText("Siguiente");
          
          if(respuestaUser.equals("a")){
            opcA.setSelected(true);
            }else{
                if(respuestaUser.equals("b")){
                opcB.setSelected(true);
                }else{
                    if(respuestaUser.equals("c")){
                        opcC.setSelected(true);
                    }else{
                        if(respuestaUser.equals("d")){
                            opcD.setSelected(true);
                        }
                    }
                }
            }
        }else{
            //se inicia el contador
            tiempo();
        }
    }
    
      public void tiempo(){
        timer = new Timer(1000, new ActionListener(){ //Ajustando para que cuente cada segundo
                @Override
                public void actionPerformed(ActionEvent e){
                    System.out.println(contador);
                    contador--;
                    tiempo.setText(contador + " s");
                    if(contador == 0 && !stop){
                        timer.stop();
                        responder(id_examen, reactivos[numPregunta],"s");
                        numPregunta++;
                        ResolverExamen r = new ResolverExamen(titulo, id_examen, numPregunta, reactivos);
                        dispose();
                    }else{
                        if(stop){
                            timer.stop();
                        }
                    }
                }
        });
        timer.start();
    }
}
