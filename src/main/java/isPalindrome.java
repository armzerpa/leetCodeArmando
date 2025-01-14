public class isPalindrome {
    public boolean solution(String s) {
        if(s.isEmpty())
            return false;

        s = s.toLowerCase().replaceAll("[^a-zA-Z]", "");
        if (s.length() == 1)
            return false;

        for(int i = 0; i < s.length(); i++){
            char firstc = s.charAt(i);
            char lastc = s.charAt(s.length()-i-1);

            if (firstc != lastc)
                return false;
        }

        return true;
    }

    public boolean solutionAI(String s) {
        // Edge case: empty string or single character is palindrome
        if (s == null || s.length() <= 1) return true;

        // Convert to lowercase and use two pointers
        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            // Skip non-alphanumeric characters from left
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }

            // Skip non-alphanumeric characters from right
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            // Compare characters (case-insensitive)
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    public static void main(String[] args) {
        isPalindrome c = new isPalindrome();
        boolean res = c.solution("0P");
        System.out.println(res);
    }
}
