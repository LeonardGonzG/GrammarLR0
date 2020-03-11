package LR0;

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

        String aux="";
        
        if (codPlus == 0) {

            lr.row.add(new ProductionLR0(codPlus + 1, IsSave, -1, "*", lr.gramUser.get(0), false, false));
            puntero = codPlus + 1;
            
            aux=identifyAfterPoint(lr.gramUser.get(0));
        }

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

}
