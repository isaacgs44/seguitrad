package dominio;

import java.util.ArrayList;
import java.util.Collections;

import lib.UtileriasArchivo;
import basedatos.BaseDatos;
import excepcion.BaseDatosException;
import excepcion.TramiteException;

public class Tramite {
	private String nombreTramite;
	private String departamento;
	private String descripcion;
	private String nombreArchivo;
	private int diasAlerta = 1;
	private ArrayList<Campo> campos;
	private ArrayList<Paso> pasos;
	
	/**
	 * 
	 */
	public Tramite() {
		nombreTramite = "";
		departamento = "";
		descripcion = "";
		nombreArchivo = "";
		diasAlerta = 1;
		setCampos(new ArrayList<Campo>());
		setPasos(new ArrayList<Paso>());
	}

	public String getNombreTramite() {
		return nombreTramite;
	}
	
	public void setNombreTramite(String nombreTramite) throws TramiteException {
		if (nombreTramite.trim().compareTo("") == 0) {
			this.nombreTramite = "";
			throw new TramiteException(TramiteException.NOMBRE_TRAMITE);
		} else {
			this.nombreTramite = nombreTramite.trim();
		}
	}
	
	public String getDepartamento() {
		return departamento;
	}
	
	public void setDepartamento(String departamento) throws TramiteException {
		if (departamento.trim().compareTo("") == 0) {
			this.departamento = "";
			throw new TramiteException(TramiteException.DEPARTAMENTO);
		} else {
			this.departamento = departamento.trim();
		}
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.trim();
	}
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	
	public void setNombreArchivo(String nombreArchivo) throws TramiteException {
		if (nombreArchivo.trim().compareTo("") == 0) {
			this.nombreArchivo = "";
			throw new TramiteException(TramiteException.NOMBRE_ARCHIVO);
		} else {
			// extensión .trad
			nombreArchivo = nombreArchivo.trim().toLowerCase();
			if (!nombreArchivo.endsWith(".trad")) {
				nombreArchivo += ".trad";
			}
			this.nombreArchivo = nombreArchivo;
		}
	}
	
	public int getDiasAlerta() {
		return diasAlerta;
	}
	
	public void setDiasAlerta(int diasAlerta) {
		this.diasAlerta = diasAlerta;
	}

	/**
	 * @return the campos
	 */
	public ArrayList<Campo> getCampos() {
		return campos;
	}

	/**
	 * @param campos the campos to set
	 */
	public void setCampos(ArrayList<Campo> campos) {
		this.campos = campos;
	}

	/**
	 * @return the pasos
	 */
	public ArrayList<Paso> getPasos() {
		return pasos;
	}

	/**
	 * @param pasos the pasos to set
	 */
	public void setPasos(ArrayList<Paso> pasos) {
		this.pasos = pasos;
	}

	public void agregarCampo(Campo campo) throws TramiteException {
		// calcular numCampo con el número de elementos
		campo.setNumCampo(getCampos().size());
		// validar que el campo no esté ya en la lista
		for (Campo c: getCampos()) {
			if (c.getNombreCampo().compareToIgnoreCase(campo.getNombreCampo()) == 0) {
				throw new TramiteException(TramiteException.CAMPO_REPETIDO);
			}
		}
		// agregar el campo en la lista
		getCampos().add(campo);
	}
	
	public int moverCampo(int posicionInicial, int direccion) {
		int posicionFinal = posicionInicial;
		boolean esInicio = false;
		boolean esFinal = false;
		if (direccion < 0) {
			if (posicionInicial == 0) {
				// posicion inicial
				posicionFinal = getCampos().size() - 1;
				esInicio = true;
			} else {
				// cualquier otra posición
				posicionFinal = posicionInicial - 1;
			}
		} else if (direccion > 0) {
			if (posicionInicial == (getCampos().size() - 1)) {
				// posicion final
				posicionFinal = 0;
				esFinal = true;
			} else {
				// cualquier otra posición
				posicionFinal = posicionInicial + 1;
			}			
		}
		// cambiar numCampo en ArrayList campos
		getCampos().get(posicionInicial).setNumCampo(posicionFinal);
		if (esInicio) {
			for (int i = posicionInicial + 1; i < getCampos().size(); i++) {
				getCampos().get(i).setNumCampo(i - 1);
			}
		} else if (esFinal) {
			for (int i = 0; i < getCampos().size() - 1; i++) {
				getCampos().get(i).setNumCampo(i + 1);
			}
		} else {
			getCampos().get(posicionFinal).setNumCampo(posicionInicial);
		}
		Collections.sort(getCampos());
		return posicionFinal;
	}
	
	public void quitarCampo(int posicion, boolean hayTramitesEspecificos) throws TramiteException {
		// validar que no sea un campo predefinido
		boolean esPredefinido;
		switch (getCampos().get(posicion).getNombreCampo()) {
		case "Nombre del solicitante":
		case "Título":
		case "Fecha de inicio":
		case "Fecha de fin":
		case "Estado":
			esPredefinido = true;
			break;
		default:
			esPredefinido = false;
			break;
		}
		if (!esPredefinido) {
			if (!getCampos().get(posicion).isEsNuevo() && hayTramitesEspecificos) {
				throw new TramiteException(TramiteException.CAMPO_DEFINIDO);
			}
			// Renumerar los campos en ArrayList
			for (int i = (posicion + 1); i < getCampos().size(); i++) {
				Campo campo = getCampos().get(i);
				campo.setNumCampo(campo.getNumCampo() - 1);
			}
			// quitar el campo
			getCampos().remove(posicion);
		} else {
			throw new TramiteException(TramiteException.CAMPO_PREDEFINIDO);
		}		
	}
	
	public Campo buscarCampo(String nombre) {
		// buscar el Campo en ArrayList
		for (Campo c: getCampos()) {
			if (c.getNombreCampo().compareToIgnoreCase(nombre) == 0) {
				return c;
			}
		}
		return null;
	}

	public void modificarCampo() {
		
	}

	public void agregarPaso(Paso paso) throws TramiteException {
		// calcular numPaso con el número de elementos
		paso.setNumPaso(getPasos().size());
		// validar que el paso no esté ya en la lista
		for (Paso p: getPasos()) {
			if (p.getNombrePaso().compareToIgnoreCase(paso.getNombrePaso()) == 0) {
				throw new TramiteException(TramiteException.PASO_REPETIDO);
			}
		}
		// agregar el paso en la lista
		getPasos().add(paso);
	}
	
	public int moverPaso(int posicionInicial, int direccion) {
		int posicionFinal = posicionInicial;
		boolean esInicio = false;
		boolean esFinal = false;
		if (direccion < 0) {
			if (posicionInicial == 0) {
				// posicion inicial
				posicionFinal = getPasos().size() - 1;
				esInicio = true;
			} else {
				// cualquier otra posición
				posicionFinal = posicionInicial - 1;
			}
		} else if (direccion > 0) {
			if (posicionInicial == (getPasos().size() - 1)) {
				// posicion final
				posicionFinal = 0;
				esFinal = true;
			} else {
				// cualquier otra posición
				posicionFinal = posicionInicial + 1;
			}			
		}
		// cambiar numPaso en ArrayList pasos
		getPasos().get(posicionInicial).setNumPaso(posicionFinal);
		if (esInicio) {
			for (int i = posicionInicial + 1; i < getPasos().size(); i++) {
				getPasos().get(i).setNumPaso(i - 1);
			}
		} else if (esFinal) {
			for (int i = 0; i < getPasos().size() - 1; i++) {
				getPasos().get(i).setNumPaso(i + 1);
			}
		} else {
			getPasos().get(posicionFinal).setNumPaso(posicionInicial);
		}
		Collections.sort(getPasos());
		return posicionFinal;
	}
	
	public void quitarPaso(int posicion, boolean hayTramitesEspecificos) throws TramiteException {
		// Validar que no se rompa la secuencia
		int numeroPaso = getPasos().get(posicion).getNumPaso();
		for (int i = (posicion + 1); i < getPasos().size(); i++) {
			Paso paso = getPasos().get(i);
			if (paso.getSecuencia() != null) {
				if (paso.getSecuencia().getNumPaso() == numeroPaso) {
					// el paso está siendo usado
					throw new TramiteException(TramiteException.SECUENCIA);
				}
			}
		}
		if (!getPasos().get(posicion).isEsNuevo() && hayTramitesEspecificos) {
			throw new TramiteException(TramiteException.PASO_DEFINIDO);
		}
		// Renumerar los pasos en ArrayList
		for (int i = (posicion + 1); i < getPasos().size(); i++) {
			Paso paso = getPasos().get(i);
			paso.setNumPaso(paso.getNumPaso() - 1);
		}
		// quitar el paso
		getPasos().remove(posicion);
	}
	
	public Paso buscarPaso(int numeroPaso) {
		// buscar el Paso en ArrayList
		for (Paso p: getPasos()) {
			if (p.getNumPaso() == numeroPaso) {
				return p;
			}
		}
		return null;
	}

	public void modificarPaso() {
		
	}
	
	/**
	 * @throws BaseDatosException 
	 * @throws TramiteException 
	 */
	public void crearTramite(BaseDatos bd) throws BaseDatosException, TramiteException {
		// insertar el registro en general
		bd.realizarAccion("INSERT INTO general VALUES("
				+ "'" + nombreTramite + "' ,"
				+ "'" + departamento + "' ," 
				+ "'" + descripcion + "' ," 
				+ "'" + nombreArchivo + "' ," 
				+ "" + diasAlerta + ")");
		// insertar los registros de los campos en meta_espec
		for (Campo campo : campos) {
			bd.realizarAccion("INSERT INTO meta_espec VALUES("
					+ "'" + campo.getNombreCampo() + "' ," 
					+ "" + campo.getNumCampo() + " ,"
					+ "'" + campo.isObligatorio() + "' ," 
					+ "'" + campo.getTipo().name() + "' ," 
					+ "'" + campo.getValorDefecto() + "')");
			campo.setEsNuevo(false);
		}
		// copiar y renombrar las plantillas en el directorio
		for (Paso paso: pasos) {
			if (paso.isConPlantilla()) {
				String plantilla = paso.getPlantilla();
				if (!plantilla.isEmpty()) {
					String extension = "";
					extension = plantilla.substring(plantilla.lastIndexOf('.'));
					UtileriasArchivo.copiarArchivo(plantilla, bd.getDirectorio() + "plantilla_" + paso.getNombrePaso() + extension);
					// cambiar los nombres en el objeto paso
					paso.setPlantilla(bd.getDirectorio() + "plantilla_" + paso.getNombrePaso() + extension);
				}
			}
		}					
		// insertar los registros de los pasos en meta_paso
		for (Paso paso : pasos) {
			bd.realizarAccion("INSERT INTO meta_paso VALUES("
					+ "'" + paso.getNombrePaso() + "' ," 
					+ "" + paso.getNumPaso() + " ,"
					+ "'" + paso.getResponsable() + "' ,"
					+ "'" + paso.isConFechaLimite() + "' ,"
					+ "'" + paso.isConPlantilla() + "' ,"
					+ "'" + paso.getPlantilla() + "' ,"
					+ "'" + paso.isConDocumento() + "' ,"
					+ "'" + paso.isConCambioEstado() + "' ,"
					+ "'" + paso.getEstado() + "' ,"
					+ "" + ((paso.getSecuencia() != null) ? paso.getSecuencia().getNumPaso() : "-1") + " ,"
					+ "'" + paso.isObligatorio() + "' ," 
					+ "'" + paso.isConRepeticion() + "' ," 
					+ "" + paso.getRepeticion() + ")");
			paso.setEsNuevo(false);
		}
                
	}
	
	public void actualizarTramite(BaseDatos bd) throws TramiteException, BaseDatosException {
		bd.eliminarRegistroTramite();
		crearTramite(bd);
	}
	
	public void recuperarTramite(BaseDatos bd, String archivo) throws TramiteException, BaseDatosException {
		// recuperar datos generales
		String[] registroGeneral = bd.obtenerGeneral();
		setNombreTramite(registroGeneral[0]);
		setDepartamento(registroGeneral[1]);
		setDescripcion(registroGeneral[2]);
		setNombreArchivo(archivo);
		setDiasAlerta(Integer.parseInt(registroGeneral[4]));
		// recuperar datos especificos
		String[] numCampos = bd.obtenerNumCampos();
		for (String n : numCampos) {
			Campo campo = new Campo();
			campo.recuperarCampo(bd, Integer.parseInt(n));
			getCampos().add(campo);
		}
		// recuperar pasos
		String[] numPasos = bd.obtenerNumPasos();
		for (String n : numPasos) {
			Paso paso = new Paso();
			int secuencia = paso.recuperarPaso(bd, Integer.parseInt(n));
			if (secuencia == -1) {
				paso.setSecuencia(null);
			} else {
				paso.setSecuencia(buscarPaso(secuencia));
			}
			getPasos().add(paso);
		}
		// FALTA
	}
}
