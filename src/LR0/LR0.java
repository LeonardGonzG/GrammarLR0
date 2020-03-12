package LR0;

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
    String BeginNT;

    public LR0(TableLR0 lr) {
        this.lr = lr;
    }

    public void lr0(TableLR0 lr, int codPlus, int codSave, int IsSave, int IdSave, int puntero) {

        String aux = "";

        if (codPlus == 0) {

            codPlus++; // codPlus = 1
            codSave = codPlus;

            lr.row.add(new ProductionLR0(codPlus, IsSave, -1, "*", lr.gramUser.get(0), false, false));
            puntero = codPlus + 1;

            BeginNT = lr.gramUser.get(0).getNT();

            aux = identifyAfterPoint(lr.gramUser.get(0));

            //Agregar las nuevas producciones a la table despues de ubicar la letra después del punto
            for (int x = 0; x < lr.gramUser.size(); x++) {

                if (lr.gramUser.get(x).getNT().equals(aux)) {

                    codPlus++;
                    lr.row.add(new ProductionLR0(codPlus, IsSave, -1, "*", lr.gramUser.get(x), false, false));
                    this.pointerCodEnd = codPlus;
                }

            }

            //revisar la primera produccón y hacer la transición
            for (int n = 0; n < lr.row.size(); n++) {

                if (lr.row.get(n).COD == codSave) {

                    lr.row.get(n).Id = IsSave + 1;
                    lr.row.get(n).transition = aux;
                    lr.row.get(n).checked = true;
                    lr.row.get(n).reducied = false;

                    //agregamos las nuevas producciones que pasan al siguiente estado con la transición
                    for (int x = 0; x < lr.gramUser.size(); x++) {

                        if (identifyPointNT(lr.gramUser.get(x).getMyList(), aux)) {

                            codPlus++;
                            IsSave++;
                            IdSave++;

                            //Mueve el punto de la producción y crea un nuevo NTProduccion
                            NTProduction auxP = new NTProduction(lr.gramUser.get(x).getNT(), movePoint(lr.gramUser.get(x).getMyList()));
                            lr.row.add(new ProductionLR0(codPlus, IsSave, -1, "*", auxP, false, false));

                            this.pointerCod = codPlus;
                            this.pointerId = IsSave + 1;
                        }

                    }

                }

            }//fin revisión

            //  System.out.println("Finzaliza primera parte");
            // return;
            lr0(lr, codPlus, codSave, IsSave, IdSave, this.pointerCod);

        } else if (codPlus == this.pointerCodEnd) {

            //  return;
        } else {

            //  int codSaveView= codPlus;
            for (int i = 0; i < lr.row.size(); i++) {

                if (lr.row.get(i).Is == IsSave && !lr.row.get(i).checked) {
//Inicio analzar
                    aux = identifyBeforePoint(lr.row.get(i).NTComplet); ///error

                    int opt = 0;

                    if (aux.equals("EIBP")) {

                        aux = identifyAfterPoint(lr.row.get(i).NTComplet);
                        opt = 1;
                    }
////----Fin analizar
                    if (aux.equals("$")) {

                        //Encuentra reducido
                        lr.row.get(i).transition = "*";
                        lr.row.get(i).checked = true;
                        lr.row.get(i).reducied = true;

                        lr0(lr, codPlus, codSave, IsSave - 1, IdSave, this.pointerCod + 1);

                    } else {

                        IdSave++;

                        lr.row.get(i).Id = IdSave;
                        lr.row.get(i).transition = aux;
                        lr.row.get(i).checked = true;
                        lr.row.get(i).reducied = false;

                        //Tener presente el before
                        if (opt == 1) {///Datos no hay antes del punto, toca hacer la transacción

                            codSave= codPlus+1;
                            
                            codPlus++;

                           // System.out.println("----> "+movePoint(lr.gramUser.get(i).getMyList()).toString());
                            NTProduction auxA = new NTProduction(lr.gramUser.get(i).getNT(), movePoint(lr.gramUser.get(i).getMyList()));
                            lr.row.add(new ProductionLR0(codPlus, IdSave, -1, "*", auxA, false, false));

                          
                            int IsSaveBefore = IsSave;

                            for (int q = 0; q < lr.row.size(); q++) {

                                int m = lr.row.get(q).Is;

                                if (lr.row.get(q).Is == IsSaveBefore && !lr.row.get(q).checked) {

                                    codPlus++;
                                    NTProduction auxR = new NTProduction(lr.row.get(q).NTComplet.getNT(), movePoint(lr.row.get(q).NTComplet.getMyList()));
                                    lr.row.add(new ProductionLR0(codPlus, IdSave, -1, "*", auxR, false, false));

                                }

                            }

                            String auxLetter = "";
                            for (int q = 0; q < lr.row.size(); q++) {

                                if (lr.row.get(q).Is == IdSave && !lr.row.get(q).checked) {

                                    String est = lr.row.get(q).NTComplet.getNT();
                                //    System.out.println("----> "+lr.row.get(q).NTComplet.getMyList().toString());

                                    auxLetter = identifyAfterPoint(lr.row.get(q).NTComplet);

                                    boolean isNT = isNoTerminal(auxLetter);

                                    //verificar si es un no terminal
                                    if (isNT) {

                                        //Agregar las nuevas producciones a la table despues de ubicar la letra después del punto
                                        for (int x = 0; x < lr.gramUser.size(); x++) {

                                            if (lr.gramUser.get(x).getNT().equals(auxLetter)) {

                                                codPlus++;
                                                lr.row.add(new ProductionLR0(codPlus, IdSave, -1, "*", lr.gramUser.get(x), false, false));

                                            }

                                        }

                                        System.out.println("Agregamos la tabla I02");
///Continuación de recorrido por la tabla
                                        //return;
                                        break;
                                    }

                                }
                            }

                        }///-----------------> opción con 1
                        /////Pasar a la siguiente tabla para seguir agregando tablas
                        System.out.println("OK");
                        
                        //Siguiente transición a la tabla próxima
                        int a= codPlus;
                        int b= IdSave; //IdSave
                        int c= IdSave+1;// IsSave
                        
                        
                        
                        int p = this.pointerCod;
                        
                        lr0(lr, codPlus, codSave, c, b, this.pointerCod + 1);
                        
                        return;

                    }

                    /////
                }

            }

        }
        ///Continuación de condicional

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
