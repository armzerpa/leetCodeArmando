import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoSum {
    public int[] solution(int[] nums, int target) {
        if (nums.length == 0) return null;

        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                int[] result = {map.get(target - nums[i]), i};
                return result;
            }
            map.put(nums[i], i);
        }
        return null;
    }

    public static void main(String[] args){
        int[] nums = {2,7,11,15};
        int target = 9;
        TwoSum t = new TwoSum();
        int[] rs = t.solution(nums, target);
        for(int n : rs){
            System.out.println(n);
        }
    }
}
