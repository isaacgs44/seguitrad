package excepcion;

public class BaseDatosException extends Exception {
	private static final long serialVersionUID = 1022255268919430920L;

	public static final String[] ERROR_CONTROLADOR = {"No se encontró el controlador", "Error en Base de Datos"};
	public static final String[] ERROR_BD = {"No se puede utilizar la base de datos", "Error en Base de Datos"};
	public static final String[] ERROR_CREARBD = {"Ya existe un archivo o directorio con el mismo nombre."
			+ "\nFavor de introducir uno diferente", "Error al crear la Base de Datos"};
	public static final String[] ERROR_ABRIRBD = {"No existe el archivo o el directorio asociado."
			+ "\nFavor de seleccionar uno diferente", "Error al abrir la Base de Datos"};
	public static final String[] ERROR_BDDESCONOCIDA = {"El archivo seleccionado no es reconocido por este sistema y no se puede abrir."
			+ "\nFavor de seleccionar uno diferente", "Error al abrir la Base de Datos"};

	private String titulo;
	
	public BaseDatosException(String msg) {
		super (msg);
		setTitulo("Error en Base de Datos");
	}
	public BaseDatosException(String[] msg) {
		super (msg[0]);
		setTitulo(msg[1]);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
