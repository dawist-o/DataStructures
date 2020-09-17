package test;

import DataStructures.DequeDecider.DequeDecider;
import DataStructures.DequeDecider.DequePriority;
import DataStructures.DequeDecider.DequeThread;
import DataStructures.MyDeque.MyDeque;
import DataStructures.MyLinkedList.MyLinkedList;

import java.lang.reflect.Array;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        MyDeque<Integer> fstDeq=new MyLinkedList();
        fstDeq.add(12);
        fstDeq.add(13);
        fstDeq.add(14);
        DequeThread fst=new DequeThread(fstDeq, DequePriority.LOW);

        MyDeque<String> secndDeq=new MyLinkedList();
        secndDeq.add("22");
        secndDeq.add("23");
        secndDeq.add("24");
        DequeThread scnd=new DequeThread(secndDeq, DequePriority.HIGH);

        MyDeque<Integer> thrdDeq=new MyLinkedList();
        thrdDeq.add(32);
        thrdDeq.add(33);
        thrdDeq.add(34);
        DequeThread thrd=new DequeThread(thrdDeq, DequePriority.LOW);

        DequeDecider dequeDecider=new DequeDecider(Arrays.asList(fst,scnd,thrd));
        System.out.println(dequeDecider.getPriorityDeque().toString());
    }
}
