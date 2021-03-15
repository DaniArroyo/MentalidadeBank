package Modelo;

import java.io.Serializable;

public class ModelTableUsuarios implements Serializable{
	private String idUsuario, user, ip, online, publicKey;

	public ModelTableUsuarios(String idUsuario, String user, String ip, String online, String publicKey) {
		this.idUsuario = idUsuario;
		this.user = user;
		this.ip = ip;
		this.publicKey = publicKey;
		this.online = online;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}
	
	
	
}
