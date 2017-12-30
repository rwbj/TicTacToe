/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Robert
 */
public class GameState implements Serializable{
    private List<Integer> options = new ArrayList<>(0);
    private int[] board;
    //int[][] symmitries = new int[3][2];
    
    public GameState(){
        board = new int[]{0,0,0,0,0,0,0,0,0};
        for (int i = 0; i < 9; i++){
            if (board[i]==0){
                for (int j = 0; j < 10; j++){
                    options.add(i);
                }
            }
        }
    }
    
    public GameState(int[] curBoard){
        board = curBoard;
        for (int i = 0; i < 9; i++){
            if (board[i]==0){
                for (int j = 0; j < 10; j++){
                    options.add(i);
                }
            }
        }
    }

    public int[] getBoard() {
        return board;
    }

    public List<Integer> getOptions() {
        return options;
    }

    public void setOptions(List<Integer> options) {
        this.options = options;
    }
    
}
