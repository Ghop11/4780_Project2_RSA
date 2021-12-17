import java.math.*;
import java.io.*;
import java.util.Random;

public class RSAGenKey {

    public static void main(String[] args) throws IOException {
        if (args.length == 1) {
            if (Integer.parseInt(args[0]) < 10){
                System.out.println("GenKey size must be 10 or greater to make n > than 262626.");
            } else {
                GenKeySize(args[0]);
            }
        }
        else if (args.length == 3) {
            BigInteger p = new BigInteger(args[0]);
            BigInteger q = new BigInteger(args[1]);
            BigInteger e = new BigInteger(args[2]);

            GenKeyWithValues(p, q, e );
        } else {
            NotValid();
        }
    }

    // Gen Key with key size
    public static void GenKeySize(String size) throws IOException {

        // the program randomly picks p and q in k bits and generates a key pair.
        int k = Integer.parseInt(size);
        Random r = new Random();


        // TODO: Part 1 --> Randomly select two distinct prime numbers
        //  use BigInteger class methods .probablePrime()
        BigInteger p = BigInteger.probablePrime(k, r);
        BigInteger q = BigInteger.probablePrime(k, r);

        // make sure p and q are distinct from each other
        while (p == q){
            q = BigInteger.probablePrime(k, r);
        }

        // TODO: Part 2 --> Compute n = pq
        //  use BigInteger class methods .multiply()
        BigInteger n = p.multiply(q);

        // TODO: Note: In order for the scheme to work, make sure that n  is bigger than 262626.
        //  make sure n is bigger than 262626
        //  keep looping until we get it
        BigInteger max = new BigInteger("262626");
        if (n.compareTo(max) == -1){
            while(n.compareTo(max) == -1){
                p = BigInteger.probablePrime(k, r);
                q = BigInteger.probablePrime(k, r);
                while (p == q){
                    q = BigInteger.probablePrime(k, r);
                }
                n = p.multiply(q);
                System.out.println("looped n: " + n);
            }
        }


        // TODO: Part 3 --> (p-1) * (q-1)
        //  use BigInteger class methods
        BigInteger O = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // TODO: Part 4 --> choose e | 1 < e < O(n)
        //  create a helper method and pass in O(n)
        BigInteger e = CoPrime(O);

        // TODO: Part 5 --> d = e^(-1*O(n))
        //  use BigInteger class methods .modInverse(O)
        BigInteger d = e.modInverse(O);

        String publicKeyE = e.toString();
        String publicKeyN = n.toString();
        String privateKeyD = d.toString();
        String privateKeyN = n.toString();


        // TODO: after public and private keys are created
        //  create a .txt file for both keys that contains the results.

        File publicFile = new File("pub_key.txt");
        File privateFile = new File("pri_key.txt");

        // output public key results to public file
        publicFile.createNewFile();
        PrintWriter pw = new PrintWriter(publicFile);
        pw.println("e = " + publicKeyE);
        pw.println("n = " + publicKeyN);
        pw.close();

        // output private key results to public file
        privateFile.createNewFile();
        PrintWriter pw_pri = new PrintWriter(privateFile);
        pw_pri.println("d = " + privateKeyD);
        pw_pri.println("n = " + privateKeyN);
        pw_pri.close();

    }

    // Gen Key with p q e
    public static void GenKeyWithValues(BigInteger p, BigInteger q, BigInteger e) throws IOException {

        File publicFile = new File("pub_key.txt");
        File privateFile = new File("pri_key.txt");

        // TODO: check if p and q are primes
        if (!p.isProbablePrime(1) ){
            System.out.println("p is not a prime");
        }
        else if (!q.isProbablePrime(1) ){
            System.out.println("q is not a prime");
        } else {
            // TODO: Part 2 --> Compute n = pq
            //  use BigInteger class methods .multiply()
            BigInteger n = p.multiply(q);

            // TODO: Part 3 --> phi --- (p-1) * (q-1)
            //  use BigInteger class methods
            BigInteger O = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

            // TODO: Part 4 --> choose e | 1 < e < O(n)
            //  create a helper method and pass in O(n)
            //  already have e
            //  Check if the given e has a gcd of 1 with phi -> O
            if(!(O.gcd(e)).equals(BigInteger.ONE)){
                System.out.println("Phi of p and q does not have a gcd of 1 with the given e");
            } else {

                // TODO: Part 5 --> d = e^(-1*O)
                //  use BigInteger class methods .modInverse(O)
                BigInteger d = e.modInverse(O);

                String publicKeyE = e.toString();
                String publicKeyN = n.toString();
                String privateKeyD = d.toString();
                String privateKeyN = n.toString();

                // output public key results to public file
                publicFile.createNewFile();
                PrintWriter pw = new PrintWriter(publicFile);
                pw.println("e = " + publicKeyE);
                pw.println("n = " + publicKeyN);
                pw.close();

                // output private key results to public file
                privateFile.createNewFile();
                PrintWriter pw_pri = new PrintWriter(privateFile);
                pw_pri.println("d = " + privateKeyD);
                pw_pri.println("n = " + privateKeyN);
                pw_pri.close();
            }
        }
    }

    // Helper methods
    public static BigInteger CoPrime(BigInteger O){
        Random r = new Random();
        int L = O.bitLength()-1;
        BigInteger e = BigInteger.probablePrime(L, r);

        // TODO: make sure e and O have a gcd of 1 which make them coprimes
        while(!(O.gcd(e)).equals(BigInteger.ONE)){
            e = BigInteger.probablePrime(L, r);
        };
        return e;
    }
    public static void NotValid(){
        System.out.println("Incorrect format");
        System.out.println("Must either be");
        System.out.println("java RSAGenKey.java (Numeric value for key size) ");
        System.out.println("java RSAGenKey.java p q e      // where p, q, e are numeric values");
    }
}
