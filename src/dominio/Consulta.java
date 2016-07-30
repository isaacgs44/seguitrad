package dominio;

import java.util.ArrayList;

import excepcion.BaseDatosException;
import basedatos.BaseDatos;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class Consulta implements Comparable<Consulta> {

    private String nombreConsulta;
    private ArrayList<Campo> campos;
    private ArrayList<String[]> valores;
    private ArrayList<TramiteEspecifico> tramitesEncontrados;
    private boolean nuevo;

    public Consulta() {
        nombreConsulta = "";
        campos = new ArrayList<>();
        valores = new ArrayList<>();
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
     * @author isaac Garay
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
     * Busca en la lista de trámites específicos los trámites que coincidan con
     * los criterios.
     *
     * @param listaTramitesEsp objeto con la lista completa de trámites
     * específicos
     *
     * @see #validar(dominio.TramiteEspecifico, java.util.ArrayList, java.util.ArrayList) 
     * @see TramiteEspecifico#buscarCampo(java.lang.String)
     * @see TramiteEspecifico#obtenerValores(int)
     *
     * @return Lista de trámites que coincidieron con los criterios de búsqueda.
     *
     * @author isaac Garay
     */
    public ArrayList<TramiteEspecifico> ejecutarConsulta(ArrayList<TramiteEspecifico> listaTramitesEsp) {
        int posicion;
        String nombreCampo;
        String valorBuscar;
        int limite;
        tramitesEncontrados = new ArrayList<>();
        ArrayList<Boolean> resultado = new ArrayList<>(); // arreglo para comprobar los criterios de bÃºsqueda
        ArrayList<Boolean> resultadoRepetidos = new ArrayList<>(); // arreglo para comprobar los criterios de bÃºsqueda
        ArrayList<String> camposAnalizados = new ArrayList<>();

        for (TramiteEspecifico t : listaTramitesEsp) {
            resultado.clear();
            resultadoRepetidos.clear();
            for (int i = 0; i < campos.size(); i++) {
                nombreCampo = campos.get(i).getNombreCampo(); // nombre del campo en criterio
                valorBuscar = getCadenaValores(valores.get(i)); // valor del criterio
                posicion = t.buscarCampo(nombreCampo);
                String[] valorRegistro = t.obtenerValores(posicion); // valor de cada registro

                switch (campos.get(i).getTipo()) {
                    case TEXTO:
                        if (criterioRepetido(nombreCampo)) {
                            if (valorRegistro[0].contains(valorBuscar)) {
                                resultadoRepetidos.add(true);
                            } else {
                                resultadoRepetidos.add(false);
                            }
                        } else if (valorRegistro[0].contains(valorBuscar)) {
                            resultado.add(true);
                        } else {
                            resultado.add(false);
                        }
                        break;
                    case FECHA:
                        limite = valorBuscar.indexOf("/");
                        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM);
                        Date fechaV = null;
                        Date fechaI = null;
                        Date fechaS = null;
                        try {
                            fechaV = formato.parse(valorRegistro[0]);
                        } catch (ParseException ex) {
                        }
                        try {
                            String fechaInicial = formato.format(new Date(Long.parseLong(valorBuscar.substring(0, limite - 1))));
                            try {
                                fechaI = formato.parse(fechaInicial);
                            } catch (ParseException ex) {
                            }
                        } catch (NumberFormatException e) {
                            fechaI = fechaV;
                        }
                        try {
                            String fechaSuperior = formato.format(new Date(Long.parseLong(valorBuscar.substring(limite, valorBuscar.length()))));
                            try {
                                fechaS = formato.parse(fechaSuperior);
                            } catch (ParseException ex) {
                            }
                        } catch (NumberFormatException e) {
                            fechaS = fechaV;
                        }
                        if (fechaV != null) {
                            if (criterioRepetido(nombreCampo)) {
                                if (fechaV.compareTo(fechaS) <= 0 && fechaV.compareTo(fechaI) >= 0) {
                                    resultadoRepetidos.add(true);
                                } else {
                                    resultadoRepetidos.add(false);
                                }
                            } else if (fechaV.compareTo(fechaS) <= 0 && fechaV.compareTo(fechaI) >= 0) {
                                resultado.add(true);
                            } else {
                                resultado.add(false);
                            }
                        }
                        break;
                    case NUMERO:
                        limite = valorBuscar.indexOf("/");
                        int num = Integer.parseInt(valorRegistro[0]);
                        int mayor;
                        int menor;
                        try {
                            menor = Integer.parseInt(valorBuscar.substring(0, limite - 1));
                        } catch (NumberFormatException e) {
                            menor = num;
                        }
                        try {
                            mayor = Integer.parseInt(valorBuscar.substring(limite + 2));

                        } catch (NumberFormatException e) {
                            mayor = num;
                        }

                        if (criterioRepetido(nombreCampo)) {
                            if (num >= menor && num <= mayor) {
                                resultadoRepetidos.add(true);
                            } else {
                                resultadoRepetidos.add(false);
                            }
                        } else if (num >= menor && num <= mayor) {
                            resultado.add(true);
                        } else {
                            resultado.add(false);
                        }
                        break;
                    case OPCMULT:
                        if (criterioRepetido(nombreCampo)) {
                            if (valorRegistro[0].equals(valorBuscar)) {
                                resultadoRepetidos.add(true);
                            } else {
                                resultadoRepetidos.add(false);
                            }
                        } else if (valorRegistro[0].equals(valorBuscar)) {
                            resultado.add(true);
                        } else {
                            resultado.add(false);
                        }
                        break;
                    case OPCEXCL:
                        if (criterioRepetido(nombreCampo)) {
                            if (valorRegistro[0].contains(valorBuscar)) {
                                resultadoRepetidos.add(true);
                            } else {
                                resultadoRepetidos.add(false);
                            }
                        } else if (valorRegistro[0].contains(valorBuscar)) {
                            resultado.add(true);
                        } else {
                            resultado.add(false);
                        }
                        break;
                }
                camposAnalizados.add(nombreCampo);
            }
            camposAnalizados.clear();
            if (validar(t, resultado, resultadoRepetidos)) {
                tramitesEncontrados.add(t);
            }
        }
        return tramitesEncontrados;
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
     * Validar si un trámite cumple con los criterios. Analiza los resultados
     * booleanos de cada comparacion con los criterios de búsqueda en un trámite
     * específico.
     *
     * @param t Trámite que se esta comprobando
     * @param resultado Lista que contiene los valores booleanos de cada
     * comparación con los criterios de búsqueda que no se repiten
     * @param resultadoRepetidos Lista que contiene los valores booleanos de
     * cada comparación con los criterios de búsqueda que se repiten (con
     * valores distintos)
     *
     * @return valor que define si el trámite se agregará a la tabla
     *
     * @autor isaac Garay
     *
     */
    private boolean validar(TramiteEspecifico t, ArrayList<Boolean> resultado, ArrayList<Boolean> resultadoRepetidos) {
        boolean agregar;
        boolean agregarRep = false;
        if (resultado.size() == 0) { // si sólo hay criterios repetidos
            agregar = false;
        } else { // hay criterios que no se repiten
            agregar = true;
        }
        for (Boolean b : resultado) { //comprobamos que el tramite coincida con todos los criterios que no se repiten
            agregar &= b;
        }
        for (Boolean b : resultadoRepetidos) { //comprobamos que el tramite coincida con al menos un criterio de los que se repiten
            agregarRep |= b;
        }
        if (resultado.size() == 0) { //si no hay criteirios que no se repitan
            agregar = agregarRep;
        } else if (resultadoRepetidos.size() > 0) { // si existen criterios repetidos
            agregar &= agregarRep;
        }
        for (TramiteEspecifico tramite : tramitesEncontrados) { // comprobamos que el tramite no exista actualmente en la tabla
            if (tramite.getIdTramite() == t.getIdTramite()) {
                agregar = false;
            }
        }
        return agregar;
    }

    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public ArrayList<Campo> getCampos() {
        return campos;
    }

    public void setCampos(ArrayList<Campo> campos) {
        this.campos = campos;
    }

    public ArrayList<String[]> getValores() {
        return valores;
    }

    public void setValores(ArrayList<String[]> valores) {
        this.valores = valores;
    }

    public ArrayList<TramiteEspecifico> getTramitesEncontrados() {
        return tramitesEncontrados;
    }

    public void setTramitesEncontrados(ArrayList<TramiteEspecifico> tramitesEncontrados) {
        this.tramitesEncontrados = tramitesEncontrados;
    }

    /**
     * Determina si existen criterios repetidos en una consulta. Obtiene un
     * arreglo con los nombres de los criterios que se repiten en una consulta
     *
     * @return arreglo con los nombres de los criterios repetidos, devuelve null
     * si no estan repetidos.
     *
     * @autor isaac Garay
     *
     */
    private ArrayList<String> obtenerCriteriosRepetidos() {
        ArrayList<String> camposRepetidos = new ArrayList<>();
        String nombreCampo;
        boolean repetido = false;
        boolean bandera;
        for (int j = 0; j < campos.size(); j++) {
            bandera = true;
            nombreCampo = campos.get(j).getNombreCampo();
            for (String s : camposRepetidos) {
                if (s.equals(nombreCampo)) {
                    bandera = false;
                }
            }
            if (bandera) {
                for (int k = j + 1; k < campos.size(); k++) {
                    if (campos.get(k).getNombreCampo().equals(nombreCampo)) {
                        camposRepetidos.add(nombreCampo);
                        repetido = true;
                        break;
                    }
                }
            }
        }
        if (repetido) {
            return camposRepetidos;
        } else {
            return null;
        }
    }

    /**
     * Determina si un campo en específico se encuentra repetido en los
     * criterios de búsqueda.
     *
     * @param nombreCampo Nombre del campo que se desea analizar
     * @return true si el campos se repite, false de lo contrario.
     * @see #obtenerCriteriosRepetidos()
     *
     * @autor isaac Garay
     *
     */
    private boolean criterioRepetido(String nombreCampo) {
        ArrayList<String> criteriosRepetidos = obtenerCriteriosRepetidos();

        if (criteriosRepetidos != null) {
            for (String s : criteriosRepetidos) {
                if (s.equals(nombreCampo)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

}
