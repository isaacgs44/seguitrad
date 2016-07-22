package dominio;

import basedatos.BaseDatos;
import excepcion.BaseDatosException;
import java.util.ArrayList;

import excepcion.TramiteEspecificoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

public class TramiteEspecifico {

    private int idTramite;
    private ArrayList<Campo> campos;
    private ArrayList<String[]> valores;
    private ArrayList<PasoEspecifico> pasosEspecificos;
    private boolean nuevo;
    private boolean cambio;
    private BaseDatos bd;
    
    /**
     * 
     */
    public TramiteEspecifico() {
        idTramite = 0;
        campos = new ArrayList<Campo>();
        valores = new ArrayList<String[]>();
        pasosEspecificos = new ArrayList<PasoEspecifico>();
        nuevo = false;
        cambio = false;
    }

    public TramiteEspecifico(BaseDatos bd) throws BaseDatosException, SQLException {
        idTramite = 0;
        campos = new ArrayList<Campo>();
        valores = new ArrayList<String[]>();
        pasosEspecificos = new ArrayList<PasoEspecifico>();
        this.bd = bd;
        nuevo = false;
        cambio = false;
    }

    public int getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(int idTramite) {
        this.idTramite = idTramite;
    }

    /**
     * @return the valores
     */
    public ArrayList<String[]> getValores() {
        return valores;
    }

    /**
     * @return the pasosEspecificos
     */
    public ArrayList<PasoEspecifico> getPasosEspecificos() {
        return pasosEspecificos;
    }

    public void agregarCampo(Campo campo, String[] valor) {
        campos.add(campo);
        valores.add(valor);
    }

	//ADD
	public void modificarCampo(Campo campo, String[] valor) {
		int posicion = buscarCampo(campo.getNombreCampo());
		if (posicion != -1) {
			valores.set(posicion, valor);
		} else {
			agregarCampo(campo, valor);
		}
	}
	
    public void quitarCampo(int posicion) {
        campos.remove(posicion);
        valores.remove(posicion);
    }

    public int buscarCampo(String nombre) {
        // buscar el Campo en ArrayList
        for (int i = 0; i < campos.size(); i++) {
            if (campos.get(i).getNombreCampo().compareToIgnoreCase(nombre) == 0) {
                return i;
            }
        }
        return -1;
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

    public void agregarPasoEspecifico(PasoEspecifico paso) {
        pasosEspecificos.add(paso);
    }

    public void quitarPasoEspecifico() {
    }

    public boolean validarTramiteEspecifico() throws TramiteEspecificoException {
        return false;
    }
    
    public void insertarTramiteEspecifico(BaseDatos bd) throws BaseDatosException, SQLException {
        this.bd = bd;
        //insertamos campos por default
        String[] valoresTramiteesp = new String[5];
        String consulta = "INSERT INTO tramites_especificos ('idRegistro','Nombre_del_solicitante', 'Título',"
                + "'Fecha_de_inicio', 'Fecha_de_fin', 'Estado') VALUES ( " + obtenerIdRegistro("idRegistro","tramites_especificos") + ", ";
        for (int i = 0; i < 5; i++) {
            String arregloTemporal[] = valores.get(i);
            valoresTramiteesp[i] = arregloTemporal[0];
        }
        consulta += "'" + valoresTramiteesp[0] + "', '" + valoresTramiteesp[1] + "',"
                + " '" + valoresTramiteesp[2] + "', '" + valoresTramiteesp[3] + "', "
                + "'" + valoresTramiteesp[4] + "')";
        bd.realizarAccion(consulta);
        
        //insertamos valores extras
        if (campos.size() > 5) {
            int idRegistro = obtenerIdRegistro("idRegistro","tramites_especificos") - 1;
            insertarTramiteEspecificocampos(idRegistro);
        }
        
        
        //++++++ TRABAJANDO +++++++++ insertamos pasos específicos
        String consultaColumnas;
        String consultaValores;
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM);
        
        for(PasoEspecifico p : pasosEspecificos ){
            consultaColumnas = "INSERT INTO pasos_especificos ('idPasoEsp','idRegistro_tramiteEsp','num_paso'";
            consultaValores = " VALUES ( "+ obtenerIdRegistro("idPasoEsp","pasos_especificos") + "," + getIdTramite() + "," + p.getNumPaso();
            
            if(p.getFechaLimite()!=null){
                consultaColumnas += ",'fecha_limite'";
                consultaValores += ", '" + formato.format(p.getFechaLimite()) + "'";
            }
                consultaColumnas += ",'realizado')";
                consultaValores += ",'false')";
                System.out.println("Consulta: " + consultaColumnas+consultaValores);
                bd.realizarAccion(consultaColumnas+consultaValores);
        }
    }

    public int idcampo_Campo(String nombre_campo) throws BaseDatosException, SQLException {
        int idCampoTramite = 0;
        ResultSet rs = bd.realizarConsulta("select num_campo from meta_espec where nombre_campo='" + nombre_campo + "'");
        while (rs.next()) {
            idCampoTramite = rs.getInt(1);
        }
        bd.cerrarConexion();
        return idCampoTramite;
    }

    public void insertarTramiteEspecificocampos(int idRegistro) throws BaseDatosException, SQLException {
        String consulta = "INSERT INTO tramites_especificos_campos ('idCampo_IdRegistro_tramiteEspec', '"
                + "idCampo_numCampoMeta_espec','Valor')"
                + " values (" + idRegistro + ", ";
        int i = 0;
        String[] consultasInsertar=new String [campos.size()];
        for (Campo c : campos) {
            String intruccionCompleta = consulta + idcampo_Campo(c.getNombreCampo()) +" ,'"+valores.get(i)[0]+"')";
            consultasInsertar[i]=intruccionCompleta;
            i++;
        }
        for (int j = 5; j < consultasInsertar.length; j++) {
            System.out.println(consultasInsertar[j]);
            bd.realizarAccion(consultasInsertar[j]);
        }
        System.out.println("");
        System.out.println("---------TRAMITE NUEVO-------------");
        System.out.println("Nombre: " + valores.get(0)[0]);
    }

    public boolean actualizarTramiteEspecifico() {
        return false;
    }

    public boolean borrarTramiteEspecifico() {
        return false;
    }

    public boolean buscarTramiteEspecifico() {
        return false;
    }

    public int obtenerIdRegistro(String id,String tabla) throws BaseDatosException, SQLException {
        int ultimoId = 0;
        ResultSet rs;
        rs = bd.realizarConsulta("SELECT MAX(" + id + ") FROM " + tabla);
        while (rs.next()) {
            ultimoId = rs.getInt(1);
        }
        bd.cerrarConexion();
        return ++ultimoId;
    }

    public ArrayList<Campo> getCampos() {
        return campos;
    }

    public void setPasosEspecificos(ArrayList<PasoEspecifico> pasosEspecificos) {
        this.pasosEspecificos = pasosEspecificos;
    }

    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public boolean isCambio() {
        return cambio;
    }

    public void setCambio(boolean cambio) {
        this.cambio = cambio;
    }

    public void modificarTramiteEspecifico(BaseDatos bd) throws BaseDatosException, SQLException {
        System.out.println("\n--------MODIFICAR TRAMITE");
        System.out.println("Nombre: "+valores.get(0)[0]);
        eliminarTramiteEspecifico(bd);
        //insertamos registro con nuevos valores
        insertarTramiteEspecifico(bd);
    }

    public void setBd(BaseDatos bd) {
        this.bd = bd;
    }

    void eliminarTramiteEspecifico(BaseDatos bd) throws BaseDatosException {
        System.out.println("\n-------------ELIMINAR TRÁMITE---------------------");
        System.out.println("Nombre: " + valores.get(0)[0]);
        this.bd=bd;         
        String c = "DELETE FROM tramites_especificos WHERE idRegistro='"+ idTramite +"'";
        String c2 = "DELETE FROM pasos_especificos WHERE idRegistro_tramiteEsp='"+ idTramite +"'";
        String c3 = "DELETE FROM tramites_especificos_campos WHERE idCampo_IdRegistro_tramiteEspec='"+ idTramite +"'";
        
        System.out.println("----------BORRAR TRAMITE ESPECIFICO-------------");
        System.out.println("Nombre: " + this.valores.get(0)[0]);
        System.out.println("ID: " + this.idTramite);
        System.out.println("Consulta elimina tramite_esp: " + c);
        System.out.println("Consulta elimina pasos_esp: " + c2);
        System.out.println("Consulta elimina tramites_esp_campos " + c3);
        bd.realizarAccion(c);
        bd.realizarAccion(c2);
        bd.realizarAccion(c3);
    }
    
    
    
}