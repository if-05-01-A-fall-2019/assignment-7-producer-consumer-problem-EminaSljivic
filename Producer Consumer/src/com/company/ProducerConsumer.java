package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ProducerConsumer {
    private final Semaphore consumerSemaphore = new Semaphore(1);
    private final Semaphore producerSemaphore = new Semaphore(1);

    private static final int N=10;
    private static int count=0;
    List<Integer> items = new LinkedList<>();
    int producerCount=0;

    public void producer() throws InterruptedException {
        int item;
        while (true) {
            synchronized(this) {
                item = produce();
                System.out.format("Produced! %d \n", item);
                if (count == N) {
                    producerSemaphore.acquire();
                }
                items.add(item);
                count++;
                if (count == 1) {
                    consumerSemaphore.release();
                }
            }
        }
    }

    public void consumer() throws InterruptedException {
        int item;
        while (true) {
            synchronized(this) {
                if (count == 0) {
                    consumerSemaphore.acquire();
                }
                item = items.remove(items.size() - 1);
                count--;
                if (count == N - 1) {
                    producerSemaphore.release();
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
        Thread.sleep(10000);
    }
}
