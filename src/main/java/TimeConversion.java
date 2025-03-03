import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class TimeConversion {

    /*
     * Complete the 'timeConversion' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */

    public static String solution(String s) {
        String amorpm = s.substring(s.length() - 2);
        String hour = s.substring(0, 2);
        String minSec = s.substring(2, s.length() - 2);

        if (amorpm.equals("AM") && hour.equals("12")) {
            return "00" + minSec;
        } else if ((amorpm.equals("AM")) || (amorpm.equals("PM") && hour.equals("12"))) {
            return s.substring(0, s.length()-2);
        }

        Integer hourInt = Integer.parseInt(hour);
        Integer hour24HoursFormat = hourInt + 12;

        return hour24HoursFormat.toString() + minSec;
    }

    public static void main(String[] args) {
        String time = solution("07:00:02PM");
        System.out.println(time);
    }

}



