package dominio;

import basedatos.BaseDatos;
import excepcion.BaseDatosException;
import java.util.Date;
import excepcion.TramiteEspecificoException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JPanel;

public class PasoEspecifico implements Comparable<PasoEspecifico> {

    private String nombrePaso;
    private int repeticion;
    private boolean realizado;
    private Date fechaLimite;
    private Date fechaRealizacion;
    private String documento;
    private int numPaso;
    private boolean cambio;
    private boolean nuevo;
    private int idPasoEsp;

    public PasoEspecifico() {
        cambio = false;
        nuevo = false;
    }

    public int getNumPaso() {
        return numPaso;
    }

    /**
     * Función que devuelve el nombre de un paso seleccionado de un JList desde la vista DialogoSeguimiento.
     * 
     * @see DialogoSeguimiento#actionPerformed(java.awt.event.ActionEvent) 
     * @param posicion El parámetro posición define el índice del paso que se ha seleccionado
     * @param pasosEspecificos El parámetro pasosEspecificos recibe una lista de pasos del trámite seleccioado
     * @return El nombre del paso sin realizar seleccionado en la vista Dialogo Seguimiento
     * @author: Cresencio
     */
    public String nombrePasoSeleccionado(int posicion, ArrayList<PasoEspecifico> pasosEspecificos) {
        String nombre = " ";
        String[] todo_pasos = new String[pasosEspecificos.size()];
        int i = 0;
        for (PasoEspecifico pe : pasosEspecificos) {
            if (!pe.isRealizado()) {
                todo_pasos[i] = pe.getNombrePaso();
                i++;
            }
        }
        for (int j = 0; j < todo_pasos.length; j++) {
            if (j == posicion) {
                nombre = todo_pasos[j];
                return nombre;
            }
        }
        return nombre;
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

    public void insertarPasoEspecifico(BaseDatos bd, TramiteEspecifico t) throws BaseDatosException, SQLException {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM);
        System.out.println("\n--------------NUEVO PASO ESPECIFICO----------------");
        System.out.println("Nombre: " + nombrePaso);
        
        String columnas = "INSERT INTO pasos_especificos ('idPasoEsp','idRegistro_tramiteEsp','num_paso'";
        String valores = " VALUES ( " + idPasoEsp + "," + t.getIdTramite() + "," + numPaso;

        if (fechaLimite != null) {
            columnas += ",'fecha_limite'";
            valores += ", '" + formato.format(fechaLimite) + "'";
    }
        if (fechaRealizacion != null) {
            columnas += ",'fecha_realizacion'";
            valores += ", '" + formato.format(fechaRealizacion) + "'";
        }
        if (documento != null && !documento.equals("")) {
            columnas += ",'documento'";
            valores += ", '" + documento + "'";
        }
        columnas += ",'realizado')";
        valores += ",'" + realizado + "')";
        System.out.println(columnas + valores);
        bd.realizarAccion(columnas + valores);
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

    public boolean isCambio() {
        return cambio;
}

    public void setCambio(boolean cambio) {
        this.cambio = cambio;
    }

    public int getIdPasoEsp() {
        return idPasoEsp;
    }

    public void setIdPasoEsp(int idPasoEsp) {
        this.idPasoEsp = idPasoEsp;
    }

    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public void modificarPasoEspecifico(BaseDatos bd, TramiteEspecifico t) throws BaseDatosException, SQLException {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM);
        System.out.println("\n--------------MODIFICAR PASO ESPECIFICO----------------");
        System.out.println("Nombre: " + nombrePaso + " - id: " + idPasoEsp);
        //borramos registro
        eliminarPasoEspecifico(bd, t);
//        insertamos con nuevos valores
        insertarPasoEspecifico(bd, t);
    }
    
    public void eliminarPasoEspecifico(BaseDatos bd, TramiteEspecifico t) throws BaseDatosException{
        t.setBd(bd);
        String c2 = "DELETE FROM pasos_especificos WHERE idRegistro_tramiteEsp='"+ t.getIdTramite() +"' and idPasoEsp='" + idPasoEsp + "'";
        System.out.println(c2);
        bd.realizarAccion(c2);
    }
    
    public void eliminarPasoEspecifico(BaseDatos bd) throws BaseDatosException{
        String c2 = "DELETE FROM pasos_especificos WHERE idPasoEsp='" + idPasoEsp + "'";
        System.out.println(c2);
        bd.realizarAccion(c2);
    }
    

}
