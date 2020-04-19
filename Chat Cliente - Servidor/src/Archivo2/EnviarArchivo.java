package Archivo2;

import java.io.*;
import java.net.*;

public class EnviarArchivo extends Thread{
	
	    //Atributos
	    int señalar;
	    byte[] VecDatos;
	    Socket servidor;
	    String Clienteip;
	    String NombredeArchivo;
	    BufferedInputStream Ingreso;    
	    BufferedOutputStream Salida;
	    
        //Constructor
	    public EnviarArchivo(String chatCliente, String pat) {
	        this.Clienteip = chatCliente;
	        this.NombredeArchivo = pat;
	    }
	    
        //Metodos
	    public void run() {

	        try {
	            final File localFile = new File(NombredeArchivo);

	            servidor = new Socket("localhost", 32768);
	            
	            Ingreso = new BufferedInputStream(new FileInputStream(localFile));
	            Salida = new BufferedOutputStream(servidor.getOutputStream());
        
	            DataOutputStream dos = new DataOutputStream(servidor.getOutputStream());
	            dos.writeUTF(localFile.getName());

	            VecDatos = new byte[8192];
	
	            while ((señalar = Ingreso.read(VecDatos)) != -1) {
	
	                Salida.write(VecDatos, 0, señalar);
	            }

	            Ingreso.close();    
	            Salida.close();    

	        } catch (Exception e) {
	        }
	    }
	}


