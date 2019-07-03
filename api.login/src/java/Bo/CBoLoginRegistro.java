/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bo;

import Catalogos.CCUsuario;
import Catalogos.ListaLogin;
import Catalogos.ListaUsuario;
import Dal.CDalRegistroUsuario;

import com.google.common.util.concurrent.ExecutionError;

/**
 *
 * @author jose.higuera
 */
public class CBoLoginRegistro {
    
   public String RegistroUsiaro(String nombre,String apellidoP,String apellidoM,int edad,String Telefono,String calle,String colonia,int numerInterior,int codigoPostal,String correo,String  contraseña) throws Throwable {
	   CDalRegistroUsuario rUser = new CDalRegistroUsuario();
	   String respuesta =null; 
	   try{
		   
		String rUsers = rUser.RegistraUsuario(nombre , apellidoP ,apellidoM , edad , Telefono , correo);
		String rDomicilio = RegistroDomicilio(calle, colonia, numerInterior, codigoPostal , correo );
		String rLogin = registroLogin(correo, contraseña);
		if ( rUsers == "ok" && rDomicilio == "ok" && rLogin == "ok" ) {
			
			respuesta = "ok";
		}
		else if(rUsers  == "0" ) {
	   		respuesta = "usuarioExiste ";
		}
		else {
			respuesta = "Error";
		
		}
	   }catch (Exception e){
		   
		respuesta ="error";
	    	throw e;
	   }
        
        return respuesta;
    }
    
    public String RegistroDomicilio(String calle , String colonia , int numerInterior , int codigoPostal , String correo ) throws Throwable{
	String Respuesta = null;        
	CDalRegistroUsuario rDomicilio = new CDalRegistroUsuario();

	try {
		
		Respuesta = rDomicilio.registroDomicilio( calle, colonia, numerInterior, codigoPostal , correo);
	}catch (Exception e) 
	{
		throw e ;    
		    
	}
	    
        return Respuesta;
    
    }
    
    public String registroLogin(String correo , String contraseña) throws Throwable{
	   String Respuesta = null;  
	   CDalRegistroUsuario rLogin = new CDalRegistroUsuario();
	    try {
		 Respuesta = rLogin.registroLogin(correo, contraseña);
	    } catch (Exception e) {
		    throw e;
	    }
	return Respuesta;
    }
   
        public ListaUsuario ConsultaUsuario ( String nombre, String apellidoP, String apellidoM, int edad,
		String Telefono, String correo  ) throws Exception, Throwable {
		
        CDalRegistroUsuario ConsUsuario = new CDalRegistroUsuario ();
        //CCUsuario ConsUsuario = new CCUsuario();
        ListaUsuario listaUser = new ListaUsuario();
        try {
            
          listaUser = ConsUsuario.consultaUsuarios();
           
        } catch (Exception e) {
            throw e;
        }
        
        return listaUser;
    
    } 
	   public ListaLogin AccesoLogin ( String correo , String contraseña , String  ip , String direccionMac , String resolucionP ) throws Exception, Throwable {
		
        CDalRegistroUsuario ConsUsuario = new CDalRegistroUsuario ();
        //CCUsuario ConsUsuario = new CCUsuario();
        ListaLogin listalog = new ListaLogin();
        try {
            
          listalog = ConsUsuario.consultarLogin(correo , contraseña , ip , direccionMac , resolucionP);
           
        } catch (Exception e) {
            throw e;
        }
        
        return listalog;
    
    } 
	
	
}
























