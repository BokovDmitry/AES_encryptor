import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class Encryptor extends AESUtils{

    public static void encrypt(String algorithm, SecretKey key, IvParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Scanner rc = new Scanner(System.in);

            try {
                //gets the file that needs to be encrypted
                System.out.println("Which file you want to encrypt? (e.g. encryptme.txt)");
                System.out.println("Type 'quit' to go back to the menu");
                String fileName = rc.nextLine();
                //if 'quit' command is entered - return to the menu
                if (fileName.equalsIgnoreCase("quit"))
                {
                    Menu.displayMenu();
                    return;
                }
                File encryptFile = new File(fileName);
                Scanner read = new Scanner(encryptFile);

                //Gets the text to encrypt from the given file
                String plainText = "";
                while (read.hasNextLine())
                    plainText += read.nextLine();
                read.close();

                //Encryption
                //source: https://www.baeldung.com/java-aes-encryption-decryption
                Cipher cipher = Cipher.getInstance(algorithm);
                cipher.init(Cipher.ENCRYPT_MODE, key, iv);
                byte[] cipherText = cipher.doFinal(plainText.getBytes());
                String result = Base64.getEncoder().encodeToString(cipherText);

                try {
                    //Writes the result into "ciphertext.txt"
                    FileWriter writer = new FileWriter("ciphertext.txt");
                    writer.write(result);
                    writer.close();
                    System.out.println(fileName + " successfully encrypted.");
                    //Displays the secret key in Base64 format, so it is more readable and easy to use
                    System.out.println("Key : " + Base64.getEncoder().encodeToString(key.getEncoded()));
                    Menu.displayMenu();
                } catch (IOException e) {
                    System.out.println("Something went wrong.");
                    encrypt(algorithm, key, iv);
                }

            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please try again.");
                encrypt(algorithm, key, iv);
            }
    }
}
