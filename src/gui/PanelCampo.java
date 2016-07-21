package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.toedter.calendar.JDateChooser;

import lib.JIntegerTextField;
import dominio.Campo;
import java.util.StringTokenizer;

/**
 * Panel que muestra los valores, el nombre y el tipo de cada campo de un
 * trámite específico.
 *
 * <p>
 * Si el trámite es nuevo los campos no se le han asignado ningún valor o
 * contienen los valores por defecto. Los campos de textos se muestran vacíos o
 * con los valores por defecto. Las fechas se muestran con la fecha de la
 * computadora siempre que su valor por defecto sea fecha actual, si la fecha no
 * tiene ningún valor por defecto el campo de fecha está vacío. Las opciones
 * excluyentes (comboBox) están seleccionadas en la primera posición. Las
 * opciones múltiples (lista) no tienen ningún valor seleccionada. Los campos de
 * texto tipo número están vacíos o tienen asignado un valor por defecto.
 * </p>
 * Si el trámite se va a modificar los campos y valores que se muestran son los
 * que ya tiene asignado el trámite específico.
 *
 * @author jesus
 *
 */
public class PanelCampo extends JPanel {

    private static final long serialVersionUID = -3353935489561706446L;
    /**
     * Guarda los componentes de un trámite específico dependiendo del tipo de
     * campo que pertenece
     * (<code>FECHA</code>,<code> NUMERO</code>,<code> TEXTO</code>,
     * <code> OPCEXCL</code>,<code> OPCMULT</code>).
     */
    private ArrayList<Object> componentes;
    /**
     * Guarda temporalmente los campos que contiene un trámite específico.
     */
    private ArrayList<Campo> campos;
    /**
     * Contiene los componenes que se visualizan en la pantalla.
     */
    private JPanel panelContenedor;
    /**
     * Guarda los valores que contiene cada campo del trámite específico.
     */
    private ArrayList<String[]> valores;

    /**
     * Constructor que es utilizado cuando el trámite específico es nuevo,
     * muestra los valores de cada campo por default.
     *
     * @param campos Todo los campos que contiene un trámite.
     */
    public PanelCampo(ArrayList<Campo> campos) {
        this(campos, null);
    }

    /**
     * Constructor que es utilizado cuando se quiere modificar un trámite
     * específico, muestra los valores que se le asignó a cada campo. Manda a
     * llamar al método inicializar.
     *
     * @param campos Todo los campos que contiene un trámite.
     * @param valores Los valores que contiene cada campo.
     * @see #inicializar()
     */
    public PanelCampo(ArrayList<Campo> campos, ArrayList<String[]> valores) {
        this.campos = campos;
        this.valores = valores;
        inicializar();
    }

    /**
     * <p>
     * Agrega al panel los componentes de un trámite específico dependiendo del
     * tipo de campo:
     * <code>FECHA</code>,<code> NUMERO</code>,<code> TEXTO</code>,
     * <code> OPCEXCL</code>,<code> OPCMULT</code>.
     * </p>
     * <p>
     *
     * Se recorre todos los campos de un trámite específico, y se va verificando
     * de qué tipo es cada campo. Todos los campos contienen dos componentes, la
     * etiqueta donde se ve el nombre del campo y el tipo de campo donde se
     * ingresan los valores del campo. Si el trámite específico es nuevo se
     * muestran los valores por default, si el trámite específico se va a
     * modificar, se muestran los valores que se le asignaron a cada campo. Si
     * el campo es obligatorio se muestra una etiqueta que contiene un * en
     * color rojo.
     *
     * </p>
     *
     * @see #colocarEtiquetaObligatorio(boolean).
     * @see #imprimirLabels(String).
     */
    private void inicializar() {
        componentes = new ArrayList<Object>();
        JTextField nuevoTexto;
        JIntegerTextField nuevoNumero;
        JComboBox<String> nuevoComboBox;
        JList<String> nuevaLista;
        DefaultListModel<String> nuevoModelo;
        JScrollPane scrollLista;
        JDateChooser nuevaFecha;
        String[] valoresCombo;
        String[] valoresLista;
        this.setLayout(new GridLayout(campos.size(), 1));
        int index = 0;
        int aux;
        if (valores != null) {
            aux = valores.size();
        } else {
            aux = campos.size();
        }

        for (int k = 0; k < aux; k++) {
            Campo c = campos.get(k);
            panelContenedor = new JPanel();
            panelContenedor.setLayout(new FlowLayout(FlowLayout.LEFT));
            colocarEtiquetaObligatorio(c.isObligatorio());
            imprimirLabels(c.getNombreCampo());
            switch (c.getTipo()) {
                case TEXTO:
                    nuevoTexto = new JTextField();
                    nuevoTexto.setPreferredSize(new Dimension(200, 30));
                    if (valores == null) {
                        nuevoTexto.setText(c.getValorDefecto());
                    } else {
                        nuevoTexto.setText(valores.get(index)[0]);
                    }
                    componentes.add(nuevoTexto);
                    panelContenedor.add(nuevoTexto);
                    this.add(panelContenedor);
                    break;
                case NUMERO:
                    nuevoNumero = new JIntegerTextField(0);
                    nuevoNumero.setPreferredSize(new Dimension(200, 30));
                    if (valores == null) {
                        nuevoNumero.setText(c.getValorDefecto());
                    } else {
                        nuevoNumero.setText(valores.get(index)[0]);
                    }
                    componentes.add(nuevoNumero);
                    panelContenedor.add(nuevoNumero);
                    this.add(panelContenedor);
                    break;
                case FECHA:
                    nuevaFecha = new JDateChooser();
                    nuevaFecha.setPreferredSize(new Dimension(200, 30));
                    if (valores == null) {
                        if (c.getValorDefecto().equals("Fecha actual")) {
                            nuevaFecha.setDate(new Date());
                        }
                    } else {
                        nuevaFecha.setDate(obtenerFecha(valores.get(index)[0]));
                    }
                    componentes.add(nuevaFecha);
                    panelContenedor.add(nuevaFecha);
                    this.add(panelContenedor);
                    break;
                case OPCEXCL:
                    nuevoComboBox = new JComboBox<String>();
                    nuevoComboBox.setPreferredSize(new Dimension(200, 30));
                    valoresCombo = c.getValorDefectoOpciones();
                    for (int i = 0; i < valoresCombo.length; i++) {
                        nuevoComboBox.addItem(valoresCombo[i]);
                    }
                    if (valores == null) {
                        nuevoComboBox.setSelectedItem(valoresCombo[0]);
                    } else {

                        for (int i = 0; i < valoresCombo.length; i++) {
                            if (valoresCombo[i].equals(valores.get(index)[0])) {
                                nuevoComboBox.setSelectedItem(valoresCombo[i]);
                            }
                        }
                    }
				//ADD
				if (c.getNombreCampo().equalsIgnoreCase("Estado")) {
					nuevoComboBox.setEnabled(false);
				}
				//Fin ADD
                    panelContenedor.add(nuevoComboBox);
                    componentes.add(nuevoComboBox);
                    this.add(panelContenedor);
                    break;
                case OPCMULT:
                    valoresLista = c.getValorDefectoOpciones();
                    nuevoModelo = new DefaultListModel<>();
                    nuevaLista = new JList<>(nuevoModelo);
                    nuevaLista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                    scrollLista = new JScrollPane();
                    scrollLista.setViewportView(nuevaLista);
                    scrollLista.setPreferredSize(new Dimension(200, 50));
                    for (int i = 0; i < valoresLista.length; i++) {
                        nuevoModelo.addElement(valoresLista[i]);
                    }
                    if (valores != null) {
                        //------------------------------------------

//                        StringTokenizer tokens = new StringTokenizer(valores.get(index)[0], "/");
//                        opciones = new String[tokens.countTokens()];
//            		while (tokens.hasMoreTokens()) {
//                        	opciones[posicion] = tokens.nextToken().trim();
//                		posicion++;
//                        }

                        char[] arreglo = valores.get(index)[0].toCharArray();
                        int cont = 0;
                        ArrayList<Integer> separacion = new ArrayList<>();
                        for (int j = 0; j < arreglo.length; j++) {
                            if (arreglo[j] == '/') {
                                separacion.add(j);
                                cont++;
                            }
                        }
                        System.out.println("contador: " + cont);
                        String[] valoresIndices = new String[cont + 1];
                        int inicio = 0;
                        int fin = 0;

                        System.out.println("Valor: " + valores.get(index)[0]);
                        System.out.println("Tamaño separacion: " + separacion.size());
                        for (int z = 0; z < separacion.size(); z++) {
                            System.out.println("indices: " + separacion.get(z));
                            fin = separacion.get(z) - 1;
                            valoresIndices[z] = valores.get(index)[0].substring(inicio, fin);
                            inicio = separacion.get(z) + 2;
                            if (z == separacion.size() - 1) {
                                inicio = separacion.get(z) + 2;
                                fin = valores.get(index)[0].length();
                                valoresIndices[++z] = valores.get(index)[0].substring(inicio, fin);
                            }
                        }

                        System.out.println("-------------------Tamaño INDICES: " + valoresIndices.length);
                        for (int j = 0; j < valoresIndices.length; j++) {
                            System.out.println(j + ".- INDICE: " + valoresIndices[j]);
                        }

                        //-----------------------------
                        String seleccion[] = valoresIndices;
                        int[] indices = new int[seleccion.length];
                        for (int i = 0; i < valoresLista.length; i++) {
                            for (int j = 0; j < seleccion.length; j++) {
                                if (valoresLista[i].equals(seleccion[j])) {
                                    indices[j] = i;
                                }
                            }
                            nuevaLista.setSelectedIndices(indices);
                        }
                    }
                    panelContenedor.add(scrollLista);
                    componentes.add(nuevaLista);
                    this.add(panelContenedor);
                    break;
            }
            index++;
        }
    }

    /**
     * Devuelve todo los componentes que contiene un trámite específico.
     *
     * @return Lista de componentes guardados.
     */
    public ArrayList<Object> getComponentes() {
        return componentes;
    }

    /**
     * Agrega las etiquetas con el nombre del campo correspondiente.
     *
     * @param nombreCampo Nombre del campo guardado.
     */
    private void imprimirLabels(String nombreCampo) {
        JLabel nuevoLabel = new JLabel(nombreCampo);
        nuevoLabel.setPreferredSize(new Dimension(200, 30));
        panelContenedor.add(nuevoLabel);
    }

    /**
     * Comprueba si el campo es obligatorio, si lo es agrega un * en color rojo.
     *
     * @param esObligatorio true si es obligatorio, false si no es obligatorio.
     */
    private void colocarEtiquetaObligatorio(boolean esObligatorio) {
        JLabel nuevoLabel = new JLabel();
        if (esObligatorio) {
            nuevoLabel.setText("*");
        } else {
            nuevoLabel.setText(" ");
        }
        nuevoLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        nuevoLabel.setForeground(Color.RED);
        nuevoLabel.setPreferredSize(new Dimension(10, 30));
        panelContenedor.add(nuevoLabel);
    }

    public static Date obtenerFecha(String fecha) {
        try{
        System.out.println("Fecha: " + fecha);
        StringTokenizer tokens = new StringTokenizer(fecha, "/");
        String[] datos = new String[tokens.countTokens()];
        int i = 0;
        while (tokens.hasMoreTokens()) {
            datos[i] = tokens.nextToken().trim();
            i++;
        }
        return new Date(Integer.parseInt(datos[2]) - 1900, Integer.parseInt(datos[1]) - 1, Integer.parseInt(datos[0]));
    }
        catch(NullPointerException e){
            return null;
        }
    }
}
