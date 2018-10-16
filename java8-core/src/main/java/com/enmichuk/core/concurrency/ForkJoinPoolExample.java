package com.enmichuk.core.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class ForkJoinPoolExample {
    public static void main(String[] args) {
        Node root = getRootNode();
        Long result = new ForkJoinPool().invoke(new ValueSumCounter(root));
        System.out.println(result);
    }

    private static Node getRootNode() {
        return getNode(1000, 3, 3);
    }

    private static Node getNode(int valueBound, int childrenCount, int deep) {
        return new Node() {
            @Override
            public Collection<Node> getChildren() {
                return getNodes(valueBound, childrenCount, deep - 1);
            }

            @Override
            public long getValue() {
                return new Random().nextInt(valueBound);
            }
        };
    }

    private static Collection<Node> getNodes(int valueBound, int childrenCount, int deep) {
        List<Node> nodes = new ArrayList<>();
        if(deep > 0) {
            IntStream.range(1, new Random().nextInt(childrenCount)).forEach(i ->
                nodes.add(getNode(valueBound, childrenCount, deep))
            );
        }
        return nodes;
    }
}

class ValueSumCounter extends RecursiveTask<Long> {
    private final Node node;

    ValueSumCounter(Node node) {
        this.node = node;
    }

    @Override
    protected Long compute() {
        long sum = node.getValue();
        List<ValueSumCounter> subTasks = new ArrayList<>();

        for(Node child : node.getChildren()) {
            ValueSumCounter task = new ValueSumCounter(child);
            task.fork();
            subTasks.add(task);
        }

        for(ValueSumCounter task : subTasks) {
            sum += task.join();
        }

        return sum;
    }

}

interface Node {
    Collection<Node> getChildren();

    long getValue();
}
