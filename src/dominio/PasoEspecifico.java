package dominio;

import java.util.Date;
import excepcion.TramiteEspecificoException;

public class PasoEspecifico implements Comparable<PasoEspecifico> {
	private String nombrePaso;
	private int repeticion;
	private boolean realizado;
	private Date fechaLimite;
	private Date fechaRealizacion;
	private String documento;
        private int numPaso;

    public int getNumPaso() {
        return numPaso;
    }

    public void setNumPaso(int numPaso) {
        this.numPaso = numPaso;
    }
	
        
        
	public String getNombrePaso() {
		return nombrePaso;
	}
	
	public void setNombrePaso(String nombrePaso) {
		this.nombrePaso = nombrePaso;
	}
	
	public int getRepeticion() {
		return repeticion;
	}
	
	public void setRepeticion(int repeticion) {
		this.repeticion = repeticion;
	}
	
	public boolean isRealizado() {
		return realizado;
	}
	
	public void setRealizado(boolean realizado) {
		this.realizado = realizado;
	}
	
	public Date getFechaLimite() {
		return fechaLimite;
	}
	
	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}
	
	public Date getFechaRealizacion() {
		return fechaRealizacion;
	}
	
	public void setFechaRealizacion(Date fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}
	
	public String getDocumento() {
		return documento;
	}
	
	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public int compareTo(PasoEspecifico o) {
		return getFechaRealizacion().compareTo(o.getFechaRealizacion());
	}

	public boolean validarPasoEspecifico() throws TramiteEspecificoException {
		return false;
	}
	
	public boolean insertarPasoEspecifico() {
		return false;
	}

	public boolean actualizarPasoEspecifico() {
		return false;
	}

	public boolean borrarPasoEspecifico() {
		return false;
	}

	public boolean buscarPasoEspecifico() {
		return false;
	}
}
