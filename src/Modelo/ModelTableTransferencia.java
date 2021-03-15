package Modelo;

import java.io.Serializable;

public class ModelTableTransferencia implements Serializable {

	private String idTransferencias, usuarioEmisor, usuarioReceptor, cantidad, fecha;

	public ModelTableTransferencia(String idTransferencias, String usuarioEmisor, String usuarioReceptor,String cantidad, String fecha) {
		this.idTransferencias = idTransferencias;
		this.usuarioEmisor = usuarioEmisor;
		this.usuarioReceptor = usuarioReceptor;
		this.cantidad = cantidad;
		this.fecha = fecha;
	}

	public String getUsuarioEmisor() {
		return usuarioEmisor;
	}

	public void setUsuarioEmisor(String usuarioEmisor) {
		this.usuarioEmisor = usuarioEmisor;
	}

	public String getIdTransferencias() {
		return idTransferencias;
	}

	public void setIdTransferencias(String idTransferencias) {
		this.idTransferencias = idTransferencias;
	}

	public String getUsuarioReceptor() {
		return usuarioReceptor;
	}

	public void setUsuarioReceptor(String usuarioReceptor) {
		this.usuarioReceptor = usuarioReceptor;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
