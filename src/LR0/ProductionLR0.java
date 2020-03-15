/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LR0;

/**
 *
 * @author Usuario
 */
public class ProductionLR0 {
    
    public int COD;
    public int Is;
    public int Id;
    public String transition;
    public NTProduction NTComplet;
    public boolean reducied;
    public boolean checked;
    public int back;

    public ProductionLR0(int COD, int Is, int Id, String transition, NTProduction NTComplet, boolean reducied, boolean checked) {
        this.COD = COD;
        this.Is = Is;
        this.Id = Id;
        this.transition = transition;
        this.NTComplet = NTComplet;
        this.reducied = reducied;
        this.checked = checked;
        this.back=0;
    }
    
    
    
    
    
}
