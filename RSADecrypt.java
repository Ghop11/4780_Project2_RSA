import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

public class RSADecrypt {

    public static void main(String[] args) throws IOException {
        // TODO: Read file to get d and n
        File textFile = new File(args[0]);
        Scanner readText = new Scanner(textFile);

        File pri_keyFile = new File(args[1]);
        Scanner pub_key = new Scanner(pri_keyFile);

        String keys[] = new String[5];
        int i = 0;
        while (pub_key.hasNextLine() && i != keys.length){
            keys[i] = pub_key.nextLine();
            i++;
        }

        // TODO: get ciphertext
        String cipherText = "";
        while (readText.hasNextLine()){
            cipherText += readText.nextLine() + "\n";
        }

        // TODO: get public key file and split each line into an array
        //  the last element in each array will be the correct value
        //  keys[0] is d = {value} and last element is the value
        //  keys[1] is n = {value} and last element is the value
        String keyDArray[] = keys[0].split("\\s+");
        String keyNArray[] = keys[1].split("\\s+");
        if (keyDArray.length <= 2 || args.length == 0){
            System.out.println("Error reading pub_key.txt or pri_key.txt");
        } else {
            String keyD = keyDArray[2];
            String keyN = keyNArray[2];
            BigInteger d = new BigInteger(keyD);
            BigInteger n = new BigInteger(keyN);

            // TODO: Note: In order for the scheme to work, make sure that n  is bigger than 262626.
            BigInteger min = new BigInteger("262626");
            if (n.compareTo(min) == -1){
                // TODO: to small
                System.out.println("n is less than 262626");
            } else {

                // TODO: In this homework,  you will use a block size of 3 bytes. To treat each block as a number,
                //  simply use a 00 - 26 encoding scheme (26 for space).

                // TODO: cipherArray will have each block of 3 chars encryption
                //  this will put each on in there to prep to decrypt each block
                String cipherArray[] = cipherText.split("\\r?\\n");
                String blockString = "";

                // TODO: loop there all elements in array
                //  take each block and send to decrypt() to return the plaintext in numeric value
                for (int k = 0; k < cipherArray.length ; k++){
                    blockString += decrypt(cipherArray[k], d, n);
                }

                // TODO: take blockString and return actually text
                String plainText = "";

                // TODO: take the blockString that has the numeric value and convert
                //  each block of 2 into the correct char
                //  returned after the final loop is the completed plaintext message
                for (int q = 0; q < (blockString.length() / 2); q++){
                    int num = q * 2;
                    String letter = blockString.substring(num, num+2);
                    plainText += BlockString(letter);
                }


                // TODO: output results to test.dec
                File decryptFIle = new File("test.dec");

                // output public key results to public file
                decryptFIle.createNewFile();
                PrintWriter pw = new PrintWriter(decryptFIle);
                pw.println(plainText);
                pw.close();
            }
        }

    }

    public static String decrypt(String text, BigInteger d, BigInteger n){
        // TODO: C^d mod n = P
        BigInteger bigText = new BigInteger(text);

        BigInteger CD = bigText.modPow(d, n);
        String results = "";

        // TODO: make sure length is 6. If not add zeros in front to make it 6 digits
        //  needs to be padded if required
        if (CD.toString().length() != 6){
            for (int w = 0; w < 6 - CD.toString().length(); w++){
                results += "0";
            }
            results += CD.toString();
        } else {
            results += CD.toString();
        }
        return results;
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
