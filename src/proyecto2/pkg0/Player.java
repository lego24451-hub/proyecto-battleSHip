/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2.pkg0;

/**
 *
 * @author spodi
 */
import java.util.Scanner;

public class Player {

    private String username;
    private String password;
    private int points;
    private String[] logs = new String[10];

    private Boards board;
    private Scanner sc = new Scanner(System.in);

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.points = 0;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getPoints() { return points; }

    public void setUsername(String u) { username = u; }
    public void setPassword(String p) { password = p; }

    public void addPoints(int p) { points += p; }

    public void addLog(String log) {
        for(int i = logs.length-1; i > 0; i--) {
            logs[i] = logs[i-1];
        }
        logs[0] = log;
    }

    public void showLogs() {
        for(int i = 0; i < logs.length; i++) {
            if(logs[i] != null) {
                System.out.println((i+1) + "- " + logs[i]);
            }
        }
    }

    public void showData() {
        System.out.println("Username: " + username);
        System.out.println("Points: " + points);
    }

    public Boards getBoard() {
        return board;
    }

    public void setupShips(int maxShips, boolean easyMode) {
        board = new Boards();

        int placed = 0;
        boolean usedA=false, usedB=false, usedS=false, usedD=false;

        while(placed < maxShips) {

            System.out.println(username + " - Barco #" + (placed+1));
           char code;

while(true) { // metodo de validacion para que solo se coloque lo mostrado
    System.out.print("Codigo (A,B,S,D): ");
    code = sc.next().toUpperCase().charAt(0);

    if(code == 'A' || code == 'B' || code == 'S' || code == 'D') {
        break;
    } else {
        System.out.println("Codigo invalido. Solo A, B, S o D.");
    }
}
            if(code=='A' && usedA) continue;
            if(code=='B' && usedB) continue;
            if(code=='S' && usedS) continue;
            if(code=='D' && usedD && !easyMode) continue;

            System.out.print("Fila: ");
            int r = sc.nextInt();
            System.out.print("Columna: ");
            int c = sc.nextInt();

            if(!board.canPlace(r,c)) {
                System.out.println("Posicion invalida.");
                continue;
            }

            board.placeShip(r,c,code);

            if(code=='A') usedA=true;
            if(code=='B') usedB=true;
            if(code=='S') usedS=true;
            if(code=='D') usedD=true;

            placed++;
            board.printBoard(true);
        }
    }
    //metodo para validar que se coloque la letra correcta
    public void setupShipsManual(int maxShips, boolean easyMode) {

    board = new Boards();
    int placed = 0;

    boolean usedA = false;
    boolean usedB = false;
    boolean usedS = false;
    boolean usedD = false;

    while(placed < maxShips) {

        System.out.println(username + " - Barco #" + (placed + 1));

        // ===== VALIDACION DE CODIGO =====
        char code;
        while(true) {
            System.out.print("Codigo (A,B,S,D): ");
            code = sc.next().toUpperCase().charAt(0);

            if(code == 'A' || code == 'B' || code == 'S' || code == 'D') {
                break;
            } else {
                System.out.println("Codigo incorrecto. Solo se permite A, B, S o D.");
            }
        }

        // ===== VALIDACION DE REPETIDOS =====
        if(code == 'A' && usedA) {
            System.out.println("Ya colocaste un Aircraft.");
            continue;
        }
        if(code == 'B' && usedB) {
            System.out.println("Ya colocaste un Battleship.");
            continue;
        }
        if(code == 'S' && usedS) {
            System.out.println("Ya colocaste un Submarine.");
            continue;
        }
        if(code == 'D' && usedD && !easyMode) {
            System.out.println("Ya colocaste un Destructor.");
            continue;
        }

        // ===== VALIDACION DE COORDENADAS =====
        int row, col;

        while(true) {
            System.out.print("Fila (0-7): ");
            row = sc.nextInt();

            System.out.print("Columna (0-7): ");
            col = sc.nextInt();

            if(row < 0 || row > 7 || col < 0 || col > 7) {
                System.out.println("Coordenadas fuera del tablero. Intente de nuevo.");
                continue;
            }

            if(!board.canPlace(row, col)) {
                System.out.println("Ya hay un barco en esa posicion.");
                continue;
            }

            break;
        }

        // ===== COLOCAR BARCO =====
        board.placeShip(row, col, code);

        // Marcar como usados
        if(code == 'A') usedA = true;
        if(code == 'B') usedB = true;
        if(code == 'S') usedS = true;
        if(code == 'D') usedD = true;

        placed++;

        // Mostrar tablero
        board.printBoard(true);
    }

    System.out.println(username + " ha terminado de colocar sus barcos.");
}
    public String[] getLogs() {
    return logs;
}
}


