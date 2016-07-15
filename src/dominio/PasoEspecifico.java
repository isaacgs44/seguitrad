package dominio;

import java.util.Date;
import excepcion.TramiteEspecificoException;
import gui.VentanaPrincipal;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.BorderUIResource;

public class PasoEspecifico extends JPanel implements Comparable<PasoEspecifico> {

    private JPanel panelContenedor;
    private ArrayList<PasoEspecifico> pasoEspecificos;
    private ArrayList<TramiteEspecifico> tramiteEspecificos;
    private JList lista;

    public PasoEspecifico(VentanaPrincipal ventanaPrincipal, TramiteEspecifico tramiteEspecifico) {
        //Escribir funcionalidad
        System.out.println("Pasos especificos de tramite en la posicion seleccionada");
        tramiteEspecificos = ventanaPrincipal.getLista().getListaTramitesEsp();
        System.out.println("Tamaño de tramites especificos " + tramiteEspecificos.size());
        int tramiteActual = tramiteEspecifico.getIdTramite();
        //pasoEspecificos = new ArrayList<>();
        for (TramiteEspecifico te : tramiteEspecificos) {
            int numPasosEsp = ventanaPrincipal.getLista().getListaTramitesEsp().get(te.getIdTramite()).getPasosEspecificos().size();
            System.out.println("NUMERO DE PASOS ESPECIFICOS "+numPasosEsp);
            System.out.println("Indice del tramite actual "+tramiteActual);
            if (te.getIdTramite() == tramiteActual) {
                while (numPasosEsp >= 1) {
                    pasoEspecificos = ventanaPrincipal.getLista().getListaTramitesEsp().get(te.getIdTramite()).getPasosEspecificos();
                    numPasosEsp--;
                }
            }
        }
        for (PasoEspecifico pe : pasoEspecificos) {
            System.out.println(pe.getNombrePaso());
        }

        panelContenedor = new JPanel();
        int numPasos = tramiteEspecifico.getPasosEspecificos().size();
        panelContenedor.setLayout(new GridLayout(numPasos, 2, 3, 3));
        String todo_pasos[] = new String[numPasos];
        int i = 0;
        for (PasoEspecifico pe : pasoEspecificos) {
            System.out.println(pe.isRealizado());
            if (!pe.isRealizado()) {
                todo_pasos[i] = pe.getNombrePaso();
                i++;
            }
        }
        lista = new JList(todo_pasos);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setBackground(Color.decode("#E5E5E5"));
        lista.setBorder(new BorderUIResource.LineBorderUIResource(Color.decode("#E5E5E5")));
        
       //JScrollPane barraDesplazamiento = new JScrollPane(lista);
       //barraDesplazamiento.setBounds(10, 30, 250, 50);
        panelContenedor.add(lista);
        this.add(panelContenedor);
    }

    public PasoEspecifico() {
    }
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
    
    public String nombrePasoSeleccionado(int posicion){
        String nombre="hola";
        String []todo_pasos= new String[pasoEspecificos.size()];
        int i=0;
        for(PasoEspecifico pe: pasoEspecificos){
            if (!pe.isRealizado()) {
                todo_pasos[i] = pe.getNombrePaso();
                i++;
            }
            for (int j = 0; j < todo_pasos.length; j++) {
                if(j==posicion){
                return  nombre=todo_pasos[j];
                }
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
        return lista;
    }

    public void setLista(JList lista) {
        this.lista = lista;
    }
}
