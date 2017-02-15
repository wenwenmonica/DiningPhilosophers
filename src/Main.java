
public class Main {
    // Initialize the "Philosophers" as threads
    public static void main(String[] args) {
        Monitor number = new Monitor(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new Philosophers(i, number)).start();
        }
    }
}
