
package database;


import java.awt.List;
import java.util.ArrayList;
import Doituong.*;


public class test {
    public static void main(String[] args) {
        ArrayList<Phong> a=new database().SelectAll("phong");
        
        System.out.println(a.get(0).getTenphong());
    }
}
