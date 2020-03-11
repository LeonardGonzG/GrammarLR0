package LR0;

import java.util.List;

/**
 *
 * @author Usuario
 */
public class LR0 {

    TableLR0 lr;

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

            aux = identifyAfterPoint(lr.gramUser.get(0));

            //Agregar las nuevas producciones a la table despues de ubicar la letra después del punto
            for (int x = 0; x < lr.gramUser.size(); x++) {

                if (lr.gramUser.get(x).getNT().equals(aux)) {

                    codPlus++;
                    lr.row.add(new ProductionLR0(codPlus, IsSave, -1, "*", lr.gramUser.get(x), false, false));
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

                            //Mueve el punto de la producción y crea un nuevo NTProduccion
                            NTProduction auxP = new NTProduction(lr.gramUser.get(x).getNT(), movePoint(lr.gramUser.get(x).getMyList()));
                            lr.row.add(new ProductionLR0(codPlus, IsSave, -1, "*", auxP, false, false));

                        }

                    }

                }

            }//fin revisión

            System.out.println("Finzaliza primera parte");
            return;
            

        }

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

            if (i == 0) {

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

        return "Error identify after point";

    }
///-------------------------------------------------------------------------------------

    public String identifyBeforePoint(NTProduction prod) {

        int pos = 0;

        for (int i = 0; i < prod.getMyList().size(); i++) {

            if (i > 0) {

                pos = i - 1;
                return prod.getMyList().get(pos);

            }

        }

        return "Error identify before point";

    }

//View table    
    public void viewRowsTable() {

        for (int m = 0; m < lr.row.size(); m++) {

            System.out.println("COD " +lr.row.get(m).COD);
            System.out.println("Is " +lr.row.get(m).Is);
            System.out.println("Id " +lr.row.get(m).Id);
            System.out.println("Transic " + lr.row.get(m).transition);
            System.out.println("Reduc " +lr.row.get(m).reducied);
            System.out.println("Producción " +lr.row.get(m).NTComplet.getNT()+" -> "+lr.row.get(m).NTComplet.getMyList().toString());
            System.out.println("Check " +lr.row.get(m).checked);
            System.out.println("--------------------------------------------------");

        }

    }

}
