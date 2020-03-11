/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LR0;

import java.util.ArrayList;
import java.util.List;


public class TableLR0 {
    
    
    private String nameTable;
    private List<NTProduction> gramUser = new ArrayList<>();
    private ArrayList<ProductionLR0> row= new ArrayList<>();

    public TableLR0(String nameTable, List<NTProduction> mainGrammar) {
       
        this.nameTable = nameTable;
        this.gramUser= mainGrammar;
    }

    public List<NTProduction> getGramUser() {
        return gramUser;
    }

    public void setGramUser(List<NTProduction> gramUser) {
        this.gramUser = gramUser;
    }

    public ArrayList<ProductionLR0> getRow() {
        return row;
    }

    public void setRow(ArrayList<ProductionLR0> row) {
        this.row = row;
    }

    public String getNameTable() {
        return nameTable;
    }
    
    

 
    
}
