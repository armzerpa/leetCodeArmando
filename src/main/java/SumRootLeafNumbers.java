import java.util.ArrayList;
import java.util.List;

public class SumRootLeafNumbers {
    public int sumNumbersAI(TreeNode root) {
        return sum(root, 0);
    }

    private int sum(TreeNode root, int currentSum) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return currentSum*10 + root.val;
        return sum(root.left, currentSum * 10 + root.val) + sum(root.right, currentSum * 10 + root.val);
    }
    public int sumNumbers(TreeNode root) {
        List<List<Integer>> allPaths = new ArrayList<>();
        findPath(root, new ArrayList<>(), allPaths);

        int total = 0;
        for (List<Integer> path : allPaths) {
            total = total + listToInteger(path);
        }

        return total;
    }

    public static int listToInteger(List<Integer> list) {
        int result = 0;
        for (int num : list) {
            result = result * 10 + num;
        }
        return result;
    }
    public static void findPath(TreeNode root, List<Integer> currentPath, List<List<Integer>> allPaths) {
        if (root == null)
            return;

        currentPath.add(root.val);

        if (root.left == null && root.right == null) {
            allPaths.add(new ArrayList<>(currentPath));
        }else {
            findPath(root.left, currentPath, allPaths);
            findPath(root.right, currentPath, allPaths);
        }

        currentPath.remove(currentPath.size() - 1);

    }

    public static void main(String[] args) {
        // Creating a sample tree:
        //         1
        //        / \
        //       2   3
        //      / \   \
        //     4   5   6
        TreeNode root = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(4),
                        new TreeNode(5)
                ),
                new TreeNode(3,
                        null,
                        new TreeNode(6)
                )
        );

        SumRootLeafNumbers s = new SumRootLeafNumbers();
        int res = s.sumNumbers(root);
        System.out.println(res);
        int resAI = s.sumNumbersAI(root);
        System.out.println(resAI);
    }
}
