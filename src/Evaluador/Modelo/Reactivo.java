package Evaluador.Modelo;

/*
    Cortes Lopez Jaime Alejandro
    Martinez Martinez Fernando
    2CM3
    Proyecto Final de POO - Aplicador y generador de examenes
*/

//Modelo del reactivo con sus atributos correspondientes

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Reactivo implements Serializable{
    private int id_reactivo;
    private String pregunta;
    private String opcionA, opcionB, opcionC, opcionD;
    private String respuesta;
    private String opc_user;
    Conexion cnx;
    private int opcSocket; //1. Registrar,2. Actualizar

    //queries SQL
    final String REGISTRAR = "{CALL ReactivoProcedure(0,?,?,?,?,?,?,1)}";
    final String CONSULTA_ALL_REACTIVOS = "{CALL ReactivoProcedure(0,'','','','','','',2)}";
    final String CONSULTAR_ONE_REACTIVO = "{CALL ReactivoProcedure(?,'','','','','','',3)}";
    final String ACTUALIZAR = "{CALL ReactivoProcedure(?,?,?,?,?,?,?,4)}";

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
    
    public int getId_reactivo() {
        return id_reactivo;
    }

    public void setId_reactivo(int id_reactivo) {
        this.id_reactivo = id_reactivo;
    }

    public String getOpcionA() {
        return opcionA;
    }

    public void setOpcionA(String opcionA) {
        this.opcionA = opcionA;
    }

    public String getOpcionB() {
        return opcionB;
    }

    public void setOpcionB(String opcionB) {
        this.opcionB = opcionB;
    }

    public String getOpcionC() {
        return opcionC;
    }

    public void setOpcionC(String opcionC) {
        this.opcionC = opcionC;
    }

    public String getOpcionD() {
        return opcionD;
    }

    public void setOpcionD(String opcionD) {
        this.opcionD = opcionD;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getOpc_user() {
        return opc_user;
    }

    public void setOpc_user(String opc_user) {
        this.opc_user = opc_user;
    }

    public int getOpcSocket() {
        return opcSocket;
    }

    public void setOpcSocket(int opcSocket) {
        this.opcSocket = opcSocket;
    }
    
    
    //Metodo para registrar un reactivo, regresa true si registro exitoso
    public boolean registrarReactivo(Reactivo r){
        cnx = new Conexion();
        boolean bandera = false;
        CallableStatement st = null;
        ResultSet rs = null;
        try{
            st = cnx.getConexion().prepareCall(REGISTRAR);
            st.setString(1, r.getPregunta());
            st.setString(2,r.getOpcionA());
            st.setString(3,r.getOpcionB());
            st.setString(4,r.getOpcionC());
            st.setString(5,r.getOpcionD());
            st.setString(6,r.getRespuesta());
            rs = st.executeQuery();
            while(rs.next()){
                int id_reactivo = rs.getInt("id_reactivo");
                if(id_reactivo > 0){
                    bandera = true;
                }
            }
        }catch(Exception e){
            System.out.println("Error al registrar reactivo: " + e.getMessage());
        }
        return bandera;
    }
    
    //Metodo para obtener todos los reactivos de la BD, regresa un arreglo de Reactivos
    public List<Reactivo> allReactivos(){
        cnx = new Conexion();
        List<Reactivo> all_reactivos = new ArrayList<>();
        CallableStatement st = null;
        ResultSet rs = null;
        try {
            st = cnx.getConexion().prepareCall(CONSULTA_ALL_REACTIVOS);
            rs = st.executeQuery();
            while(rs.next()){
                Reactivo r = new Reactivo();
                r.setId_reactivo(rs.getInt("id_reactivo"));
                r.setPregunta(rs.getString("pregunta"));
                r.setOpcionA(rs.getString("opcionA"));
                r.setOpcionB(rs.getString("opcionB"));
                r.setOpcionC(rs.getString("opcionC"));
                r.setOpcionD(rs.getString("opcionD"));
                r.setRespuesta(rs.getString("respuesta"));
                all_reactivos.add(r);
            }
            
        } catch (Exception e) {
            System.out.println("Error consulta All Reactivos: "+ e.getMessage());
        }
        
        return all_reactivos;
    }
    
    //Metodo para obtener los datos de un reactivo
    public Reactivo oneReactivo(int id_reactivo){
        cnx = new Conexion();
        Reactivo r = new Reactivo();
        CallableStatement st = null;
        ResultSet rs = null;
        try {
            st = cnx.getConexion().prepareCall(CONSULTAR_ONE_REACTIVO);
            st.setInt(1, id_reactivo);
            rs = st.executeQuery();
            while(rs.next()){
                r.setId_reactivo(rs.getInt("id_reactivo"));
                r.setPregunta(rs.getString("pregunta"));
                r.setOpcionA(rs.getString("opcionA"));
                r.setOpcionB(rs.getString("opcionB"));
                r.setOpcionC(rs.getString("opcionC"));
                r.setOpcionD(rs.getString("opcionD"));
                r.setRespuesta(rs.getString("respuesta"));
            }
        } catch (Exception e) {
            System.out.println("Error consulta All Reactivos: "+ e.getMessage());
        }
        return r;
    }
    
    //Metodo para actualizar un reactivo, regresa true si la actualizacion fue exitosa
    public boolean actualizarReactivo(Reactivo r){
        cnx = new Conexion();
        boolean bandera = false;
        CallableStatement st = null;
        ResultSet rs = null;
        try{
            st = cnx.getConexion().prepareCall(ACTUALIZAR);
            st.setInt(1, r.getId_reactivo());
            st.setString(2, r.getPregunta());
            st.setString(3,r.getOpcionA());
            st.setString(4,r.getOpcionB());
            st.setString(5,r.getOpcionC());
            st.setString(6,r.getOpcionD());
            st.setString(7,r.getRespuesta());
            
            rs = st.executeQuery();
            while(rs.next()){
                if(rs.getString("msj").equals("actualizado")){
                    bandera = true;
                }
            }
        }catch(Exception e){
            System.out.println("Error al registrar reactivo: " + e.getMessage());
        }
        return bandera;
    }
    
}
