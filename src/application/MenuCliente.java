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

public class MenuCliente extends ClienteRMI {

	static String keyCifrado = "";
	@FXML
	private TextField textFieldCantidadTotal;
	@FXML
	private TextField textFieldUsuarioReceptor;
	@FXML
	private TextField textFieldKey;
	@FXML
	private TextField textFieldBalance;
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

	public void verificarTransaccion(ActionEvent event) throws Exception {
		String cantidadTotal = textFieldCantidadTotal.getText().toString();
		String usuarioReceptor = textFieldUsuarioReceptor.getText().toString();
		DateFormat fechaLog = horaActual();
		textFieldCantidadTotal.clear();
		textFieldUsuarioReceptor.clear();
		String idTransaccion = randomIdTransaccion();
		if (!cantidadTotal.equalsIgnoreCase("") || !usuarioReceptor.equalsIgnoreCase("")) {
			if (Integer.parseInt(cantidadTotal) <= getBalance()) {
				boolean estadoTransaccion = realizarTransaccion(idTransaccion, usuarioReceptor, cantidadTotal,
						fechaLog);
				if (estadoTransaccion) {
					int cantidadTransferencia = Integer.parseInt(cantidadTotal);
					boolean estadoUpdateBalance = balanceUpdates(cantidadTransferencia, usuarioReceptor);
					if (estadoUpdateBalance) {
						loadBalance();
						toastKey(keyCifrado);
					} else {
						toastError();
					}
				} else {
					toastError();
				}
			} else {
				toastErrorBalance();
			}
		} else {
			toastVacio();
		}
	}

	public String randomIdTransaccion() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		StringBuilder idTransferencia = new StringBuilder();
		Random rnd = new Random();
		while (idTransferencia.length() < 16) {
			int index = (int) (rnd.nextFloat() * chars.length());
			idTransferencia.append(chars.charAt(index));
		}
		String idTransferenciaStr = idTransferencia.toString();
		return idTransferenciaStr;

	}

	public static void keyCifrado(String key) {
		keyCifrado = key;
	}

	public void toastKey(String key) throws Exception {

		TextArea textArea = new TextArea(
				"\n\n" + key + "\n\nCompártala únicamente con el receptor de la transaccion mediante un método seguro");
		textArea.setEditable(false);
		textArea.setWrapText(true);
		GridPane gridPane = new GridPane();
		gridPane.setMaxWidth(Double.MAX_VALUE);
		gridPane.add(textArea, 0, 0);

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("CLAVE GENERADA");
		alert.getDialogPane().setContent(gridPane);
		alert.showAndWait();
	}

	public void toastError() throws Exception {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("Transaccion Fallida");
		alert.setContentText("Compruebe que dispone de saldo y el destinatario existe");
		alert.showAndWait();
	}

	public void toastErrorBalance() throws Exception {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("Transaccion Fallida");
		alert.setContentText("No dispone del salario suficiente");
		alert.showAndWait();
	}

	public DateFormat horaActual() {
		DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		return hourdateFormat;
	}

	public void logout(ActionEvent event) throws Exception {
		changeScene("MenuLogin.fxml");
	}

	public void verPerfil(ActionEvent event) throws Exception {
		changeScene("MenuPerfil.fxml");
	}

	public void loadTransferencias(Event event) throws Exception {
		tableTransferencias.getItems().clear();
		HashMap<Integer, ModelTableTransferencia> transferencias = getTransferencias();
		transferencias.forEach((k, transferencia) -> oblist.add(transferencia));
		col_idTransferencias
				.setCellValueFactory(new PropertyValueFactory<ModelTableTransferencia, String>("idTransferencias"));
		col_usuarioEmisor
				.setCellValueFactory(new PropertyValueFactory<ModelTableTransferencia, String>("usuarioEmisor"));
		col_usuarioReceptor
				.setCellValueFactory(new PropertyValueFactory<ModelTableTransferencia, String>("usuarioReceptor"));
		col_cantidad.setCellValueFactory(new PropertyValueFactory<ModelTableTransferencia, String>("cantidad"));
		col_fecha.setCellValueFactory(new PropertyValueFactory<ModelTableTransferencia, String>("fecha"));

		tableTransferencias.setItems(oblist);
	}

	public void loadUsuarios(Event event) throws Exception {
		tableUsuarios.getItems().clear();
		HashMap<Integer, ModelTableUsuarios> usuarios = getUsuarios();
		usuarios.forEach((k, usuario) -> oblistUsr.add(usuario));

		col_idUsuario.setCellValueFactory(new PropertyValueFactory<ModelTableUsuarios, String>("idUsuario"));
		col_usuario.setCellValueFactory(new PropertyValueFactory<ModelTableUsuarios, String>("user"));
		col_ip.setCellValueFactory(new PropertyValueFactory<ModelTableUsuarios, String>("ip"));
		col_online.setCellValueFactory(new PropertyValueFactory<ModelTableUsuarios, String>("online"));
		col_publicKey.setCellValueFactory(new PropertyValueFactory<ModelTableUsuarios, String>("publicKey"));

		tableUsuarios.setItems(oblistUsr);
	}

	public void descifrarTransferencia(ActionEvent event) throws Exception {
		if (tableTransferencias.getSelectionModel().getSelectedItem() != null) {
			ModelTableTransferencia selectedTransferencia = tableTransferencias.getSelectionModel().getSelectedItem();
			String idTransferencia = descifrarValor(selectedTransferencia.getIdTransferencias(),
					textFieldKey.getText().toString());
			String cantidad = descifrarValor(selectedTransferencia.getCantidad(), textFieldKey.getText().toString());
			String fecha = descifrarValor(selectedTransferencia.getFecha(), textFieldKey.getText().toString());
			toastDescifrado(idTransferencia, selectedTransferencia.getUsuarioEmisor(),
					selectedTransferencia.getUsuarioReceptor(), cantidad, fecha);
		} else {
			toastVacioTransaccion();
		}
	}

	public void chatear(ActionEvent event) throws Exception {
		if (tableUsuarios.getSelectionModel().getSelectedItem() != null) {
			ModelTableUsuarios selectedUser = tableUsuarios.getSelectionModel().getSelectedItem();
			String idUsuario = selectedUser.getIdUsuario();
			String user = selectedUser.getUser();
			String ip = selectedUser.getIp();
			String online = selectedUser.getOnline();
			String publicKey = selectedUser.getPublicKey();
			System.out.println(publicKey);

			MarcoCliente mimarco = new MarcoCliente(ip, publicKey);
			mimarco.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		} else {
			toastVacioTransaccion();
		}
	}

	public void toastDescifrado(String idTransferencia, String usuarioEmisor, String usuarioReceptor, String cantidad,
			String fecha) throws Exception {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Aqui su transferencia ");
		alert.setContentText("ID Transferencia: " + idTransferencia + "\r\n" + "Usuario Emisor: " + usuarioEmisor
				+ "\r\n" + "Usuario Receptor: " + usuarioReceptor + "\r\n" + "Cantidad: " + cantidad + "\r\n"
				+ "Fecha: " + fecha);
		alert.showAndWait();
	}

	public void toastVacioTransaccion() throws Exception {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Seleccione la transferencia que desea consultar ");
		alert.setContentText("");
		alert.showAndWait();
	}

	public void toastVacio() throws Exception {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Error en el Registro");
		alert.setContentText("No puede dejar los campos vacios");
		alert.showAndWait();
	}

	public void loadBalance() throws Exception {
		String balance = Integer.toString(getBalance());
		textFieldBalance.setText(balance + " €");
	}

}
