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
                System.out.println("Which file you want to encrypt? (e.g. encryptme.txt)");
                String fileName = rc.nextLine();
                File encryptFile = new File(fileName);
                Scanner read = new Scanner(encryptFile);

                String plainText = "";
                while (read.hasNextLine())
                    plainText += read.nextLine();
                read.close();

                Cipher cipher = Cipher.getInstance(algorithm);
                cipher.init(Cipher.ENCRYPT_MODE, key, iv);
                byte[] cipherText = cipher.doFinal(plainText.getBytes());
                String result = Base64.getEncoder().encodeToString(cipherText);

                try {
                    FileWriter writer = new FileWriter("ciphertext.txt");
                    writer.write(result);
                    writer.close();
                    System.out.println(fileName + " successfully encrypted.");
                    System.out.println("Key : " + Base64.getEncoder().encodeToString(key.getEncoded()));
                    Menu.displayMenu();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    encrypt(algorithm, key, iv);
                }

            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please try again.");
                encrypt(algorithm, key, iv);
            }
    }
}
