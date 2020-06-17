import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.lang.*;
public class Exr {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("Encrypted: " + Arrays.toString(encrypt("Hello")));
        System.out.println("Decrypted: " + decrypt(new int[]{72, 33, -73, 84, -12, -3, 13, -13, -68}));
        System.out.println("Can move: " + canMove("Rook", "A8", "H8"));
        System.out.println("Can move: " + canMove("Bishop", "A7", "G1"));
        System.out.println("Can move: " + canMove("Queen", "C4", "D6"));
        System.out.println("Can complete: " + canComplete("bbutl", "beautiful"));
        System.out.println("Nechto: " + sumDigProd(new int[]{16, 28}));
        System.out.println("Vowel Group: " + Arrays.toString(sameVowelGroup(new String[]{"hoops", "chuff", "bot", "bottom"})));
        System.out.println("Validated: " + validateCard(1234567890123456L));
        System.out.println("To english: " + numToEng(782));
        System.out.println("To russian: " + numToRus(782));
        System.out.println("Hash: " + getSha256Hash("password123"));
        System.out.println("Correct title: " + correctTitle("jOn SnoW, kINg IN thE noRth."));
        System.out.println("Hex: \n" + hexLattice(37));
    }
    public static int[] encrypt(String str) {
        char[] wp = str.toCharArray();
        int[] array = new int[str.length()];
        array[0] = wp[0];
        for (int i = 1; i<wp.length; i++) {
            array[i] = wp[i]-wp[i-1];
        }
        return array;
    }
    public static String decrypt(int[] arr) {
        String decrypted = "";
        decrypted += (char) arr[0];
        int ch =  arr[0];
        for (int i = 1; i<arr.length; i++) {
            ch += arr[i];
            decrypted += (char) ch;
        }
        return decrypted;
    }
    public static boolean canMove(String name, String pos, String des) {
        switch (name) {
            case "Pawn":
                return pos.charAt(1) == des.charAt(1)-1;
            case "Rook":
                return (des.contains(pos.substring(0,1)) || des.contains(pos.substring(1)));
            case "Knight":
                if (Math.abs(pos.charAt(1)-des.charAt(1))==2 && Math.abs((int) pos.charAt(0) - (int) des.charAt(0))==1) return true;
                else return Math.abs(pos.charAt(1) - des.charAt(1)) == 1 && Math.abs((int) pos.charAt(0) - (int) des.charAt(0)) == 2;
            case "Bishop":
                return (Math.abs(pos.charAt(1)-des.charAt(1)) == Math.abs(pos.charAt(0) - des.charAt(0)));
            case "Queen":
                return (Math.abs(pos.charAt(1)-des.charAt(1))==Math.abs(pos.charAt(0) - des.charAt(0)) ||
                        des.contains(pos.substring(0,1)) || des.contains(pos.substring(1)));
            case "King":
                return Math.abs(pos.charAt(1) - des.charAt(1))<=1 && Math.abs(pos.charAt(0) - des.charAt(0))<=1;
        }
        return false;
    }
    public static boolean canComplete(String unf, String full) {
        int count = 0;
        char[] wp = full.toCharArray();
        for (char ch : wp)
        {
            if (ch == unf.charAt(count))
            {
                count++;
                if (count == unf.length())
                    return true;
            }
        }
        return false;
    }
    public static int sumDigProd(int[] arr) {
        int bigb = 0;
        for (int num : arr) {
            bigb+=num;
        }
        System.out.println(bigb);
        while (bigb/10>0) {
            char[] chars = ("" + bigb).toCharArray();
            bigb=1;
            for (char ch : chars) {
                bigb*=Character.getNumericValue(ch);
            }
            }
            return bigb;
    }
    public static String[] sameVowelGroup(String[] str) {
        String vowels = "aeiou";
        int count = 0;
        String finals = "";
        char[] decoy = str[0].toCharArray();
        String check = "", bad = vowels;
        for (char ch : decoy) {
            if (vowels.contains(Character.toString(ch))) {check += ch;
            bad =  bad.replace(ch, ' ');}
        }
        char[] chars = check.toCharArray(), bads = bad.toCharArray();
        for (String word : str) {
            count=0;
            for (char ch : chars) {
                if (word.contains(Character.toString(ch))) count++;
            }
            for (char bd : bads) {
                if (word.contains(Character.toString(bd))) count--;
            }
            if (count == chars.length) finals+=word + " ";
        }
        finals = finals.substring(0,finals.length()-1);
        return finals.split(" ");
    }
    public static boolean validateCard(long num) {
        char[] wp = ("" + num).toCharArray();
        int checkDigit = Character.getNumericValue(wp[wp.length-1]);
        wp = Arrays.copyOf(wp, wp.length-1);
        for(int i=0; i<wp.length/2; i++){
            char temp = wp[i];
            wp[i] = wp[wp.length -i -1];
            wp[wp.length -i -1] = temp;
        }
        for (int i=0; i<wp.length; i+=2) {
            int number = Character.getNumericValue(wp[i])*2;
            if (number>9) {char[] decoy = ("" + number).toCharArray();
            number = Character.getNumericValue(decoy[0]) + Character.getNumericValue(decoy[1]);
            }
            wp[i] = (char)(number+'0');
        }
        int number = 0;
        for (char ch : wp) {
           number += Character.getNumericValue(ch);
        }
        wp = ("" + number).toCharArray();
        return (10-Character.getNumericValue(wp[1]) == checkDigit);
    }
    public static String twoz (char[] wp) {
        String[] twos = new String[] {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
        String[] ten2tw = new String[] {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
        String[] tens = new String[] {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        int control1 = Character.getNumericValue(wp[0]);
        int control2 = Character.getNumericValue(wp[1]);
        switch (control1) {
            case 1:
                return ten2tw[control2];
            default:
                return twos[control1]+ " " +tens[control2];
        }
    }
    public static String numToEng(int a) {
        char[] wp = ("" + a).toCharArray();
        String[] tens = new String[] {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        String number = "";
        int control0 = Character.getNumericValue(wp[0]);
        if (wp.length==3) {
            number += tens[control0] + " hundred ";
            wp = Arrays.copyOfRange(wp, 1, wp.length);
        }
        number += twoz(wp);
        return number;
    }
    public static String dvaz (char[] wp) {
        String[] twos = new String[] {"", "", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};
        String[] ten2tw = new String[] {"десять", "одиннадцать", "двеннадцать", "триннадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};
        String[] tens = new String[] {"", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"};
        int control1 = Character.getNumericValue(wp[0]);
        int control2 = Character.getNumericValue(wp[1]);
        switch (control1) {
            case 1:
                return ten2tw[control2];
            default:
                return twos[control1]+ " " +tens[control2];
        }
    }
    public static String numToRus(int a) {
        char[] wp = ("" + a).toCharArray();
        String[] tens = new String[] {"", "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот"};
        String number = "";
        int control0 = Character.getNumericValue(wp[0]);
        if (wp.length==3) {
            number += tens[control0] + " ";
            wp = Arrays.copyOfRange(wp, 1, wp.length);
        }
        number += dvaz(wp);
        return number;
    }
    public static String getSha256Hash(String msg) throws NoSuchAlgorithmException {
        final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(msg.getBytes(StandardCharsets.UTF_8));
        char[] hexChars = new char[hash.length * 2];
        for (int j = 0; j < hash.length; j++) {
            int v = hash[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
    public static  String correctTitle (String str) {
        char[] wp = ("" + str).toCharArray();
        wp[0] = Character.toUpperCase(wp[0]);
        for (int i = 1; i < wp.length; i++) {
            if ((wp[i-1] == ' ' || wp[i-1] == '-') && !((wp[i]=='t' || wp[i]=='T') && (wp[i+1]=='h' || wp[i+1]=='H') &&
                    (wp[i+2]=='e' || wp[i+2]=='E') && wp[i+3]==' ') && !((wp[i]=='a' || wp[i]=='A') &&
                    (wp[i+1]=='n' || wp[i+1]=='N') && (wp[i+2]=='d' || wp[i+2]=='D') && wp[i+3]==' ') &&
                    !((wp[i]=='o' || wp[i]=='O') && (wp[i+1]=='f' || wp[i+1]=='F') && wp[i+2]==' ') &&
                    !((wp[i]=='i' || wp[i]=='I') && (wp[i+1]=='n' || wp[i+1]=='N') && wp[i+2]==' '))
                wp[i]=Character.toUpperCase(wp[i]);
            else wp[i] = Character.toLowerCase(wp[i]);
        }
        String title = new String (wp);
        return title;
    }
    public static String hexLattice(int n) {
        String upHalf = "", middle = "", full = "", lowHalf = "";
        int count = 1, i = 0, j =0;
        for (i = 1; count < n; i++) {
            count += i*6;
        }
        if (n!=count) {
            return "Invalid";
        }
        for (j = 0; j < i*2-1; j++) {
            middle += "o ";
        }
        upHalf = middle;
        for (j = 1; j < i; j++) {
            upHalf = " " + upHalf.substring(0, upHalf.length()-2);
            middle = upHalf  + "\n" +  middle + "\n" + upHalf;
        }
        return middle;
    }
}
