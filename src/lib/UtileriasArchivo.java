/* Clase: UtileriasArchivo
 Autor: Manuel Valdés Marrero
 Fecha: 19 Enero 2004
 Descripción: Permite ... copiar archivos. */
package lib;

import java.io.*;

public class UtileriasArchivo {
	/* static boolean copiarArchivo(String origen, String destino)
	 Recibe el nombre del archivo origen con la ruta completa, y el nombre del archivo destino donde se hará la copia, con todo y ruta. 
	 Regresa un tipo File con el archivo destino, o null si ocurrió un error. Si ya existía un archivo con ese nombre se sobreescribirá. */
	public static File copiarArchivo(String origen, String destino) {
		int i;
		FileInputStream archOrigen = null;
		FileOutputStream archDestino = null;
		// Abrir archivo Origen
		try {
			archOrigen = new FileInputStream(origen);
		} catch (FileNotFoundException e) {
			return null;
		}
		// Abrir archivo Destino
		try {
			archDestino = new FileOutputStream(destino);
		} catch (FileNotFoundException e) {
			try {
				archOrigen.close();
			} catch (IOException e1) {
				return null;
			}
			return null;
		}
		// Copia el archivo byte por byte
		try {
			do {
				i = archOrigen.read();
				if (i != -1) {
					archDestino.write(i);
				}
			} while (i!= -1);
		} catch (IOException e) {
			try {
				archOrigen.close();
				archDestino.close();
			} catch (IOException e1) {
				return null;
			}
			return null;
		}
		try {
			archOrigen.close();
			archDestino.close();
		} catch (IOException e1) {
			return null;
		}
		return new File(destino);
	}
}
