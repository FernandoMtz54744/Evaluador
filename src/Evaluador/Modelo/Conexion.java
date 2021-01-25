package Evaluador.Modelo;

/*
    Cortes Lopez Jaime Alejandro
    Martinez Martinez Fernando
    2CM3
    Proyecto Final de POO - Aplicador y generador de examenes
*/

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion implements Serializable{
   private Connection conexion = null;
    final String MOTOR = "mysql";
    final String SERVIDOR = "localhost";
    final String PUERTO = "8084";
    final String BASE = "Evaluador";
    final String USUARIO = "root";
    final String CONTRA = "n0m3l0";
	
    public Conexion() {

            final String URL = "jdbc:" + MOTOR + "://" + SERVIDOR + "/" + BASE + "?serverTimezone=UTC";	
            final String USERNAME = USUARIO;
            final String PASSWORD = CONTRA;
            try {

                Class.forName("com." + MOTOR + ".cj.jdbc.Driver");
                conexion = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Conexion exitosa");

            } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Conexion fallida");

            }

    }

    public Connection getConexion() {
            return conexion;
    }
}
