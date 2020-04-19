package Cliente;

import java.net.*;
import java.io.*;
import java.util.logging.*;

import Cliente.Cliente;
import Cliente.HiloparaRecibir;

public class HiloparaRecibir extends Thread {
	
	    //Atributos
	    private final Cliente ventanaCliente;
	    private String mensaje;
	    private ObjectInputStream entrada;
	    private Socket cliente;
	    
        //Constructor
	    public HiloparaRecibir(Socket cliente, Cliente ventana) {
	        this.cliente = cliente;
	        this.ventanaCliente = ventana;
	    }
	    
        //Metodos
	    public void mostrarMensaje(String mensaje) {
	        ventanaCliente.CuadrodeChat.append(mensaje);
	    }

	    public void run() {
	        try {
	            entrada = new ObjectInputStream(cliente.getInputStream());
	        } catch (IOException ex) {
	            Logger.getLogger(HiloparaRecibir.class.getName()).log(Level.SEVERE, null, ex);
	        }

	        do {
	            try {
	                mensaje = (String) entrada.readObject();
	                ventanaCliente.mostrarMensaje(mensaje);
	            } catch (SocketException ex) {
	            } catch (EOFException eofException) {
	                ventanaCliente.mostrarMensaje("No se encuentra la conexion con el Servidor");
	                mensaje ="****";
	            } catch (IOException ex) {
	                Logger.getLogger(HiloparaRecibir.class.getName()).log(Level.SEVERE, null, ex);
	                ventanaCliente.mostrarMensaje("No se encuentra la conexion con el Servidor");
	                mensaje ="****";
	            } catch (ClassNotFoundException classNotFoundException) {
	                ventanaCliente.mostrarMensaje("Se desconoce el Objeto");
	                mensaje ="****";
	            }

	        } while (!mensaje.equals("****")); 

	        try {
	            entrada.close();
	            cliente.close();
	        } catch (IOException ioException) {
	            ioException.printStackTrace();
	        }

	        ventanaCliente.mostrarMensaje("Conexion Finalizada");
	    }

}
