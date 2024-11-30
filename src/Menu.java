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
    public void displayMenu() {
        try
        {
            System.out.println("1. Encrypt a File");
            System.out.println("2. Decrypt a File");
            System.out.println("3. Quit the application ");

            Scanner input = new Scanner(System.in);
            int option = input.nextInt();

            switch(option) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid option");
                    displayMenu();
                    break;

            }
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input");
            System.out.println("Input should be a number from 1 to 3");
            displayMenu();
        }
    }
    public void encryptFile() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Encryptor encryptor = new Encryptor();
        SecretKey key = AESUtils.generateKey(128);
        IvParameterSpec ivParameterSpec = AESUtils.generateIv();
        Encryptor.encrypt("AES/CBC/PKCS5Padding", key, ivParameterSpec);
    }
}
