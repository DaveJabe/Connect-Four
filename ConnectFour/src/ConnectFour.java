import java.util.Scanner;
public class ConnectFour {

    // METHODS

    public static boolean legalBoardSize(int height, int width) {
        if (height >= 4 && width >= 5)
            return true;
        System.out.println();
        if (height < 4)
            System.out.println("Height must be greater than or equal to 4!");
        if (width < 5)
            System.out.println("Width must be greater than or equal to 5!");
        System.out.println();
        return false;
    }

    // This will set each spot in the array = to '-'
    public static void initializeBoard(char[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) { // 'for' loop within a 'for loop to account for each index
                array[i][j] = '-'; // sets each index to = '-'
            }
        }
    }

    // This will print the board
    public static void printBoard(char[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) { // 'for' loop within a 'for' loop to print each index of the board array
                    System.out.print(array[i][j] + " "); /* prints each index in a row and extra white space
                                                         (this will keep indices from touching each other when printed) */
                }
            System.out.println(); // prints a new line for the next row to begin
            }
    }

    public static char[][] setUpBoard(int height, int width) {
            char[][] board = new char[height][width]; // initializes char[][] to be used as board for the game
            initializeBoard(board); // call to initialize board (set each index = '-')
            printBoard(board); // call to print the board
            System.out.println();
            System.out.println("Player 1: x");
            System.out.println("Player 2: o");
            System.out.println();
            return board;
    }

    //Places the token in the column that the user has chosen.
    // Will find the next available spot in that column if there are already tokens there.
    // The row that the token is placed in is returned.
    public static int insertChip(char[][] array, int col, char chipType) {
        int count = array.length - 1; // initializes "count" variable to return the row in which the chip is inserted
        for (int i = array.length - 1; i >= 0; i--) { // 'for' loop, starting at the bottom row, inserting a chip if the space is "empty" (contains a '-')
            if (array[i][col] == '-') {
                array[i][col] = chipType;
                return count;
            }
            count--; // subtracts from count if the row was already occupied by a chip
        }
        return -1; // "out of bounds case"; returns - 1 to indicate chip could not be inserted (column is full)
    }

    //After a token is added, checks whether the token in this location of the specified chip type, creates four in a row.
    // Will return true if someone won, and false otherwise.
    public static boolean checkIfWinner(char[][] array, int col, int row, char chipType) {
        int inARow = 0; // initializes variable to track how many of the same chip type in a row
        int inACol = 0; // initializes variable to track how many of the same chip type in a column
        for (int i = 0; i < array[row].length; i++) { // 'for' loop to check for the player's chip type at each index in a row

            if (array[row][i] == chipType) // adds to "inARow" if the same chip type is found at the index of a row
                inARow++;

            else  // else, "inARow" is reset
                inARow = 0;

            if (inARow == 4) // declares winner if 4 of the same chip type in a row (in a row)
                return true;
        }
        for (int i = 0; i < array.length; i++) { // 'for' loop to check for the player's chip type at each index in a column

            if (array[i][col] == chipType) // adds to "inACol" if the same chip type is found at the index of a column
                    inACol++;

                else // else, "inACol" is reset
                    inACol = 0;

            if (inACol == 4) // declares winner if 4 of the same chip type in a row (in a column)
                return true;
        }
        return false; // declares no winner
    }

    public static boolean checkIfDraw(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public static void playerPrompt(int playerNumber, int boardHeight, int boardWidth, char[][] board) {
        char chipType = playerNumber == 1 ? 'x' : 'o';
        Scanner scnr = new Scanner(System.in);
        boolean check1 = false; // Checks that column is within bounds
        boolean check2 = false; // Checks that column is not full
        int col;
        int row;

        do {
            do {
                System.out.print("Player " + playerNumber + ": Which column would you like to choose? (1 - " + boardWidth + ")");
                col = scnr.nextInt() - 1; // player selects column they would like to place their chip (subtract 1 because array starts at 0)
                if (col >= boardWidth) {
                    System.out.println();
                    System.out.println("Column out of bounds!");
                    System.out.println();
                } else
                    check1 = true;
            } while (!check1);

            row = insertChip(board, col, chipType);
            System.out.println();

            if (row == -1) { // insertChip() returns -1 if the column is full (chip could not be inserted)
                System.out.println("No more room for chips in that column!");
            } else {
                printBoard(board); // call to print updated board
                check2 = true;
            }
            System.out.println();
        } while (!check2);

        if (checkIfWinner(board, col, row, chipType)) { // if true is returned, player 1 wins the game
            System.out.println("Player " + playerNumber + " won the game!");
            System.exit(0);
        }

        if (checkIfDraw(board)) { // if draw = true, all indices have a chip and nobody wins
                System.out.println("Draw. Nobody wins.");
                System.exit(0);
        }
    }

    // MAIN PROGRAM

    @SuppressWarnings("InfiniteLoopStatement") // Included to suppress warning for infinite while loop that wraps player prompts
    public static void main(String[] args) {
        int height = 0; // initializes height dimension for array
        int width = 0; // initializes length dimension for array
        Scanner scnr = new Scanner(System.in); // new Scanner instance
        boolean loop = true;

        while (loop) {
            System.out.print("What would you like the height of the board to be? ");
            height = scnr.nextInt(); // height = user input
            System.out.print("What would you like the length of the board to be? ");
            width = scnr.nextInt(); // length = user input

            loop = !legalBoardSize(height, width);
        }

        char[][] board = setUpBoard(height, width);

        while (true) { // while loop to maintain game
            playerPrompt(1, height, width, board);
            playerPrompt(2, height, width, board);
        }
    }
}
