/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

/**
 *
 * @author Robert
 */
public class Player {
    String name;
    int id;
    
    public Player(){
        name = "noName";
        id = 1;//defult to X?
    }
    
    public Player(int playerId){
        id = playerId;
    }
    
    public Player(String playerName, int playerId){
        name = playerName;
        id = playerId;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public int getPlay() throws Exception{
        return 0;
    }
    
}
