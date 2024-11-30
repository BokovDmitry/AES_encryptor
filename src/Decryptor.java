import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class Decryptor {

    public static void decrypt(String algorithm, IvParameterSpec iv){
        Scanner rc = new Scanner(System.in);
            try {
                System.out.println("Which file you want to decrypt? (e.g. decryptme.txt)");
                String fileName = rc.nextLine();
                File file = new File(fileName);
                Scanner read = new Scanner(file);

                System.out.println("Please enter a valid key: ");
                String encodedKey = rc.nextLine();
                byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
                SecretKey key = new SecretKeySpec(decodedKey, "AES");

                String cipheredText = "";
                while (read.hasNextLine())
                    cipheredText += read.nextLine();
                read.close();

                Cipher cipher = Cipher.getInstance(algorithm);
                cipher.init(Cipher.DECRYPT_MODE, key, iv);
                byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipheredText));
                String result = new String(plainText, StandardCharsets.UTF_8);

                try {
                    FileWriter writer = new FileWriter("plaintext.txt");
                    writer.write(result);
                    writer.close();
                    System.out.println(fileName + " successfully decrypted to a file plaintext.txt. ( "+file.getAbsolutePath()+" )");
                    Menu.displayMenu();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    decrypt(algorithm, iv);
                }

            }catch (FileNotFoundException e) {
                System.out.println("File Not Found");
                decrypt(algorithm, iv);
            } catch (Exception e) {
                System.out.println("Decryption failed: " + e.getMessage());
                decrypt(algorithm, iv);
            }
    }

}
