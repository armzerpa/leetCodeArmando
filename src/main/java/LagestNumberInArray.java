import java.util.ArrayList;
import java.util.List;

public class LagestNumberInArray {

    public String solution(int[] nums) {
        List<String> stringNums = new ArrayList<>();
        for (int n : nums) {
            stringNums.add(String.valueOf(n));
        }

        stringNums.sort((str1, str2) -> (str2 + str1).compareTo(str1 + str2));

        if (stringNums.get(0).equals("0"))
            return "0";

        return String.join("", stringNums);
    }

    public static void main(String[] args) {
        int[] array = {9, 5, 34, 3, 30};
        LagestNumberInArray l = new LagestNumberInArray();
        String res = l.solution(array);

        System.out.println(res);
    }
}
