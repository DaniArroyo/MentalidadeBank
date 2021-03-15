package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login extends ClienteRMI {

	Stage window;
	Button button;

	@FXML
	private Button btnLogin;
	@FXML
	private Button createAccount;
	@FXML
	private TextField loginUsername;
	@FXML
	private PasswordField loginPassword;

	public void verificarLogin(ActionEvent event) throws Exception {

		int idResultado = login(loginUsername.getText().toString(), loginPassword.getText().toString());
		if (idResultado == -1) {
			loginUsername.clear();
			loginPassword.clear();
			toast();
		} else {
			String ip;

			try (final DatagramSocket socket = new DatagramSocket()) {
				socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
				ip = socket.getLocalAddress().getHostAddress();
			}

			KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
			keygen.initialize(1024);
			KeyPair keypair = keygen.generateKeyPair();

			// Generate key pair
			KeyPair keyPair = getKeyPair();
			String privateKey = new String(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()));
			String publicKey = new String(Base64.getEncoder().encode(keyPair.getPublic().getEncoded()));

			writeToFile("Key/privateKey", privateKey.getBytes());

			setOnline(loginUsername.getText(), "1", ip, publicKey);
			changeScene("MenuCliente.fxml");
		}
	}

	public KeyPair getKeyPair() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(1024);
		return generator.generateKeyPair();
	}

	public PublicKey getPublicKey(String publicKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] decodedKey = Base64.getDecoder().decode(publicKey.getBytes());
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
		return keyFactory.generatePublic(keySpec);
	}

	public PrivateKey getPrivateKey(String privateKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] decodedKey = Base64.getDecoder().decode(privateKey.getBytes());
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
		return keyFactory.generatePrivate(keySpec);
	}

	public void writeToFile(String path, byte[] key) throws IOException {
		File f = new File(path);
		f.getParentFile().mkdirs();

		FileOutputStream fos = new FileOutputStream(f);
		fos.write(key);
		fos.flush();
		fos.close();
	}

	public void createAccount(ActionEvent event) throws Exception {
		changeScene("MenuRegistro.fxml");
	}

	public void toast() throws Exception {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Usuario o contraseña incorrectos");
		alert.setContentText("Vuelva a intentarlo o pulse 'Crear cuenta'");

		alert.showAndWait();
	}

}
