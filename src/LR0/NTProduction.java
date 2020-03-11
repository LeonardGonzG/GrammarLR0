/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LR0;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class NTProduction {
    
    private String NT;
    private  List<String> myList = new ArrayList<>();
    
    public NTProduction(String NTComplete, String [] myList){
        this.NT=NTComplete;
        
        for(int i=2; i<myList.length; i++){
        
            this.myList.add(myList[i]);
            this.myList.add(" ");
            
        }
    
    }

    public List<String> getMyList() {
        return myList;
    }

    public String getNT() {
        return NT;
    }

    public void setMyList(List<String> myList) {
        this.myList = myList;
    }

    public void setNT(String NT) {
        this.NT = NT;
    }
      
}
