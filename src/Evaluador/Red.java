package Evaluador;

import Evaluador.Modelo.Reactivo;
import Evaluador.Modelo.Usuario;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Red implements Runnable{
    
    @Override
    public void run(){
        String msj ="";
        try {
            ServerSocket servidor = new ServerSocket(8085);
            JOptionPane.showMessageDialog(null, "Servidor iniciado correctamente");
            while(true){
            Socket s = servidor.accept();
            System.out.println("Conexion en socket aceptada");
            ObjectInputStream recibe = new ObjectInputStream(s.getInputStream());
            ObjectOutputStream envia = new ObjectOutputStream(s.getOutputStream());
            Object obj = recibe.readObject();
            
                if(obj.getClass().getSimpleName().equals("Usuario")){
                    System.out.println("Type: Usuario");
                    if(((Usuario)obj).getOpcSocket() == 1){ //Registra
                        Usuario usr = new Usuario();
                        boolean register = usr.registarse((Usuario)obj);
                        envia.writeObject(register);
                    }else{
                        if(((Usuario)obj).getOpcSocket() == 2){ //Login
                            Usuario usr = new Usuario();
                            usr = usr.iniciarSesion((Usuario) obj);
                            envia.writeObject(usr);
                        }
                    }
                }else{
                    if(obj.getClass().getSimpleName().equals("Reactivo")){
                        if(((Reactivo)obj).getOpcSocket() == 1){
                            Reactivo r = new Reactivo();
                            boolean registro = r.registrarReactivo((Reactivo)obj);
                            envia.writeObject(registro);
                        }else{
                            if(((Reactivo)obj).getOpcSocket() == 2){
                                Reactivo r = new Reactivo();
                                boolean registro = r.actualizarReactivo((Reactivo)obj);
                                envia.writeObject(registro);
                            }
                        }
                        
                    }else{
                        System.out.println("nel");
                    }
                    
                }
            
            }
        } catch (Exception ex) {
            System.out.println("Error en servidor: " + ex.getMessage());
        }
    }
    
}
