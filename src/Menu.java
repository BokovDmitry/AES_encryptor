import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    // Instance variable to store the ivParameterSpec
    private static IvParameterSpec ivParameterSpec;

    public static void displayMenu() {
        try {
            System.out.println("1. Encrypt a File");
            System.out.println("2. Decrypt a File");
            System.out.println("3. Quit the application");

            Scanner input = new Scanner(System.in);
            int option = input.nextInt();

            switch (option) {
                case 1:
                    encryptFile();
                    break;
                case 2:
                    decryptFile();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid option");
                    displayMenu();
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            System.out.println("Input should be a number from 1 to 3");
            displayMenu();
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public static void encryptFile() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        SecretKey key = AESUtils.generateKey(128);
        ivParameterSpec = AESUtils.generateIv();
        Encryptor.encrypt("AES/CBC/PKCS5Padding", key, ivParameterSpec);
    }

    public static void decryptFile() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if (ivParameterSpec == null) {
            System.out.println("Error: No IV for decryption. Please encrypt a file first.");
            return;
        }
        // Use the stored ivParameterSpec for decryption
        Decryptor.decrypt("AES/CBC/PKCS5Padding", ivParameterSpec);
    }
}
