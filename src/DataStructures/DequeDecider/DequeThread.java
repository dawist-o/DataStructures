package DataStructures.DequeDecider;

import DataStructures.MyDeque.MyDeque;

import java.util.ArrayList;

public class DequeThread {
    private MyDeque deque;
    private DequePriority priority;

    public DequeThread(MyDeque deque, DequePriority priority) {
        this.deque = deque;
        this.priority = priority;
    }

    public MyDeque getDeque() {
        return deque;
    }

    public void setDeque(MyDeque deque) {
        this.deque = deque;
    }

    public DequePriority getPriority() {
        return priority;
    }

    public void setPriority(DequePriority priority) {
        this.priority = priority;
    }
}
