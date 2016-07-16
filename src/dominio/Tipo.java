package dominio;

public enum Tipo {
	TEXTO ("Texto"),
	NUMERO ("Número"),
	FECHA ("Fecha"),
	OPCMULT ("Opciones múltiples"),
	OPCEXCL ("Opciones excluyentes");
	private final String nombreTipo;
	
	private Tipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
	}
	
	public String getTipo() {
		return nombreTipo;
	}
	  
	public String toString() {
		return getTipo();
	}
}
