package com.enmichuk.core.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExample {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("dsf", "hea", "", "uew", "ome", "ims", "");
        System.out.println("List: " + strings);
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        System.out.println("Numbers: " + numbers);
        List<Integer> integers = Arrays.asList(1, 2, 13, 4, 15, 6, 17, 8, 19);
        System.out.println("Integers: " + integers);

        long count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println("Empty Strings: " + count);

        count = strings.stream().filter(string -> string.length() == 3).count();
        System.out.println("Strings of length 3: " + count);

        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println("Filtered List: " + filtered);

        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("Merged String: " + mergedString);

        List<Integer> squaresList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        System.out.println("Squares List: " + squaresList);
        System.out.println("List: " + integers);

        Stream<Integer> numbersStream = numbers.stream();
        Map<Integer, List<Integer>> groupedNumbers = numbersStream.collect(Collectors.groupingBy(n -> n));
        System.out.println(groupedNumbers);

        try {
            numbersStream.findFirst().get();
        } catch(IllegalStateException e) {
            System.out.println("Java 8 stream cannot be reused: " + e.getMessage());
        }

        filtered.stream().filter(s -> {
            System.out.println("filter: " + s);
            return true;
        }).forEach(s -> System.out.println("forEach: " + s));

        List<Integer> numbersAndSomethingElse = numbers.stream().flatMap(i -> Stream.of(i, i + i, i * i)).collect(Collectors.toList());
        System.out.println("Numbers and some operations on it: " + numbersAndSomethingElse);

        Optional<Integer> optionalNumberSum = numbers.stream().reduce((first, second) -> first + second);
        System.out.println("Sum of numbers: " + optionalNumberSum);

        Integer numberSum = numbers.stream().reduce(0, (first, second) -> first + second);
        System.out.println("Sum of numbers: " + numberSum);

        delineate();
        Integer notParallelNumberSum = numbers.stream().reduce(0,
                (first, second) -> {
                    System.out.format("Accumulator: first = %s, second = %s", first, second);
                    System.out.println();
                    return first + second;
                },
                (first, second) -> {
                    System.out.format("Combiner: first = %s, second = %s", first, second);
                    System.out.println();
                    return first + second;
                });
        System.out.println("Sum of numbers: " + notParallelNumberSum);

        delineate();
        Integer parallelNumberSum = numbers.parallelStream().reduce(0,
                (first, second) -> {
                    System.out.format("Accumulator: first = %s, second = %s", first, second);
                    System.out.println();
                    return first + second;
                },
                (first, second) -> {
                    System.out.format("Combiner: first = %s, second = %s", first, second);
                    System.out.println();
                    return first + second;
                });
        System.out.println("Sum of numbers: " + parallelNumberSum);
        delineate();

        IntSummaryStatistics stats = integers.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("Highest number in List : " + stats.getMax());
        System.out.println("Lowest number in List : " + stats.getMin());
        System.out.println("Sum of all numbers : " + stats.getSum());
        System.out.println("Average of all numbers : " + stats.getAverage());
        System.out.println("Random Numbers: ");

        //print ten random numbers
        Random random = new Random();
        random.ints().limit(10).sorted().forEach(System.out::println);

        //parallel processing
        count = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println("Empty Strings: " + count);
    }

    public static void delineate() {
        System.out.println("==================================================");
    }
}
