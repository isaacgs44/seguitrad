package basedatos;

import java.io.File;
import java.sql.*;

import excepcion.BaseDatosException;

public class BaseDatos {
    
    private static final int numTablas = 7; //FIXME
    private Connection conexion;
    private Statement instruccion;
    private String controlador;
    private String url;
    private String user;
    private String pass;
    private String ruta;
    private String directorio;
    public BaseDatos(String nombreArchivo, boolean esNueva) throws BaseDatosException {
        String nombreDirectorio = nombreArchivo.substring(0, nombreArchivo.lastIndexOf('.'));
        File dir = null;
        File archivo = null;
        if (!esNueva) {
            dir = new File(nombreDirectorio);
            archivo = new File(nombreArchivo);
            // validar que exista la bd y la carpeta		
            if (!dir.exists() || !archivo.exists()) {
                throw new BaseDatosException(BaseDatosException.ERROR_ABRIRBD);
            }
            ruta = nombreArchivo;
            directorio = nombreDirectorio;
            controlador = "org.sqlite.JDBC";
            url = "jdbc:sqlite:" + ruta;
            user = "";
            pass = "";
            // validar que sea bd del sistema revisando los nombres de las tablas
            String[] tablas = obtenerTablas();
            boolean esValida = true;
            int contador = 0;
            for (String tabla : tablas) {
                switch (tabla) {
                    case "general":
                    case "meta_espec":
                    case "meta_paso":
                    case "consulta":
                    case "tramites_especificos":
                    case "tramites_especificos_campos":
                    case "pasos_especificos":
                        contador++;
                        break;
                    default:
                        esValida = false;
                        break;
                }
            }
            if (!esValida || contador != numTablas) {
                throw new BaseDatosException(BaseDatosException.ERROR_BDDESCONOCIDA);
            }
        } else {
            dir = new File("tramites" + File.separator + nombreDirectorio);
            archivo = new File("tramites" + File.separator + nombreArchivo);
            // validar que no exista la bd y la carpeta		
            if (dir.exists() || archivo.exists()) {
                throw new BaseDatosException(BaseDatosException.ERROR_CREARBD);
            }
            dir.mkdir();
            ruta = "tramites" + File.separator + nombreArchivo;
            directorio = "tramites" + File.separator + nombreDirectorio;
            controlador = "org.sqlite.JDBC";
            url = "jdbc:sqlite:" + ruta;
            user = "";
            pass = "";
        }
        instruccion = null;
    }

    private void realizarConexion() throws BaseDatosException {
        try {
            Class.forName(controlador);
            conexion = DriverManager.getConnection(url, user, pass);
        } catch (SQLException excepcionSQL) {
            throw new BaseDatosException(excepcionSQL.getMessage());
        } catch (ClassNotFoundException claseNoEncontrada) {
            throw new BaseDatosException(BaseDatosException.ERROR_CONTROLADOR);
        }
    }

    public void cerrarConexion() throws BaseDatosException {
        try {
            if (instruccion != null) {
                instruccion.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException excepcionSQL) {
            throw new BaseDatosException(excepcionSQL.getMessage());
        }
    }

    public ResultSet realizarConsulta(String consulta) throws BaseDatosException {
        try {
            realizarConexion();
            instruccion = null;
            instruccion = conexion.createStatement();
            ResultSet resultados = instruccion.executeQuery(consulta);
            return resultados;
        } catch (SQLException excepcionSQL) {
            throw new BaseDatosException(excepcionSQL.getMessage());
        }
    }

    public boolean realizarAccion(String accion) throws BaseDatosException {
        try {
            realizarConexion();
            instruccion = null;
            instruccion = conexion.createStatement();
            int dato = instruccion.executeUpdate(accion);
            if (dato >= 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException excepcionSQL) {
            throw new BaseDatosException(excepcionSQL.getMessage());
        } finally {
            cerrarConexion();
        }
    }

    private String[] obtenerTablas() throws BaseDatosException {
        String[] nombres = null;
        // bloque para obtener los nombres de las tablas que tiene la BD abierta
        try {
            realizarConexion();
            String[] tipotabla = {"TABLE"};
            // de la base de datos se obtiene todo lo referente a sus tablas
            ResultSet nomTablas = conexion.getMetaData().getTables(null, null, null, tipotabla);
            // determinar cuantas tablas son
            int contador = 0;
            while (nomTablas.next()) {
                contador++;
            }
            nombres = new String[contador];
            nomTablas = conexion.getMetaData().getTables(null, null, null, tipotabla);
            // ciclo para leer los nombres de cada tabla, que se encuentra en el campo 3 de cada registro
            nomTablas.next();
            for (int i = 1; i <= contador; i++) {
                nombres[i - 1] = nomTablas.getString(3);
                nomTablas.next();
            }
        } catch (SQLException e) {
            throw new BaseDatosException(e.getMessage());
        } finally {
            cerrarConexion();
        }
        // regresa arreglo con los nombres de las tablas
        return nombres;
    }

    public void crearTablas() throws BaseDatosException {
        String instruccion;
        // crear tabla general
        instruccion = "CREATE TABLE general ("
                + "nombre_tramite VARCHAR(100) UNIQUE NOT NULL PRIMARY KEY, "
                + "departamento_area VARCHAR(100) NOT NULL, "
                + "descripcion VARCHAR(300) NULL, "
                + "nombre_archivo VARCHAR(50) UNIQUE NOT NULL, "
                + "dias_alerta NUMERIC NOT NULL)";
        realizarAccion(instruccion);
        // crear tabla meta_espec
        instruccion = "CREATE TABLE meta_espec ("
                + "nombre_campo VARCHAR(100) UNIQUE NOT NULL PRIMARY KEY, "
                + "num_campo NUMERIC UNIQUE NOT NULL, "
                + "obligatorio BOOLEAN NOT NULL, "
                + "tipo_campo VARCHAR(20) NOT NULL, "
                + "valor_defecto VARCHAR(200) NULL)";
        realizarAccion(instruccion);
        // crear tabla meta_paso
        instruccion = "CREATE TABLE meta_paso ("
                + "nombre_paso VARCHAR(100) UNIQUE NOT NULL PRIMARY KEY, "
                + "num_paso NUMERIC UNIQUE NOT NULL, "
                + "responsable VARCHAR(50) NOT NULL, "
                + "con_fecha_limite BOOLEAN NOT NULL, "
                + "con_plantilla BOOLEAN NOT NULL, "
                + "plantilla VARCHAR(100) NULL, "
                + "con_documento BOOLEAN NOT NULL, "
                + "con_cambio_estado BOOLEAN NOT NULL, "
                + "estado VARCHAR(50) NULL, "
                + "secuencia NUMERIC NULL, "
                + "obligatorio BOOLEAN NOT NULL, "
                + "con_repeticion BOOLEAN NOT NULL, "
                + "repeticion NUMERIC NULL)";
        realizarAccion(instruccion);
        //Falta
        // crear tabla tramites especificos y pasos especificos
        instruccion = "CREATE TABLE tramites_especificos ("
                + "idRegistro NUMERIC UNIQUE NOT NULL, Nombre_del_solicitante VARCHAR(100) NOT NULL, "
                + "Título VARCHAR(100) NOT NULL, "
                + "Fecha_de_inicio VARCHAR(100) NOT NULL,"
                + "Fecha_de_fin VARCHAR(100) NOT NULL,"
                + "Estado VARCHAR(100) NOT NULL)";
        realizarAccion(instruccion);

        //crear tabla tramites especificos_campos
        instruccion = "CREATE TABLE tramites_especificos_campos ("
                + "idCampo_IdRegistro_tramiteEspec NUMERIC,"
                + "idCampo_numCampoMeta_espec NUMERIC,"
                + "Valor VARCHAR(100) )";
        realizarAccion(instruccion);

        // crear tabla consultas
        instruccion = "CREATE TABLE consulta ("
                + "nombre_consulta VARCHAR(100) NOT NULL, "
                + "nombre_campo VARCHAR(100) NOT NULL, "
                + "valores_campo VARCHAR(100) NOT NULL )";
        realizarAccion(instruccion);
        
        
        instruccion = "CREATE TABLE pasos_especificos ( " +
        "idPasoEsp NUMERIC UNIQUE NOT NULL," +
        "idRegistro_tramiteEsp NUMERIC NOT NULL," +
        "num_paso NUMERIC NOT NULL," +
        "fecha_limite VARCHAR(100)," +
        "fecha_realizacion VARCHAR(100), " +
        "documento VARCHAR(100)," +
        "realizado BOOLEAN" +
        ")";
        realizarAccion(instruccion);
    }

    public void eliminarRegistroTramite() throws BaseDatosException {
        realizarAccion("DELETE FROM general");
        realizarAccion("DELETE FROM meta_espec");
        realizarAccion("DELETE FROM meta_paso");
    }

    public String[] obtenerGeneral() throws BaseDatosException {
        try {
            String[] registroGeneral = new String[5];
            ResultSet resultados = realizarConsulta("SELECT nombre_tramite, departamento_area, descripcion, nombre_archivo, dias_alerta FROM general");
            if (resultados == null) {
                registroGeneral[0] = "";
                registroGeneral[1] = "";
                registroGeneral[2] = "";
                registroGeneral[3] = "";
                registroGeneral[4] = "1";
            } else {
                resultados.next();
                registroGeneral[0] = resultados.getString(1);
                registroGeneral[1] = resultados.getString(2);
                registroGeneral[2] = resultados.getString(3);
                registroGeneral[3] = resultados.getString(4);
                registroGeneral[4] = String.valueOf(resultados.getInt(5));
            }
            return registroGeneral;
        } catch (SQLException e) {
            throw new BaseDatosException(e.getMessage());
        } finally {
            cerrarConexion();
        }
    }
     
    public String getDirectorio() {
        return directorio + File.separator;
    }
     
    public String[] obtenerNumCampos() throws BaseDatosException {
        try {
            String[] numCampos = null;
            ResultSet resultados = realizarConsulta("SELECT count(num_campo) FROM meta_espec");
            resultados.next();
            numCampos = new String[resultados.getInt(1)];
            resultados.close();
            int i = 0;
            resultados = realizarConsulta("SELECT num_campo FROM meta_espec");
            while (resultados.next()) {
                numCampos[i] = String.valueOf(resultados.getInt(1));
                i++;
            }
            return numCampos;
        } catch (SQLException e) {
            throw new BaseDatosException(e.getMessage());
        } finally {
            cerrarConexion();
        }
    }

    public String[] obtenerCampo(int numCampo) throws BaseDatosException {
        try {
            String[] registroCampo = new String[4];
            ResultSet resultados = realizarConsulta("SELECT nombre_campo, obligatorio, tipo_campo, valor_defecto FROM meta_espec WHERE num_campo = '" + numCampo + "'");
            if (resultados == null) {
                registroCampo[0] = "";
                registroCampo[1] = "false";
                registroCampo[2] = "null";
                registroCampo[3] = "";
            } else {
                resultados.next();
                registroCampo[0] = resultados.getString(1);
                registroCampo[1] = resultados.getString(2);
                registroCampo[2] = resultados.getString(3);
                registroCampo[3] = resultados.getString(4);
            }
            return registroCampo;
        } catch (SQLException e) {
            throw new BaseDatosException(e.getMessage() + " aqui");
        } finally {
            cerrarConexion();
        }
    }

    public String[] obtenerNumPasos() throws BaseDatosException {
        try {
            String[] numPasos = null;
            ResultSet resultados = realizarConsulta("SELECT count(num_paso) FROM meta_paso");
            resultados.next();
            numPasos = new String[resultados.getInt(1)];
            resultados.close();
            int i = 0;
            resultados = realizarConsulta("SELECT num_paso FROM meta_paso ORDER BY num_paso");
            while (resultados.next()) {
                numPasos[i] = String.valueOf(resultados.getInt(1));
                i++;
            }
            return numPasos;
        } catch (SQLException e) {
            throw new BaseDatosException(e.getMessage());
        } finally {
            cerrarConexion();
        }
    }

    public String[] obtenerPaso(int numPaso) throws BaseDatosException {
        try {
            String[] registroPaso = new String[12];
            ResultSet resultados = realizarConsulta("SELECT nombre_paso, responsable, con_fecha_limite, "
                    + "con_plantilla, plantilla, con_documento, con_cambio_estado, estado, "
                    + "secuencia, obligatorio, con_repeticion, repeticion FROM meta_paso WHERE num_paso = '" + numPaso + "'");
            if (resultados == null) {
                registroPaso[0] = "";
                registroPaso[1] = "Propio";
                registroPaso[2] = "false";
                registroPaso[3] = "false";
                registroPaso[4] = "";
                registroPaso[5] = "true";
                registroPaso[6] = "false";
                registroPaso[7] = "";
                registroPaso[8] = "";
                registroPaso[9] = "true";
                registroPaso[10] = "false";
                registroPaso[11] = "1";
            } else {
                resultados.next();
                registroPaso[0] = resultados.getString(1);
                registroPaso[1] = resultados.getString(2);
                registroPaso[2] = resultados.getString(3);
                registroPaso[3] = resultados.getString(4);
                registroPaso[4] = resultados.getString(5);
                registroPaso[5] = resultados.getString(6);
                registroPaso[6] = resultados.getString(7);
                registroPaso[7] = resultados.getString(8);
                registroPaso[8] = String.valueOf(resultados.getInt(9));
                registroPaso[9] = resultados.getString(10);
                registroPaso[10] = resultados.getString(11);
                registroPaso[11] = String.valueOf(resultados.getInt(12));
            }
            return registroPaso;
        } catch (SQLException e) {
            throw new BaseDatosException(e.getMessage());
        } finally {
            cerrarConexion();
        }
    }

    public int numRegistrosMeta_espec() throws SQLException, BaseDatosException {
        ResultSet rs1 = realizarConsulta("select count(*) from meta_espec");
        int var = 0;
        while (rs1.next()) {
            var = rs1.getInt(1);
        }
        cerrarConexion();
        return var;
    }

   
}