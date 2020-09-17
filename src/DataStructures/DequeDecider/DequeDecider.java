package DataStructures.DequeDecider;

import DataStructures.MyDeque.MyDeque;
import DataStructures.MyLinkedList.MyLinkedList;

import java.util.*;
import java.util.function.Consumer;

public class DequeDecider {
    private List<DequeThread> dequesThreads;

    public MyDeque getPriorityDeque() {
        MyDeque<Object> outputDeque = new MyLinkedList();

        //adding all priority thread first
        for (DequeThread thread : dequesThreads) {
            if (thread.getPriority() == DequePriority.HIGH)
                outputDeque.addAllLast(thread.getDeque());
        }
        boolean isNotEmpty = true;
        while (isNotEmpty) {
            isNotEmpty=false;
            for (DequeThread thread : dequesThreads) {
                if (thread.getPriority() == DequePriority.LOW &&
                        !thread.getDeque().isEmpty()) {
                    System.out.println(thread.getDeque().getFirst());
                    outputDeque.addLast(thread.getDeque().removeFirst());
                    isNotEmpty = !thread.getDeque().isEmpty();
                    continue;
                }
            }
        }

        return outputDeque;
    }

    @Override
    public String toString() {
        return dequesThreads.toString();
    }

    public void addDequeThread(DequeThread dequeThread) {
        dequesThreads.add(dequeThread);
    }

    public void addAllDequeThread(List<DequeThread> dequeThreads) {
        dequesThreads.addAll(dequeThreads);
    }

    public void clearThreads() {
        dequesThreads.clear();
    }

    public DequeDecider(List<DequeThread> dequesThreads) {
        this.dequesThreads = dequesThreads;
    }

    public DequeDecider() {
    }
}
