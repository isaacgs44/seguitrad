package dominio;

import java.io.File;

import basedatos.BaseDatos;
import excepcion.BaseDatosException;
import excepcion.TramiteException;

public class Paso implements Comparable<Paso> {
	private String nombrePaso;
	private int numPaso;
	private String responsable = "Propio";
	private boolean conFechaLimite = false;
	private boolean conPlantilla = false;
	private String plantilla;
	private boolean conDocumento = true;
	private boolean conCambioEstado = false;
	private String estado;
	private Paso secuencia = null;
	private boolean obligatorio = true;
	private boolean conRepeticion = false;
	private int repeticion = 1;
	private transient boolean esNuevo = true;
	
        
        
	public String getNombrePaso() {
		return nombrePaso;
	}
	
	public void setNombrePaso(String nombrePaso) throws TramiteException {
		if (nombrePaso.trim().compareTo("") == 0) {
			this.nombrePaso = "";
			throw new TramiteException(TramiteException.NOMBRE_PASO);
		} else {
			this.nombrePaso = nombrePaso.trim();
		}
	}
	
	public int getNumPaso() {
		return numPaso;
	}
	
	public void setNumPaso(int numPaso) {
		this.numPaso = numPaso;
	}
	
	public String getResponsable() {
		return responsable;
	}
	
	public void setResponsable(String responsable) throws TramiteException {
		if (responsable.trim().compareTo("") == 0) {
			this.responsable = "";
			throw new TramiteException(TramiteException.RESPONSABLE);
		} else {
			this.responsable = responsable.trim();
		}
	}
	
	public boolean isConFechaLimite() {
		return conFechaLimite;
	}
	
	public void setConFechaLimite(boolean conFechaLimite) throws TramiteException {
		if (isConFechaLimite()) {
			if (isObligatorio() && repeticion != 0) {
				this.conFechaLimite = conFechaLimite;
			} else {
				throw new TramiteException(TramiteException.FECHA_LIMITE);
			} 
		} else {
			this.conFechaLimite = conFechaLimite;
		}
	}
	
	public boolean isConPlantilla() {
		return conPlantilla;
	}
	
	public void setConPlantilla(boolean conPlantilla) {
		this.conPlantilla = conPlantilla;
	}
	
	public String getPlantilla() {
		return plantilla;
	}
	
	public void setPlantilla(String plantilla) throws TramiteException {
		if (plantilla.trim().isEmpty()) {
			this.plantilla = plantilla;
		} else if (plantilla.endsWith(".docx") || plantilla.endsWith(".pdf") 
				|| plantilla.endsWith(".doc")) {
			File f = new File(plantilla);
			if (f.exists()) {
				this.plantilla = plantilla;
			} else {
				throw new TramiteException(TramiteException.PLANTILLA_EXISTE);
			}
		} else {
			throw new TramiteException(TramiteException.PLANTILLA);
		}
	}
	
	public boolean isConDocumento() {
		return conDocumento;
	}
	
	public void setConDocumento(boolean conDocumento) {
		this.conDocumento = conDocumento;
	}
	
	public boolean isConCambioEstado() {
		return conCambioEstado;
	}
	
	public void setConCambioEstado(boolean conCambioEstado) {
		this.conCambioEstado = conCambioEstado;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public Paso getSecuencia() {
		return secuencia;
	}
	
	public void setSecuencia(Paso secuencia) {
		this.secuencia = secuencia;
	}
	
	public boolean isObligatorio() {
		return obligatorio;
	}
	
	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}
	
	public int getRepeticion() {
		return repeticion;
	}
	
	public void setRepeticion(int repeticion) {
		this.repeticion = repeticion;	
	}
	
	public boolean isConRepeticion() {
		return conRepeticion;
	}
	
	public void setConRepeticion(boolean conRepeticion) {
		this.conRepeticion = conRepeticion;
	}

	public boolean isEsNuevo() {
		return esNuevo;
	}
	
	public void setEsNuevo(boolean esNuevo) {
		this.esNuevo = esNuevo;
	}

	public int compareTo(Paso o) {
		return getNumPaso() - o.getNumPaso();
	}
	
	public boolean validarPaso() throws TramiteException {
		return false;
	}
	
	public boolean insertarPaso() {
		return false;
	}

	public boolean actualizarPaso() {
		return false;
	}

	public boolean borrarPaso() {
		return false;
	}

	public int recuperarPaso(BaseDatos bd, int numPaso) throws TramiteException, BaseDatosException {
		// recuperar datos del paso
		String[] registroPaso = bd.obtenerPaso(numPaso);
		if (registroPaso != null) {
			setNumPaso(numPaso);
			setNombrePaso(registroPaso[0]);
			setResponsable(registroPaso[1]);
			setConFechaLimite(Boolean.parseBoolean(registroPaso[2]));
			setConPlantilla(Boolean.parseBoolean(registroPaso[3]));
			setPlantilla(registroPaso[4]);
			setConDocumento(Boolean.parseBoolean(registroPaso[5]));
			setConCambioEstado(Boolean.parseBoolean(registroPaso[6]));
			setEstado(registroPaso[7]);
			int secuencia = Integer.parseInt(registroPaso[8]);
			setObligatorio(Boolean.parseBoolean(registroPaso[9]));
			setConRepeticion(Boolean.parseBoolean(registroPaso[10]));
			setRepeticion(Integer.parseInt(registroPaso[11]));
			setEsNuevo(false);
			return secuencia;
		}
		return -1;
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getNombrePaso();
	}

	
}
