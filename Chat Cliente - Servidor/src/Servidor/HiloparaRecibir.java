package Servidor;

import java.net.*;
import java.io.*;
import java.util.logging.*;

import Servidor.Servidor;
import Servidor.HiloparaRecibir;

public class HiloparaRecibir extends Thread{
	
	//Atributos
	private final Servidor ventanaServidor;
    private String mensaje;
    private ObjectInputStream entrada;
    private Socket cliente;
    
    //Constructor
    public HiloparaRecibir(Socket cliente, Servidor ventana) {
        this.cliente = cliente;
        this.ventanaServidor = ventana;
    }
    
    //Metodos
    public void mostrarMensaje(String mensaje) {
        ventanaServidor.CuadrodeChat.append(mensaje);
    }

    public void run() {
        try {
            entrada = new ObjectInputStream(cliente.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(HiloparaRecibir.class.getName()).log(Level.SEVERE, null, ex);
            ventanaServidor.mostrarMensaje("Error al enviar Mensaje");
        }

        do {
            try {
                mensaje = (String) entrada.readObject();
                ventanaServidor.mostrarMensaje(mensaje);
            } catch (SocketException ex) {
                ventanaServidor.mostrarMensaje("No se encuentra la conexion con el Cliente");
                mensaje = "****";
            } catch (EOFException eofException) {
                ventanaServidor.mostrarMensaje("No se encuentre la conexion con el Cliente");
                mensaje = "****";
            } catch (IOException ex) {
                Logger.getLogger(HiloparaRecibir.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException classNotFoundException) {
                ventanaServidor.mostrarMensaje("Se desconoce el Objeto");
            }
        } 
        while (!mensaje.equals("****"));

        try {
            entrada.close();
            cliente.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        ventanaServidor.mostrarMensaje("Conexion Finalizada");
    }
	
}