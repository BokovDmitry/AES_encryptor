
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void Menu()
    {
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
                    Menu();
                    break;

            }
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input");
            System.out.println("Input should be a number from 1 to 3");
            Menu();
        }

    }


    public static void main(String[] args) {
        Menu();
    }
}