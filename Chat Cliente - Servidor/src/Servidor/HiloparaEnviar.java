package Servidor;

import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import Servidor.Servidor;

public class HiloparaEnviar extends Thread {
	
	//Atributos
	private final Servidor ventanaServidor;
    private ObjectOutputStream salida;
    private String mensaje;
    private Socket conexion;
    
    //Constructor
    public HiloparaEnviar(Socket conexion, final Servidor ventana) {
        this.conexion = conexion;
        this.ventanaServidor = ventana;

        
        ventanaServidor.insertarMensaje.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mensaje = event.getActionCommand();
                SQL(mensaje);
                enviarMensaje(mensaje); 
                ventanaServidor.insertarMensaje.setText(""); 
            }
        });
    }
    
    //Metodos
    private void SQL(String mensaje) {
    	String SQL = mensaje;
        try (Connection conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ChatClienteServidor","postgres","williamapw12")) {
        Statement statement = conexion.createStatement();
        ResultSet Rs = statement.executeQuery("INSERT INTO chatmaria (mensaje) VALUES('"+SQL+"')");
       } catch (SQLException e) {
    	
    }
    
    }


    private void enviarMensaje(String mensaje) {
        try {
        	Date fecha = new Date();
        	String strDateFormat = "hh: mm: ss a dd/MM/yy";
        	SimpleDateFormat cambio = new SimpleDateFormat(strDateFormat);
            salida.writeObject("Servidor" + " dice: " + mensaje+"\n "+cambio.format(fecha));
            salida.flush(); 
            ventanaServidor.mostrarMensaje("YO: " + mensaje+"\n \t "+cambio.format(fecha));
        } catch (IOException ioException) {
            ventanaServidor.mostrarMensaje("No se encuentra al Cliente");
        }
    }
    
    public void mostrarMensaje(String mensaje) {
        ventanaServidor.CuadrodeChat.append(mensaje);
    }

    public void run() {
        try {
            salida = new ObjectOutputStream(conexion.getOutputStream());
            salida.flush();
        } catch (SocketException ex) {
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (NullPointerException ex) {
        }
    }
}


