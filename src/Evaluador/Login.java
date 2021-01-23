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
import javax.swing.*;

public class Login extends JFrame implements ActionListener{
    //Se declaran los componentes de la ventana
    JLabel header, usuarioText, passwordText, rect;
    JTextField usuario;
    JPasswordField pass;
    JButton login, registrarse, info;
    Font fo = new Font("Arial Rounded MT Bold", Font.PLAIN,18);
    
    public Login(){
        //Se inicia la ventana
        setBounds(20, 20, 1000, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(229,229,229));
        
        header = new JLabel("Evaluador y Generador de Examenes", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(81,45,168));
        header.setForeground(Color.WHITE);
        header.setFont(fo);
        header.setBounds(0, 0, 1000, 50);
        add(header);
        
        rect = new JLabel();
        rect.setOpaque(true);
        rect.setBackground(Color.WHITE);
        rect.setBounds(300, 100, 400, 400);
        add(rect,1);
        
        usuarioText = new JLabel("Usuario");
        usuarioText.setFont(fo.deriveFont(16f));
        usuarioText.setBounds(350,150, 100,20);
        add(usuarioText,0);
        
        passwordText = new JLabel("Password");
        passwordText.setFont(fo.deriveFont(16f));
        passwordText.setBounds(350,250,100,20);
        add(passwordText,0);
        
        usuario = new JTextField();
        usuario.setBounds(350, 180, 300, 30);
        usuario.setFont(fo.deriveFont(16f));
        add(usuario,0);
        
        pass = new JPasswordField();
        pass.setBounds(350, 280, 300, 30);
        pass.setFont(fo.deriveFont(16f));
        add(pass,0);
        
        login = new JButton("Login");
        login.setForeground(Color.WHITE);
        login.setBackground(new Color(81,45,168));
        login.setFont(fo);
        login.setBounds(350, 380, 300, 40);
        login.addActionListener(this);
        add(login,0);
        
        registrarse = new JButton("Registrarse");
        registrarse.setForeground(Color.WHITE);
        registrarse.setBackground(new Color(81,45,168));
        registrarse.setFont(fo.deriveFont(14f));
        registrarse.setBounds(500, 450, 150, 30);
        registrarse.addActionListener(this);
        add(registrarse,0);
        
        info = new JButton("Acerca de");
        info.setForeground(Color.WHITE);
        info.setBackground(new Color(81,45,168));
        info.setFont(fo);
        info.setBounds(800, 500, 150, 40);
        info.addActionListener(this);
        add(info,0);
        
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Login")){
            //Se loguea usuario
            Usuario usr = new Usuario();
            usr.setId_usuario(-1);
            usr.setUsuario(usuario.getText());
            usr.setPass(String.valueOf(pass.getPassword()));
            usr = usr.iniciarSesion(usr);
            if(usr.getId_usuario() != -1){ //se va al menu de usuario
                if(usr.getTipo().equals("Administrador")){
                    MenuAdmin m = new MenuAdmin();
                    dispose();
                }else{
                     MenuAlumno m = new MenuAlumno(usr.getId_usuario());
                     dispose();
                }
               
            }else{//usuario no registrado o datos incorrectos
                JOptionPane.showMessageDialog(null, "Usuario no registrado o datos incorrectos");
            }
        }else{
            if(e.getActionCommand().equals("Registrarse")){
                //Se va a ventana de registro
                Registrarse r = new Registrarse();
                dispose();
            }else{
                if(e.getActionCommand().equals("Acerca de")){
                    //se va a ventana acerca de
                    Acerca a = new Acerca();
                    dispose();
                }
            }
        }
    }
}
