package Servidor;

import Archivo2.EnviarArchivo;
import Archivo2.RecibirArchivo;
import Archivo2.EnviarArchivo;
import Archivo2.RecibirArchivo;
import Servidor.HiloparaEnviar;
import Servidor.HiloparaRecibir;
import Servidor.Servidor;


import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.logging.*;


import Cliente.Cliente;

public class Servidor extends JFrame {
	
	    //Atributos
	    public JTextField insertarMensaje;
	    public JTextArea CuadrodeChat;
	    public JMenuItem agregar;
	    private static ServerSocket servidor;
	    private static Socket cliente ;
	    private static String Clienteip = "10.0.0.0";
	    public static String usuario = "Servidor";
	    public static Servidor ventanadeServidor;
	    String dir_recibo = "C:\\ServidorArchivo";
	    
        //Constructor
	    public Servidor() {
	    	setTitle("Servidor" );
	        getContentPane().setLayout(null);
	   
	        insertarMensaje = new JTextField();
	        insertarMensaje.setBackground(SystemColor.scrollbar);
	        insertarMensaje.setBounds(0, 381, 300, 36);
	        insertarMensaje.setEditable(false);
	        getContentPane().add(insertarMensaje);

	        CuadrodeChat = new JTextArea();
	        CuadrodeChat.setFont(new Font("Arial", Font.PLAIN, 16));
	        CuadrodeChat.setDisabledTextColor(Color.BLACK);
	        CuadrodeChat.setSelectionColor(Color.BLACK);
	        CuadrodeChat.setCaretColor(Color.BLACK);
	        CuadrodeChat.setEditable(false);
	        JScrollPane scrollPane = new JScrollPane(CuadrodeChat);
	        scrollPane.setBounds(0, 0, 300, 380);
	        getContentPane().add(scrollPane);
	        CuadrodeChat.setBackground(Color.BLACK);
	        CuadrodeChat.setForeground(Color.white);
	        insertarMensaje.setForeground(Color.blue);

	        JMenuItem salir = new JMenuItem("Cerrar");
	        salir.setForeground(Color.RED);
	        salir.setBackground(Color.BLACK);
	        agregar = new JMenuItem("Agregar Archivo");
	        agregar.setBackground(Color.BLACK);
	        agregar.setForeground(Color.BLUE);
	        agregar.setEnabled(false);
	        JMenuBar barra = new JMenuBar();
	        setJMenuBar(barra);
	        barra.add(salir);
	        barra.add(agregar);
	        
            //Finalizar Conexion
	        salir.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                System.exit(0); 
	            }
	        });
	        
            //Entrada de Archivo
	        agregar.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                javax.swing.JFileChooser SeleccionarVentana = new javax.swing.JFileChooser();
	                int seleccion = SeleccionarVentana.showOpenDialog(SeleccionarVentana);
	                String path = SeleccionarVentana.getSelectedFile().getAbsolutePath();

	              
	                if (seleccion == JFileChooser.APPROVE_OPTION) {
	                	JOptionPane.showMessageDialog(null, path);
	                    ventanadeServidor.mostrarMensaje("Enviando Archivo...");
	                    RecibirArchivo ObtenerArchivo = new RecibirArchivo(path , usuario, 32768, "localhost");
	                    ObtenerArchivo.start();
	                    EnviarArchivo EnvioArchivo = new EnviarArchivo("localhost", path);																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																											
	                    EnvioArchivo.start();
	                    ventanadeServidor.mostrarMensaje("Archivo Enviado Con Exito");
	                  
	                }
	            }
	        });
	        setSize(312, 480);
	        setVisible(true); 

	    }

	    public static void main(String[] args) {
	    	
	        ventanadeServidor = new Servidor();
	        ventanadeServidor.setLocationRelativeTo(null);
	        ventanadeServidor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	        try {
	            
	            servidor = new ServerSocket(11111, 100);
	            ventanadeServidor.mostrarMensaje("Esperando al Cliente ...");
	       
	            while (true) {
	                try {
	                 
	                    cliente = servidor.accept();
	                    ventanadeServidor.mostrarMensaje("Conectado a : " + cliente.getInetAddress().getHostName());
	                    ventanadeServidor.habilitar(true);
	                    
	                    HiloparaEnviar hiloEnviarServidor = new HiloparaEnviar(cliente, ventanadeServidor);
	                    hiloEnviarServidor.start();
	                    HiloparaRecibir hiloRecibirServidor = new HiloparaRecibir(cliente, ventanadeServidor);
	                    hiloRecibirServidor.start();
	                    
	                } catch (IOException ex) {
	                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
	                    ventanadeServidor.mostrarMensaje("No hay conexion con el cliente");
	                }
	            }
	        } catch (IOException ex) {
	            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
	            ventanadeServidor.mostrarMensaje("No se encontró IP del Servidor");
	        }
	    }
	    
        //Metodos
	    public void mostrarMensaje(String mensaje) {
	        CuadrodeChat.append(mensaje + "\n");
	    }

	    public void habilitar(boolean editable) {
	        insertarMensaje.setEditable(editable);
	        agregar.setEnabled(editable);
	    }
	
	

}
