package Mastermind2;

import java.util.Arrays;

import static Mastermind2.GameModel.ATTEMPTS;
import static Mastermind2.GameModel.DIGITS;

/***
 * class that is a container for the codes guessed and the code to guess of the game
 * also contains methods to calculate the number of white and black keys per row
 * @author Jeroen Weltens
 */
public class MasterMindMatrix {

    /** class to represent the colors/fill for the board */
    enum Colors{ EMPTY, WHITE, BLUE, YELLOW, ORANGE, RED, BLACK };

    private Colors[][] mMatrix = new Colors[ATTEMPTS][DIGITS];
    private Colors[] mCode = new Colors[DIGITS];

    /***
     * constructor --> initiates the matrix and makes it empty
     */
    public MasterMindMatrix()
    {
        for(int i = 0; i < ATTEMPTS; ++i){
            for(int j = 0; j < DIGITS; ++j){
                mMatrix[i][j] = Colors.EMPTY;
            }
        }
    };

    /***
     * sets the code to guess for current round
     * @param code a code chosen by the codemaster
     * @pre code has exactly the number of DIGITS
     * @pre code doesn't contain EMPTY or null
     * @post the code is saved in the object
     */
    public void set_code(Colors[] code)
    {
        mCode = code;
    }

    public Colors[] get_code() {
        return mCode;
    }

    /**
     * insert a row into the matrix
     * @pre row has exactly the number of DIGITS
     * @pre code doesn't contain EMPTY or null
     * @pre turnNumber is a number between 0 and ATTEMPTS-1
     * @param row the attempt you want to insert in the matrix
     * @param turnNumber the number of turns passed in current round
     */
    public void set_row(Colors[] row, int turnNumber)
    {
        mMatrix[turnNumber] = row;
    }

    /***
     * get an element out of the matrix
     * @pre 0 <= row < ATTEMPTS
     * @pre 0 <= column < DIGITS
     * @param row  the row of the selected element
     * @param column the column of the selected element
     * @return the selected element
     */
    public Colors get_Fill(int row, int column)
    {
        return mMatrix[row][column];
    }

    /***
     * method that calculates the amount of black keys for a specific guess
     * @pre 0 <= turnNumber < ATTEMPTS
     * @param turnNumber the number of the turn where you want the result of
     * @return the amount of black keys that result from that turn
     * @throws RoundOverException when all digits are guessed correctly
     */
    public int calc_numOf_blackKeys(int turnNumber)
    {
        int counter = 0;
        for (int i = 0; i < DIGITS; ++i){
            if (mMatrix[turnNumber][i] == Colors.EMPTY){
                return 0;
            }
            if (mCode[i] == mMatrix[turnNumber][i]){
                ++counter;
            }
        }
        if (counter == DIGITS){
            throw new RoundOverException("guessed");
        }
        return counter;
    }


    /***
     * method that calculates the amount of white keys for a specific guess
     * @pre 0 <= turnNumber < ATTEMPTS
     * @param turnNumber the number of the turn where you want the result of
     * @return the amount of white keys that result from that turn
     */
    public int calc_numOf_whiteKeys(int turnNumber)
    {
        if (mMatrix[turnNumber][0] == Colors.EMPTY){
            return 0;
        }
        int counter = 0;
        int[] black_key_positions = new int[DIGITS];
        for(int i = 0; i < DIGITS; ++i){
            black_key_positions[i] = -1;
        }
        int[] positions_in_code_used = new int[DIGITS]; //keeps up which digits are already used
        for (int j = 0; j < DIGITS; j++){
            positions_in_code_used[j] = -1;
        }
        for (int j = 0; j < DIGITS; j++){
            if(mCode[j] == mMatrix[turnNumber][j]){
                black_key_positions[j] = j;
            }
        }
        for (int i = 0; i < DIGITS; ++i){
            if (!int_in_array(black_key_positions, i)){ //else the key is black
                for (int j = 0; j < DIGITS; ++j){
                    if (!int_in_array(black_key_positions, j)){
                        if(mCode[j] == mMatrix[turnNumber][i]){
                            if (!int_in_array(positions_in_code_used, j)){
                                counter++;
                                positions_in_code_used[j] = j;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return counter;
    }

    /***
     * simple method to check of an array of integers contains a certain value
     * @pre the array is initialized
     * @param array the array to be evaluated
     * @param value the value to search
     * @return true if the value is in the array
     */
    private boolean int_in_array(int[] array, int value)
    {
        for(int i: array){
            if(i == value){
                return true;
            }
        }
        return false;
    }

    /***
     * method to set all elements from the matrix to EMPTY
     */
    public void reset_matrix(){
        for(int i = 0; i < ATTEMPTS; ++i){
            for(int j = 0; j < DIGITS; ++j){
                mMatrix[i][j] = Colors.EMPTY;
            }
        }
    }
}
