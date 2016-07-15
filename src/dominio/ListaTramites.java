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
import java.sql.ResultSet;

public class ListaTramites {

    private Tramite tramite;
    private ArrayList<TramiteEspecifico> listaTramites;
    private ArrayList<Consulta> listaConsultas;
    private boolean hayCambios;
    private BaseDatos bd;
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
        listaConsultas = new ArrayList<>();
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
     * Método que borra la Base de Datos y la vuelve a crear con todas las modificaciones
     * 
     * @see Consulta
     * @throws BaseDatosException
     *
     * 
     * @autor isaac - cresencio
     */
    public void guardarArchivo() throws BaseDatosException {
        // FALTA - borrar toda la BD antes de insertar
        bd = this.getBd();
        
        // insertamos consutas...
        for (Consulta consulta : getListaConsultas()) {
            
            consulta.insertarConsulta(bd);
        }
    }

    /**
     *
     */
    public void guardarArchivoComo() {
    }

    /**
     * Método que recupera todos los trámites específicos y consultas de la base de datos.
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
                            tramiteEspecifico.agregarCampo(campos.get(i), valoresDefecto.get(i));
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

                for (Paso p : pasos) {
                    PasoEspecifico pasoEspecifico = new PasoEspecifico();
                    pasoEspecifico.setNombrePaso(p.getNombrePaso());
                    // Falta. crear la repetición de pasos
                    pasoEspecifico.setRepeticion(p.getRepeticion());
                    pasoEspecifico.setRealizado(false);
                    pasoEspecifico.setFechaRealizacion(null);
                    pasoEspecifico.setDocumento("");
                    /*if (p.isObligatorio() && p.isConFechaLimite()) {
                    
                     fecha = fechas.get(index);
                     pasoEspecifico.setFechaLimite(fecha.getDate());
                     index++;
                     } else {
                     pasoEspecifico.setFechaLimite(null);
                     }*/

                    tramiteEspecifico.agregarPasoEspecifico(pasoEspecifico);
                }
                agregarTramiteEspecifico(tramiteEspecifico);

            }
            bd.cerrarConexion();

            // Recuperación de conslutas
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

    public void recargarArchivo() throws BaseDatosException, SQLException, TramiteException {
        // abrir bd
        bd = new BaseDatos(fd.getDirectory() + fd.getFile(), false);
        inicializar();
        // recuperar tramite
        tramite = new Tramite();
        tramite.recuperarTramite(bd, fd.getFile());

        // Recuperamos trámites específicos
        ArrayList<Campo> campos = tramite.getCampos();
        ArrayList<Paso> pasos = tramite.getPasos();
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

            for (Paso p : pasos) {
                PasoEspecifico pasoEspecifico = new PasoEspecifico();
                pasoEspecifico.setNombrePaso(p.getNombrePaso());
                // Falta. crear la repetición de pasos
                pasoEspecifico.setRepeticion(p.getRepeticion());
                pasoEspecifico.setRealizado(false);
                pasoEspecifico.setFechaRealizacion(null);
                pasoEspecifico.setDocumento("");
                /*if (p.isObligatorio() && p.isConFechaLimite()) {
                    
                 fecha = fechas.get(index);
                 pasoEspecifico.setFechaLimite(fecha.getDate());
                 index++;
                 } else {
                 pasoEspecifico.setFechaLimite(null);
                 }*/

                tramiteEspecifico.agregarPasoEspecifico(pasoEspecifico);
            }
            agregarTramiteEspecifico(tramiteEspecifico);

        }
        bd.cerrarConexion();
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
}
