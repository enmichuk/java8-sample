package org.enmichuk.ignite.gettingstarted;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteCallable;

import java.util.ArrayList;
import java.util.Collection;

public class IgniteComputeApplication {
    public static void main(String[] args) {
        try (Ignite ignite = Ignition.start("config/example-ignite.xml")) {
            System.out.println();
            System.out.println(">>> Compute callable example started.");

            Collection<IgniteCallable<Integer>> calls = new ArrayList<>();

            // Iterate through all words in the sentence and create callable jobs.
            for (String word : "Count characters using callable".split(" ")) {
                calls.add(() -> {
                    System.out.println();
                    System.out.println("Printing '" + word + "' on this node from ignite job.");

                    return word.length();
                });
            }

            // Execute collection of callables on the ignite.
            Collection<Integer> res = ignite.compute().call(calls);

            int sum = res.stream().mapToInt(i -> i).sum();

            System.out.println();
            System.out.println("Total number of characters in the phrase is '" + sum + "'.");
            System.out.println("Check all nodes for output (this node is also part of the cluster).");
        }
    }
}
