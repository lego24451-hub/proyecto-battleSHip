package proyecto2.pkg0;

import java.util.Scanner;

public class Battleship {

    private Player[] players = new Player[20];
    private int playerCount = 0;
    private Player currentUser = null;

    private Scanner sc = new Scanner(System.in);

    private int difficulty = 4;
    private String difficultyName = "NORMAL";
    private String gameMode = "TUTORIAL";

    public void startMenu() {
        int option;
        do {
            System.out.println("\n=== BATTLESHIP DINAMICO ===");
            System.out.println("1. Login");
            System.out.println("2. Crear Player");
            System.out.println("3. Salir");
            option = sc.nextInt();
            sc.nextLine();

            if(option==1) login();
            if(option==2) createPlayer();

        } while(option!=3);
    }

    private void login() {
        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        for(int i=0;i<playerCount;i++) {
            if(players[i].getUsername().equals(user)
            && players[i].getPassword().equals(pass)) {
                currentUser = players[i];
                mainMenu();
                return;
            }
        }
        System.out.println("Login incorrecto");
    }

    private void createPlayer() {
        System.out.print("Nuevo username: ");
        String user = sc.nextLine();
        
        // Validar que sea único
        for(int i = 0; i < playerCount; i++) {
            if(players[i].getUsername().equals(user)) {
                System.out.println("El username ya existe");
                return;
            }
        }
        
        System.out.print("Password: ");
        String pass = sc.nextLine();

        players[playerCount++] = new Player(user, pass);
        currentUser = players[playerCount-1];
        mainMenu();
    }

    private void mainMenu() {
        int op;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Jugar");
            System.out.println("2. Configuracion");
            System.out.println("3. Perfil");
            System.out.println("4. Cerrar sesion");
            op = sc.nextInt();
            sc.nextLine();

            if(op==1) playGame();
            if(op==2) configMenu();
            if(op==3) currentUser.showData();

        } while(op!=4);
    }

    private void configMenu() {
        int op;
        do {
            System.out.println("\n=== CONFIGURACION ===");
            System.out.println("1. Dificultad");
            System.out.println("2. Modo");
            System.out.println("3. Regresar");
            op = sc.nextInt();
            sc.nextLine();

            if(op==1) configDifficulty();
            if(op==2) configMode();

        } while(op!=3);
    }

    private void configDifficulty() {
        System.out.println("1.EASY 2.NORMAL 3.EXPERT 4.GENIUS");
        int op = sc.nextInt();
        sc.nextLine();

        if(op==1){difficulty=5; difficultyName="EASY";}
        if(op==2){difficulty=4; difficultyName="NORMAL";}
        if(op==3){difficulty=2; difficultyName="EXPERT";}
        if(op==4){difficulty=1; difficultyName="GENIUS";}
        
        System.out.println("Dificultad cambiada a: " + difficultyName);
    }

    private void configMode() {
        System.out.println("1.TUTORIAL 2.ARCADE");
        int op = sc.nextInt();
        sc.nextLine();

        if(op==1) gameMode="TUTORIAL";
        if(op==2) gameMode="ARCADE";
        
        System.out.println("Modo cambiado a: " + gameMode);
    }

    private void playGame() {

        System.out.print("Jugador 2: ");
        String name2 = sc.nextLine();

        Player p2 = null;
        for(int i=0;i<playerCount;i++) {
            if(players[i].getUsername().equals(name2))
                p2 = players[i];
        }
        if(p2==null) {
            System.out.println("Jugador no encontrado");
            return;
        }

        boolean easy = difficulty==5;

        currentUser.setupShips(difficulty, easy);
        p2.setupShips(difficulty, easy);

        Player attacker = currentUser;
        Player defender = p2;

        while(attacker.getBoard().hasShips()
           && defender.getBoard().hasShips()) {

            // Limpiar las F del turno anterior
            defender.getBoard().clearFailMarks();

            System.out.println("\n========================================");
            System.out.println("Turno de: " + attacker.getUsername());
            System.out.println("========================================");
            System.out.println("- " + defender.getUsername() + " tiene " 
                + defender.getBoard().countShips() + " barcos aun -");
            
            // Mostrar estado de barcos en modo TUTORIAL
            if(gameMode.equals("TUTORIAL")) {
                defender.getBoard().showShipsStatus();
            }
            
            System.out.println("Coordenada de bomba (ingresa -1 -1 para retirarte):");
            System.out.print("Fila: ");
            int r = sc.nextInt();
            System.out.print("Col: ");
            int c = sc.nextInt();

            // Verificar retiro
            if(r == -1 || c == -1) {
                sc.nextLine();
                System.out.print("¿Seguro que deseas retirarte? (S/N): ");
                String confirm = sc.nextLine().toUpperCase();

                if(confirm.equals("S")) {
                    defender.addPoints(3);
                    defender.addLog("Victoria por retiro de " + attacker.getUsername());
                    attacker.addLog("Derrota por retiro propio contra " + defender.getUsername());

                    System.out.println(attacker.getUsername() + " se ha retirado.");
                    System.out.println("GANADOR: " + defender.getUsername());
                    return;
                } else {
                    continue;
                }
            }

            boolean hit = defender.getBoard().attack(r,c);

            if(hit) {
                Ship hitShip = defender.getBoard().getShipAt(r, c);
                
                if(hitShip != null) {
                    System.out.println("\nSE HA BOMBARDEADO UN " + hitShip.getName().toUpperCase() + "!");
                    System.out.println("Vida restante: " + hitShip.getCurrentLife() + "/" + hitShip.getMaxLife());
                    
                    // Mostrar tablero ANTES de regenerar
                    System.out.println("\n--- Tablero de " + defender.getUsername() + " ANTES de regenerar ---");
                    defender.getBoard().printBoard(gameMode.equals("TUTORIAL"));
                    
                    // Verificar si se hundió ANTES de regenerar
                    if(hitShip.isSunk()) {
                        System.out.println("\n*** SE HUNDIO EL " + hitShip.getName().toUpperCase() + "! Del " + defender.getUsername() + " ***\n");
                    }
                    
                    // REGENERAR EL TABLERO si aún hay barcos
                    if(defender.getBoard().hasShips()) {
                        defender.getBoard().clearHitMarks(); // Limpiar la X temporal
                        defender.getBoard().regenerateBoard();
                        System.out.println("¡TABLERO REGENERADO! Los barcos se han movido a nuevas posiciones.\n");
                        
                        // Mostrar tablero DESPUÉS de regenerar
                        System.out.println("--- Tablero de " + defender.getUsername() + " DESPUES de regenerar ---");
                        defender.getBoard().printBoard(gameMode.equals("TUTORIAL"));
                    }
                }
            } else {
                // Mostrar tablero con la F
                System.out.println("\n--- Tablero de " + defender.getUsername() + " ---");
                defender.getBoard().printBoard(gameMode.equals("TUTORIAL"));
            }

            Player temp = attacker;
            attacker = defender;
            defender = temp;
        }

        Player winner =
            attacker.getBoard().hasShips() ? attacker : defender;
        Player loser = (winner == attacker) ? defender : attacker;

        winner.addPoints(3);
        winner.addLog(winner.getUsername() + " hundio todos los barcos de " + loser.getUsername() + " en modo " + difficultyName);
        loser.addLog("Derrota contra " + winner.getUsername() + " en modo " + difficultyName);

        System.out.println("\n========================================");
        System.out.println("GANADOR: " + winner.getUsername());
        System.out.println("========================================");
    }
}