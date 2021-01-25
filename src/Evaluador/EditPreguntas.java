package Evaluador;

/*
    Cortes Lopez Jaime Alejandro
    Martinez Martinez Fernando
    2CM3
    Proyecto Final de POO - Aplicador y generador de examenes
*/

import Evaluador.Modelo.Reactivo;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

//clase para editar o crear Reactivos
public class EditPreguntas extends JFrame implements ActionListener, ItemListener{
    //Se declaran los componentes de la ventana
    JLabel header, preguntaText, rect;
    ButtonGroup group;
    JButton terminar, back;
    JRadioButton opcARadio, opcBRadio, opcCRadio, opcDRadio;
    JTextField pregunta, opcA, opcB, opcC, opcD;
    String respuesta = "a"; //Example 'a' Se obtiene del radio button seleccionado
    Font fo = new Font("Arial Rounded MT Bold", Font.PLAIN,18);
    int id_reactivo;
    
    public EditPreguntas(int id_reactivo){
        this.id_reactivo = id_reactivo;
        //Se inicia la ventana
        setBounds(20, 20, 1000, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(229,229,229));
        
        header = new JLabel("Edite o Registre su pregunta", SwingConstants.CENTER);
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
        
        preguntaText = new JLabel("Pregunta:");
        preguntaText.setFont(fo.deriveFont(16f));
        preguntaText.setBounds(120,100, 100,50);
        add(preguntaText,0);
        
        pregunta = new JTextField();
        pregunta.setBounds(205, 110, 600, 30);
        pregunta.setFont(fo.deriveFont(16f));
        add(pregunta,0);
        
        group  = new ButtonGroup();
        
        opcARadio = new JRadioButton("A");
        opcARadio.setFont(fo.deriveFont(16f));
        opcARadio.setBounds(150, 200, 20, 30);
        opcARadio.setBackground(Color.WHITE);
        opcARadio.addItemListener(this);
        opcARadio.setSelected(true);
        add(opcARadio,0);
        group.add(opcARadio);
        
        opcBRadio = new JRadioButton("B");
        opcBRadio.setFont(fo.deriveFont(16f));
        opcBRadio.setBounds(150, 250, 20, 30);
        opcBRadio.setBackground(Color.WHITE);
        opcBRadio.addItemListener(this);
        add(opcBRadio,0);
        group.add(opcBRadio);
        
        opcCRadio = new JRadioButton("C");
        opcCRadio.setFont(fo.deriveFont(16f));
        opcCRadio.setBounds(150, 300, 20, 30);
        opcCRadio.setBackground(Color.WHITE);
        opcCRadio.addItemListener(this);
        add(opcCRadio,0);
        group.add(opcCRadio);
        
        opcDRadio = new JRadioButton("D");
        opcDRadio.setFont(fo.deriveFont(16f));
        opcDRadio.setBounds(150, 350, 20, 30);
        opcDRadio.setBackground(Color.WHITE);
        opcDRadio.addItemListener(this);
        add(opcDRadio,0);
        group.add(opcDRadio);
        
        opcA = new JTextField();
        opcA.setBounds(180, 200, 400, 30);
        opcA.setFont(fo.deriveFont(16f));
        add(opcA,0);
        
        opcB = new JTextField();
        opcB.setBounds(180, 250, 400, 30);
        opcB.setFont(fo.deriveFont(16f));
        add(opcB,0);
        
        opcC = new JTextField();
        opcC.setBounds(180, 300, 400, 30);
        opcC.setFont(fo.deriveFont(16f));
        add(opcC,0);
        
        opcD = new JTextField();
        opcD.setBounds(180, 350, 400, 30);
        opcD.setFont(fo.deriveFont(16f));
        add(opcD,0);
        
        //Boton editar o registrar
        terminar = new JButton("Registrar");
        terminar.setForeground(Color.WHITE);
        terminar.setBackground(new Color(81,45,168));
        terminar.setFont(fo);
        terminar.setBounds(800, 500, 150, 40);
        terminar.addActionListener(this);
        add(terminar,0);
        
        back = new JButton("Regresar");
        back.setForeground(Color.WHITE);
        back.setBackground(new Color(81,45,168));
        back.setFont(fo);
        back.setBounds(100, 500, 150, 40);
        back.addActionListener(this);
        add(back,0);
        
        if(id_reactivo != -1){ //si id_reactivo es distinto de -1 se carga la pregunta con el id
            cargarReactivo(id_reactivo);
        }
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Reactivo r = new Reactivo();
        r.setId_reactivo(id_reactivo);
        r.setPregunta(pregunta.getText());
        r.setOpcionA(opcA.getText());
        r.setOpcionB(opcB.getText());
        r.setOpcionC(opcC.getText());
        r.setOpcionD(opcD.getText());
        r.setRespuesta(respuesta);
        
        if(e.getActionCommand().equals("Regresar")){
            MenuAdmin m = new MenuAdmin();
            dispose();
        }else{
            if(e.getActionCommand().equals("Registrar")){
                //Se crea un reactivo
                    //RED
                    try{
                        Socket s = new Socket("localhost", 8085);
                        ObjectOutputStream enviar = new ObjectOutputStream(s.getOutputStream());
                        ObjectInputStream recibir = new ObjectInputStream(s.getInputStream());
                        r.setOpcSocket(1); 
                        enviar.writeObject(r);
                        boolean registro = (boolean) recibir.readObject();
                        
                        if (registro) {
                            JOptionPane.showMessageDialog(null, "Reactivo registrado correctamente");
                            MenuAdmin m = new MenuAdmin();
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Ocurrio un error al registrar");
                        }
                    }catch(Exception ex){
                        System.out.println("Error al registrar preguntas");
                    }
            }else{
                if(e.getActionCommand().equals("Actualizar")){
                    
                     //RED
                    try{
                        Socket s = new Socket("localhost", 8085);
                        ObjectOutputStream enviar = new ObjectOutputStream(s.getOutputStream());
                        ObjectInputStream recibir = new ObjectInputStream(s.getInputStream());
                        r.setOpcSocket(2); 
                        enviar.writeObject(r);
                        boolean registro = (boolean) recibir.readObject();
                        
                        if (registro) {
                            JOptionPane.showMessageDialog(null, "Reactivo actualizado correctamente");
                            MenuAdmin m = new MenuAdmin();
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Ocurrio un error al actualizar");
                        }
                    }catch(Exception ex){
                        System.out.println("Error al registrar preguntas");
                    }
                }
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(opcARadio.isSelected()){
            respuesta = "a";
        }else{
            if(opcBRadio.isSelected()){
                respuesta="b";
            }else{
                if(opcCRadio.isSelected()){
                    respuesta="c";
                }else{
                    if(opcDRadio.isSelected()){
                        respuesta="d";
                    }
                }
            }
        }
    }
    
    public void cargarReactivo(int id_reactivo){
        Reactivo r = new Reactivo();
        r = r.oneReactivo(id_reactivo);
        pregunta.setText(r.getPregunta());
        opcA.setText(r.getOpcionA());
        opcB.setText(r.getOpcionB());
        opcC.setText(r.getOpcionC());
        opcD.setText(r.getOpcionD());
        respuesta = r.getRespuesta();
        terminar.setText("Actualizar");
        
        if(respuesta.equals("a")){
            opcARadio.setSelected(true);
        }else{
            if(respuesta.equals("b")){
                opcBRadio.setSelected(true);
            }else{
                if(respuesta.equals("c")){
                    opcCRadio.setSelected(true);
                }else{
                    opcDRadio.setSelected(true);
                }
            }
        }
    }
    
    
}
