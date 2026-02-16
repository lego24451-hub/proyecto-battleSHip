package proyecto2.pkg0;

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

    public String getUsername() { 
        return username; 
    }public String getPassword() 
    { return password; }
    public int getPoints() { 
        return points; 
    }public void setUsername(String u) 
    { username = u; 
    }public void setPassword(String p){ 
        password = p;
    }public void addPoints(int p){ 
        points += p; 
    }public void addLog(String log){
        for(int i = logs.length-1; i > 0; i--){
            logs[i] = logs[i-1];
        }
        logs[0] = log;
    }

    public void showLogs() {
        System.out.println("\n=== ULTIMOS 10 JUEGOS ===");
        for(int i = 0; i < logs.length; i++) {
            if(logs[i] != null) {
                System.out.println((i+1) + "- " + logs[i]);
            }
        }
    }

    public void showData() {
        System.out.println("\n=== PERFIL ===");
        System.out.println("Username: " + username);
        System.out.println("Points: " + points);
    }

    public Boards getBoard() {
        return board;
    }

    public void setupShips(int maxShips, boolean easyMode) {
        board = new Boards();

        int placed = 0;
        boolean usedPA=false, 
                usedAZ=false, 
                usedSM=false, 
                usedDT=false;

        System.out.println("\n=== COLOCACION DE BARCOS: " + username + " ===");
        System.out.println("Barcos a colocar: " + maxShips);
        System.out.println("PA=Portaaviones(5) | AZ=Acorazado(4) | SM=Submarino(3) | DT=Destructor(2)");

        while(placed < maxShips) {

            System.out.println("\n" + username + " - Barco #" + (placed+1));
            System.out.print("Codigo (PA,AZ,SM,DT): ");
            String code = sc.next().toUpperCase();

            // Validar código
            if(!code.equals("PA") && !code.equals("AZ") && 
               !code.equals("SM") && !code.equals("DT")) {
                System.out.println("Codigo invalido. Use PA, AZ, SM o DT");
                continue;
            }

            // Validar repetidos
            if(code.equals("PA") && usedPA) {
                System.out.println("Ya colocaste un Portaaviones");
                continue;
            }
            if(code.equals("AZ") && usedAZ) {
                System.out.println("Ya colocaste un Acorazado");
                continue;
            }
            if(code.equals("SM") && usedSM) {
                System.out.println("Ya colocaste un Submarino");
                continue;
            }
            if(code.equals("DT") && usedDT && !easyMode) {
                System.out.println("Ya colocaste un Destructor");
                continue;
            }

            System.out.print("Fila (0-7): ");
            int r = sc.nextInt();
            System.out.print("Columna (0-7): ");
            int c = sc.nextInt();

            if(!board.canPlace(r,c)) {
                System.out.println("Posicion invalida.");
                continue;
            }

            board.placeShip(r,c,code);

            if(code.equals("PA")) 
                usedPA=true;
            if(code.equals("AZ"))
                usedAZ=true;
            if(code.equals("SM"))
                usedSM=true;
            if(code.equals("DT"))
                usedDT=true;

            placed++;
            board.printBoard(true);
        }
        
        System.out.println("\n" + username + " ha terminado de colocar sus barcos.");
        System.out.println("Presiona ENTER para continuar...");
        sc.nextLine();
        sc.nextLine();
    }
}