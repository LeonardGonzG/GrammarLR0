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
    private ArrayList<String> myList = new ArrayList<>();

    public NTProduction(String NTComplete, String[] myList) {
        this.NT = NTComplete;

        this.myList.add(myList[2]);
        for (int i = 3; i < myList.length; i++) {

            this.myList.add(myList[i]);
            this.myList.add(" ");

        }

    }

    public NTProduction(String NTComplete, ArrayList<String> myList) {

        this.NT = NTComplete;
        this.myList = myList;

    }

    public ArrayList<String> getMyList() {
        return myList;
    }

    public String getNT() {
        return NT;
    }

    public void setMyList(ArrayList<String> myList) {
        this.myList = myList;
    }

    public void setNT(String NT) {
        this.NT = NT;
    }

}
