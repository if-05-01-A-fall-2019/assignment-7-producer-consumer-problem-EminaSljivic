package com.company;

import java.util.LinkedList;
import java.util.List;
public class ProducerConsumer {

    private static final int N=10;
    private static int count=0;
    List<Integer> items = new LinkedList<>();
    int producerCount=0;

    public void producer() throws InterruptedException {
        int item;
        while (true) {
            synchronized(this) {
                item = produce();
                if (count == N) {
                    wait();
                }
                items.add(item);
                count++;
                System.out.format("Produced! %d \n", item);
                if (count == 1) {
                    notify();
                }
            }
        }
    }

    public void consumer() throws InterruptedException {
        int item;
        while (true) {
            synchronized(this) {
                if (count == 0) {
                    wait();
                }
                item = items.remove(items.size() - 1);
                count--;
                if (count == N - 1) {
                    notify();
                }
                consume(item);
                System.out.format("Consumed! %d \n", item);
            }
        }
    }

    private int produce() throws InterruptedException {
        Thread.sleep(10);
        return producerCount++;
    }
    private void consume(int item) throws InterruptedException {
        Thread.sleep(1000);
    }
}
