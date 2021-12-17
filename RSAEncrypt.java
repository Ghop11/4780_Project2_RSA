import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class RSAEncrypt {

    // TODO: c:\> java RSAEncrypt test.txt  pub_key.txt
    public static void main(String[] args) throws IOException {
        // TODO: Read file to get e and n
        File textFile = new File(args[0]);
        Scanner readText = new Scanner(textFile);

        File pub_keyFile = new File(args[1]);
        Scanner pub_key = new Scanner(pub_keyFile);

        String keys[] = new String[5];
        int i = 0;
        while (pub_key.hasNextLine()){
            keys[i] = pub_key.nextLine();
            i++;
        }

        // TODO: get plaintext
        String plaintext = "";
        while (readText.hasNextLine()){
            plaintext += readText.nextLine().toLowerCase();
        }

        // TODO: get public key file and split each line into an array
        //  the last element in each array will be the correct value
        //  keys[0] is e = {value} and last element is the value
        //  keys[1] is n = {value} and last element is the value
        String keyEArray[] = keys[0].split("\\s+");
        String keyNArray[] = keys[1].split("\\s+");
        String keyE = keyEArray[2];
        String keyN = keyNArray[2];

        // TODO: convert string value to BigInteger
        BigInteger e = new BigInteger(keyE);
        BigInteger n = new BigInteger(keyN);

        // TODO: Note: In order for the scheme to work, make sure that n  is bigger than 262626.
        BigInteger max = new BigInteger("262626");
        if (n.compareTo(max) == -1){
            // TODO: too small
            System.out.println("n is less than 262626");
        } else {

            // TODO: In this homework,  you will use a block size of 3 bytes. To treat each block as a number,
            //  simply use a 00 - 26 encoding scheme (26 for space).


            // TODO: To generate the first encrypted number, pick up the first block
            //  consisting of three letters: t, h, and i. Then transform the block to an integer 190708 and encrypt it.
            //  ThreeBytesBlock will return the fully encrypted message
            String blockString = ThreeByteBlock(plaintext, e, n);

            File encFIle = new File("test.enc");

            // output public key results to public file
            encFIle.createNewFile();
            PrintWriter pw = new PrintWriter(encFIle);
            pw.println(blockString);
            pw.close();
        }

    }

    // helper methods:
    public static String ThreeByteBlock(String plainText, BigInteger e, BigInteger n){

        String results = "";

        // TODO: get length to see how many loops is need
        int length = plainText.length();
        int rounds = length/3;
        // TODO: if length % 3 is not 0 we need to add another loop
        if (length%3 != 0){rounds++;}

        // TODO: need to check if there is 3 characters 2 or 1 in order to be success for non divisible by 3 plaintext
        //  inside the for loop do this check
        //  if there is 3 then get the numeric value for all three chars
        //  if there is only 2 then add a "99" at the end of one and two conversions
        //  if there is 1 then add "9999"
        //  this will return an empty string "" when converted back
        for (int i = 0; i < rounds; i++){
            int num = i * 3;
            String one = "";
            String two = "";
            String three = "";
            String encryptedNumbers = "";
            if(length-1 >= num+2){
                String threeBlock = plainText.substring(num, num+3);
                 one = BlockNumber(threeBlock.substring(0, 1));
                 two = BlockNumber(threeBlock.substring(1, 2));
                 three = BlockNumber(threeBlock.substring(2, 3));
                 encryptedNumbers = one + two + three;
            }
            else if (length-1 >= num+1){
                String threeBlock = plainText.substring(num, num+2);
                 one = BlockNumber(threeBlock.substring(0, 1));
                 two = BlockNumber(threeBlock.substring(1, 2));
                 encryptedNumbers = one + two + "99";
            }
            else if (length-1 >= num) {
                String threeBlock = plainText.substring(num, num+1);
                 one = BlockNumber(threeBlock.substring(0, 1));
                 encryptedNumbers = one + "9999";
            }


            // TODO: encrypt message
            //  take the numeric value of chars all together and encrypted it
            //  This should be a string length of 6
            String cipher = encrypt(encryptedNumbers, e, n);

            // TODO: use \n in string to make it easier to know what needs to be decrypted
            //  for each block of 3 chars
            results += cipher + "\n";
        }
        return results;
    }
    public static String encrypt(String text, BigInteger e, BigInteger n){
        // TODO: C=P^e mod n
        BigInteger bigText = new BigInteger(text);
        BigInteger PE = bigText.modPow(e, n);
        return PE.toString();
    }

    // takes in a char and returns the int value of that char
    public static String BlockNumber(String s){
        // TODO: results will be changed no matter what in the switch case
        //  return the string length of 2 for every char passed through
        String results = "00";
        switch (s){
            case "a":
                results = "00";
                break;
            case "b":
                results = "01";
                break;
            case "c":
                results = "02";
                break;
            case "d":
                results = "03";
                break;
            case "e":
                results = "04";
                break;
            case "f":
                results = "05";
                break;
            case "g":
                results = "06";
                break;
            case "h":
                results = "07";
                break;
            case "i":
                results = "08";
                break;
            case "j":
                results = "09";
                break;
            case "k":
                results = "10";
                break;
            case "l":
                results = "11";
                break;
            case "m":
                results = "12";
                break;
            case "n":
                results = "13";
                break;
            case "o":
                results = "14";
                break;
            case "p":
                results = "15";
                break;
            case "q":
                results = "16";
                break;
            case "r":
                results = "17";
                break;
            case "s":
                results = "18";
                break;
            case "t":
                results = "19";
                break;
            case "u":
                results = "20";
                break;
            case "v":
                results = "21";
                break;
            case "w":
                results = "22";
                break;
            case "x":
                results = "23";
                break;
            case "y":
                results = "24";
                break;
            case "z":
                results = "25";
                break;
            case " ":
                results = "26";
                break;
            default:
                results = "99";
                break;
        }

        return results;
    }
    public static String BlockString(String s){
        // TODO: results will be changed no matter what in the switch case
        //  return the the correct char for the numeric value passed
        String results = "00";
        switch (s){
            case "00":
                results = "a";
                break;
            case "01":
                results = "b";
                break;
            case "02":
                results = "c";
                break;
            case "03":
                results = "d";
                break;
            case "04":
                results = "e";
                break;
            case "05":
                results = "f";
                break;
            case "06":
                results = "g";
                break;
            case "07":
                results = "h";
                break;
            case "08":
                results = "i";
                break;
            case "09":
                results = "j";
                break;
            case "10":
                results = "k";
                break;
            case "11":
                results = "l";
                break;
            case "12":
                results = "m";
                break;
            case "13":
                results = "n";
                break;
            case "14":
                results = "o";
                break;
            case "15":
                results = "p";
                break;
            case "16":
                results = "q";
                break;
            case "17":
                results = "r";
                break;
            case "18":
                results = "s";
                break;
            case "19":
                results = "t";
                break;
            case "20":
                results = "u";
                break;
            case "21":
                results = "v";
                break;
            case "22":
                results = "w";
                break;
            case "23":
                results = "x";
                break;
            case "24":
                results = "y";
                break;
            case "25":
                results = "z";
                break;
            case "26":
                results = " ";
                break;
            default:
                results = "";
                break;
        }

        return results;
    }

}

