package Archivo1;

import java.io.*;
import java.net.*;

public class RecibirArchivo extends Thread{
	
	    //Atributos
	    private String path, Servidorip, NombredeServidor;
	    private int puerto;
	    private Socket ClienteArchivos;
	
        //Constructor
	    public RecibirArchivo(String ficher, String serv, int port, String ipServ) {
	        this.path = ficher;
	        this.puerto = port;
	        this.NombredeServidor = serv;
	        this.Servidorip = ipServ;
	    }
	    
        //Metodos
	    public void run() {

	        ServerSocket server;   
	        Socket receptor;       
	        BufferedInputStream bis;
	        BufferedOutputStream bos;
	        byte[] receivedData;   
	        int indicador;          
	        String file;            

	        try {
	            server = new ServerSocket(32768);
	            while (true) {
	                receptor = server.accept();

	                receivedData = new byte[1024];
	                bis = new BufferedInputStream(receptor.getInputStream());
	                DataInputStream dis = new DataInputStream(receptor.getInputStream());
	                
	                file = dis.readUTF();
	                file = file.substring(file.indexOf('\\') + 1, file.length());

	                bos = new BufferedOutputStream(new FileOutputStream(file));
	                while ((indicador = bis.read(receivedData)) != -1) {
	                    bos.write(receivedData, 0, indicador);
	                }

	                bos.close();
	                dis.close();
	            }
	        } catch (Exception e) {
	        }
	    }

}
