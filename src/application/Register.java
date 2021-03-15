package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class Register extends ClienteRMI {

	@FXML
	private Button btnLogin;
	@FXML
	private Button alreadyHaveAccount;
	@FXML
	private TextField loginUsername;
	@FXML
	private TextField loginName;
	@FXML
	private TextField loginLastName;
	@FXML
	private PasswordField loginPassword;

	public void verificarRegistro(ActionEvent event) throws Exception {
		if (loginUsername.getText().toString().equalsIgnoreCase("")
				|| loginPassword.getText().toString().equalsIgnoreCase("")) {
			toastVacio();
		} else {
			boolean resultadoRegistro = registrar(loginUsername.getText().toString(),
					loginPassword.getText().toString(), loginName.getText().toString(),
					loginLastName.getText().toString());
			if (resultadoRegistro) {
				toastExito();
				System.out.println("registrado correctamente");
				changeScene("MenuLogin.fxml");
			} else {
				toastFalloDB();
			}
		}
	}

	public void alreadyHaveAccount(ActionEvent event) throws Exception {
		changeScene("MenuLogin.fxml");
	}

	public void toastVacio() throws Exception {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Error en el Registro");
		alert.setContentText("No puede dejar los campos vacios");
		alert.showAndWait();
	}

	public void toastExito() throws Exception {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Usuario registrado correctamente");
		alert.setContentText("El usuario ha sido registrado correctamente");
		alert.showAndWait();
	}

	public void toastFalloDB() throws Exception {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("Fallo al registrar su usuario");
		alert.setContentText("Es posible que su nombre de usuario esté en uso");
		alert.showAndWait();
	}

}
