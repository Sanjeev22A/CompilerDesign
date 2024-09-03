import java.util.Scanner;

public class ParserCaller {
    public static void main(String[] args) {
        // Create a Scanner object to read input from the user
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for input
        System.out.println("Enter the code to be parsed:");

        // Read the input string from the user
        String input = scanner.nextLine();

        // Create a Parser instance
        Parser parser = new Parser(input);

        // Initialize the current state (if needed)
        parser.currentState = -1; // Assuming starting with a keyword

        // Parse the input string
        parser.parseFunc(input);

        // Close the scanner
        scanner.close();
    }
}
