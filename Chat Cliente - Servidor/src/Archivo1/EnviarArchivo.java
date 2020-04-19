package Archivo1;

import java.io.*;
import java.net.*;

public class EnviarArchivo extends Thread{
	
	    //Atributos 
	    int señalar; 
	    byte[] VecDatos;
	    Socket cliente;
	    String Servidorip;
	    String NombredeArchivo;
	    BufferedInputStream Ingreso;    
	    BufferedOutputStream Salida;
	    
        //Constructor
	    public EnviarArchivo(String chatServer, String pat) {
	        this.Servidorip = chatServer;
	        this.NombredeArchivo = pat;
	    }
	    
        //Metodos
	    public void run() {

	        try {
	            final File localFile = new File(NombredeArchivo);

	            cliente = new Socket(Servidorip, 32768);
	            
	            Ingreso = new BufferedInputStream(new FileInputStream(localFile));
	            Salida = new BufferedOutputStream(cliente.getOutputStream());
        
	            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
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



