package application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import Modelo.ModelTableTransferencia;
import Modelo.ModelTableUsuarios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class MenuPerfil extends ClienteRMI {

	static String keyCifrado = "";
	@FXML
	private TextField textFieldNombreUsuario;
	@FXML
	private TextField textFieldApellidos;
	@FXML
	private TextField textFieldKey;
	@FXML
	private TextField textFieldBalance;
	@FXML
	private TextField textFieldNombre;
	@FXML
	private Button btnRealizarTransaccion;
	@FXML
	private Button btnLogout;

	@FXML
	private Button btnPerfil;
	@FXML
	private Button btnDescifrar;
	@FXML
	private Button btnChat;

	// Transferencias
	@FXML
	private TableView<ModelTableTransferencia> tableTransferencias;
	@FXML
	private TableColumn<ModelTableTransferencia, String> col_idTransferencias;
	@FXML
	private TableColumn<ModelTableTransferencia, String> col_usuarioEmisor;
	@FXML
	private TableColumn<ModelTableTransferencia, String> col_usuarioReceptor;
	@FXML
	private TableColumn<ModelTableTransferencia, String> col_cantidad;
	@FXML
	private TableColumn<ModelTableTransferencia, String> col_fecha;

	// Usuarios
	@FXML
	private TableView<ModelTableUsuarios> tableUsuarios;
	@FXML
	private TableColumn<ModelTableUsuarios, String> col_idUsuario;
	@FXML
	private TableColumn<ModelTableUsuarios, String> col_usuario;
	@FXML
	private TableColumn<ModelTableUsuarios, String> col_ip;
	@FXML
	private TableColumn<ModelTableUsuarios, String> col_online;
	@FXML
	private TableColumn<ModelTableUsuarios, String> col_publicKey;

	ObservableList<ModelTableTransferencia> oblist = FXCollections.observableArrayList();
	ObservableList<ModelTableUsuarios> oblistUsr = FXCollections.observableArrayList();

	public void logout(ActionEvent event) throws Exception {
		changeScene("MenuLogin.fxml");
	}

	public void verMenuCliente(ActionEvent event) throws Exception {
		changeScene("MenuCliente.fxml");
	}

	public void loadPerfil() throws Exception {
		String nombreUsuario = getNombreUsuario();
		String nombre = getNombre();
		String apellidos = getApellidos();
		String balance = Integer.toString(getBalance());
		textFieldBalance.setText(balance + " €");
		textFieldNombreUsuario.setText(nombreUsuario);
		textFieldNombre.setText(nombre);
		textFieldApellidos.setText(apellidos);
	}

}
