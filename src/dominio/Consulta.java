package dominio;

import java.util.ArrayList;

import excepcion.BaseDatosException;
import basedatos.BaseDatos;
import java.sql.SQLException;
import java.util.Date;
import java.text.DateFormat;
import java.util.StringTokenizer;
import javax.swing.table.DefaultTableModel;

public class Consulta implements Comparable<Consulta> {

    private String nombreConsulta;
    private ArrayList<Campo> campos;
    private ArrayList<String[]> valores;
    private ArrayList<TramiteEspecifico> tramitesEncontrados;
    private DefaultTableModel modelo;
    private boolean nuevo;
    private final String[] columnNames = {"Nombre", "Título",
        "Fecha de inicio", "Fecha de fin", "Estado"};

    public Consulta() {
        nombreConsulta = "";
        campos = new ArrayList<>();
        valores = new ArrayList<>();
        Object[][] data = {};
        modelo = new DefaultTableModel(data, columnNames);
        nuevo = false;
    }

    public Consulta(Consulta consulta) {
        this.nombreConsulta = consulta.getNombreConsulta();
        this.campos = new ArrayList<>();
        for (Campo campo : consulta.campos) {
            this.campos.add(campo);
        }
        this.valores = new ArrayList<>();
        for (String[] valor : consulta.valores) {
            this.valores.add(valor);
        }
        nuevo = false;
    }

    public String getNombreConsulta() {
        return nombreConsulta;
    }

    public void setNombreConsulta(String nombreConsulta) {
        this.nombreConsulta = nombreConsulta;
    }

    public void agregarCampo(Campo campo, String[] valor) {
        campos.add(campo);
        valores.add(valor);
    }

    public void quitarCampo(int posicion) {
        campos.remove(posicion);
        valores.remove(posicion);
    }

    public Campo obtenerCampo(int posicion) {
        if (posicion < 0 || posicion >= campos.size()) {
            return null;
        } else {
            return campos.get(posicion);
        }
    }

    public String[] obtenerValores(int posicion) {
        if (posicion < 0 || posicion >= valores.size()) {
            return null;
        } else {
            return valores.get(posicion);
        }
    }

    /**
     * Método para insertar una consuta en la base de datos.
     *
     * @param bd - objeto que tiene activa la conexión
     * @see Campo#getNombreCampo()
     * @throws excepcion.BaseDatosException
     */
    public void insertarConsulta(BaseDatos bd) throws BaseDatosException {
        String nombreCampo;
        String valor;
        for (int i = 0; i < campos.size(); i++) {
            nombreCampo = campos.get(i).getNombreCampo();
            valor = getCadenaValores(valores.get(i));
            bd.realizarAccion("INSERT INTO consulta VALUES("
                    + "'" + nombreConsulta + "' ,"
                    + "'" + nombreCampo + "' ,"
                    + "'" + valor + "')");
        }
    }

    /**
     * Método que ejecuta la consulta para reconocer los trámites específicos
     * con los que coinciden los criterios de búsqueda y los agrega a un
     * TableModel
     *
     * @param listaTramitesEsp objeto con la lista completa de trámites
     * específicos
     *
     * @see #validar(dominio.TramiteEspecifico, java.util.ArrayList)
     * @see Campo#getNombreCampo()
     * @see #getCadenaValores(java.lang.Object[])
     * @see TramiteEspecifico#buscarCampo(java.lang.String)
     * @see TramiteEspecifico#obtenerValores(int)
     *
     * @return TableModel para llenar el TableModel con los resultados de la
     * consulta
     *
     * @throws excepcion.BaseDatosException
     * @throws java.sql.SQLException
     *
     * @autor isaac
     */
    public DefaultTableModel ejecutarConsulta(ArrayList<TramiteEspecifico> listaTramitesEsp) throws BaseDatosException, SQLException {
        int posicion;
        ArrayList<String[]> datosTabla = new ArrayList<>();
        Object[][] data = null;
        String nombreCampo;
        String valorBuscar;
        int limite;
        tramitesEncontrados = new ArrayList<>();
        ArrayList<Boolean> resultado = new ArrayList<>(); // arreglo para comprobar los criterios de búsqueda
        for (TramiteEspecifico t : listaTramitesEsp) {
            resultado.clear();
            for (int i = 0; i < campos.size(); i++) {
                nombreCampo = campos.get(i).getNombreCampo();
                valorBuscar = getCadenaValores(valores.get(i));
                posicion = t.buscarCampo(nombreCampo);
                String[] valor = t.obtenerValores(posicion);

                switch (campos.get(i).getTipo()) {
                    case FECHA:
                        limite = valorBuscar.indexOf("/");
                        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM);
                        String fechaInferior = formato.format(Long.parseLong(valorBuscar.substring(0, limite - 1)));
                        String fechaSuperior = formato.format(Long.parseLong(valorBuscar.substring(limite + 2)));
                        Date fechaI = obtenerFecha(fechaInferior);
                        Date fechaS = obtenerFecha(fechaSuperior);
                        Date fechaV = obtenerFecha(valor[0]);
                        System.out.println(fechaI);
                        System.out.println(fechaS);
                        System.out.println(fechaV);
                        
                        if ((fechaV.before(fechaS) && fechaV.after(fechaI)) || valor[0].equals(fechaInferior) || valor[0].equals(fechaSuperior)) {
                            resultado.add(Boolean.TRUE);
                        } else {
                            resultado.add(Boolean.FALSE);
                        }
                        break;
                    case NUMERO:
                        limite = valorBuscar.indexOf("/");
                        int menor = Integer.parseInt(valorBuscar.substring(0, limite - 1));
                        int mayor = Integer.parseInt(valorBuscar.substring(limite + 2));
                        int num = Integer.parseInt(valor[0]);
                        if (num >= menor && num <= mayor) {
                            resultado.add(Boolean.TRUE);
                        } else {
                            resultado.add(Boolean.FALSE);
                        }
                        break;
                    case TEXTO:
                        if (valor[0].contains(valorBuscar)) {
                            resultado.add(Boolean.TRUE);
                        } else {
                            resultado.add(Boolean.FALSE);
                        }
                        break;
                    case OPCMULT:
                        if (valor[0].contains(valorBuscar)) {
                            resultado.add(Boolean.TRUE);
                        } else {
                            resultado.add(Boolean.FALSE);
                        }
                        break;
                    case OPCEXCL:
                        if (valor[0].contains(valorBuscar)) {
                            resultado.add(Boolean.TRUE);
                        } else {
                            resultado.add(Boolean.FALSE);
                        }
                        break;
                }
            }

            if (validar(t, resultado)) {
                datosTabla.add(valoresTramite(t));
            }
        }

        //creamos TableModel
        data = new Object[datosTabla.size()][5];
        for (int i = 0; i < datosTabla.size(); i++) {
            data[i] = datosTabla.get(i);
        }
        modelo = new DefaultTableModel(data, columnNames);
        return modelo;

    }

    /**
     * Guarda en un arreglo los valores de un trámite específico y lo añade a la
     * lista de trámites encontrados
     *
     * @param t - trámite específico
     * @return Arreglo con datos del trámite específico
     * @see TramiteEspecifico#obtenerValores(int)
     * @see TramiteEspecifico#buscarCampo(java.lang.String)
     *
     */
    private String[] valoresTramite(TramiteEspecifico t) {
        int index;

        String[] valores = new String[5];
        index = t.buscarCampo("Nombre del solicitante");
        valores[0] = t.obtenerValores(index)[0];
        index = t.buscarCampo("Título");
        valores[1] = t.obtenerValores(index)[0];
        index = t.buscarCampo("Fecha de inicio");
        valores[2] = t.obtenerValores(index)[0];
        index = t.buscarCampo("Fecha de fin");
        valores[3] = t.obtenerValores(index)[0];
        index = t.buscarCampo("Estado");
        valores[4] = t.obtenerValores(index)[0];
        tramitesEncontrados.add(t);
        return valores;
    }

    private String getCadenaValores(Object[] arreglo) {
        String cadena = "";
        if (arreglo == null || arreglo.length == 0) {
            return "";
        }
        for (int i = 0; i < arreglo.length; i++) {
            cadena += arreglo[i];
            if (i != (arreglo.length - 1)) {
                cadena += " / ";
            }
        }
        return cadena;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return nombreConsulta;
    }

    @Override
    public int compareTo(Consulta o) {
        return this.getNombreConsulta().compareToIgnoreCase(o.getNombreConsulta());
    }

    /**
     * Método para validar si un trámite se agrega a la tabla cuando se ejecuta
     * una consulta
     *
     * @param t - trámite que se esta comprobando
     * @param resultado - lista que contiene los valores booleanos de cada
     * comparación con los criterios de búsqueda
     *
     * @return - valor que define si el trámite se agregará a la tabla
     * @see TramiteEspecifico#getIdTramite()
     * @autor isaac
     *
     */
    private boolean validar(TramiteEspecifico t, ArrayList<Boolean> resultado) {
        boolean agregar = true;
        for (Boolean b : resultado) { //comprobamos que el tramite coincida con todos los criterios
            agregar = agregar && b;
        }

        for (TramiteEspecifico tramite : tramitesEncontrados) { // comprobamos que el tramite no exista actualmente en la tabla
            if (tramite.getIdTramite() == t.getIdTramite()) {
                agregar = false;
            }
        }
        return agregar;
    }

     private Date obtenerFecha(String fecha) {
        System.out.println("Fecha: " + fecha);
        StringTokenizer tokens = new StringTokenizer(fecha, "/");
        String[] datos = new String[tokens.countTokens()];
        int i = 0;
        while (tokens.hasMoreTokens()) {
            datos[i] = tokens.nextToken().trim();
            i++;
        }     
        return new Date(Integer.parseInt(datos[2])-1900, Integer.parseInt(datos[1])-1, Integer.parseInt(datos[0]));
    }

    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }   
}
