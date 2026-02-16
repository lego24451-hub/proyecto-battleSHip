/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto2.pkg0;

/**
 *
 * @author spodi
 */


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
            System.out.println("\n1. Login");
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
        System.out.print("Password: ");
        String pass = sc.nextLine();

        players[playerCount++] = new Player(user, pass);
        currentUser = players[playerCount-1];
        mainMenu();
    }

    private void mainMenu() {
        int op;
        do {
            System.out.println("\n1. Jugar");
            System.out.println("2. Configuracion");
            System.out.println("3. Perfil");
            System.out.println("4. Reportes");
            System.out.print("5. Cerrar session");
            op = sc.nextInt();
            sc.nextLine();

            if(op==1) playGame();
            if(op==2) configMenu();
            if(op==3) profileMenu();
            if(op==4) reportsMenu();

        } while(op!=5);
        
    }

    private void configMenu() {
        int op;
        do {
            System.out.println("\n1. Dificultad");
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
    }

    private void configMode() {
        System.out.println("1.TUTORIAL 2.ARCADE");
        int op = sc.nextInt();
        sc.nextLine();

        if(op==1) gameMode="TUTORIAL";
        if(op==2) gameMode="ARCADE";
    }

    private void playGame() {

        System.out.print("Jugador 2: ");
        String name2 = sc.nextLine();

        Player p2 = null;
        for(int i=0;i<playerCount;i++) {
            if(players[i].getUsername().equals(name2))
                p2 = players[i];
        }
        if(p2==null) return;

        boolean easy = difficulty==5;

        currentUser.setupShips(difficulty, easy);
        p2.setupShips(difficulty, easy);

        Player attacker = currentUser;
        Player defender = p2;

        while(attacker.getBoard().hasShips()
           && defender.getBoard().hasShips()) {
            System.out.println("\nCoordenada de bomba (" + attacker.getUsername() + "):");
System.out.println("- " + defender.getUsername() + " tiene "
        + defender.getBoard().countShips() + " barcos aun -");

            System.out.println(attacker.getUsername()+" ataca");
           System.out.print("Fila: ");
int r = sc.nextInt();

System.out.print("Col: ");
int c = sc.nextInt();

// ===== RETIRO =====
if(r == -1 || c == -1) { //metodo de retiro 
    sc.nextLine(); // limpiar buffer
    System.out.print("¿Seguro que deseas retirarte? (S/N): ");
    String confirm = sc.nextLine().toUpperCase();

    if(confirm.equals("S")) {
        defender.addPoints(3);
        defender.addLog("Victoria por retiro de " + attacker.getUsername());

        System.out.println(attacker.getUsername() + " se ha retirado.");
        System.out.println("Ganador: " + defender.getUsername());
        return; // termina el juego
    } else {
        continue; // vuelve a pedir coordenadas
    }
}

            boolean hit = defender.getBoard().attack(r,c);

           if(hit) {
    System.out.println("Impacto!");

    if(defender.getBoard().countShips() == 0) {
        System.out.println("Todos los barcos destruidos!");
    }
} else {
    System.out.println("Agua");
}

            defender.getBoard().printBoard(
                gameMode.equals("TUTORIAL"));

            Player temp = attacker;
            attacker = defender;
            defender = temp;
        }

        Player winner =
            attacker.getBoard().hasShips() ? attacker : defender;

        winner.addPoints(3);
        winner.addLog(winner.getUsername() +
    " hundio todos los barcos de " +
    (winner == currentUser ? p2.getUsername() : currentUser.getUsername()) +
    " en modo " + difficultyName + ".");

        System.out.println("Ganador: "+winner.getUsername());
    }
    private void profileMenu() {
    int op;

    do {
        System.out.println("\n--- PERFIL ---");
        System.out.println("1. Ver datos");
        System.out.println("2. Modificar username");
        System.out.println("3. Modificar password");
        System.out.println("4. Eliminar cuenta");
        System.out.println("5. Ver historial");
        System.out.println("6. Regresar");

        op = sc.nextInt();
        sc.nextLine();

        if(op == 1) currentUser.showData();

        if(op == 2) {
            System.out.print("Nuevo username: ");
            String newUser = sc.nextLine();
            currentUser.setUsername(newUser);
            System.out.println("Username actualizado.");
        }

        if(op == 3) {
            System.out.print("Nueva password: ");
            String newPass = sc.nextLine();
            currentUser.setPassword(newPass);
            System.out.println("Password actualizada.");
        }

        if(op == 4) {
            deleteAccount();
            return;
        }

        if(op == 5) currentUser.showLogs();

    } while(op != 6);
}
    private void deleteAccount() {

    System.out.print("¿Seguro que deseas eliminar tu cuenta? (S/N): ");
    String confirm = sc.nextLine().toUpperCase();

    if(confirm.equals("S")) {

        for(int i = 0; i < playerCount; i++) {
            if(players[i] == currentUser) {

                for(int j = i; j < playerCount - 1; j++) {
                    players[j] = players[j+1];
                }

                players[playerCount - 1] = null;
                playerCount--;

                currentUser = null;

                System.out.println("Cuenta eliminada.");
                return;
            }
        }
    } else {
        System.out.println("Cancelado.");
    }

    
}
   private void reportsMenu() {

    int op;

    do {
        System.out.println("\n--- REPORTES ---");
        System.out.println("1. Descripcion de mis ultimos 10 juegos");
        System.out.println("2. Ranking de jugadores");
        System.out.println("3. Regresar");

        op = sc.nextInt();
        sc.nextLine();

        if(op == 1) showLastGames();
        if(op == 2) showRanking();

    } while(op != 3);
}
   private void showLastGames() {

    System.out.println("\n--- MIS ULTIMOS 10 JUEGOS ---");

    String[] logs = currentUser.getLogs();

    for(int i = 0; i < logs.length; i++) {
        if(logs[i] != null) {
            System.out.println((i+1) + "- " + logs[i]);
        }
    }
}
   private void showRanking() {

    System.out.println("\n--- RANKING DE JUGADORES ---");

    // Copia del arreglo para no modificar el original
    Player[] temp = new Player[playerCount];

    for(int i = 0; i < playerCount; i++) {
        temp[i] = players[i];
    }

    // Ordenar de mayor a menor (Burbuja simple)
    for(int i = 0; i < playerCount - 1; i++) {
        for(int j = 0; j < playerCount - i - 1; j++) {
            if(temp[j].getPoints() < temp[j+1].getPoints()) {

                Player aux = temp[j];
                temp[j] = temp[j+1];
                temp[j+1] = aux;
            }
        }
    }

    // Mostrar ranking
    for(int i = 0; i < playerCount; i++) {
        System.out.println((i+1) + ". "
            + temp[i].getUsername()
            + " - "
            + temp[i].getPoints() + " pts");
    }
}
}


