
package proyecto2.pkg0;

public class Ship {
    private String code;
    private int maxLife;
    private int currentLife;
    private String name;

    public Ship(String code) {
        this.code = code;

        switch(code) {
            case "PA": 
                maxLife = 5; 
                name = "Portaaviones";
                break;
            case "AZ": 
                maxLife = 4; 
                name = "Acorazado";
                break;
            case "SM": 
                maxLife = 3; 
                name = "Submarino";
                break;
            case "DT": 
                maxLife = 2; 
                name = "Destructor";
                break;
            default:
                maxLife = 1;
                name = "Unknown";
        }
        
        this.currentLife = maxLife;
    }

    public String getCode() {
        return code;
    }

    public int getCurrentLife() {
        return currentLife;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public String getName() {
        return name;
    }

    public void hit() {
        if(currentLife > 0) {
            currentLife--;
        }
    }

    public boolean isSunk() {
        return currentLife <= 0;
    }
}