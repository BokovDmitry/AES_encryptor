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
                //Gets the file the user wants to decrypt
                System.out.println("Which file you want to decrypt? (e.g. decryptme.txt)");
                System.out.println("Type quit to go back to the menu");
                String fileName = rc.nextLine();
                //if 'quit' command is entered - return to the menu
                if (fileName.equalsIgnoreCase("quit"))
                {
                    Menu.displayMenu();
                    return;
                }
                File decryptFile = new File(fileName);
                File targetFile = new File("plaintext.txt");
                Scanner read = new Scanner(decryptFile);

                //Prompts the user to input a valid key
                System.out.println("Please enter a valid key: ");
                String encodedKey = rc.nextLine();
                //Converts the String to SecretKey instance
                byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
                SecretKey key = new SecretKeySpec(decodedKey, "AES");

                //Gets the ciphered text from the given file
                String cipheredText = "";
                while (read.hasNextLine())
                    cipheredText += read.nextLine();
                read.close();

                //Decryption process
                //source: https://www.baeldung.com/java-aes-encryption-decryption
                Cipher cipher = Cipher.getInstance(algorithm);
                cipher.init(Cipher.DECRYPT_MODE, key, iv);
                byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipheredText));
                String result = new String(plainText, StandardCharsets.UTF_8);

                try {
                    //Writes the result into a target file (plaintext.txt)
                    FileWriter writer = new FileWriter(targetFile);
                    writer.write(result);
                    writer.close();
                    //Displays the message that the given file was successfully decrypted and the result is written at the location shown
                    System.out.println(fileName + " successfully decrypted to a file plaintext.txt. ( "+targetFile.getAbsolutePath()+" )");
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
