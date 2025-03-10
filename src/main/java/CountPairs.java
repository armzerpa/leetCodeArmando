import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CountPairs {
    // a + k = b
    public static int countPairs(List<Integer> numbers, int k) {
        //todo check nulls
        HashSet<Integer> uniqueNumbers = new HashSet<>(numbers);
        HashSet<Integer> pairs = new HashSet<>();
        for(int n : numbers) {
            if (uniqueNumbers.contains(n + k)){
                pairs.add(n);
            }
        }
        return pairs.size();
    }

    public static void main(String[] args) {
        List<Integer> n = Arrays.asList(1, 1, 1, 2);
        int pairs = countPairs(n, 0);
        System.out.println(pairs);
    }
}
