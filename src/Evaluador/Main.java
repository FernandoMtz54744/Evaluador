package Evaluador;

/*
    Cortes Lopez Jaime Alejandro
    Martinez Martinez Fernando
    2CM3
    Proyecto Final de POO - Aplicador y generador de examenes
*/

public class Main {
    public static void main(String args[]){
        Login l = new Login();
        Thread h = new Thread(new Red());
        h.start();
    }
}
