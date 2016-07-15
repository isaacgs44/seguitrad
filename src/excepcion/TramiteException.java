package excepcion;

public class TramiteException extends Exception {
	private static final long serialVersionUID = -6166277717034919830L;
	
	public static final String[] NOMBRE_TRAMITE = {"Nombre vacío, el sistema requiere un dato", "Error en campo Nombre de trámite"};
	public static final String[] DEPARTAMENTO = {"Departamento vacío, el sistema requiere un dato", "Error en Datos generales"};
	public static final String[] NOMBRE_ARCHIVO = {"Nombre del archivo vacío, el sistema requiere un dato", "Error en Datos generales"};
	public static final String[] NOMBRE_CAMPO = {"Nombre del campo vacío, el sistema requiere un dato", "Error en Datos específicos"};
	public static final String[] VALOR_DEFECTO_OPCMULT = {"Debe tener al menos una opción", "Error en Datos específicos"};
	public static final String[] VALOR_DEFECTO_OPCEXCL = {"Debe tener al menos dos opciones", "Error en Datos específicos"};
	public static final String[] CAMPO_REPETIDO = {"El campo está repetido", "Error en Datos específicos"};
	public static final String[] CAMPO_DEFINIDO = {"El campo ya está siendo usado por el sistema, \n"
			+ "por lo tanto no se puede quitar", "Error en Datos específicos"};
	public static final String[] CAMPO_PREDEFINIDO = {"El campo es necesario para que el sistema funcione correctamente, \n"
			+ "por lo tanto no se puede quitar", "Error en Datos específicos"};
	public static final String[] NOMBRE_PASO = {"Nombre del paso vacío, el sistema requiere un dato", "Error en Pasos"};
	public static final String[] PASO_REPETIDO = {"El paso está repetido", "Error en Pasos"};
	public static final String[] RESPONSABLE = {"Nombre del resposable vacío, el sistema requiere un dato", "Error en Pasos"};
	public static final String[] FECHA_LIMITE = {"No puede establecerse una fecha límite si el paso no es obligatorio o tiene una repetición indefinida", "Error en Pasos"};
	public static final String[] PLANTILLA = {"Seleccione un archivo con extension .doc, .docx o .pdf", "Error en Pasos"};
	public static final String[] PLANTILLA_EXISTE = {"El archivo de la plantilla no existe.\nSeleccione otro archivo", "Error en Pasos"};
	public static final String[] SECUENCIA = {"Si se elimina el paso, se romperá la secuencia de pasos.\n"
			+ "Deberá eliminar primero los pasos relacionados", "Error en Pasos"};	
	public static final String[] PASO_DEFINIDO = {"El paso ya está siendo usado por el sistema, \n"
			+ "por lo tanto no se puede quitar", "Error en Pasos"};
	public static final String[] CONSULTA_REPETIDA = {"El nombre de la consulta ya está siendo utilizado", "Error en Nueva Consulta"};

	private String titulo;
	
	public TramiteException(String msg) {
		super(msg);
		setTitulo("Error en trámite");
	}
	
	public TramiteException(String[] msg) {
		super(msg[0]);
		setTitulo(msg[1]);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
