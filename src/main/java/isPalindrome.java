public class isPalindrome {
    public boolean solution(String s) {
        if(s.isEmpty())
            return false;

        s = s.toLowerCase().replaceAll("[^a-zA-Z]", "");
        for(int i = 0; i < s.length(); i++){
            char firstc = s.charAt(i);
            char lastc = s.charAt(s.length()-i-1);

            if (firstc != lastc)
                return false;
        }

        return true;
    }

    public static void main(String[] args) {
        isPalindrome c = new isPalindrome();
        boolean res = c.solution("A man, a plan, a canal: Panama");
        System.out.println(res);
    }
}
