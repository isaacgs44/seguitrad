package dominio;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import basedatos.BaseDatos;
import excepcion.BaseDatosException;
import excepcion.TramiteException;
import gui.PanelCampo;
import gui.VentanaPrincipal;
import java.sql.ResultSet;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ListaTramites {

    private Tramite tramite;
    private ArrayList<TramiteEspecifico> listaTramites;
    private ArrayList<TramiteEspecifico> tramiesBasura; 
    private ArrayList<PasoEspecifico> pasosBasura;
    private ArrayList<Consulta> listaConsultas;
    private boolean hayCambios;
    private BaseDatos bd;
    private PanelCampo panelCampo;
    FileDialog fd = new FileDialog(new Frame(), "Seleccionar trámite", FileDialog.LOAD);

    /**
     *
     */
    public ListaTramites() {
        inicializar();
    }

    private void inicializar() {
        tramite = null;
        listaTramites = new ArrayList<>();
        tramiesBasura = new ArrayList<>();
        listaConsultas = new ArrayList<>();
        pasosBasura = new ArrayList<>();
        hayCambios = false;
    }

    public Tramite getTramite() {
        return tramite;
    }

    /**
     * @param tramite
     */
    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    /**
     * @return the listaTramites
     */
    public ArrayList<TramiteEspecifico> getListaTramitesEsp() {
        return listaTramites;
    }

    /**
     * @param listaTramites the listaTramites to set
     */
    public void setListaTramites(ArrayList<TramiteEspecifico> listaTramites) {
        this.listaTramites = listaTramites;
    }

    /**
     * @return the listaConsultas
     */
    public ArrayList<Consulta> getListaConsultas() {
        return listaConsultas;
    }

    /**
     * @param listaConsultas the listaConsultas to set
     */
    public void setListaConsultas(ArrayList<Consulta> listaConsultas) {
        this.listaConsultas = listaConsultas;
    }

    /**
     * @return the hayCambios
     */
    public boolean isHayCambios() {
        return hayCambios;
    }

    /**
     * @param hayCambios the hayCambios to set
     */
    public void setHayCambios(boolean hayCambios) {
        this.hayCambios = hayCambios;
    }

    /**
     * @return the bd
     */
    public BaseDatos getBd() {
        return bd;
    }

    /**
     * @param bd the bd to set
     */
    public void setBd(BaseDatos bd) {
        this.bd = bd;
    }

    public void agregarTramiteEspecifico(TramiteEspecifico tramite) {
        listaTramites.add(tramite);
    }

    public void agregarConsulta(Consulta consulta) throws TramiteException {
        // validar que no esté repetido el nombre
        for (Consulta c : listaConsultas) {
            if (c.getNombreConsulta().compareToIgnoreCase(consulta.getNombreConsulta()) == 0) {
                throw new TramiteException(TramiteException.CONSULTA_REPETIDA);
            }
        }
        listaConsultas.add(consulta);
        Collections.sort(listaConsultas);
    }

    /**
     * @throws BaseDatosException
     * @throws TramiteException
     */
    public void crearTramite() throws BaseDatosException, TramiteException {
        // crear bd y directorio
        bd = new BaseDatos(tramite.getNombreArchivo(), true);
        bd.crearTablas();
        // insertar los registros de la estructura del trámite
        tramite.crearTramite(bd);
    }

    public void quitarTramite() {
    }

    public void modificarTramite() throws BaseDatosException, TramiteException {
        // insertar los registros de la estructura del trámite
        tramite.actualizarTramite(bd);
    }

    /**
     * Método que borra la Base de Datos y la vuelve a crear con todas las
     * modificaciones
     *
     * @see Consulta
     * @throws BaseDatosException
     *
     *
     * @autor isaac - cresencio
     */
    public void guardarArchivo() throws BaseDatosException, SQLException {
        bd = getBd();
        // insertamos nuevas consultas...
        System.out.println("--------------------------------GUARDA VALORES----------------------------------");
        for (Consulta consulta : getListaConsultas()) {
            if (consulta.isNuevo()) {
                System.out.println("nueva consulta: " + consulta.getNombreConsulta());
            consulta.insertarConsulta(bd);
                consulta.setNuevo(false);
        }
    }

        // insertamos nuevos registros y cambios...
        for (TramiteEspecifico t : getListaTramitesEsp()) {
            if (t.isNuevo()) { // agregar nuevo registro
                System.out.println("nuevo registro: " + t.getValores().get(0)[0]);
                System.out.println("id: " + t.getIdTramite());
                t.insertarTramiteEspecifico(bd);
                t.setNuevo(false);
            } else if (t.isCambio()) { // modificar registro
                System.out.println("cambio registro: " + t.getValores().get(0)[0]);
                System.out.println("id: " + t.getIdTramite());
                t.modificarTramiteEspecifico(bd);
                t.setCambio(false);
            }
        }
        for (TramiteEspecifico t : getListaTramitesEsp()) {
            for (PasoEspecifico p : t.getPasosEspecificos()) {
                if (p.isNuevo()) { // en el caso de realizar un indefinido
                    System.out.println("Paso nuevo: " + p.getNombrePaso());
                    System.out.println("id: " + obtenerIDPasoEsp());
                    p.setIdPasoEsp(obtenerIDPasoEsp());
                    p.insertarPasoEspecifico(bd, t);
                    p.setNuevo(false);
                } else if (p.isCambio()) { // al cambiar de estado
                    System.out.println("cambio en paso: " + p.getNombrePaso());
                    System.out.println("id: " + p.getIdPasoEsp());
                    p.setIdPasoEsp(p.getIdPasoEsp());
                    p.modificarPasoEspecifico(bd, t);
                    p.setCambio(false);
                }
            }
        }
        
        if(tramiesBasura.size()>0){ // hay registros por borrar
            System.out.println("--------------Eliminar Tramite---------");
            for(TramiteEspecifico t: tramiesBasura){
                System.out.println("Nombre: " + t.getValores().get(0)[0]);
                System.out.println("Id: "+ t.getIdTramite());
                t.eliminarTramiteEspecifico(bd);
                
                // falta eliminar documentos
                
            }
            tramiesBasura.clear();
        }
        
        if(pasosBasura.size()>0){
            for(PasoEspecifico p : pasosBasura){
                p.eliminarPasoEspecifico(bd);
            }
            pasosBasura.clear();
        }
        
        setHayCambios(false);
    }

    /**
     *
     */
    public void guardarArchivoComo() {
    }

    /**
     * Método que recupera todos los trámites específicos y consultas de la base
     * de datos.
     *
     * @see Consulta#agregarCampo(dominio.Campo, java.lang.String[])
     * @see #agregarConsulta(dominio.Consulta)
     * @see Tramite#recuperarTramite(basedatos.BaseDatos, java.lang.String)
     * @see TramiteEspecifico#agregarCampo(dominio.Campo, java.lang.String[])
     * @see TramiteEspecifico#agregarPasoEspecifico(dominio.PasoEspecifico)
     * @see BaseDatos#cerrarConexion()
     * @see BaseDatos#realizarConsulta(java.lang.String)
     * @see BaseDatos#numRegistrosMeta_espec()
     *
     * @throws BaseDatosException
     * @throws TramiteException
     * @throws SQLException
     * @autor cresencio -isaac
     */
    public void abrirArchivo() throws BaseDatosException, TramiteException, SQLException {
        fd.setDirectory(System.getProperty("user.dir") + File.separator + "tramites");
        fd.setFile("*.trad");
        fd.setVisible(true);
        if (fd.getFile() != null) {
            // abrir bd
            bd = new BaseDatos(fd.getDirectory() + fd.getFile(), false);
            inicializar();
            // recuperar tramite
            tramite = new Tramite();
            tramite.recuperarTramite(bd, fd.getFile());

            // Recuperamos trámites específicos
            ArrayList<Campo> campos = tramite.getCampos();
            ArrayList<Paso> pasos = tramite.getPasos();
            ArrayList<PasoEspecifico> pasoEspecificos;
            int registrosMeta_Espec = bd.numRegistrosMeta_espec();
            String consulta = "SELECT * FROM tramites_especificos";

            ResultSet rs = bd.realizarConsulta(consulta);
            this.listaTramites.clear();
            while (rs.next()) {
                ArrayList<String[]> valores = new ArrayList<>();
                ArrayList<String[]> valoresDefecto = new ArrayList<>();
                TramiteEspecifico tramiteEspecifico = new TramiteEspecifico();

                tramiteEspecifico.setIdTramite(Integer.parseInt(rs.getString(1)));
                valores.add(0, new String[1]);
                valores.get(0)[0] = rs.getString(2);
                valores.add(1, new String[1]);
                valores.get(1)[0] = rs.getString(3);
                valores.add(2, new String[1]);
                valores.get(2)[0] = rs.getString(4);
                valores.add(3, new String[1]);
                valores.get(3)[0] = rs.getString(5);
                valores.add(4, new String[1]);
                valores.get(4)[0] = rs.getString(6);

                //Trabajando en cargar datos por defecto
                for (int j = 0; j < 5; j++) {
                    tramiteEspecifico.agregarCampo(campos.get(j), valores.get(j));
                }

                if (comprobarCamposAdicionales(tramiteEspecifico.getIdTramite())) {
                    String consulta2 = "select   Valor, idRegistro,valor_defecto from meta_espec inner join "
                            + "tramites_especificos inner join tramites_especificos_campos where idCampo_numCampoMeta_espec=num_campo "
                            + "and idCampo_IdRegistro_tramiteEspec= idRegistro ";
                    ResultSet rs1 = bd.realizarConsulta(consulta2);
                    int k = 5;
                    while (rs1.next()) {
                        ArrayList<String[]> valoresCamposBD = new ArrayList<>();
                        if (tramiteEspecifico.getIdTramite() == rs1.getInt(2)) {
                            valoresCamposBD.add(0, new String[1]);
                            valoresCamposBD.get(0)[0] = rs1.getString(1);
                            tramiteEspecifico.agregarCampo(campos.get(k), valoresCamposBD.get(0));
                            k++;
                        }
                    }
                    if (k < registrosMeta_Espec) {
                        for (int i = k; i < campos.size(); i++) {
                            valoresDefecto.add(0, new String[1]);
                            valoresDefecto.get(0)[0] = campos.get(i).getValorDefecto();
                            tramiteEspecifico.agregarCampo(campos.get(i), valoresDefecto.get(0));
                        }
                    }
                }
                if (registrosMeta_Espec != 5) {
                    if (comprobarCamposAdicionales(tramiteEspecifico.getIdTramite()) == false) {
                        for (int i = 5; i < campos.size(); i++) {
                            valoresDefecto.add(0, new String[1]);
                            valoresDefecto.get(0)[0] = campos.get(i).getValorDefecto();
                            tramiteEspecifico.agregarCampo(campos.get(i), valoresDefecto.get(0));
                        }
                    }
                }

                /*TRABAJANDO EN RECUPERACION DE PASOS ESPECIFICOS*/
                String consultaPasosEsp = "select * from pasos_especificos inner join meta_paso"
                        + " where pasos_especificos.num_paso= meta_paso.num_paso"
                        + " and idRegistro_tramiteEsp = " + tramiteEspecifico.getIdTramite();
                ResultSet resultSet = bd.realizarConsulta(consultaPasosEsp);
                int i = 0;
                while (resultSet.next()) {
                    PasoEspecifico pasoEspecifico = new PasoEspecifico();
                    if (resultSet.getInt("repeticion") >= 1) {
                        String nombrePaso = resultSet.getInt("repeticion") == 1 ? resultSet.getString("nombre_paso") : resultSet.getString("nombre_paso") + " " + i;
                        pasoEspecifico.setNombrePaso(nombrePaso);
                    } else {
                        pasoEspecifico.setNombrePaso(resultSet.getString("nombre_paso"));
                    }
                    boolean realizado = Boolean.parseBoolean(resultSet.getString("realizado"));
                    pasoEspecifico.setRealizado(realizado);
                    try {
                        //Date d = new Date(resultSet.getString("fecha_realizacion"));
                        pasoEspecifico.setFechaRealizacion(PanelCampo.obtenerFecha(resultSet.getString("fecha_realizacion")));
                        System.out.println(PanelCampo.obtenerFecha(resultSet.getString("fecha_realizacion")));
                        //pasoEspecifico.setFechaRealizacion(d);
                    } catch (IllegalArgumentException e) {
                        pasoEspecifico.setFechaRealizacion(null);
                    }
                    pasoEspecifico.setDocumento(resultSet.getString("documento"));
                    boolean obligatorio = Boolean.parseBoolean(resultSet.getString("obligatorio"));
                    boolean con_fecha_limite = Boolean.parseBoolean(resultSet.getString("con_fecha_limite"));
                    if (obligatorio && con_fecha_limite) {
                        //Date d = new Date(resultSet.getString("fecha_limite"));
                        pasoEspecifico.setFechaLimite(PanelCampo.obtenerFecha(resultSet.getString("fecha_limite")));
                    } else {
                        pasoEspecifico.setFechaLimite(null);
                    }
                    //Se añadió esto para poder distinguir cuando un paso especifico tiene repeticion
                    int repeticiones = resultSet.getInt("repeticion");
                    if (repeticiones == 1 || repeticiones == 0) {
                        i = 1;
                    } else {
                        i++;
                    }
                    pasoEspecifico.setRepeticion(repeticiones);
                    pasoEspecifico.setIdPasoEsp(Integer.parseInt(resultSet.getString("idPasoEsp")));
                    pasoEspecifico.setNumPaso(Integer.parseInt(resultSet.getString(3)));
                    tramiteEspecifico.agregarPasoEspecifico(pasoEspecifico);
                }
                agregarTramiteEspecifico(tramiteEspecifico);
            }
            bd.cerrarConexion();

            // Recuperación de consultas
            String consulta3 = "SELECT * FROM consulta";
            ResultSet rs3 = bd.realizarConsulta(consulta3);
            boolean nuevo;
            while (rs3.next()) {
                Consulta c = new Consulta();
                c.setNombreConsulta(rs3.getString(1));
                nuevo = true;
                for (Campo campo : tramite.getCampos()) {
                    if (campo.getNombreCampo().equals(rs3.getString(2))) {
                        String[] valores;
                        int separador;
                        switch (campo.getTipo()) {
                            case FECHA:
                            case NUMERO:
                                valores = new String[2];
                                separador = rs3.getString(3).indexOf("/");
                                valores[0] = rs3.getString(3).substring(0, separador - 1);
                                valores[1] = rs3.getString(3).substring(separador + 2);

                                break;

                            default:
                                valores = new String[1];
                                valores[0] = rs3.getString(3);
                        }

                        for (Consulta c2 : listaConsultas) {
                            if (c.getNombreConsulta().equals(c2.getNombreConsulta())) { //la consulta ya existe en la lista
                                c2.agregarCampo(campo, valores);
                                nuevo = false;
                                break;
                            }
                        }
                        if (nuevo) {
                            c.agregarCampo(campo, valores);
                            agregarConsulta(c);
                        }
                    }
                }
            }
            bd.cerrarConexion();

        } else {
            // no se seleccionó nada o se canceló
            setBd(null);
        }
    }

    //Comprueba la existencia de registros en la tabla tramites_especificos_campos para un tramite especifico y
    //de esta manera saber cuando cargar valores por defecto, o cargar los registros contenidos en la tabla consultada.
    public boolean comprobarCamposAdicionales(int iDRegistro) throws BaseDatosException, SQLException {
        String consulta = "select count(*) from tramites_especificos_campos where idCampo_IdRegistro_tramiteEspec=" + iDRegistro;
        ResultSet rs = bd.realizarConsulta(consulta);
        int numRegistros = 0;
        while (rs.next()) {
            numRegistros = rs.getInt(1);
        }
        if (numRegistros == 0) {
            return false;
        } else {
            return true;
        }
    }

    //Añade los registros adicionales al tramite especifico en caso de que los tenga y de que la comprobacion de campos resulte verdadera
    public void cargarDatosAdicionales(ArrayList<Campo> campos) throws BaseDatosException, SQLException {
        String consulta2 = "select   Valor, idRegistro,valor_defecto from meta_espec inner join "
                + "tramites_especificos inner join tramites_especificos_campos where idCampo_numCampoMeta_espec=num_campo "
                + "and idCampo_IdRegistro_tramiteEspec= idRegistro ";
        ResultSet rs1 = bd.realizarConsulta(consulta2);
        while (rs1.next()) {
            ArrayList<String[]> valores = new ArrayList<>();
            int k = 5;
            for (TramiteEspecifico t : listaTramites) {
                if (t.getIdTramite() == rs1.getInt(2)) {
                    valores.add(0, new String[1]);
                    valores.get(0)[0] = rs1.getString(1);
                    t.agregarCampo(campos.get(k), valores.get(0));
                    k++;
                }
            }
        }
    }

    public void cerrarArchivo() {
        inicializar();
    }

    private int obtenerIDPasoEsp() {
        int nuevoID = 0;
        for(TramiteEspecifico t : listaTramites){
            for(PasoEspecifico p : t.getPasosEspecificos()){
                if(p.getIdPasoEsp() > nuevoID)
                    nuevoID = p.getIdPasoEsp();
}
        }
        nuevoID++;
        return nuevoID;
    }

    public ArrayList<TramiteEspecifico> getTramiesBasura() {
        return tramiesBasura;
    }

    public void setTramiesBasura(ArrayList<TramiteEspecifico> tramiesBasura) {
        this.tramiesBasura = tramiesBasura;
    }

    public ArrayList<PasoEspecifico> getPasosBasura() {
        return pasosBasura;
    }

    public void setPasosBasura(ArrayList<PasoEspecifico> pasosBasura) {
        this.pasosBasura = pasosBasura;
    }

    
    
    
    
}
