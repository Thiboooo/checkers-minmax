package iut.dames.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author samuel
 */
public class TestTemps {
    
    static void delay_loop(long loops, long multipl)
        {
          long d1 = multipl;
          do{
            long d0 = loops;
            do {
              --d0;
            } while (d0 >= 0);
            --d1;
          }while (d1 >=0);
        }
    
    public static void main(String[] args){
        long debut = System.nanoTime();
        delay_loop(14969400,1000);
        long fin = System.nanoTime();
        long temps = fin-debut;
        System.out.println("temps = " + temps/1_000_000.0 + "ms");
    }
}
