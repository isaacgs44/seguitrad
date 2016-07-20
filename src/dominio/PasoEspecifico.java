package dominio;

import java.util.Date;
import excepcion.TramiteEspecificoException;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JPanel;

public class PasoEspecifico implements Comparable<PasoEspecifico>{

    private JPanel panelContenedor;
    private JList lista;
    private String nombrePaso;
    private int repeticion;
    private boolean realizado;
    private Date fechaLimite;
    private Date fechaRealizacion;
    private String documento;
    private int numPaso;

    public PasoEspecifico(ArrayList<PasoEspecifico> pasoEspecificos) {
    }

    public PasoEspecifico() {
    }

    public void realizarPaso(Date fechaRealizacion, boolean realizado, String documento, int indice, ArrayList<PasoEspecifico> pasosEspecificos) {
        int numPasosFaltantes = 0;
        for (PasoEspecifico pe : pasosEspecificos) {
            if (!pe.isRealizado()) {
                numPasosFaltantes++;
            }
        }
        String[] todo_pasos = new String[numPasosFaltantes];
        int i = 0;
        for (PasoEspecifico pe : pasosEspecificos) {
            if (!pe.isRealizado()) {
                todo_pasos[i] = pe.getNombrePaso();
                i++;
            }
        }
        System.out.println("Indice Seleccionado " + indice);
        for (PasoEspecifico pe : pasosEspecificos) {
            if (todo_pasos[indice].equals(pe.getNombrePaso())) {
                if (!pe.isRealizado()) {
                    pe.setRealizado(realizado);
                    pe.setFechaRealizacion(fechaRealizacion);
                    pe.setDocumento(documento);
                }
            }
        }
    }

    public int getNumPaso() {
        return numPaso;
    }

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

    public JList getLista() {
        return this.lista;
    }

    public void setLista(JList lista) {
        this.lista = lista;
    }
}
