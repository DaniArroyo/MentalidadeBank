package application;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;

import Modelo.PaqueteEnvio;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ClienteChat {

	public static void main(String[] args) {

	}

}

class MarcoCliente extends JFrame {

	public MarcoCliente(String ip, String publicKey) {

		System.out.println(ip);

		setBounds(600, 300, 280, 350);

		LaminaMarcoCliente milamina = new LaminaMarcoCliente(ip, publicKey);

		add(milamina);

		setVisible(true);

		addWindowListener(new EnvioOnline());
	}

}

class EnvioOnline extends WindowAdapter {

	@Override
	public void windowOpened(WindowEvent e) {

		try {

			Socket misocket = new Socket("192.168.1.42", 9999);

			PaqueteEnvio datos = new PaqueteEnvio();

			datos.setMensaje(" online");

			ObjectOutputStream paquete_datos = new ObjectOutputStream(misocket.getOutputStream());

			paquete_datos.writeObject(datos);

			misocket.close();

		} catch (Exception e2) {
		}

	}

}

class LaminaMarcoCliente extends JPanel implements Runnable {

	public LaminaMarcoCliente(String ip, String publicKey) {

		String nick_usuario = JOptionPane.showInputDialog("Nick: ");

		JLabel n_nick = new JLabel("Nick: ");

		add(n_nick);

		nick = new JLabel();

		nick.setText(nick_usuario);

		add(nick);

		JLabel texto = new JLabel("Online: ");

		add(texto);

		this.ip = ip;

		this.publicKey = publicKey;

		campochat = new JTextArea(12, 20);

		add(campochat);

		campo1 = new JTextField(20);

		add(campo1);

		miboton = new JButton("Enviar");

		EnviaTexto mievento = new EnviaTexto();

		miboton.addActionListener(mievento);

		add(miboton);

		Thread mihilo = new Thread(this);

		mihilo.start();

	}

	private class EnviaTexto implements ActionListener {

		private static final int MAX_ENCRYPT_BLOCK = 117;

		public String encrypt(String data, PublicKey publicKey) throws Exception {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			int inputLen = data.getBytes().length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offset = 0;
			byte[] cache;
			int i = 0;

			while (inputLen - offset > 0) {
				if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
				}
				out.write(cache, 0, cache.length);
				i++;
				offset = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();

			return new String(Base64.getEncoder().encode(encryptedData));
		}

		public PublicKey getPublicKey(String publicKey) throws Exception {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] decodedKey = Base64.getDecoder().decode(publicKey.getBytes());
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
			return keyFactory.generatePublic(keySpec);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			campochat.append("\n" + campo1.getText());

			try {

				KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
				KeyPair keypair = keygen.generateKeyPair();
				Cipher rsaCipher = Cipher.getInstance("RSA");

				Socket misocket = new Socket("192.168.1.42", 9999);

				PaqueteEnvio datos = new PaqueteEnvio();

				datos.setNick(nick.getText());

				datos.setIp(ip);

				String clavePublica = publicKey;
				String mensaje = campo1.getText();

				String encryptData = encrypt(mensaje, getPublicKey(publicKey));

				datos.setMensaje(encryptData);

				ObjectOutputStream paquete_datos = new ObjectOutputStream(misocket.getOutputStream());

				paquete_datos.writeObject(datos);

				misocket.close();

			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchPaddingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	private JTextField campo1;

	private String ip, publicKey;

	private JLabel nick;

	private JTextArea campochat;

	private JButton miboton;
	private static final int MAX_DECRYPT_BLOCK = 128;

	@Override
	public void run() {

		try {

			ServerSocket servidor_cliente = new ServerSocket(9090);

			Socket cliente;
			PaqueteEnvio paqueteRecibido;

			while (true) {

				cliente = servidor_cliente.accept();

				ObjectInputStream flujoentrada = new ObjectInputStream(cliente.getInputStream());

				paqueteRecibido = (PaqueteEnvio) flujoentrada.readObject();

				if (!paqueteRecibido.getMensaje().equals(" online")) {

					String clavePrivada = leerFichero();

					String decryptData = decrypt(paqueteRecibido.getMensaje(), getPrivateKey(clavePrivada));

					campochat.append("\n" + paqueteRecibido.getNick() + ": " + decryptData);

				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public String leerFichero() {

		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {

			archivo = new File("Key/privateKey");
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;
			linea = br.readLine();
			return linea;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return "null";
	}

	public PrivateKey getPrivateKey(String privateKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] decodedKey = Base64.getDecoder().decode(privateKey.getBytes());
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
		return keyFactory.generatePrivate(keySpec);
	}

	public String decrypt(String data, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] dataBytes = Base64.getDecoder().decode(data);
		int inputLen = dataBytes.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offset = 0;
		byte[] cache;
		int i = 0;

		while (inputLen - offset > 0) {
			if (inputLen - offset > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
			}
			out.write(cache, 0, cache.length);
			i++;
			offset = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();

		return new String(decryptedData, "UTF-8");
	}

}