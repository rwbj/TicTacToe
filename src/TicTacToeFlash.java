/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.io.IOException;
import java.util.Scanner;
/*import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;//*///all the test imports

/**
 *
 * @author Robert
 */
public class TicTacToeFlash {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        //runHvH();
        //runHvRNGAI();
        runHvAI();
        gsFileReader();
    }
    
    private static void runHvAI() throws Exception{
        Board b = new Board();
        System.out.print("player 1 enter name: ");
        Human p = new Human(new Scanner(System.in).nextLine(),1);
        System.out.print("player 2 enter name: ");
        AIMind ai = new AIMind(new Scanner(System.in).nextLine(),2);
        System.out.print("use custom AI memory folder? (yes/no)");
        if (new Scanner(System.in).nextLine().charAt(0) == 'y'){
            System.out.print("enter AI memory folder name: ");
            ai.setGSFolder(new Scanner(System.in).nextLine());
        } else{System.out.println("using defautl folder ("+ai.getGSFolder().getName()+")");}
        boolean loop = true;
        while(loop){
            int turn = 0;
            boolean noWinner = true;
            while(!b.isOver()){
                b.printBoard();
                if (turn%2==0){//player's turn
                    System.out.println(p.getName()+"\'s turn");
                    int val = 1;
                    int pos = 0;
                    while(val!=0){
                        pos = p.getPlay();
                        val = b.getCell(pos);
                        if (val!=0){System.out.println("that cell is occupied");}
                    }
                    b.setCell(pos,p.id);
                    //b.printBoard();
                    turn++;
                    noWinner = !b.isOver();
                }
                else{//ai's turn
                    System.out.println(ai.getName()+"\'s turn");
                    int val = 1;
                    int pos = 0;
                    //while(val!=0){
                    pos = ai.getPlay(b.getBoard());
                    val = b.getCell(pos);
                    if (val!=0){System.out.println("that cell is occupied");}
                    //}
                    b.setCell(pos,ai.id);
                    //b.printBoard();
                    turn++;
                    noWinner = !b.isOver();
                }
            }
            b.printBoard();
            if (b.getWinner()==0){//tie
                System.out.println("cat's game");
                ai.endGame(1);
            }
            else if (b.getWinner()==p.id){//player wins
                System.out.println(p.name+" won!");
                ai.endGame(-1);
            }
            else{//ai wins
                System.out.println(ai.name+" won!");
                ai.endGame(3);
            }
            
            ai.clearData();
            b.clearBoard();
            
            System.out.print("keep playing? (yes/no)");
            if (new Scanner(System.in).nextLine().charAt(0) == 'n'){
                loop = false;
            }
        }
    }
    
    private static void gsFileReader() throws IOException{
        System.out.println("reading Game State options list...");
        AIMind ai = new AIMind();
        System.out.print("\n\nuse custom AI memory folder? (yes/no)");
        if (new Scanner(System.in).nextLine().charAt(0) == 'y'){
            System.out.print("enter AI memory folder name: ");
            ai.setGSFolder(new Scanner(System.in).nextLine());
        } else{System.out.println("using defautl folder ("+ai.getGSFolder().getName()+")");}
        boolean loop = true;
        while(loop){
            System.out.print("enter gs file name: ");
            GameState gs = ai.readGS(new Scanner(System.in).nextLine());
            System.out.println("the current list of options are:\n"+
                    gs.getOptions().toString());
            System.out.print("another file? (yes/no)");
            if (new Scanner(System.in).nextLine().charAt(0) == 'n'){
                loop = false;
            }
        }
    }
    
    private static void runHvH() throws Exception{
        Board b = new Board();
        System.out.print("player 1 enter name: ");
        Human p1 = new Human(new Scanner(System.in).nextLine(),1);
        System.out.print("player 2 enter name: ");
        Human p2 = new Human(new Scanner(System.in).nextLine(),2);
        Human p = p1; //this will not work for AI ==> needs to refer to AI object directly!
        int turn = 0;
        while(!b.isOver()){
            b.printBoard();
            if (turn%2==0){p = p1;}
            else{p = p2;}
            System.out.println(p.getName()+"\'s turn");
            int val = 1;
            int pos = 0;
            while(val!=0){
                pos = p.getPlay();
                val = b.getCell(pos);
                if (val!=0){System.out.println("that cell is occupied");}
            }
            b.setCell(pos,p.id);
            //b.printBoard();
            turn++;
        }
        b.printBoard();
        b.printWinner();
        if (b.getWinner()==0){System.out.println("cat's game");}
        else{System.out.println(p.name+" won!");}
    }
    
    /*private static void testAI() throws Exception{
        AIMind ai = new AIMind(1);
        int boardArr[][] = {{2,0,0},{0,2,0},{0,1,0}};
        Board b = new Board(boardArr);
        b.printBoard();
        int cw = ai.getRNGPlay(b.getBoard());
        System.out.println(cw);//*//*
        
        int adjust = -1;
        List op = new GameState().getOptions();
        System.out.println(op.toString());
        if(adjust<0){//remove for negitive adjust
            adjust = Math.abs(adjust);
            System.out.println(adjust);
            for (int j = 0; j<adjust; j++){
                if(op.contains(2)){
                    op.remove(op.indexOf(j));
                }
            }
        }
        System.out.println(op.toString());
    }//*/
    
    private static void runHvRNGAI() throws Exception{
    Board b = new Board();
        System.out.print("player 1 enter name: ");
        Human p = new Human(new Scanner(System.in).nextLine(),1);
        System.out.print("player 2 enter name: ");
        AIMind ai = new AIMind(new Scanner(System.in).nextLine(),2);
        //Human p = p1; //this will not work for AI ==> needs to refer to AI object directly!
        int turn = 0;
        while(!b.isOver()){
            b.printBoard();
            if (turn%2==0){//player's turn
                System.out.println(p.getName()+"\'s turn");
                int val = 1;
                int pos = 0;
                while(val!=0){
                    pos = p.getPlay();
                    val = b.getCell(pos);
                    if (val!=0){System.out.println("that cell is occupied");}
                }
                b.setCell(pos,p.id);
                //b.printBoard();
                turn++;
            }
            else{//ai's turn
                System.out.println(ai.getName()+"\'s turn");
                int val = 1;
                int pos = 0;
                while(val!=0){
                    pos = ai.getRNGPlay(b.getBoard());
                    val = b.getCell(pos);
                    if (val!=0){System.out.println("that cell is occupied");}
                }
                b.setCell(pos,ai.id);
                //b.printBoard();
                turn++;
            }
        }
        b.printBoard();
        b.printWinner();
        if (b.getWinner()==0){System.out.println("cat's game");}
        //else{System.out.println(p.name+" won!");}
    }
    
    /*private static void testZone(){
        int initalGrid[][] = {{0,1,0},{2,1,0},{2,1,2}};
        Board b = new Board(initalGrid);
        b.printBoard();//*/
        
        /*String fileName = "p" + 1 + "gs";
        int board[] = {2,0,0,1,2,0,2,1,0};
        for (int b:board){
            fileName = fileName + Integer.toString(b);
        }
        fileName = fileName + ".dat";
        System.out.println(fileName);//*/
        
        /*String test = "notest";
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s + '\\' + test);
        File f = new File(s + '\\' + test);
        System.out.println(f.getAbsolutePath());
        if (f.exists()){System.out.println("found test");}//*/
        
        /*List<Integer> options = new ArrayList<>(0);
        System.out.println(options.size());
        for (int i = 0; i<10; i++){
            options.add(1);
        }
        System.out.println(options.size());//*/
        
        //while(true){
            /*System.out.println("enter a number: ");
            int a = new Scanner(System.in).nextInt()-1;
            int z = a/3;
            int j = a - (z*3);
            //System.out.println("x/3 = " + z);
            System.out.println("row " + z + ", colom " + j);//*/
            //turnXHuman();
            //turnOHuman();
            /*System.out.println("entet a row:");
            int i = new Scanner(System.in).nextInt();
            System.out.println("entet a colom:");
            int j = new Scanner(System.in).nextInt();
            int refNum = (i*3)+j+1;
            System.out.println("refNum = " + refNum);
        }
    }//*/
    
}
