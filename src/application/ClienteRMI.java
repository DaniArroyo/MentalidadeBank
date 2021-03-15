package application;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.util.HashMap;

import Modelo.CifradoManager;
import Modelo.IUsuarios;
import Modelo.ModelTableTransferencia;
import Modelo.ModelTableUsuarios;
import Modelo.Transferencia;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClienteRMI extends Application {
	static IUsuarios banco = null;
	static String user = "";
	private static Stage stg;
	CifradoManager cifradoManager = new CifradoManager();
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnRegistro;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("MenuPrincipal.fxml"));
			stg = primaryStage;
			Scene scene = new Scene(root, 400, 400);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setHeight(800);
			primaryStage.setWidth(1400);

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {
					try {
						File pk = new File("Key/privateKey");
						pk.delete();
						banco.setOnline(user, "0", "", "");
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Platform.exit();
					System.exit(0);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println("Localizando el registro de objetos remotos");
			Registry registry = LocateRegistry.getRegistry("localhost", 5555);
			System.out.println("Obteniendo el stab del objeto remoto");
			banco = (IUsuarios) registry.lookup("Banco");
			launch(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean balanceUpdates(int balanceUpdate, String usuarioReceptor) throws Exception {
		return banco.updateBalance(balanceUpdate, usuarioReceptor);
	}

	public int getBalance() throws Exception {
		return banco.recogerBalance();
	}

	public String getNombreUsuario() throws Exception {
		return banco.recogerNombreUsuario();
	}

	public String getNombre() throws Exception {
		return banco.recogerNombre();
	}

	public String getApellidos() throws Exception {
		return banco.recogerApellidos();
	}

	public String descifrarValor(String valor, String key) throws Exception {
		return cifradoManager.descifrarDatosSimetrico(valor, key);
	}

	public HashMap<Integer, ModelTableTransferencia> getTransferencias() throws Exception {
		return banco.recogerTransferencias();
	}

	public HashMap<Integer, ModelTableUsuarios> getUsuarios() throws Exception {
		return banco.recogerUsuarios();
	}

	public boolean realizarTransaccion(String idTransaccion, String usuarioReceptor, String cantidadTotal,
			DateFormat fechaLog) throws Exception {
		Transferencia transaccion = cifradoManager.cifradoSimetricoTransanccion(idTransaccion, usuarioReceptor,
				cantidadTotal, fechaLog);
		MenuCliente.keyCifrado(transaccion.getKey());
		return banco.insertarTransferencia(transaccion.getIdTransaccion(), transaccion.getUsuarioReceptor(),
				transaccion.getCantidadTotal(), transaccion.getFecha());
	}

	public int login(String user, String password) throws Exception {
		password = CifradoManager.cifrarTextoHash(password);
		ClienteRMI.user = user;
		return banco.login(user, password);
	}

	public boolean setOnline(String user, String online, String ip, String clavePublica) throws Exception {
		return banco.setOnline(user, online, ip, clavePublica);
	}

	public boolean registrar(String user, String password, String nombre, String apellidos) throws Exception {
		password = CifradoManager.cifrarTextoHash(password);
		return banco.registro(user, password, nombre, apellidos);
	}

	public void menuLogin(ActionEvent event) throws IOException {
		changeScene("MenuLogin.fxml");
	}

	public void menuRegistro(ActionEvent event) throws IOException {
		changeScene("MenuRegistro.fxml");
	}

	public void changeScene(String fxml) throws IOException {
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		stg.getScene().setRoot(pane);
	}

}
