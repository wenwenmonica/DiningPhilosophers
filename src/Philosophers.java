import java.util.Random;

public class Philosophers implements Runnable {
    private Random random;
    // Philosopher id
    private int id;
    private Monitor monitor;

    // Constructor
    public Philosophers(int id, Monitor monitor) {
        this.id = id;
        this.monitor = monitor;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            monitor.takeChopstick(id);
            waitForNextStep();
            monitor.returnChopstick(id);
            waitForNextStep();
        }
    }

    public void waitForNextStep() {
        try {
            Thread.sleep(random.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}