package Mastermind2;

import static Mastermind2.GameModel.ATTEMPTS;
import static Mastermind2.GameModel.DIGITS;

/***
 * class container for all keys that indicate the number of correct digits
 * @author Jeroen Weltens
 */
public class KeyMatrix {
    enum Key{ NONE, WHITE, BLACK };

    private Key[][] mMatrix = new Key[ATTEMPTS][DIGITS];

    /***
     * constructor --> initializes empty matrix
     */
    public KeyMatrix(){
        for (int i = 0; i < ATTEMPTS; ++i){
            for (int j = 0; j < DIGITS; ++j){
                mMatrix[i][j] = Key.NONE;
            }
        }
    }

    /***
     * method to insert a row of keys into the matrix
     * @pre 0 <= rowNumber < ATTEMPTS
     * @pre blackKeys + whiteKeys <= DIGITS, blackKeys & whiteKeys != 0
     * @param rowNumber the number of the row you want to insert
     * @param blackKeys the number of black keys in the row
     * @param whiteKeys the number of white keys in the row
     * @post the row is added to the keyMatrix
     */
    public void set_row(int rowNumber, int blackKeys, int whiteKeys)
    {
        for(int i = 0; i < blackKeys; ++i){
            mMatrix[rowNumber][i] = Key.BLACK;
        }
        for(int i = blackKeys; i < blackKeys + whiteKeys; ++i){
            mMatrix[rowNumber][i] = Key.WHITE;
        }
    }

    /***
     * method to get a certain element in the matrix
     * @pre 0 <= row < ATTEMPTS
     * @pre 0 <= column < DIGITS
     * @param row the row of the element
     * @param column the column of the element
     * @return the element on the given location
     */
    public Key get_Fill(int row, int column) {
        return mMatrix[row][column];
    }

    /***
     * method to reset all elements of the matrix to empty
     * @post all elements of the matrix are empty
     */
    public void reset()
    {
        for (int i = 0; i < ATTEMPTS; ++i){
            for (int j = 0; j < DIGITS; ++j){
                mMatrix[i][j] = Key.NONE;
            }
        }
    }
}
