import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    private enum State {
        Think,
        Hungry,
        Eat
    }
    private State[] states;
    private int number;
    private Lock lock = new ReentrantLock();
    Condition[] self;

    // Constructor
    public Monitor(int number) {
        this.number = number;
        states = new State[number];
        self = new Condition[number];
        for (int i = 0; i < number; i++) {
            states[i] = State.Think;
            this.self[i] = lock.newCondition();
        }
    }

    // Pick up the chopsticks
    public void takeChopstick(int i) {
        states[i] = State.Hungry;
        System.out.println(String.format("Philosopher %d is hungry.", i));
        test(i);
        if (states[i] != State.Eat) {
            try {
                synchronized (this.self[i]) {
                    this.self[i].wait();
                    System.out.println(String.format("Philosopher %d is eating.", i));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void returnChopstick(int i) {
            states[i] = State.Think;
            System.out.println(String.format("Philosopher %d is thinking.", i));
            // Test whether the two neighbors are eligible to eat
            test((i + number - 1) % number);
            test((i + 1) % number);
    }

    // If both neighbors are not eating, then i wakeup and eat
    public void test(int i) {
        if ((states[(i + number - 1) % number] != State.Eat) &&
                states[i] == State.Hungry &&
                states[(i + 1) % number] != State.Eat) {
            states[i] = State.Eat;
            synchronized (this.self[i]) {
                this.self[i].notifyAll();
            }
        }
    }
}
