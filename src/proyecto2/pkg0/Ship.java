/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2.pkg0;

/**
 *
 * @author spodi
 */
public class Ship {
    private char code;
    private int life;

    public Ship(char code) {
        this.code = code;

        switch(code) {
            case 'A': life = 5; break;
            case 'B': life = 4; break;
            case 'S': life = 3; break;
            case 'D': life = 2; break;
        }
    }

    public char getCode() {
        return code;
    }

    public void hit() {
        life--;
    }

    public boolean isSunk() {
        return life <= 0;
    }
}


