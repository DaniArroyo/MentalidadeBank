package Servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import Modelo.ModelTableTransferencia;
import Modelo.ModelTableUsuarios;

public class ConexionBD {

	private static final String CONTROLADOR = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/bbddproyecto";
	private static final String USUARIO = "root";
	private static final String PASS = "";

	static {
		try {
			Class.forName(CONTROLADOR);
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
			e.printStackTrace();
		}
	}

	public Connection get_conexion() {

		Connection conexion = null;

		try {

			conexion = DriverManager.getConnection(URL, USUARIO, PASS);
			System.out.println("Conexion OK");

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Error en la conexion");
			e.printStackTrace();
		}

		return conexion;

	}

	public int login(String user, String password) {
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;

			String sql = "SELECT * FROM usuario WHERE user=? AND pass=?";
			ps = this.get_conexion().prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, password);

			rs = ps.executeQuery();

			if (rs.next()) {
				int idUser = rs.getInt("idUser");
				return idUser;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public boolean registro(String user, String password, String nombre, String apellidos) {
		try {
			PreparedStatement ps = null;

			String sql = "INSERT INTO usuario (user, pass, balance,nombre, apellidos) VALUES (?,?,?,?,?)";
			ps = this.get_conexion().prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, password);
			ps.setLong(3, 1000);
			ps.setString(4, nombre);
			ps.setString(5, apellidos);
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertarTransferencia(String idTransaccion, int usuarioEmisor, int usuarioReceptor, String cantidad,
			String fecha) {
		try {
			PreparedStatement ps = null;

			String sql = "INSERT INTO transferencias (idTransaccion, usuarioEmisor, usuarioReceptor, cantidad, fecha) VALUES (?,?,?,?,?)";
			ps = this.get_conexion().prepareStatement(sql);
			ps.setString(1, idTransaccion);
			ps.setLong(2, usuarioEmisor);
			ps.setLong(3, usuarioReceptor);
			ps.setString(4, cantidad);
			ps.setString(5, fecha);
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int idUsuarioReceptor(String usuarioReceptor) {
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;

			String sql = "SELECT * FROM usuario WHERE user = ?";
			ps = this.get_conexion().prepareStatement(sql);
			ps.setString(1, usuarioReceptor);
			rs = ps.executeQuery();

			if (rs.next()) {
				int idUserReceptor = rs.getInt("idUser");
				return idUserReceptor;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public String nombreUsuarioReceptor(int idUsuarioReceptor) {
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;

			String sql = "SELECT * FROM usuario WHERE idUser = ?";
			ps = this.get_conexion().prepareStatement(sql);
			ps.setLong(1, idUsuarioReceptor);
			rs = ps.executeQuery();

			if (rs.next()) {
				String nombreUserReceptor = rs.getString("user");
				return nombreUserReceptor;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "usuario Desconocido";
	}

	public HashMap<Integer, ModelTableTransferencia> getTransferencias(int idUsuarioLogeado) {
		HashMap<Integer, ModelTableTransferencia> hmapTransferencias = new HashMap<Integer, ModelTableTransferencia>();
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			int counter = 1;
			String sql = "SELECT * FROM transferencias WHERE usuarioEmisor = ? OR usuarioReceptor = ?";
			ps = this.get_conexion().prepareStatement(sql);
			ps.setLong(1, idUsuarioLogeado);
			ps.setLong(2, idUsuarioLogeado);
			rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString("idTransaccion"));
				String nombreUsuarioReceptor = nombreUsuarioReceptor(rs.getInt("usuarioReceptor"));
				String nombreUsuarioEmisor = nombreUsuarioReceptor(rs.getInt("usuarioEmisor"));
				hmapTransferencias.put(counter, new ModelTableTransferencia(rs.getString("idTransaccion"),
						nombreUsuarioEmisor, nombreUsuarioReceptor, rs.getString("cantidad"), rs.getString("fecha")));
				counter++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hmapTransferencias;
	}

	public HashMap<Integer, ModelTableUsuarios> getUser() {
		HashMap<Integer, ModelTableUsuarios> hmapUsuarios = new HashMap<Integer, ModelTableUsuarios>();
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			int counter = 1;
			String sql = "SELECT * FROM usuario WHERE online = ?";
			ps = this.get_conexion().prepareStatement(sql);
			ps.setString(1, "1");
			rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString("idUser"));
				String id = rs.getString("idUser");
				String nombreUser = rs.getString("user");
				String ip = rs.getString("IP");
				String online = rs.getString("online");
				String publicKey = rs.getString("publicKey");
				hmapUsuarios.put(counter, new ModelTableUsuarios(id, nombreUser, ip, online, publicKey));
				counter++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hmapUsuarios;
	}

	public int balanceUsuario(int idUsuarioLogeado) {
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;

			String sql = "SELECT balance FROM usuario WHERE idUser = ?";
			ps = this.get_conexion().prepareStatement(sql);
			ps.setLong(1, idUsuarioLogeado);
			rs = ps.executeQuery();
			if (rs.next()) {
				int balanceUsuario = rs.getInt("balance");
				return balanceUsuario;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public String nombreUsuario(int idUsuarioLogeado) {
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;

			String sql = "SELECT user FROM usuario WHERE idUser = ?";
			ps = this.get_conexion().prepareStatement(sql);
			ps.setLong(1, idUsuarioLogeado);
			rs = ps.executeQuery();
			if (rs.next()) {
				String nombreUsuario = rs.getString("user");
				return nombreUsuario;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Usuario Invalido";
	}

	public String nombre(int idUsuarioLogeado) {
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;

			String sql = "SELECT nombre FROM usuario WHERE idUser = ?";
			ps = this.get_conexion().prepareStatement(sql);
			ps.setLong(1, idUsuarioLogeado);
			rs = ps.executeQuery();
			if (rs.next()) {
				String nombre = rs.getString("nombre");
				return nombre;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Nombre Invalido";
	}

	public String apellidos(int idUsuarioLogeado) {
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;

			String sql = "SELECT apellidos FROM usuario WHERE idUser = ?";
			ps = this.get_conexion().prepareStatement(sql);
			ps.setLong(1, idUsuarioLogeado);
			rs = ps.executeQuery();
			if (rs.next()) {
				String apellidos = rs.getString("apellidos");
				return apellidos;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Apellidos Invalidos";
	}

	public boolean newBalanceUsuario(int newBalance, int idUsuarioLogeado) {
		try {
			PreparedStatement ps = null;

			String sql = "UPDATE usuario SET balance = ? WHERE idUser = ?";
			ps = this.get_conexion().prepareStatement(sql);
			ps.setLong(1, newBalance);
			ps.setLong(2, idUsuarioLogeado);
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean setOnlineOffline(String user, String numOnline, String ip, String PublicKey) {
		try {

			PreparedStatement ps = null;

			String sql = "UPDATE usuario SET online = ?, IP = ?, publicKey = ? WHERE user = ?";
			ps = this.get_conexion().prepareStatement(sql);
			ps.setString(1, numOnline);
			ps.setString(2, ip);
			ps.setString(3, PublicKey);
			ps.setString(4, user);
			ps.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}