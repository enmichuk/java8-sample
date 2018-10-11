package com.enmichuk.core.concurrency;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class FutureExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        List<Future<Integer>> resultList = new ArrayList<>();

        Random random = new Random();

        for (int i=0; i<4; i++) {
            Integer number = random.nextInt(10);
            FactorialCalculator task = new FactorialCalculator(number);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            Future<Integer> result = executor.submit(task);
            resultList.add(result);
        }

        for(Future<Integer> future : resultList) {
            try {
                System.out.println("Future result is - " + " - " + future.get() + "; And Task done is " + future.isDone());
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }
}

class FactorialCalculator implements Callable<Integer> {
    private Integer number;

    public FactorialCalculator(Integer number) {
        this.number = number;
        System.out.println("Created at " + new Date().getTime() + " for number " + this.number);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Start at   " + new Date().getTime() + " for number " + this.number);
        int result = 1;
        if ((number > 1)) {
            for (int i = 2; i <= number; i++) {
                result *= i;
                TimeUnit.MILLISECONDS.sleep(20);
            }
        }
        System.out.println("Result for number - " + number + " -> " + result);
        System.out.println("Calculated in thread " + Thread.currentThread().getName());
        return result;
    }
}
