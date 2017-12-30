/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.Scanner;

/**
 *
 * @author Robert
 */
public class Human extends Player{

    public Human() {
        super();
    }

    public Human(int playerId) {
        super(playerId);
    }

    public Human(String playerName, int playerId) {
        super(playerName, playerId);
    }
    
    /**
     * this will ask for the player's input via command line
     * @return 
     * it will return the location on the board that the player wanted to play (1-9)
     * it is expected that invalid (occupied cells) plays be caught in the main, not by this class
     * @throws java.lang.Exception
     */
    @Override
    public int getPlay() throws Exception{
        int row=0;
        int col=0;
        //System.out.println("pick you play");
        boolean loop = true;
        do{
            try{
                System.out.print("pick a row: ");
                row = new Scanner(System.in).nextInt();
                if (row > 3 || row < 1){
                    throw(new Exception());
                }
                System.out.print("pick a col: ");
                col = new Scanner(System.in).nextInt();
                if (col > 3 || col < 1 ){
                    throw(new Exception());
                }
                loop = false;
            }catch(Exception e){
                System.out.println("\nmust be an int between 1-3");
            }
        }while(loop);
        //System.out.println("retruning: "+((((row-1)*3)+col)-1));
        return (((row-1)*3)+col)-1;
    }
}
