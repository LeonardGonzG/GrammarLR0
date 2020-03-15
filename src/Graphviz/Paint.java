/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphviz;

import LR0.LR0;
import LR0.ProductionLR0;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author ASUS
 */
public class Paint {

    String point = "&bull;";
    String rowLetter = "-&gt;";
    String parentOpen = "&#40;";
    String parentClose = "&#41;";
    String reducidedProd = "<td bgcolor=\"grey\" align=\"right\">$</td>";

    public void writeDocument(LR0 table) throws IOException {
        /*
         File img = new File("C:\\Users\\Usuario\\Documents\\NetBeansProjects\\LR0\\src\\Graphviz\\File\\AutomatonGrammar.png");

         if (img.delete()) {
         System.out.println("El fichero ha sido borrado satisfactoriamente");
         } else {
         System.out.println("El fichero no puede ser borrado");
         }
         */
        
        FileWriter fichero = new FileWriter("C:\\Users\\Usuario\\Documents\\NetBeansProjects\\LR0\\src\\Graphviz\\File\\file1.dot");

        String doc= makeDocument(table);

        fichero.write(doc);
        fichero.close();
    }

    public void loadDocument() {

        try {

//path del dot.exe,por lo general es la misma, pero depende de donde hayas instalado el paquete de Graphviz
            String dotPath = "C:\\graphviz-2.38\\bin\\dot.exe";

//path del archivo creado con el codigo del graphviz que queremos
            String fileInputPath = "C:\\Users\\Usuario\\Documents\\NetBeansProjects\\LR0\\src\\Graphviz\\File\\file1.dot";

//path de salida del grafo, es decir el path de la imagen que vamos a crear con graphviz
            String fileOutputPath = "C:\\Users\\Usuario\\Documents\\NetBeansProjects\\LR0\\src\\Graphviz\\File\\AutomatonGrammar.png";

//tipo de imagen de salida, en este caso es jpg
            String tParam = "-Tjpg";

            String tOParam = "-o";

//concatenamos nuestras direcciones. Lo que hice es crear un vector, para poder editar las direcciones de entrada y salida, usando las variables antes inicializadas
//recordemos el comando en la consola de windows: C:\Archivos de programa\Graphviz 2.21\bin\dot.exe -Tjpg grafo1.txt -o grafo1.jpg Esto es lo que concatenamos en el vector siguiente:
            String[] cmd = new String[5];
            cmd[0] = dotPath;
            cmd[1] = tParam;
            cmd[2] = fileInputPath;
            cmd[3] = tOParam;
            cmd[4] = fileOutputPath;

//Invocamos nuestra clase 
            Runtime rt = Runtime.getRuntime();

//Ahora ejecutamos como lo hacemos en consola
            rt.exec(cmd);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
    }

    public String makeDocument(LR0 t) {

        String m = "digraph g {graph [fontsize=30 labelloc=\"t\" label=\"\" splines=true overlap=false rankdir = \"LR\"];ratio = auto;" + "\n";

        int numStatesTable=numStates(t);
        
        //construir nodos
        int contCodEnd = 0;

        String nameState = "";

        while (contCodEnd <= numStatesTable) {

            nameState = "state" + contCodEnd;
            m += "\"" + nameState + "\" [ style = \"filled, bold\" penwidth = 2 fillcolor = \"white\" fontname = \"Courier New\" shape = \"Mrecord\" label =<" + "\n";
            m += "<table border=\"0\" cellborder=\"0\" cellpadding=\"3\" bgcolor=\"white\">" + "\n";
            m+="<tr><td bgcolor=\"black\" align=\"center\" colspan=\"2\"><font color=\"white\">State #"+contCodEnd+"</font></td></tr>";
            for (int i = 0; i < t.lr.row.size(); i++) {

                if (t.lr.row.get(i).Is == contCodEnd) {

                    m += makeProdState(t.lr.row.get(i));
                }

            }
            m += "</table>> ];" + "\n" + "\n";
            contCodEnd++;
        }

        for (int x = 0; x < t.lr.row.size(); x++) {

            if (t.lr.row.get(x).Id > 0 && t.lr.row.get(x).Id < 9999) {
                String trans = "state" + t.lr.row.get(x).Is + " -> " + "state" + t.lr.row.get(x).Id + " "
                        + "[ penwidth = 1 fontsize = 14 fontcolor = \"black\" label = \"" + t.lr.row.get(x).transition + "\" ];" + "\n";
                m += trans;

            }
        }

        m += "}";

        return m;

    }

    public String makeProdState(ProductionLR0 m) {

        String prod = "<tr><td align=\"left\" port=\"r0\">";

        prod += parentOpen + m.COD + parentClose + " " + m.NTComplet.getNT() + rowLetter;

        for (int k = 0; k < m.NTComplet.getMyList().size(); k++) {

            if (m.NTComplet.getMyList().get(k).equals(".")) {

                prod += point;
            } else {

                prod += m.NTComplet.getMyList().get(k);
            }
        }

        prod += "</td>";

        if (m.reducied) {
            prod += reducidedProd;

        }
        prod += "</tr>"+"\n";

        return prod;

    }

    public int numStates(LR0 t) {

        int v = 0;

        for (int c = 0; c < t.lr.row.size(); c++) {
            if (t.lr.row.get(c).Is > v) {
                v = t.lr.row.get(c).Is;
            }

        }
        
        return v;

    }

}
