package Modelo;

import java.io.Serializable;

public class Transferencia implements Serializable {
	private String usuarioReceptor;
	private String cantidadTotal;
	private String fecha;
	private String key;
	private String idTransaccion;
	
	public Transferencia(String idTransaccion, String usuarioReceptor2, String cantidadTotal, String fecha, String key) {
		super();
		this.usuarioReceptor = usuarioReceptor2;
		this.cantidadTotal = cantidadTotal;
		this.fecha = fecha;
		this.key = key;
		this.idTransaccion = idTransaccion;
	}

	public String getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(String idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUsuarioReceptor() {
		return usuarioReceptor;
	}

	public void setUsuarioReceptor(String usuarioReceptor) {
		this.usuarioReceptor = usuarioReceptor;
	}

	public String getCantidadTotal() {
		return cantidadTotal;
	}

	public void setCantidadTotal(String cantidadTotal) {
		this.cantidadTotal = cantidadTotal;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
