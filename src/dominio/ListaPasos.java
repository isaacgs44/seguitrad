/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import basedatos.BaseDatos;
import java.util.ArrayList;

/**
 *
 * @author Cresenciofl
 */
public class ListaPasos {

    private ArrayList<TramiteEspecifico> listaTramiteEspecifico;
    private TramiteEspecifico tramiteEspecifico;
    private ArrayList<PasoEspecifico> listaPasoEspecifico;

    public void inicializar(TramiteEspecifico tramiteEspecifico) {
        this.tramiteEspecifico= tramiteEspecifico;
        listaPasoEspecifico = new ArrayList<>();
    }

    public void cargarPasosespecificos() {
        System.out.println("Aqui anda la cosa esta");
        for(PasoEspecifico te: listaPasoEspecifico){
            
        }

    }
}
