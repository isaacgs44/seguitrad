package dominio;

import java.util.StringTokenizer;

import basedatos.BaseDatos;
import excepcion.BaseDatosException;
import excepcion.TramiteException;

public class Campo implements Comparable<Campo> {
	private String nombreCampo;
	private int numCampo;
	private Tipo tipo;
	private String valorDefecto;
	private boolean obligatorio = false;
	private transient boolean esNuevo = true;
	
	public Campo() {
		nombreCampo = "";
		numCampo = 0;
		tipo = null;
		valorDefecto = "";
		obligatorio = false;
		esNuevo = true;
                System.out.println("Prueba");
	}
	
	/**
	 * @param nombreCampo
	 * @param numCampo
	 * @param tipo
	 * @param valorDefecto
	 * @param obligatorio
	 * @throws TramiteException 
	 */
	public Campo(String nombreCampo, int numCampo, Tipo tipo,
			String valorDefecto, boolean obligatorio) throws TramiteException {
		setNombreCampo(nombreCampo);
		setNumCampo(numCampo);
		setTipo(tipo);
		setValorDefecto(valorDefecto);
		setObligatorio(obligatorio);
		setEsNuevo(true);
	}

	public String getNombreCampo() {
		return nombreCampo;
	}
	
	public void setNombreCampo(String nombreCampo) throws TramiteException {
		// validar que no esté vacío
		if (nombreCampo.trim().compareTo("") == 0) {
			this.nombreCampo = "";
			throw new TramiteException(TramiteException.NOMBRE_CAMPO);
		} else {
			this.nombreCampo = nombreCampo.trim();
		}
	}
	
	public int getNumCampo() {
		return numCampo;
	}
	
	public void setNumCampo(int numCampo) {
		this.numCampo = numCampo;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public String[] getValorDefectoOpciones() {
		String[] opciones;
		int posicion = 0;
		StringTokenizer tokens = new StringTokenizer(valorDefecto, "/");
		opciones = new String[tokens.countTokens()];
		while (tokens.hasMoreTokens()) {
			opciones[posicion] = tokens.nextToken().trim();
			posicion++;
		}
		return opciones;
	}
	
	public void setValorDefectoOpciones(String[] opciones) throws TramiteException {
		if (getTipo() == Tipo.OPCMULT) {
			if (opciones.length >= 1) {
				setValorDefecto(getCadenaOpciones(opciones));
			} else {
				this.valorDefecto = "";
				throw new TramiteException(TramiteException.VALOR_DEFECTO_OPCMULT);
			}
		}
		if (getTipo() == Tipo.OPCEXCL) {
			if (opciones.length >= 2) {
				setValorDefecto(getCadenaOpciones(opciones));
			} else {
				this.valorDefecto = "";
				throw new TramiteException(TramiteException.VALOR_DEFECTO_OPCEXCL);
			}
		}
	}

	private String getCadenaOpciones(String[] arreglo) {
		String cadena = "";
		if (arreglo == null || arreglo.length == 0) {
			return "";
		}
		for (int i = 0; i < arreglo.length; i++) {
			cadena += arreglo[i];
			if (i != (arreglo.length - 1)) {
				cadena += " / ";
			}
		}
		return cadena;
	}

	public String getValorDefecto() {
		return valorDefecto;
	}

	public void setValorDefecto(String valorDefecto) {
		this.valorDefecto = valorDefecto.trim();
	}

	public boolean isObligatorio() {
		return obligatorio;
	}
	
	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	public boolean isEsNuevo() {
		return esNuevo;
	}
	
	public void setEsNuevo(boolean esNuevo) {
		this.esNuevo = esNuevo;
	}

	public int compareTo(Campo o) {
		return getNumCampo() - o.getNumCampo();
	}
	
	public boolean validarCampo() throws TramiteException {
		return false;
	}
	
	public boolean insertarCampo() {
		return false;
	}

	public boolean actualizarCampo() {
		return false;
	}

	public boolean borrarCampo() {
		return false;
	}

	public void recuperarCampo(BaseDatos bd, int numCampo) throws TramiteException, BaseDatosException {
		// recuperar datos del campo
		String[] registroCampo = bd.obtenerCampo(numCampo);
		if (registroCampo != null) {
			setNumCampo(numCampo);
			setNombreCampo(registroCampo[0]);
			setObligatorio(Boolean.parseBoolean(registroCampo[1]));
			setTipo(Tipo.valueOf(registroCampo[2]));
			setValorDefecto(registroCampo[3]);
			setEsNuevo(false);
		}
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getNombreCampo();
	}
}
