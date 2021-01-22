    package Evaluador.Modelo;

/*
    Lopez Cortes Jaime Alejandro
    Martinez Martinez Fernando
    2CM3
    Proyecto Final de POO - Aplicador y generador de examenes
*/

//Modelo del usuario con sus atributos y métodos correspondientes
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class Usuario {
    private int id_usuario;
    private String usuario;
    private String pass;
    private String tipo;
    Conexion cnx;
    
    //querys a ejecutar
    final String REGISTRARSE = "{CALL UsuarioProcedure(0,?,?,?,1)}";
    final String INICIAR_SESION = "{CALL UsuarioProcedure(0,?,?,'n',2)}";
    final String CONSULTAREXAMENES = "{CALL ExamenProcedure(0,?,'','','',0,5)}"; 

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
     //Método para registrar un usuario, regresa true si el registro fue exitoso
    public boolean registarse(Usuario usr){
        cnx = new Conexion();
        CallableStatement st = null;
        ResultSet rs = null;
        boolean bandera=false;
        try{
            st = cnx.getConexion().prepareCall(REGISTRARSE);
            st.setString(1, usr.getUsuario());
            st.setString(2, usr.getPass());
            st.setString(3, usr.getTipo());
            rs = st.executeQuery();
            while(rs.next()){
                int id_usr = rs.getInt("id_usuario");
                if(id_usr > 0){
                    bandera = true;
                }
                
            }
        }catch(Exception e){
            System.out.println("Error al registar: " + e.getMessage());
        }
        return bandera;
    }
    
    //Metodo para iniciar sesion, regresa el id_usuario si login correcto, -1 en caso contrario
    public Usuario iniciarSesion(Usuario usr){
        cnx = new Conexion();
        CallableStatement st = null;
        ResultSet rs = null;
        try{
            st = cnx.getConexion().prepareCall(INICIAR_SESION);
            st.setString(1, usr.getUsuario());
            st.setString(2, usr.getPass());
            st.executeUpdate();
            rs = st.executeQuery();
            while(rs.next()){
                usr.setId_usuario(rs.getInt("id_usuario"));
                usr.setTipo(rs.getString("tipo"));
            }
        }catch(Exception e){
            System.out.println("Error al iniciar sesion: " + e.getMessage());
        }
        return usr;
    }    
    
     //Metodo para consultar todos los examenes de un usuario, devuelve un arreglo de Examen
    public List<Examen> consultarExamenes(int id_usr){
        List<Examen> examenes = new ArrayList<>();
        cnx = new Conexion();
        CallableStatement st = null;
        ResultSet rs = null;
        try {
            st = cnx.getConexion().prepareCall(CONSULTAREXAMENES);
            st.setInt(1, id_usr);
            rs = st.executeQuery();
            while(rs.next()){
                Examen ex = new Examen();
                ex.setId_examen(rs.getInt("id_examen"));
                ex.setTitulo(rs.getString("titulo"));
                ex.setFecha(rs.getString("fecha"));
                ex.setId_usuario(rs.getInt("id_usuario"));
                ex.setCalificacion(rs.getInt("calificacion"));
                examenes.add(ex);
            }
        } catch (Exception e) {
               System.out.println("Error consultar examenes:" + e.getMessage());
        }
      return examenes;
    }
}
