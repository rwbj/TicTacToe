/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author Robert
 */
public class AIMind extends Player{

    private File gsFolder;
    int turn = 0;
    private GameState gs[] = new GameState[5];
    private int plays[] = new int[5];

    public File getGSFolder() {
        return gsFolder;
    }

    public void setGSFolder(String newGSFolder) {
        Path relPath = Paths.get("");
        String strRelPath = relPath.toAbsolutePath().toString();
        //System.out.println("Current relative path is: " + strRelPath);
        gsFolder = new File(strRelPath+'\\'+newGSFolder);
        if(!gsFolder.exists()||!gsFolder.isDirectory()){//creates directory if nonexistatn
            gsFolder.mkdir();
        }
    }
    
    public AIMind(){
        super();
        this.setGSFolder("AImemory");
        //gs = new GameState[]{new GameState(),new GameState(),new GameState(),new GameState(),new GameState()};
    }

    public AIMind(int playerId){
        super(playerId);
        this.setGSFolder("AImemory");
    }

    public AIMind(String playerName, int playerId) {
        super(playerName, playerId);
        this.setGSFolder("AImemory");
    }
    
    /**
     * this will reset the turn count to 0 and saves the game states
     * @param adjust the amount to change the GS.options by
     * 0 = no adjustment
     * recommendation:
     *      win = 3
     *      tie = 1
     *      lose = -1
     */
    public void endGame(int adjust) throws IOException{
        List<Integer> op;
        for(int i = 0; i < turn ;i++){//for each Game State played
            op = gs[i].getOptions();
            if(adjust!=0){
                if(adjust>0){
                    for (int j = 0; j<adjust; j++){//adds adjust amount of played int
                        op.add(plays[i]);
                    }
                }
                else if(adjust<0){//remove for negitive adjust
                    adjust = Math.abs(adjust);
                    for (int j = 0; j<adjust; j++){
                        if(op.contains(plays[i])){
                            op.remove(op.indexOf(plays[i]));
                        }
                    }
                }
                gs[i].setOptions(op);
                writeGS(genFileName(gs[i].getBoard()), gs[i]);
            }
        }
        clearData();
    }
    
    public void clearData(){
        turn = 0;
        gs = new GameState[5];
        plays = new int[5];
    }

    //@Override
    public int getPlay(int[][] board2d) throws Exception {
        int board[] = convBoard(board2d);
        /*GameState curGS = getGS(board);
        int rng = new Random().nextInt(curGS.options.length);
        return curGS.options[rng];//*/
        int cell;
        cell = canWin(board);
        if(cell!=-1){return cell;}
        cell = canBlock(board);
        if(cell!=-1){return cell;}
        gs[turn] = getGS(board);//saves the Game State to return at end game
        int listLength = gs[turn].getOptions().size();
        Random rng = new Random();
        boolean loop = true;
        while(loop){
            int rNum = gs[turn].getOptions().get(rng.nextInt(listLength));
            if (board[rNum]==0){
                cell = rNum;
                loop = false;
            }
        }
        plays[turn] = cell;
        turn++;
        return cell;
    }
    
    public int getRNGPlay(int[][] board2d) throws Exception {
        int board[] = convBoard(board2d);
        int cell;
        cell = canWin(board);
        if(cell!=-1){return cell;}
        cell = canBlock(board);
        if(cell!=-1){return cell;}
        Random rng = new Random();
        boolean loop = true;
        while(loop){
            int rNum = rng.nextInt(9);
            if (board[rNum]==0){
                cell = rNum;
                loop = false;
            }
        }
        turn++;
        return cell;
    }
    
    /**
     * converts a board into the file naming format
     * use this to:
     *      create new file name and
     *      find existing files by name
     * @param board
     * @return the String file name according to the current board
     */
    private String genFileName(int[] board){
        String fileName = "p" + this.id + "gs";
        for (int b:board){
            fileName = fileName + Integer.toString(b);
        }
        fileName = fileName + ".dat";
        return fileName;
    }
    
    /**
     * checks the existence of a file by file name (within folder)
     * @param fileName : String
     * @return Boolean
     * if file in folder: true
     * if file not in folder: false
     */
    private boolean gsExists(String fileName){
        //file = genFileName(board);//file should be a var of calcMove()
        boolean b = false;
        File f = new File(gsFolder.getAbsolutePath()+"\\"+fileName);
        if (f.exists()){
            b=true;
            try{
                if (!f.canRead()||!f.canWrite()){b=false;}
                if (!f.isFile()){b=false;}
            }catch(Exception e){System.out.println(e.getMessage());}//*/
          }
        return b;
    }
    
    /**
     * finds and reads in the needed gs.dat file as a GameState
     * @param fileName : String
     * @return currentGS : GameState 
     */
    public GameState readGS(String fileName) throws FileNotFoundException, IOException{
        fileName = gsFolder.getPath()+"\\"+fileName;
        GameState retGS = new GameState();
        ObjectInputStream ois;
        try{
            ois = new ObjectInputStream(new FileInputStream(fileName));
            retGS = (GameState)ois.readObject();
            ois.close();
        }catch(Exception e){System.out.println(e.getMessage());}
        return retGS;
    }
    
    private void writeGS(String fileName, GameState saveGS) throws FileNotFoundException, IOException{
        fileName = gsFolder.getPath()+"\\"+fileName;
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
        oos.writeObject(saveGS);
    }
    
    /**
     * this will create a new Game State
     * best used when that Game State file doesn't exist (call gsExists first)
     * @param board
     * @return 
     */
    private GameState createGS(int[] board, String fileName) throws FileNotFoundException, IOException{
        return new GameState(board);
    }
    
    /**
     * will get saved or create new GameState depending on what is needed
     * @param board
     * @return current game state : GameState
     */
    private GameState getGS(int[] board) throws IOException{
        String fn = genFileName(board);
        GameState curGS = new GameState(board);
        if (gsExists(fn)){
            curGS = readGS(fn);
        }
        else{
            curGS = createGS(board,fn);
        }
        return curGS;
    }
    
    private int[] convBoard(int[][] board){
        int count = 0;
        int retBoard[] = new int[9];
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                retBoard[count] = board[i][j];
                count++;
            }
        }
        return retBoard;
    }
    
    /**
     * checks if and where a winning move is
     * @param board, player ID to check for
     * @return int -1 if no winning move, 0-8 if winning move (the winning play)
     */
    private int aPlayerCanWin(int[] board, int playerCheck, String message){
        int emptyCell = -1;//if empty cell found{ if emCell==-1{set emCell = cell index}else break because more than 1 empty cell}
        //col
        for (int i = 0; i<3;i++){
            emptyCell = -1;
            try{
                for (int j = i; j<9 ; j = j+3){
                    if (board[j]==0){//empty cell
                        if(emptyCell==-1){//is first empty cell
                            emptyCell=j;
                        }
                        else{
                            throw new Exception();
                        }
                    }
                    else if(board[j]!=playerCheck){
                        throw new Exception();
                    }
                }
            }catch(Exception e){emptyCell=-1;}
            if (emptyCell!=-1){
                System.out.println(message);
                return emptyCell;
            }
        }
        //row
        for (int i = 0;i<9;i=i+3){
            try{
                for (int j = i;j<(i+3);j++){
                    if (board[j]==0){//empty cell
                        if(emptyCell==-1){//is first empty cell
                            emptyCell=j;
                        }
                        else{
                            throw new Exception();
                        }
                    }
                    else if(board[j]!=playerCheck){
                        throw new Exception();
                    }
                }
            }catch(Exception e){emptyCell=-1;}
            if (emptyCell!=-1){
                System.out.println(message);
                return emptyCell;
            }
        }
        //diag top to bottom (left to right)
        try{
            for (int j = 0;j<9;j=j+4){//hits cells 0,4,8
                if (board[j]==0){//empty cell
                    if(emptyCell==-1){//is first empty cell
                        emptyCell=j;
                    }
                    else{
                        throw new Exception();
                    }
                }
                else if(board[j]!=playerCheck){
                    throw new Exception();
                }
            }
        }catch(Exception e){emptyCell=-1;}
        if (emptyCell!=-1){
            System.out.println(message);
            return emptyCell;
        }
        //diag bottom to top
        try{
            for (int j = 2;j<7;j=j+2){//hits cells 2,4,6
                if (board[j]==0){//empty cell
                    if(emptyCell==-1){//is first empty cell
                        emptyCell=j;
                    }
                    else{
                        throw new Exception();
                    }
                }
                else if(board[j]!=playerCheck){
                    throw new Exception();
                }
            }
        }catch(Exception e){emptyCell=-1;}
        if (emptyCell!=-1){
            System.out.println(message);
            return emptyCell;
        }
        return emptyCell;
    }
    
    private int canWin(int[] board){
        return aPlayerCanWin(board,this.id,"AI found winning move");
    }
    
    private int canBlock(int[] board){
        int otherId = 2;
        if (this.id==2){
            otherId = 1;
        }
        return aPlayerCanWin(board,otherId,"AI found blocking move");
    }

}
