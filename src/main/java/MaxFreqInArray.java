import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MaxFreqInArray {

    // benchmark
    // 10 -> 6 ms, 100 -> 10 ms, 1000 -> 40ms, 10000 -> 258ms, 100000 -> 18890ms (0.3min), 1000000 -> too slowww
    public static List<Integer> solution(List<Integer> numbers, List<Integer> q) {
        if (numbers.isEmpty() || q.isEmpty() || q.size() < numbers.size())
            return null;
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> cache = new HashMap<>();

        for(int i : q) {
            if (cache.containsKey(i)) { //cache works only if we have repeated indexes and increase space complexity
                result.add(cache.get(i));
            } else {
                List<Integer> sub = numbers.subList((i - 1), numbers.size());
                int max = sub.stream().max(Integer::compareTo).orElse(0);
                int freq = Collections.frequency(sub, max);
                result.add(freq);
                cache.put(i, freq);
            }
        }
        return result;
    }

    public static List<Integer> solution2(List<Integer> numbers, List<Integer> q) {
        if (numbers.isEmpty() || q.isEmpty() || q.size() < numbers.size())
            return null;

        List<Integer> result = new ArrayList<>();
        int i = -1;
        int justRemoveItem = -1;
        int max = 0;
        int freq = 0;
        List<Integer> sub = numbers;
        for (int a : q) {
            if (i != -1) justRemoveItem = numbers.get(i);
            if (justRemoveItem == max) freq--;

            if (freq > 0) {
                result.add(freq);
            } else {
                if ((a - 1) > 0)
                    sub = numbers.subList((a - 1), numbers.size());

                max = Collections.max(sub);
                freq = Collections.frequency(sub, max);
                result.add(freq);
            }
            i++;
        }

        return result;
    }

    public static int getMaxFrequencyItem(List<Integer> array) {
        return 0;
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(5, 4, 5, 3, 2);
        List<Integer> q = Arrays.asList(1, 2, 3, 4, 5);

        int n = 1000000;

        List<Integer> nBig = generateRandomList(n, 5);
        //List<Integer> nBig = generateFixedList(n, 2);
        //nBig.set(0, 5);
        List<Integer> qBig = IntStream.rangeClosed(1, n)
                .boxed()
                .collect(Collectors.toList());

        long startTime = System.currentTimeMillis();
        List<Integer> freqRes = solution2(nBig, qBig);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        double executionTimeMinutes = (double) executionTime / (1000 * 60);

        System.out.println("Execution time (milliseconds): " + executionTime + " and in minutes: " + executionTimeMinutes);
        System.out.println("Result: " + freqRes.size());
    }

    public static List<Integer> generateFixedList(int n, int fixedValue) {
        Random random = new Random();

        return IntStream.range(0, n)
                .map(i -> fixedValue)
                .boxed()
                .collect(Collectors.toList());
    }

    public static List<Integer> generateRandomList(int n, int bound) {
        Random random = new Random();

        return IntStream.range(0, n)
                .map(i -> random.nextInt(bound))
                .boxed()
                .collect(Collectors.toList());
    }
}
