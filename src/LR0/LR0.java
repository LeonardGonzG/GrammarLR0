package LR0;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leonardo González Gutiérrez
 */
public class LR0 {

    public TableLR0 lr;
    int pointerCod;
    public int pointerCodEnd;
    int pointerId;
    int backStates;
    String BeginNT;

    public LR0(TableLR0 lr) {
        this.lr = lr;
    }

    public void lr0(TableLR0 lr, int codPlus, int codSave, int IsSave, int IdSave, int puntero) {

        String aux = "";

        if (codPlus == 0) {

            codPlus++; // codPlus = 1

            lr.row.add(new ProductionLR0(codPlus, IsSave, -1, "*", lr.gramUser.get(0), false, false));

            this.pointerCod = codPlus;

            codPlus = recursionTablaNT(codPlus, IsSave);

            //revisar la primera produccón y hacer la transición con la primera producción
            for (int n = 0; n < lr.row.size(); n++) {

                if (lr.row.get(n).COD == this.pointerCod) {

                    aux = identifyAfterPoint(lr.row.get(n).NTComplet);

                    lr.row.get(n).Id = IsSave + 1;
                    lr.row.get(n).transition = aux;

                    this.backStates = IsSave;

                    break;
                }
            }

            lr0(lr, codPlus, codSave, this.backStates, IdSave + 1, this.pointerCod);

        } else {

            //  int codSaveView= codPlus;
            for (int i = 0; i < lr.row.size(); i++) {
                
                
//                //Estados completos revisados, volvel al estado anterior y revisar producciones
//                if(lr.row.get(i).Is == IsSave && stateComplete(lr.row.get(i).Is)){
//                    
//                    
//                     System.out.println("OK!!!!");   
//                     lr0(lr, codPlus, codSave, lr.row.get(i).back, IdSave, this.pointerCod + 1);
//                
//                     return;
//                }
                
                
                

                if (lr.row.get(i).Is == IsSave && lr.row.get(i).transition.equals("*")) {

                    aux = identifyAfterPoint(lr.row.get(i).NTComplet);

                    if (aux.equals("$")) {

                        codPlus = recursionTablaNT(codPlus, IsSave);

                        lr.row.get(i).Id = 9999;
                        lr.row.get(i).back = this.backStates;
                        lr.row.get(i).checked = true;
                        lr.row.get(i).reducied = true;

                        lr.row.get(i).transition = "-";

                        if (stateComplete(lr.row.get(i).Is)) {

                            int m = lr.row.get(i).back;

//                            viewRowsTable();
//                            System.out.println("............................................");
                            lr0(lr, codPlus, codSave, lr.row.get(i).back, IdSave, this.pointerCod + 1);
                        } else {

//                            viewRowsTable();
//                            System.out.println("............................................");
                            lr0(lr, codPlus, codSave, this.backStates, IdSave + 1, this.pointerCod);

                        }

                    } else {
                        
                        if(!verifyStateExist(lr.row.get(i))){ //Verifica si hay una producción igual, sino es así sigue a generar los nuevos estados
                        
                            lr.row.get(i).Id = IdSave;
                           // lr.row.get(i).back = this.backStates;
                            lr.row.get(i).checked = true;
                            lr.row.get(i).reducied = false;
                            lr.row.get(i).transition = aux;

                            //Miramos primero que hay antes del punto                   
                            ArrayList<String> copia = (ArrayList<String>) lr.row.get(i).NTComplet.getMyList();
                            NTProduction auxA = new NTProduction(lr.row.get(i).NTComplet.getNT(), movePoint((ArrayList<String>) copia.clone()));

                            this.backStates = IsSave;

                            codPlus++;

                            ProductionLR0 aN = new ProductionLR0(codPlus, IdSave, -3333, "*", auxA, false, false);
                            aN.back = this.backStates;
                            lr.row.add(aN);

                            String m = identifyAfterPoint(auxA);
                            //Agregar estados después del punto
                            codPlus = NTAfterPointProd(codPlus, m, IdSave, this.backStates);

                            lr0(lr, codPlus, codSave, IdSave, IdSave + 1, this.pointerCod);
                      
                            return;
                        
                        
                       }
                        
                        

                            

                      
                    }

                    break;

                }

                if (lr.row.get(i).Is == IsSave && !lr.row.get(i).transition.equals("*") && !lr.row.get(i).checked) {

                    String nt = lr.row.get(i).NTComplet.getNT();
                    String mmm = lr.row.get(i).NTComplet.getMyList().get(1);

                    lr.row.get(i).checked = true;

                    ArrayList<String> copia = (ArrayList<String>) lr.row.get(i).NTComplet.getMyList();
                    NTProduction auxA = new NTProduction(lr.row.get(i).NTComplet.getNT(), movePoint((ArrayList<String>) copia.clone()));

                    this.backStates = IsSave;
                    
                    codPlus++;

                    ProductionLR0 aN = new ProductionLR0(codPlus, IdSave, -22222, "*", auxA, false, false);
                    aN.back = this.backStates;

                    lr.row.add(aN);

//                    viewRowsTable();
//                    System.out.println("............................................");
                    lr0(lr, codPlus, codSave, IsSave + 1, IdSave + 1, this.pointerCod);

                    break;

                }

                if (lr.row.get(i).Is == IsSave && !lr.row.get(i).transition.equals("*") && lr.row.get(i).checked && lr.row.get(i).Id < 0) {
//-----------------

                    ArrayList<String> copia = (ArrayList<String>) lr.row.get(i).NTComplet.getMyList();
                    NTProduction auxA = new NTProduction(lr.row.get(i).NTComplet.getNT(), movePoint((ArrayList<String>) copia.clone()));

                    this.backStates = IsSave;

                    codPlus++;

                    ProductionLR0 aN = new ProductionLR0(codPlus, IdSave, -111111, "*", auxA, false, false);
                    aN.back = this.backStates;

                    lr.row.add(aN);

                    int proxState = IdSave;
//
//                    viewRowsTable();
//                   System.out.println("............................................");
                    lr0(lr, codPlus, codSave, proxState, IdSave + 1, this.pointerCod);

                    return;
                }

            }

        }
        ///Continuación de condicional

    }

    public boolean verifyStateExist(ProductionLR0 NT) {

        for (int i = 0; i < lr.row.size(); i++) {

            if (NT.NTComplet.getNT().equals(lr.row.get(i).NTComplet.getNT())
                    && lr.row.get(i).checked
                    && NT.transition.equals(lr.row.get(i).transition)) {

                boolean exist = compareProduction(NT.NTComplet.getMyList(), lr.row.get(i).NTComplet.getMyList());

                ////MODIFICAR DATOS DE LAS PRODUCCIONES QUE APUN
                
                if(exist){
                
                   
                    
                    for(int m=0; m<lr.row.size(); m++){
                    
                    
                        if(lr.row.get(m).COD==NT.COD){
                        
                            lr.row.get(m).Id = lr.row.get(i).Id;
                            lr.row.get(m).checked = true;
                            
                            return true;
                        }
                    
                    }
                    
                    
                    
                }
                
            }

        }

        return false;

    }

    public boolean compareProduction(ArrayList<String> a, ArrayList<String> b) {

        if (a.size() != b.size()) {
            return false;

        } else {

            int cont = 0;

            for (int x = 0; x < a.size(); x++) {

                if (a.get(x).equals(b.get(x))) {
                    cont++;

                }

            }

            if (cont == a.size()) {
                return true;

            }

        }

        return false;

    }

    public int NTAfterPointProd(int codPlus, String NT, int IsSave, int back) {

        int proxCodPlus = codPlus;

        for (int m = 0; m < lr.gramUser.size(); m++) {

            if (lr.gramUser.get(m).getNT().equals(NT)) {

                codPlus++;

                ProductionLR0 aN = new ProductionLR0(codPlus, IsSave, -3333, "*", lr.gramUser.get(m), false, false);
                aN.back = back;

                lr.row.add(aN);

            }

        }

        for (int k = 0; k < lr.row.size(); k++) {

            if (proxCodPlus >= lr.row.get(k).COD) {

                String aux = identifyBeforePoint(lr.row.get(k).NTComplet);

                for (int m = 0; m < lr.gramUser.size(); m++) {

                    if (lr.gramUser.get(m).getNT().equals(aux)) {

                        codPlus++;

                        ProductionLR0 aN = new ProductionLR0(codPlus, IsSave, -3333, "*", lr.gramUser.get(m), false, false);
                        aN.back = back;

                        lr.row.add(aN);

                        proxCodPlus++;

                    }

                }
            }

        }

        return codPlus;

    }

    public int recursionTablaBefore(int codPlus, int back, int IsSave, String let) {

        for (int g = 0; g < lr.row.size(); g++) {

            String aux = identifyBeforePoint(lr.row.get(g).NTComplet);

            if (lr.row.get(g).Is == back && aux.equals(let)) {

                ArrayList<String> copia = (ArrayList<String>) lr.row.get(g).NTComplet.getMyList();
                NTProduction auxA = new NTProduction(lr.row.get(g).NTComplet.getNT(), movePoint((ArrayList<String>) copia.clone()));

                codPlus++;

                ProductionLR0 aN = new ProductionLR0(codPlus, IsSave, 0, "*", auxA, false, false);
                aN.back = back;

                lr.row.add(aN);

            }

        }

        return codPlus;

    }

    ///Completos los estados con la marca revisado
    public boolean stateComplete(int Is) {

        int contBool = 0;
        int cont = 0;

        for (int i = 0; i < lr.row.size(); i++) {

            if (lr.row.get(i).Is == Is) {

                cont++;
                if (lr.row.get(i).checked) {

                    contBool++;
                }

            }
        }

        if (contBool == cont) {
            return true;

        }

        return false;

    }

    ///Llenar tabla con NT que se producen
    public int recursionTablaNT(int codPlus, int IsSave) {

        //Agregar las nuevas producciones a la table despues de ubicar la letra después del punto
        for (int g = 0; g < lr.row.size(); g++) {

            String aux = identifyAfterPoint(lr.row.get(g).NTComplet);
            boolean isToNT = isNoTerminal(aux);

            if (isToNT) {
                if (lr.row.get(g).Is == IsSave) {

                    for (int x = 0; x < lr.gramUser.size(); x++) {

                        if (lr.gramUser.get(x).getNT().equals(aux)) {

                            codPlus++;

                            ProductionLR0 mon = new ProductionLR0(codPlus, IsSave, -1, "*", lr.gramUser.get(x), false, false);
                            mon.back = lr.row.get(g).back;///Se actualizó

                            lr.row.add(mon);
                            this.pointerCodEnd = codPlus;
                        }
                    }
                }

            }

        }

        return codPlus;

    }

    /// ---------- Verificar si hay un .T o NT para pasarlo    
    public boolean yesMovePoint(ArrayList<String> myList, String letter) {

        for (int x = 0; x < myList.size(); x++) {

            int postBefore = x - 1;

            if (myList.get(x).equals(letter) && myList.get(postBefore).equals(".")) {

                return true;

            }

        }

        return false;

    }
    //Identifica si hay un NT o T después del punto
    ///-------------------------------------------------------------------

    public boolean identifyPointNT(List<String> rowProd, String letter) {

        for (int i = 0; i < rowProd.size(); i++) {

            int a = i + 1;

            if (rowProd.get(i).equals(".") && rowProd.get(a).equals(letter)) {

                return true;
            }
        }

        return false;

    }

    ////-----------------------------------------------------------------------
    public ArrayList<String> movePoint(ArrayList<String> rowProd) {

        for (int i = 0; i < rowProd.size(); i++) {

            if (rowProd.get(i).equals(".")) {

                rowProd.set(i, " ");
                int aux = i + 2;
                rowProd.set(aux, ".");

                return rowProd;
            }

        }

        return rowProd;

    }

    ///----------------------------------------------------------------------------
    public String identifyAfterPoint(NTProduction prod) {

        int pos = 0;

        for (int i = 0; i < prod.getMyList().size(); i++) {

            if (prod.getMyList().get(i).equals(".") && i == 0) {

                return prod.getMyList().get(1);

            } else if (prod.getMyList().get(i).equals(".")) {

                if (i == (prod.getMyList().size() - 1)) {

                    return "$";

                } else {

                    pos = i + 1;

                    return prod.getMyList().get(pos);
                }

            }

        }

        return "EIAP";

    }
    ///-------------------------------------------------------------------------------------

    public String identifyBeforePoint(NTProduction prod) {

        int pos = 0;

        for (int i = 0; i < prod.getMyList().size(); i++) {

            String let = prod.getMyList().get(i);
            if (i > 0 && prod.getMyList().get(i).equals(".")) {

                int posEnd = prod.getMyList().size() - 1;

                if (i == posEnd && prod.getMyList().get(i).equals(".")) {
                    return "$";

                } else {
                    pos = i - 1;
                    return prod.getMyList().get(pos);

                }

            }

        }

        return "EIBP";

    }
    //---------------Verify that letter is a No terminal

    public boolean isNoTerminal(String letter) {

        for (int x = 0; x < lr.gramUser.size(); x++) {

            if (letter.equals(lr.gramUser.get(x).getNT())) {
                return true;

            }
        }

        return false;

    }

    //View table    
    public void viewRowsTable() {

        for (int m = 0; m < lr.row.size(); m++) {

            System.out.println("COD " + lr.row.get(m).COD);
            System.out.println("Is " + lr.row.get(m).Is);
            System.out.println("Id " + lr.row.get(m).Id);
            System.out.println("Back " + lr.row.get(m).back);
            System.out.println("Transic " + lr.row.get(m).transition);
            System.out.println("Reduc " + lr.row.get(m).reducied);
            System.out.println("Producción " + lr.row.get(m).NTComplet.getNT() + " -> " + lr.row.get(m).NTComplet.getMyList().toString());
            System.out.println("Check " + lr.row.get(m).checked);
            System.out.println("--------------------------------------------------");

        }

    }

}
