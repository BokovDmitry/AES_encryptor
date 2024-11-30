import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class Encryptor extends AESUtils{

    public static String encrypt(String algorithm, SecretKey key, IvParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Scanner rc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Which file you want to encrypt? (e.g. encryptme.txt)");
                String fileName = rc.nextLine();
                File file = new File(fileName);
                Scanner read = new Scanner(file);

                String plainText = "";
                while (read.hasNextLine())
                    plainText += read.nextLine();
                read.close();

                Cipher cipher = Cipher.getInstance(algorithm);
                cipher.init(Cipher.ENCRYPT_MODE, key, iv);
                byte[] cipherText = cipher.doFinal(plainText.getBytes());
                return Base64.getEncoder().encodeToString(cipherText);

            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please try again.");
            }
        }
    }
}
