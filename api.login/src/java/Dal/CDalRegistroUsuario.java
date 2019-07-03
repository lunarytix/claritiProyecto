/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import Catalogos.*;

/**
 *
 * @author jose.higuera
 */
public class CDalRegistroUsuario {

    public final Connection abrirConexionPrincipal() throws Exception,Throwable
    {
        
        Connection conexion = null;
        String usuario = "postgres";
        String password  = "Minimo12";
        
        try
        {
            String urlDatabase =  "jdbc:postgresql://localhost:5432/clariti";
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(urlDatabase ,usuario , password);
            
        }
        catch(Exception ex)
        {
            throw ex;	            
        }
        return conexion;    
    }
    
    public String RegistraUsuario (String nombre , String  apellidoP ,String  apellidoM , int edad , String telefono, String correo ) throws Throwable{
       String Respuesta = null;
       Connection conexionPrincipal = null;
       PreparedStatement Query = null;
       ResultSet result = null;
       String sConsulta = "";
       int valor = 0;
       try {
	       
       conexionPrincipal = abrirConexionPrincipal();
       sConsulta = String.format("SELECT fun_RegistroUsuario from  fun_RegistroUsuario('%s','%s','%s','%d','%s','%s');",nombre , apellidoP , apellidoM , edad , telefono , correo);
            
       Query = conexionPrincipal.prepareStatement(sConsulta); 
       result = Query.executeQuery();
       
       while(result.next()){
       	valor = result.getInt("fun_RegistroUsuario");
       }
       if (valor == 0){ 
       		Respuesta =  "0";
       }else {
       		Respuesta = "ok";
       }
       
       }
       catch(Exception e){
       		Respuesta = "off";
	    throw  e;
       	
       }
     return Respuesta;
//            
//        while (result.next()) {
//            Long id = result.getLong("id");
//            String nombres = result.getString("nombres");
//            String apellidos = result.getString("apellidos");
//            System.out.println(id + "t" + nombres + "t" + apellidos);
//        }

        
        /* 
        Connection conn = null;
        try {
        conn = getConnection(); //utilizando cualquiera de los métodos de arriba
        //usar la variable con...
        } finally {
        if (conn != null) {
        conn.close();
        }
        }*/
        
    }
    
	public String registroDomicilio(String calle , String colonia , int numerInterior , int codigoPostal ,String  correo)throws Throwable{

		String Respuesta = null;
		Connection conexionPrincipal = null;
		PreparedStatement Query = null;
		ResultSet result = null;
		String sConsulta = "";
		try{
		conexionPrincipal = abrirConexionPrincipal();
		sConsulta = String.format("SELECT fun_RegistroDomicilio('%s','%s','%d','%d','%s');", calle , colonia , numerInterior, codigoPostal , correo  );

		Query = conexionPrincipal.prepareStatement(sConsulta); 
		result = Query.executeQuery(); 
		Respuesta ="ok";
			}
		catch(Exception e) {
			Respuesta ="off";
			throw  e;
		}
		return Respuesta;

	}

	public String registroLogin(String correo , String contraseña )throws Throwable{
		String Respuesta = null;
		Connection conexionPrincipal = null;
		PreparedStatement Query = null;
		ResultSet result = null;
		String sConsulta = "";
		try{
		conexionPrincipal = abrirConexionPrincipal();
		sConsulta = String.format("SELECT fun_RegistroLogin('%s','%s');",correo , contraseña);

		Query = conexionPrincipal.prepareStatement(sConsulta); 
		result = Query.executeQuery(); 
		Respuesta = "ok";
		}catch (Exception e)
		{
			Respuesta ="off";
			 throw e ;
		}
		return Respuesta;
	}
	
	public ListaUsuario consultaUsuarios() throws  Exception,  Throwable{
        ListaUsuario listUs = new ListaUsuario();
        
        CCUsuario ConsUsuario = new CCUsuario();
        String sQueryString = "";
        ResultSet result = null;
        PreparedStatement stmt = null;
        Connection conexionUsuario = null;
        short valor = 1;
        
        try {
           conexionUsuario =  abrirConexionPrincipal();
           
           sQueryString = "SELECT idUsuario, nombre , apellidoP  ,apellidoM , edad , telefono , correo FROM fun_consultarUsuario (?::SMALLINT,?::INT)";
                
                stmt = conexionUsuario.prepareStatement(sQueryString);

                stmt.setShort(1, valor);
                stmt.setInt(2, (int)1);

                result = stmt.executeQuery();

                if(result.next())
                {
                    ConsUsuario.setIdUsuario(result.getInt("idUsuario"));
                    ConsUsuario.setNombre(result.getString("nombre"));
                    ConsUsuario.setApellidoP(result.getString("apellidoP"));
                    ConsUsuario.setApellidoM(result.getString("apellidoM"));
                    ConsUsuario.setEdad(result.getInt("edad"));
		    ConsUsuario.setTelefono(result.getString("telefono"));
		    ConsUsuario.setCorreo(result.getString("correo"));
                    
                    listUs.lista.add(ConsUsuario);
                    
                }
                
                return listUs;
           
            
        } catch (Exception e) 
        {
                throw e;
        }finally
        {
                sQueryString = null;
                result = null;
                stmt = null;
                ConsUsuario = null;
        }
	
}
	
	public ListaLogin consultarLogin(String correo , String contraseña , String  ip , String direccionMac , String resolucionP) throws  Exception,  Throwable{
        int idAcceso = 0;
		ListaLogin listLog = new ListaLogin();
        
        CClogin ConsLogin = new CClogin();
        String sQueryString = "";
        ResultSet result = null;
        PreparedStatement stmt = null;
        Connection conexionUsuario = null;
        short valor = 1;
        
        try {
           conexionUsuario =  abrirConexionPrincipal();
           
           sQueryString = "SELECT idAcceso ,correo, contraseña , ip  ,direccionMac , resolucionP  FROM fun_consultarLogin (?::INT,?::VARCHAR,"+
		   "?::VARCHAR,?::VARCHAR,?::VARCHAR,?::VARCHAR)";
                
                stmt = conexionUsuario.prepareStatement(sQueryString);

                stmt.setInt(1, (int) idAcceso);
                stmt.setString(2, correo);
		stmt.setString(3, contraseña);
		stmt.setString(4, ip);
		stmt.setString(5, direccionMac);
		stmt.setString(6, resolucionP);
		
		
		

                result = stmt.executeQuery();

                if(result.next())
                {
		    ConsLogin.setIdAcceso(result.getInt("idAcceso"));
                    ConsLogin.setCorreo(result.getString("correo"));
		    ConsLogin.setContraseña(result.getString("contraseña"));
                    ConsLogin.setIp(result.getString("ip"));
                    ConsLogin.setDireccionMac(result.getString("direccionMac"));
                    ConsLogin.setResolucionP(result.getString("resolucionP"));
                    
                    listLog.lista.add(ConsLogin);
                    
                }
                
                return listLog;
           
            
        } catch (Exception e) 
        {
                throw e;
        }finally
        {
                sQueryString = null;
                result = null;
                stmt = null;
                ConsLogin = null;
        }
	 
        
    }

}





























