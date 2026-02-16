
package proyecto2.pkg0;

import java.util.Random;

public class Boards {

    private String[][] board;
    private Ship[][] shipReferences;
    private Ship[] ships; // Lista de todos los barcos
    private int shipCount;
    private Random random;

    public Boards() {
        board = new String[8][8];
        shipReferences = new Ship[8][8];
        ships = new Ship[10]; // Máximo 10 barcos
        shipCount = 0;
        random = new Random();
        
        clearBoard();
    }

    private void clearBoard(){
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = "~";
                shipReferences[i][j] = null;
            }
        }
    }

    public boolean canPlace(int r, int c){
        return r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c].equals("~");
    }

    public void placeShip(int r, int c, String code) {
        Ship ship = new Ship(code);
        board[r][c] = code;
        shipReferences[r][c] = ship;
        ships[shipCount++] = ship; // Guardar en la lista de barcos
    }

    public boolean attack(int r, int c){
        // Verificar límites
        if(r < 0 || r >= 8 || c < 0 || c >= 8) {
            System.out.println("Coordenada fuera del tablero");
            return false;
        }

        // Si ya fue atacado o es agua
        if(board[r][c].equals("~") || board[r][c].equals("X") || board[r][c].equals("F")) {
            board[r][c] = "F"; // Marcar fallo (desaparecerá en siguiente turno)
            return false;
        }
        
        // Si hay un barco
        if(shipReferences[r][c] != null) {
            Ship ship = shipReferences[r][c];
            
            // Reducir vida
            ship.hit();
            
            // Marcar impacto temporal
            board[r][c] = "X";
            
            return true;
        }
        
        return false;
    }

    // Método para regenerar el tablero aleatoriamente
    public void regenerateBoard(){
        // Limpiar el tablero
        clearBoard();
        
        // Recolocar cada barco que NO esté hundido
        for(int i = 0; i < shipCount; i++){
            Ship ship = ships[i];
            
            if(!ship.isSunk()) {
                // Buscar posición aleatoria válida
                boolean placed = false;
                int attempts = 0;
                
                while(!placed && attempts < 100) {
                    int r = random.nextInt(8);
                    int c = random.nextInt(8);
                    
                    if(canPlace(r, c)) {
                        board[r][c] = ship.getCode();
                        shipReferences[r][c] = ship;
                        placed = true;
                    }
                    attempts++;
                }
            }
        }
    }

    // Limpiar las F (fallos) del turno anterior
    public void clearFailMarks(){
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j].equals("F")) {
                    board[i][j] = "~";
                }
            }
        }
    }

    // Limpiar las X (impactos temporales) para regeneración
    public void clearHitMarks() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j].equals("X")) {
                    board[i][j] = "~";
                }
            }
        }
    }

    public boolean hasShips() {
        for(int i = 0; i < shipCount; i++) {
            if(!ships[i].isSunk()) {
                return true;
            }
        }
        return false;
    }

    public int countShips() {
        int count = 0;
        for(int i = 0; i < shipCount; i++) {
            if(!ships[i].isSunk()) {
                count++;
            }
        }
        return count;
    }

    public Ship getShipAt(int r, int c) {
        if(r >= 0 && r < 8 && c >= 0 && c < 8){
            return shipReferences[r][c];
        }
        return null;
    }

    public void printBoard(boolean showShips){
        System.out.println("\n  0 1 2 3 4 5 6 7");
        for(int i = 0; i < 8; i++) {
            System.out.print(i + " ");
            for(int j = 0; j < 8; j++) {

                String cell = board[i][j];

                if(!showShips && !cell.equals("X") && !cell.equals("F") && !cell.equals("~")) {
                    System.out.print("~ ");
                } else {
                    System.out.print(cell + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Mostrar estado de barcos
    public void showShipsStatus() {
        System.out.println("\n=== ESTADO DE LOS BARCOS ===");
        for(int i = 0; i < shipCount; i++) {
            Ship ship = ships[i];
            String status = ship.isSunk() ? "HUNDIDO" : "FLOTANDO";
            System.out.println(ship.getName() + " (" + ship.getCode() + "): " 
                + ship.getCurrentLife() + "/" + ship.getMaxLife() + " - " + status);
        }
        System.out.println();
    }
}