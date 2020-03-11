
package Graphviz;

import java.io.IOException;

/**
 *
 * @author Usuario
 */
public class TestPaint {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
         Paint test = new Paint();
        
       //test.writeDocument("");
       test.loadDocument();
       System.out.println("Ok!!");
    }
    
}
