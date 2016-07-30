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
import java.sql.ResultSet;
import java.util.StringTokenizer;
import lib.UtileriasArchivo;

/**
 * Clase que permite la recuperación de datos de un trámite desde la base de
 * datos.
 *
 * @author Isaac
 * @author Cresencio
 */
public class ListaTramites {

    /**
     * Permite acceder a los métodos de la clase <code>Tramite</code>
     */
    private Tramite tramite;
    /**
     * Almacena la lista de trámites específicos.
     */
    private ArrayList<TramiteEspecifico> listaTramites;
    /**
     * Almacena los trámites específicos eliminados en memoria.
     */
    private ArrayList<TramiteEspecifico> tramitesBasura;
    /**
     * Almacena los pasos específicos eliminados en memoria.
     */
    private ArrayList<PasoEspecifico> pasosBasura;
    /**
     * Contiene la lista de consultas almacenadas en la base de datos y las
     * agregadas en memoria desde el módulo de Estadísticas.
     */
    private ArrayList<Consulta> listaConsultas;
    /**
     * Permite saber si se realizaron cambios en el trámite.
     */
    private boolean hayCambios;
    /**
     * Permite acceder a los métodos de la clase <code>BaseDatos</code>
     */
    private BaseDatos bd;
    /**
     * Crea un diálogo para seleccionar el trámite con el que se trabajará.
     */
    private FileDialog fd = new FileDialog(new Frame(), "Seleccionar trámite", FileDialog.LOAD);

    /**
     *
     */
    public ListaTramites() {
        inicializar();
    }

    private void inicializar() {
        tramite = null;
        listaTramites = new ArrayList<>();
        tramitesBasura = new ArrayList<>();
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
     * @throws BaseDatosException Puede ocurrir en caso de que existiera un
     * conflicto con la base de datos (error de conexión, error de sintáxis,
     * error en inserción de datos por llaves duplicadas).
     *
     */
    public void guardarArchivo() throws BaseDatosException, SQLException {
        bd = getBd();
        // insertamos nuevas consultas...
        for (Consulta consulta : getListaConsultas()) {
            if (consulta.isNuevo()) {
                consulta.insertarConsulta(bd);
                consulta.setNuevo(false);
            }
        }

        // insertamos nuevos registros y cambios...
        for (TramiteEspecifico t : getListaTramitesEsp()) {
            if (t.isNuevo()) { // agregar nuevo trámite específico
                t.insertarTramiteEspecifico(bd);
                t.setNuevo(false);
            } else if (t.isModificar()) { // modificar trámite específico
                t.modificarTramiteEspecifico(bd);
                t.setModificar(false);
            } else if (t.isCambioEstado()) { // actualizar estado de un trámite específico
                t.actualizarEstadoTramEsp(bd);
                t.setCambioEstado(false);
            }

        }
        for (TramiteEspecifico t : getListaTramitesEsp()) {
            for (PasoEspecifico p : t.getPasosEspecificos()) {
                if (p.isNuevo()) { // en el caso de realizar un indefinido
                    p.setIdPasoEsp(obtenerIDPasoEsp());
                    p.insertarPasoEspecifico(bd, t);
                    p.setNuevo(false);
                } else if (p.isCambio()) { // al cambiar de estado
                    p.setIdPasoEsp(p.getIdPasoEsp());
                    p.modificarPasoEspecifico(bd, t);
                    p.setCambio(false);
                }
            }
        }

        if (tramitesBasura.size() > 0) { // hay registros por borrar
            for (TramiteEspecifico t : tramitesBasura) {
                t.eliminarTramiteEspecifico(bd);

                // falta eliminar documentos
            }
            tramitesBasura.clear();
        }

        if (pasosBasura.size() > 0) {
            for (PasoEspecifico p : pasosBasura) {
                p.eliminarPasoEspecifico(bd);
            }
            pasosBasura.clear();
        }

        setHayCambios(false);
    }

    /**
     * Método para guardar un trámite con otro nombre (crear una copia).
     * <p>
     * Este método crea una copia de los datos de un trámite abierto desde la
     * ventana principal y la coloca en la carpeta trámites, en dado caso de
     * haber una coincidencia con el nombre que se le quiere establecer al nuevo
     * trámite el sistema marcará error.
     * </p>
     *
     * @param nombreNuevo Almacena el nuevo nombre que se le establecerá a la
     * copia del trámite.
     * @throws excepcion.BaseDatosException Puede surgir al crear el archivo de
     * la base de datos.
     * @throws java.sql.SQLException Puede ocurrir si hay algún error de
     * sintáxis SQL.
     */
    public boolean guardarArchivoComo(String nombreNuevo) throws BaseDatosException, SQLException {
        boolean isRepetido = true;
        String[] dirOrigen;
        int i = 0;
        StringTokenizer tokens = new StringTokenizer(fd.getFile(), ".");
        dirOrigen = new String[tokens.countTokens()];
        while (tokens.hasMoreTokens()) {
            dirOrigen[i] = tokens.nextToken().trim();
            i++;
        }
        File carpeta_Original = new File("tramites" + File.separator + dirOrigen[0]);
        File carpeta_nueva = new File("tramites" + File.separator + nombreNuevo);
        if (!carpeta_nueva.exists()) {
            carpeta_nueva.mkdir();
            for (String s : carpeta_Original.list()) {
                UtileriasArchivo.copiarArchivo(fd.getDirectory() + dirOrigen[0] + File.separator + s, fd.getDirectory() + nombreNuevo + File.separator + s);
            }
            UtileriasArchivo.copiarArchivo(fd.getDirectory() + fd.getFile(), fd.getDirectory() + nombreNuevo + ".trad");
            bd = new BaseDatos(fd.getDirectory() + nombreNuevo + ".trad", false);
            guardarArchivo();
            isRepetido = false;
            return isRepetido;
        }
        return isRepetido;
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
     * @throws BaseDatosException Puede ocurrir en caso de tener conflictos de
     * conexión con la base de datos.
     * @throws TramiteException Puede ocurrir en caso de no cumplir con alguna
     * validación del trámite.
     * @throws SQLException Ocurre cuando existe error en sintáxis SQL, o error
     * en inserción de datos por duplicación de llaves.
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
            String consulta = "SELECT * FROM tramites_especificos";

            ResultSet rs = bd.realizarConsulta(consulta);
            this.listaTramites.clear();
            while (rs.next()) {
                ArrayList<String[]> valores = new ArrayList<>();
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
                int numNombre = 0;
                int numTitulo = 0;
                int numFechaInicio = 0;
                int numFechaFin = 0;
                int numEstado = 0;
                for (Campo c : campos) {
                    if (c.getNombreCampo().equals("Nombre del solicitante")) {
                        numNombre = c.getNumCampo();
                        tramiteEspecifico.agregarCampo(c, valores.get(0));
                    }
                    if (c.getNombreCampo().equals("Título")) {
                        numTitulo = c.getNumCampo();
                        tramiteEspecifico.agregarCampo(c, valores.get(1));
                    }
                    if (c.getNombreCampo().equals("Fecha de inicio")) {
                        numFechaInicio = c.getNumCampo();
                        tramiteEspecifico.agregarCampo(c, valores.get(2));
                    }
                    if (c.getNombreCampo().equals("Fecha de fin")) {
                        numFechaFin = c.getNumCampo();
                        tramiteEspecifico.agregarCampo(c, valores.get(3));
                    }
                    if (c.getNombreCampo().equals("Estado")) {
                        numEstado = c.getNumCampo();
                        tramiteEspecifico.agregarCampo(c, valores.get(4));
                    }
                }

                if (comprobarCamposAdicionales(tramiteEspecifico.getIdTramite())) {
                    String consulta2 = "select   num_campo, Valor, idRegistro,valor_defecto from meta_espec inner join "
                            + "tramites_especificos inner join tramites_especificos_campos where idCampo_numCampoMeta_espec=num_campo "
                            + "and idCampo_IdRegistro_tramiteEspec= idRegistro ";
                    ResultSet rs1 = bd.realizarConsulta(consulta2);
                    while (rs1.next()) {
                        ArrayList<String[]> valoresCamposBD = new ArrayList<>();
                        if (tramiteEspecifico.getIdTramite() == rs1.getInt("idRegistro") && numNombre != rs1.getInt("num_campo") && numTitulo != rs1.getInt("num_campo")
                                && numFechaInicio != rs1.getInt("num_campo") && numFechaFin != rs1.getInt("num_campo") && numEstado != rs1.getInt("num_campo")) {
                            valoresCamposBD.add(0, new String[1]);
                            valoresCamposBD.get(0)[0] = rs1.getString("Valor");
                            tramiteEspecifico.agregarCampo(campos.get(rs1.getInt("num_campo")), valoresCamposBD.get(0));
                        }
                    }
                }

                /*RECUPERACION DE PASOS ESPECÍFICOS*/
                String consultaPasosEsp = "select * from pasos_especificos inner join meta_paso"
                        + " where pasos_especificos.num_paso = meta_paso.num_paso "
                        + " and idRegistro_tramiteEsp = " + tramiteEspecifico.getIdTramite() + " order by num_paso";
                ResultSet resultSet = bd.realizarConsulta(consultaPasosEsp);
                int i = 0;

                while (resultSet.next()) {
                    PasoEspecifico pasoEspecifico = new PasoEspecifico();
                    if (resultSet.getInt("repeticion") >= 1) {
                        String nombrePaso = resultSet.getInt("repeticion") == 1 ? resultSet.getString("nombre_paso") : resultSet.getString("nombre_paso") + " " + i;
                        pasoEspecifico.setNombrePaso(nombrePaso);
                    } else if (resultSet.getInt("repeticion") == 0) { // indefinidos
                        pasoEspecifico.setNombrePaso("*" + resultSet.getString("nombre_paso"));
                    }

                    boolean realizado = Boolean.parseBoolean(resultSet.getString("realizado"));
                    pasoEspecifico.setRealizado(realizado);
                    try {
                        //Date d = new Date(resultSet.getString("fecha_realizacion"));
                        pasoEspecifico.setFechaRealizacion(PanelCampo.obtenerFecha(resultSet.getString("fecha_realizacion")));
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

    /**
     * Comprueba la existencia de registros adicionales de un trámite
     * específico. Este método permite saber cuando existen campos adicionales a
     * los 5 predefinidos en un trámite específico.
     *
     * @param iDRegistro Contiene el id del trámite específico para el que se
     * hara la comprobación.
     * @return verdadero en caso de haber registros, y falso de ser lo
     * contrario.
     * @throws BaseDatosException Puede surgir en caso de que haya conflicto con
     * la conexión en la base de datos.
     * @throws SQLException Puede ocurrir en caso de que la sintáxis SQL fuese
     * incorrecta.
     */
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

    public void cerrarArchivo() {
        tramite = null;
        listaTramites.clear();
        tramitesBasura.clear();
        listaConsultas.clear();
        pasosBasura.clear();
        hayCambios = false;
    }

    private int obtenerIDPasoEsp() {
        int nuevoID = 0;
        for (TramiteEspecifico t : listaTramites) {
            for (PasoEspecifico p : t.getPasosEspecificos()) {
                if (p.getIdPasoEsp() > nuevoID) {
                    nuevoID = p.getIdPasoEsp();
                }
            }
        }
        nuevoID++;
        return nuevoID;
    }

    /**
     * Método para obtener la lista de trámites específicos que fueron
     * eliminados en memoria.
     *
     * Este método se utiliza para saber que trámites eliminar al momento de
     * guardar los cambios realizados al trámite.
     *
     * @return Lista con objetos del tipo <code>TramiteEspecifico</code>.
     */
    public ArrayList<TramiteEspecifico> getTramitesBasura() {
        return tramitesBasura;
    }

    /**
     * Método para llenar la lista con trámites específicos eliminados desde
     * <code>DialogoEliminarRegistro</code>.
     *
     * @param tramitesBasura Contiene el trámite específico eliminado.
     */
    public void setTramitesBasura(ArrayList<TramiteEspecifico> tramitesBasura) {
        this.tramitesBasura = tramitesBasura;
    }

    /**
     * Método para obtener la lista de pasos específicos que fueron eliminados
     * en memoria.
     *
     * Este método se utiliza para saber que pasos específicos realizados
     * eliminar al momento de guardar los cambios realizados al trámite.
     *
     * @return Lista con objetos del tipo <code>PasoEspecifico</code>.
     */
    public ArrayList<PasoEspecifico> getPasosBasura() {
        return pasosBasura;
    }

    /**
     * Método para llenar la lista con los pasos específicos eliminados desde
     * <code>DialogoSeguimiento</code>.
     *
     * @param pasosBasura Contiene el paso específico eliminado.
     */
    public void setPasosBasura(ArrayList<PasoEspecifico> pasosBasura) {
        this.pasosBasura = pasosBasura;
    }

}
