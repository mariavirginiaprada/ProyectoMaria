package Cliente;

import Archivo1.EnviarArchivo;
import Archivo1.RecibirArchivo;
import Archivo1.EnviarArchivo;
import Archivo1.RecibirArchivo;
import Cliente.HiloparaEnviar;
import Cliente.HiloparaRecibir;
import Cliente.Cliente;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.*;


public class Cliente extends JFrame {
	
	   //Atributos
	    public JTextField insertarMensaje;
	    public JTextArea CuadrodeChat;
	    public JMenuItem agregar;
	    private static ServerSocket servidor;
	    private static Socket cliente;
	    private static String Servidorip = "127.0.0.1";
	    public static Cliente ventanadeCliente;
	    public static String usuario;
	    public boolean recibir;

        //Constructor
	    public Cliente() {
	    	setTitle("Cliente");
	        getContentPane().setLayout(null);
	        
	        insertarMensaje = new JTextField();
	        insertarMensaje.setBounds(0, 380, 298, 40);
	        insertarMensaje.setEditable(false);
	        getContentPane().add(insertarMensaje);

	        CuadrodeChat = new JTextArea();
	        CuadrodeChat.setFont(new Font("Arial", Font.PLAIN, 16));
	        CuadrodeChat.setEditable(false);
	        JScrollPane scrollPane = new JScrollPane(CuadrodeChat);
	        scrollPane.setBounds(0, 0, 300, 380);
	        getContentPane().add(scrollPane);
	        CuadrodeChat.setBackground(Color.white);
	        CuadrodeChat.setForeground(Color.black);
	        insertarMensaje.setForeground(Color.blue);

	        JMenuItem salir = new JMenuItem("Salir");
	        salir.setBackground(Color.LIGHT_GRAY);
	        salir.setForeground(Color.RED);
	        agregar = new JMenuItem("Agregar Archivo");
	        agregar.setBackground(Color.LIGHT_GRAY);
	        agregar.setEnabled(false);
	        JMenuBar barra = new JMenuBar();
	        barra.setBackground(Color.LIGHT_GRAY);
	        setJMenuBar(barra);
	        barra.add(salir);
	        barra.add(agregar);
	        
            //Finalizar la Conexion
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
	                    ventanadeCliente.mostrarMensaje("Enviando Archivo...");
	                    RecibirArchivo ObtenerArchivo = new RecibirArchivo(path, usuario, 32768, "localhost");
	                    ObtenerArchivo.start();
	                    EnviarArchivo EnvioArchivo = new EnviarArchivo(Servidorip, path);
	                    EnvioArchivo.start();
	                    ventanadeCliente.mostrarMensaje("Archivo Enviado Con Exito");
	                }
	            }
	        });
	        setSize(314, 483);
	        setVisible(true);
	    }

	    public static void main(String[] args) {
	    	
	      
	        ventanadeCliente = new Cliente();
	        ventanadeCliente.setLocationRelativeTo(null);
	        ventanadeCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	

	        try {
	            ventanadeCliente.mostrarMensaje("Buscando Servidor ...");
	            cliente = new Socket(InetAddress.getByName(Servidorip), 11111);
	            ventanadeCliente.mostrarMensaje("Conectado a :" + cliente.getInetAddress().getHostName());
	            ventanadeCliente.habilitar(true);

	            HiloparaEnviar hiloEnviarCliente = new HiloparaEnviar(cliente, ventanadeCliente);
	            hiloEnviarCliente.start();
	            HiloparaRecibir hiloRecibirCliente = new HiloparaRecibir(cliente, ventanadeCliente);
	            hiloRecibirCliente.start();
	        } catch (IOException ex) {
	            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
	            ventanadeCliente.mostrarMensaje("No se encuentra al Servidor");
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
	
	

