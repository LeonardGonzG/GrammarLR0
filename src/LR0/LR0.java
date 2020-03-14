package LR0;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leonardo González Gutiérrez
 */
public class LR0 {

    TableLR0 lr;
    int pointerCod;
    int pointerCodEnd;
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
                    lr.row.get(n).checked = true;
                    lr.row.get(n).reducied = false;

                    this.backStates = IsSave;

                    break;
                }
            }

            lr0(lr, codPlus, codSave, this.backStates, IdSave + 1, this.pointerCod);

        } else {

            //  int codSaveView= codPlus;
            for (int i = 0; i < lr.row.size(); i++) {

                if (lr.row.get(i).Is == IsSave && lr.row.get(i).transition.equals("*")) {

                    codPlus = recursionTablaNT(codPlus, IsSave);

                    aux = identifyAfterPoint(lr.row.get(i).NTComplet);

                    if (aux.equals("$")) {

                        lr.row.get(i).Id = 9999;
                        lr.row.get(i).back = this.backStates;
                        lr.row.get(i).checked = true;
                        lr.row.get(i).reducied = true;

                        lr.row.get(i).transition = "-";

                        lr0(lr, codPlus, codSave, lr.row.get(i).back, IdSave, this.pointerCod + 1);

                    } else {
                        
                        
                        aux = identifyBeforePoint(lr.row.get(i).NTComplet);
                        
                        /*

                        this.backStates = IdSave;

                        //Nuevo estado 
                        IdSave++;

                        lr.row.get(i).Id = IdSave;
                        lr.row.get(i).transition = aux;
                        lr.row.get(i).checked = true;
                        lr.row.get(i).reducied = false;

                        //Tener presente el before
                        if (opt == 1) {///Datos no hay antes del punto, toca hacer la transacción

                            codSave = codPlus + 1;

                            this.pointerCod = codPlus + 1;

                            codPlus++;

                            // System.out.println("----> "+movePoint(lr.gramUser.get(i).getMyList()).toString());
                            NTProduction auxA = new NTProduction(lr.gramUser.get(i).getNT(), movePoint(lr.gramUser.get(i).getMyList()));

                            ProductionLR0 a = new ProductionLR0(codPlus, IdSave, -1, "*", auxA, false, false);
                            a.back = this.backStates;

                            lr.row.add(a);
                            int IsSaveBefore = this.backStates;

                            for (int q = 0; q < lr.row.size(); q++) {

                                int m = lr.row.get(q).Is;

                                if (lr.row.get(q).Is == IsSaveBefore && lr.row.get(q).transition.equals("*")) {

                                    ///verificar que tenga la misma transaccion   A->.vC, F-.>.vABx
                                    codPlus++;

                                    NTProduction auxR = new NTProduction(lr.row.get(q).NTComplet.getNT(), movePoint(lr.row.get(q).NTComplet.getMyList()));

                                    ProductionLR0 c = new ProductionLR0(codPlus, IdSave, -1, "*", auxR, false, false);
                                    c.back = this.backStates;

                                    lr.row.add(c);

                                }

                            }
                            String auxLetter = "";

                            //Tabla actual IdSave
                            for (int q = 0; q < lr.row.size(); q++) {

                                if (lr.row.get(q).COD == this.pointerCod) {

                         //    System.out.println("----> "+lr.row.get(q).NTComplet.getMyList().toString());
                                    //Miramos que hay después del punto si es NT o T
                                    auxLetter = identifyAfterPoint(lr.row.get(q).NTComplet);

                                    boolean isNT = isNoTerminal(auxLetter);

                                    //verificar si es un NT
                                    if (isNT) {

                                        //Agregar las nuevas producciones a la table despues de ubicar la letra después del punto
                                        for (int x = 0; x < lr.gramUser.size(); x++) {

                                            if (lr.gramUser.get(x).getNT().equals(auxLetter)) {

                                                codPlus++;

                                                ProductionLR0 ok = new ProductionLR0(codPlus, IdSave, -1, "*", lr.gramUser.get(x), false, false);
                                                ok.back = this.backStates;
                                                lr.row.add(ok);

                                            }

                                        }

                                        System.out.println("Tabla completada");
                         ///Continuación de recorrido por la tabla
                                        //return;

                                    }
                                    return;
                                }

                            }

                        }

                         ///Fin Opción 1
                        /////Pasar a la siguiente tabla para seguir agregando tablas
                        //---------> lr0(lr, codPlus, codSave, IdSave, this.backStates, this.pointerCod + 1);
                        return;*/

                    }

                } else if (lr.row.get(i).Is == IsSave && !lr.row.get(i).transition.equals("*")) {

                    NTProduction auxA = new NTProduction(lr.gramUser.get(i).getNT(), movePoint(lr.gramUser.get(i).getMyList()));

                    codPlus++;

                    ProductionLR0 aN = new ProductionLR0(codPlus, IdSave, 0, "*", auxA, false, false);
                    aN.back = this.backStates;

                    lr.row.add(aN);
                    this.backStates = IdSave;

                    lr0(lr, codPlus, codSave, this.backStates, IdSave + 1, this.pointerCod);

                    break;

                }

            }

        }
        ///Continuación de condicional

    }

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
                            mon.back = this.backStates;

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
    public boolean yesMovePoint(List<String> myList, String letter) {

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
    public List<String> movePoint(List<String> rowProd) {

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
            System.out.println("Transic " + lr.row.get(m).transition);
            System.out.println("Reduc " + lr.row.get(m).reducied);
            System.out.println("Producción " + lr.row.get(m).NTComplet.getNT() + " -> " + lr.row.get(m).NTComplet.getMyList().toString());
            System.out.println("Check " + lr.row.get(m).checked);
            System.out.println("--------------------------------------------------");

        }

    }

}
