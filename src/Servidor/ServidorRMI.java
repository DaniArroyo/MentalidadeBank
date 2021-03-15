package Servidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.crypto.NoSuchPaddingException;

import Modelo.IUsuarios;
import Modelo.ModelTableTransferencia;
import Modelo.ModelTableUsuarios;
import Modelo.Usuario;

public class ServidorRMI implements IUsuarios {

	private ConexionBD con;
	private int idUser;
	private int idUsuarioReceptor;

	public ServidorRMI() throws NoSuchAlgorithmException, NoSuchPaddingException {
		idUser = 0;
		new HashMap<Integer, Usuario>();
		con = new ConexionBD();
		con.get_conexion();
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException {
		Registry reg = null;
		try {

			reg = LocateRegistry.createRegistry(5555);
		} catch (Exception e) {
			System.out.println("ERROR: No se ha podido crear el registro");
			e.printStackTrace();
		}

		ServidorRMI serverObject = new ServidorRMI();
		try {

			reg.rebind("Banco", UnicastRemoteObject.exportObject(serverObject, 0));
		} catch (Exception e) {
			System.out.println("ERROR: No se ha podido inscribir el objeto servidor.");
			e.printStackTrace();
		}
	}

	@Override
	public int login(String user, String password) throws RemoteException {
		idUser = con.login(user, password);
		return idUser;
	}

	@Override
	public boolean registro(String user, String password, String nombre, String apellidos) throws RemoteException {
		return con.registro(user, password, nombre, apellidos);
	}

	@Override
	public boolean insertarTransferencia(String idTransaccion, String usuarioReceptor, String cantidad, String fecha) {
		idUsuarioReceptor = con.idUsuarioReceptor(usuarioReceptor);
		return con.insertarTransferencia(idTransaccion, idUser, idUsuarioReceptor, cantidad, fecha);
	}

	@Override
	public boolean setOnline(String user, String online, String ip, String PublicKey) throws RemoteException {
		return con.setOnlineOffline(user, online, ip, PublicKey);
	}

	@Override
	public HashMap<Integer, ModelTableTransferencia> recogerTransferencias() {
		return con.getTransferencias(idUser);
	}

	@Override
	public HashMap<Integer, ModelTableUsuarios> recogerUsuarios() throws RemoteException {
		// TODO Auto-generated method stub
		return con.getUser();
	}

	@Override
	public int recogerBalance() {
		return con.balanceUsuario(idUser);
	}

	@Override
	public String recogerNombreUsuario() {
		return con.nombreUsuario(idUser);
	}

	@Override
	public String recogerNombre() {
		return con.nombre(idUser);
	}

	@Override
	public String recogerApellidos() {
		return con.apellidos(idUser);
	}

	@Override
	public boolean updateBalance(int balanceUpdate, String usuarioReceptor) {
		boolean estado;
		idUsuarioReceptor = con.idUsuarioReceptor(usuarioReceptor);
		int oldBalance = con.balanceUsuario(idUsuarioReceptor);
		int newBalance = oldBalance + balanceUpdate;
		estado = con.newBalanceUsuario(newBalance, idUsuarioReceptor);
		oldBalance = con.balanceUsuario(idUser);
		newBalance = oldBalance - balanceUpdate;
		estado = con.newBalanceUsuario(newBalance, idUser);
		return estado;
	}

}
