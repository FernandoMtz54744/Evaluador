    package Evaluador.Modelo;

/*
    Cortes Lopez Jaime Alejandro
    Martinez Martinez Fernando
    2CM3
    Proyecto Final de POO - Aplicador y generador de examenes
*/

//Modelo de examen con sus atributos correspondientes

import java.sql.*;


public class Examen {
    private int id_examen;
    private int id_usuario;
    private String titulo, fecha;
    private int calificacion;
    Conexion cnx;
    
    //querys SQL
    final String CREAREXAMEN = "{CALL ExamenProcedure(0,?,?,?,'',0,1)}";
    final String CONSULTAREACTIVOS = "{CALL ExamenProcedure(?,0,'','','',0,2)}";
    final String RESPONDER = "{CALL ExamenProcedure(?,0,'','',?,?,3)}";
    final String CONSULTAREXAMEN = "{CALL ExamenProcedure(?,0,'','','',0,4)}";
    final String CALIFICAR = "{CALL ExamenProcedure(?,0,'','','',0,6)}";
    final String CONSULTAREACTIVORESPONDIDO ="{CALL ExamenProcedure(?,0,'','','',?,7)}";

    public int getId_examen() {
        return id_examen;
    }

    public void setId_examen(int id_examen) {
        this.id_examen = id_examen;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }
    
    //Metodo para crear un examen, devuelve el id de examen
    public int crearExamen(int id_usr, Examen ex){
        cnx = new Conexion();
        CallableStatement st = null;
        ResultSet rs = null;
        int id_ex=0;
        try {
            st = cnx.getConexion().prepareCall(CREAREXAMEN);
            st.setInt(1, id_usr);
            st.setString(2, ex.getTitulo());
            st.setString(3, ex.getFecha());
            rs = st.executeQuery();
            while(rs.next()){
                id_ex = rs.getInt("id_examen");
            }
        } catch (Exception e) {
            System.out.println("Error crear Examen: " + e.getMessage());
        }
      return id_ex;
    }
    
    //metodo para obtener los reactivos pertenecientes a un examen, devuelve un arreglo con los 10 id's de los reactivos
    public int[] reactivosExamen(int id_examen){
        cnx = new Conexion();
        int [] reactivos = new int[10];
        CallableStatement st = null;
        ResultSet rs = null;
        try {
            st = cnx.getConexion().prepareCall(CONSULTAREACTIVOS);
            st.setInt(1, id_examen);
            rs = st.executeQuery();
            int contador  = 0;
            while(rs.next()){
                reactivos[contador] = rs.getInt("id_reactivo");
                contador++;
            }
        } catch (Exception e) {
            System.out.println("Error consultar reactivos examen: " + e.getMessage());
        }
      return reactivos;
    }
    
    //Metodo para responder una pregunta, regresa true si la respuesta fue actualizada
    public boolean Responde(int id_examen, int id_reactivo, String opc_usr){
        boolean bandera = false;
        cnx = new Conexion();
        CallableStatement st = null;
        ResultSet rs = null;
        try {
            st = cnx.getConexion().prepareCall(RESPONDER);
            st.setInt(1, id_examen);
            st.setString(2, opc_usr);
            st.setInt(3, id_reactivo);
            rs = st.executeQuery();
            while(rs.next()){
                if(rs.getString("msj").equals("actualizado")){
                    bandera = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error responder pregunta: " + e.getMessage());
        }
      return bandera;
    }
    
    
    //Metodo para consultar un examen, devuelve todos los datos de un examen
    public Examen consultarExamen(int id_ex){
        cnx = new Conexion();
        Examen ex = null;
        CallableStatement st = null;
        ResultSet rs = null;
        try {
            st = cnx.getConexion().prepareCall(CONSULTAREXAMEN);
            st.setInt(1, id_ex);
            rs = st.executeQuery();
            while(rs.next()){
                ex.setId_examen(rs.getInt("id_examen"));
                ex.setTitulo(rs.getString("titulo"));
                ex.setFecha(rs.getString("fecha"));
                ex.setId_usuario(rs.getInt("id_usuario"));
                ex.setCalificacion(rs.getInt("calificacion"));
            }
        } catch (Exception e) {
               System.out.println("Error consultar examen" + e.getMessage());
        }
      return ex;
    }
    
    //Metodo para calificar un examen, devuelve la calificacion del usuario
    public int calificar (int id_examen ){
        int calificacion = -1;
        CallableStatement st = null;
        ResultSet rs = null;
        cnx = new Conexion();
        try {
            st = cnx.getConexion().prepareCall(CALIFICAR);
            st.setInt(1, id_examen);
            rs = st.executeQuery();
            while(rs.next()){
                calificacion = rs.getInt("calif");
            }
        } catch (Exception e) {
            System.out.println("Error al calificar examen: " + e.getMessage());
        }
     return calificacion;
    }
    
    //Obtiene el dato de una pregunta junto con la respuesta del usuario
    public Reactivo reactivoRespondido(int id_examen, int id_reactivo){
        cnx = new Conexion();
        Reactivo r = new Reactivo();
        CallableStatement st = null;
        ResultSet rs = null;
        try {
            st = cnx.getConexion().prepareCall(CONSULTAREACTIVORESPONDIDO);
            st.setInt(1, id_examen);
            st.setInt(2, id_reactivo);
            rs = st.executeQuery();
            while(rs.next()){
                r.setId_reactivo(rs.getInt("id_reactivo"));
                r.setPregunta(rs.getString("pregunta"));
                r.setOpcionA(rs.getString("opcionA"));
                r.setOpcionB(rs.getString("opcionB"));
                r.setOpcionC(rs.getString("opcionC"));
                r.setOpcionD(rs.getString("opcionD"));
                r.setRespuesta(rs.getString("respuesta"));
                r.setOpc_user(rs.getString("opc_usuario"));
            }
        } catch (Exception e) {
                System.out.println("Error al consultar reactivo respondido: " +e.getMessage() );
        }
        return r;
    }
  
    
}
