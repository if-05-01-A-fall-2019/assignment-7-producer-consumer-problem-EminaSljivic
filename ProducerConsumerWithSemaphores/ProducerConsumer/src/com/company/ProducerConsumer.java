package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ProducerConsumer {
    private final Semaphore consumerSemaphore = new Semaphore(0);
    private final Semaphore producerSemaphore = new Semaphore(N);

    private static final int N = 10;
    private static int count = 0;
    List<Integer> items = new LinkedList<>();
    int producerCount = 0;

    public void producer() throws InterruptedException {
        int item;
        while (true) {
            producerSemaphore.acquire();
            item = produce();
            System.out.format("Produced! %d \n", item);
            items.add(item);
            count++;
            consumerSemaphore.release();
        }
    }

    public void consumer() throws InterruptedException {
        int item;
        while (true) {
            consumerSemaphore.acquire();
            item = items.remove(items.size() - 1);
            count--;
            consume(item);
            System.out.format("Consumed! %d \n", item);
            producerSemaphore.release();
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
