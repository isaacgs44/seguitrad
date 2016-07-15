package excepcion;

public class TramiteEspecificoException extends Exception {

	private static final long serialVersionUID = -4641956284062171989L;
	public static final String[] CAMPO_TEXTO_VACIO = {"Todos los campos marcados con * deben que contener un dato", "Error campo vacío"};
	public static final String[] FECHA_NULL = {"Todos los campos de fecha marcados con * deben que contener una fecha", "Error no se le ha asignado fecha"};
	public static final String[] NUMERO_VACIO = {"Los campos marcados con * se le debe que asignar un valor", "Error falta asignar un valor"};
	public static final String[] LISTA_VACIO = {"Los campos marcados con * se le debe que asignar un valor", "Error falta seleccionar un valor en las lista de opciones multiples"};
	public static final String[] COMBO_VACIO = {"Los campos marcados con * se le debe que asignar un valor", "Error falta seleccionar un valor en las lista de opciones"};

	private String titulo;
	
	public TramiteEspecificoException(String msg) {
		super(msg);
		setTitulo("Error en trámite");
	}
	
	public TramiteEspecificoException(String[] msg) {
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

