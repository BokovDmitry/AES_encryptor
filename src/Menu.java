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
            System.out.println("Something went wrong...");
        }
    }

    //Function that lets the user set the AES key size
    public static int keySizeMenu()
    {
        try
        {
            Scanner input = new Scanner(System.in);
            System.out.println("Select the AES key size: ");
            System.out.println("1. 128");
            System.out.println("2. 192");
            System.out.println("3. 256");
            int option = input.nextInt();
            int keySize;

            switch (option)
            {
                case 1: keySize = 128; break;
                case 2: keySize = 192; break;
                case 3: keySize = 256; break;
                default: System.out.println("Invalid option. Input must be a number between 1 and 3"); return keySizeMenu();
            }

            System.out.println(keySize);

            return keySize;

        }catch (InputMismatchException e)
        {
            System.out.println("Invalid input. Input must be a number between 1 and 3");
            return keySizeMenu();
        }
    }

    //Encryption Function
    public static void encryptFile() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        try
        {
            int keySize = keySizeMenu();
            //generates a key of a given size (keySize)
            SecretKey key = AESUtils.generateKey(keySize);
            //Generates an initialisation vector and assigns the value to an instance variable ivParameterSpec
            ivParameterSpec = AESUtils.generateIv();
            //Encrypt function is called
            Encryptor.encrypt("AES/CBC/PKCS5Padding", key, ivParameterSpec);
        }catch (InputMismatchException e)
        {
            System.out.println("Invalid input");
        }
    }

    public static void decryptFile() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        //Checks if the initialisation vector exists. If false prints out the error message
        if (ivParameterSpec == null) {
            System.out.println("Error: No IV for decryption. Please encrypt a file first.");
            return;
        }
        // If true calls the decrypt function with appropriate key and initialisation vector
        Decryptor.decrypt("AES/CBC/PKCS5Padding", ivParameterSpec);
    }
}
