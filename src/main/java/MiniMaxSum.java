import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MiniMaxSum {

    public static void solution(List<Integer> arr) {
        Collections.sort(arr);

        long totalSum = 0;
        for (int num : arr) {
            totalSum += num;
        }

        // Min sum = total sum - largest element
        // Max sum = total sum - smallest element
        long minSum = totalSum - arr.get(arr.size() - 1);
        long maxSum = totalSum - arr.get(0);

        System.out.println(minSum + " " + maxSum);

        /*
        Integer min = 0;
        Integer max = 0;

        Collections.sort(arr);

        for(int i = 0; i < arr.size(); i++) {
            //System.out.println(arr.get(i));
            if(i != 0)
                max += arr.get(i);

            if(i != arr.size()-1)
                min += arr.get(i);
        }
        System.out.println(min + " " + max);*/
    }

    public static void main(String[] args) {
        Integer[] array = {140638725, 436257910, 953274816, 734065819, 362748590};
        MiniMaxSum l = new MiniMaxSum();
        List<Integer> list21 = Arrays.asList(array);
        solution(list21);
    }
}
