/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2.pkg0;

/**
 *
 * @author spodi
 */
public class Boards {

    private char[][] board;

    public Boards() {
        board = new char[8][8];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = '~';
            }
        }
    }

    public boolean canPlace(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == '~';
    }

    public void placeShip(int r, int c, char code) {
        board[r][c] = code;
    }

   public boolean attack(int r, int c) {

    if(r < 0 || r > 7 || c < 0 || c > 7) {
        System.out.println("Ataque fuera del tablero.");
        return false;
    }

    if(board[r][c] == 'o' || board[r][c] == 'X') {
        System.out.println("Ya atacaste esa posicion.");
        return false;
    }

    if(board[r][c] == '~') {
        board[r][c] = 'o';
        return false;
    } else {
        board[r][c] = 'X';
        return true;
    }
}

    public boolean hasShips() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] == 'A' || board[i][j] == 'B' ||
                   board[i][j] == 'S' || board[i][j] == 'D') {
                    return true;
                }
            }
        }
        return false;
    }

    public void printBoard(boolean showShips) {
        System.out.println("  0 1 2 3 4 5 6 7");
        for(int i = 0; i < 8; i++) {
            System.out.print(i + " ");
            for(int j = 0; j < 8; j++) {

                char cell = board[i][j];

                if(!showShips &&
                   (cell == 'A' || cell == 'B' ||
                    cell == 'S' || cell == 'D')) {
                    System.out.print("~ ");
                } else {
                    System.out.print(cell + " ");
                }
            }
            System.out.println();
        }
    }
    public int countShips() { //metodo para contar cuantos barcos van quedando
    int count = 0;

    for(int i = 0; i < 8; i++) {
        for(int j = 0; j < 8; j++) {
            if(board[i][j] == 'A' || board[i][j] == 'B' ||
               board[i][j] == 'S' || board[i][j] == 'D') {
                count++;
            }
        }
    }
    return count;
}
   
}


