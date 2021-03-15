package Modelo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface IUsuarios extends Remote {

	public int login(String user, String password) throws RemoteException;

	public boolean registro(String user, String password, String nombre, String apellidos) throws RemoteException;

	public boolean insertarTransferencia(String idTransaccion, String usuarioReceptor, String cantidadTotal,
			String fecha) throws RemoteException;

	public HashMap<Integer, ModelTableTransferencia> recogerTransferencias() throws RemoteException;

	public HashMap<Integer, ModelTableUsuarios> recogerUsuarios() throws RemoteException;

	public String recogerNombreUsuario() throws RemoteException;

	public String recogerNombre() throws RemoteException;

	public String recogerApellidos() throws RemoteException;

	public int recogerBalance() throws RemoteException;

	public boolean updateBalance(int balanceUpdate, String usuarioReceptor) throws RemoteException;

	public boolean setOnline(String user, String online, String ip, String clavePublica) throws RemoteException;

}
