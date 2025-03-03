import java.util.Stack;

public class BalancedBrackets {
    public static String isBalanced(String s) {
        Stack<Character> stack = new Stack<>();

        for (char ch : s.toCharArray()) {
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch); // Push opening brackets onto the stack
            } else {
                if (stack.isEmpty()) return "NO"; // Closing bracket without an opening pair

                char top = stack.pop();
                if ((ch == ')' && top != '(') ||
                        (ch == '}' && top != '{') ||
                        (ch == ']' && top != '[')) {
                    return "NO"; // Mismatched pair
                }
            }
        }
        return stack.isEmpty() ? "YES" : "NO"; // Stack should be empty if balanced
    }

    public static void main(String[] args) {
        String[] testCases = { "{[()]}", "{[(])}", "{{[[(())]]}}" };

        for (String test : testCases) {
            System.out.println(test + " → " + isBalanced(test));
        }
    }
}
