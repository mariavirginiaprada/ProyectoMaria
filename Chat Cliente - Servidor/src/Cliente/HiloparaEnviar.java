package Cliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;

public class HiloparaEnviar extends Thread {
	
	//Atributos
	private final Cliente ventanaCliente;
	private ObjectOutputStream salida;
	private String mensaje;
	private Socket conexion;
 	static Statement statement;
 	static int id;    

	//Constructor
 	public HiloparaEnviar(Socket conexion, final Cliente ventana) {
	        this.conexion = conexion;
	        this.ventanaCliente = ventana;

	
	        ventanaCliente.insertarMensaje.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	                mensaje = event.getActionCommand();
	                SQL(event.getActionCommand());
	                enviarMensaje(mensaje); 
	                ventanaCliente.insertarMensaje.setText(""); 
	            }
	        });
	    }
	    
	    //Metodos
 	    private void SQL(String mensaje) {
	    	String SQL = mensaje;
	        try (Connection conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ChatClienteServidor","postgres","williamapw12")) {
	        statement = conexion.createStatement();
	        ResultSet Rs = statement.executeQuery("INSERT INTO chatmaria (mensaje) VALUES('"+mensaje+"')");
	       } catch (SQLException e) {
	    	
	    }
	    
	    }
 
	    private void enviarMensaje(String mensaje) {
	        try {
	        	Date fecha = new Date();
	        	String strDateFormat = "hh:mm:ss a dd/MM/yy";
	        	SimpleDateFormat cambio = new SimpleDateFormat(strDateFormat);
	        	salida.writeObject("Cliente"+ " dice: " + mensaje+"\n "+cambio.format(fecha));
	            salida.flush(); 
	            ventanaCliente.mostrarMensaje("Yo: " + mensaje+"\n \t "+cambio.format(fecha));
	        } catch (IOException ioException) {
	            ventanaCliente.mostrarMensaje("No se encuentra al Servidor");
	        }
	    }

	    public void mostrarMensaje(String mensaje) {
	        ventanaCliente.CuadrodeChat.append(mensaje);
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
