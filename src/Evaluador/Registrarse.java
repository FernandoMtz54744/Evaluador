package Evaluador;

/*
    Lopez Cortes Jaime Alejandro
    Martinez Martinez Fernando
    2CM3
    Proyecto Final de POO - Aplicador y generador de examenes
*/

import Evaluador.Modelo.Usuario;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Registrarse extends JFrame implements ActionListener, ItemListener{
    
    //Se declaran los componentes de la ventana
    JLabel header, usuarioText, passwordText, rect;
    JTextField usuario, key;
    JPasswordField pass;
    JButton crear,back;
    Font fo = new Font("Arial Rounded MT Bold", Font.PLAIN,18);
    JRadioButton tipo1, tipo2;
    ButtonGroup group;
    String keyS = "123456";
    String tipo="Usuario";
            
    public Registrarse(){
        //Se inicia la ventana
        setBounds(20, 20, 1000, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(229,229,229));
        
        header = new JLabel("Registro", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(81,45,168));
        header.setForeground(Color.WHITE);
        header.setFont(fo);
        header.setBounds(0, 0, 1000, 50);
        add(header);
        
        rect = new JLabel();
        rect.setOpaque(true);
        rect.setBackground(Color.WHITE);
        rect.setBounds(300, 80, 400, 400);
        add(rect,1);
        
        usuarioText = new JLabel("Nombre de usuario");
        usuarioText.setFont(fo.deriveFont(16f));
        usuarioText.setBounds(350,100, 200,20);
        add(usuarioText,0);
        
        passwordText = new JLabel("Password");
        passwordText.setFont(fo.deriveFont(16f));
        passwordText.setBounds(350,180,100,20);
        add(passwordText,0);
        
        usuario = new JTextField();
        usuario.setBounds(350, 130, 300, 30);
        usuario.setFont(fo.deriveFont(16f));
        add(usuario,0);
        
        key = new JTextField(keyS);
        key.setBounds(350, 325, 300, 30);
        key.setFont(fo.deriveFont(16f));
        add(key,0);
        key.setVisible(false);
        
        pass = new JPasswordField();
        pass.setBounds(350, 200, 300, 30);
        pass.setFont(fo.deriveFont(16f));
        add(pass,0);
        
        group = new ButtonGroup();
        tipo1 = new JRadioButton("Usuario");
        tipo1.setFont(fo.deriveFont(16f));
        tipo1.setBounds(350, 250, 100, 30);
        tipo1.setBackground(Color.WHITE);
        tipo1.addItemListener(this);
        tipo1.setSelected(true);
        add(tipo1,0);
        group.add(tipo1);
        
        tipo2 = new JRadioButton("Administrador");
        tipo2.setFont(fo.deriveFont(16f));
        tipo2.setBounds(350, 280, 150, 30);
        tipo2.setBackground(Color.WHITE);
        tipo2.addItemListener(this);
        add(tipo2,0);
        group.add(tipo2);
        
        crear = new JButton("Crear usuario");
        crear.setForeground(Color.WHITE);
        crear.setBackground(new Color(81,45,168));
        crear.setFont(fo);
        crear.setBounds(350, 380, 300, 40);
        crear.addActionListener(this);
        add(crear,0);
        
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
        if(e.getActionCommand().equals("Crear usuario")){
            //Se registra al usuario
            if(key.getText().equals(keyS)){ //Antes la clave de coincidir
                Usuario usr = new Usuario();
                usr.setUsuario(usuario.getText());
                usr.setPass(String.valueOf(pass.getPassword()));
                usr.setTipo(tipo);
                if(usr.registarse(usr)){ //True si registro exitoso, se devuelve al login
                    JOptionPane.showMessageDialog(null, "Registro exitoso, ya puede loguearse");
                    Login l = new Login();
                    dispose();
                }else{// registro fallido
                    JOptionPane.showMessageDialog(null, "Error en el registro");
                }
            }else{
                JOptionPane.showMessageDialog(null, "La clave no es correcta");
            }
            
        }else{
            if(e.getActionCommand().equals("Regresar")){
                //Se regresa al login
                Login l = new Login();
                dispose();
            }
        }
        
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        if(tipo1.isSelected()){
            //Tipo = Usuario seleccionado
            key.setText(keyS);
            key.setVisible(false);
            tipo = "Usuario";
        }else{
            if(tipo2.isSelected()){
                //Tipo = Administrador
                key.setText("Teclee la llave");
                key.setVisible(true);
                tipo = "Administrador";
            }
        }
    }
}
