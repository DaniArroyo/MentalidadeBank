package Modelo;

import java.io.Serializable;

public class Usuario implements Serializable {
	private String user;
	private String pass;

	public Usuario(String user, String pass) {
		super();
		this.user = user;
		this.pass = pass;
	}

	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}

}
