package Mastermind2;

import javax.swing.*;

import static Mastermind2.GameModel.ATTEMPTS;
import static Mastermind2.GameModel.DIGITS;

/***
 * class that is a UI of the Mastermind board
 * @author Jeroen Weltens
 */
public class BoardView extends JPanel{

    private MasterMindField[][] mMatrixLabels = new MasterMindField[ATTEMPTS][DIGITS];
    private MasterMindField[] mKeyLabels = new MasterMindField[ATTEMPTS];
    private MasterMindField[] mCodeLabels = new MasterMindField[DIGITS + 1];

    /***
     * constructor --> makes an empty version of the mastermind board
     * @post the layout of the panel is set
     * @post all MastermindFields are created and added to the layout
     */
    public BoardView()
    {
        setLayout(new java.awt.GridLayout(ATTEMPTS + 1, DIGITS + 1, 0, 0));
        for (int i = 0; i < ATTEMPTS + 1; i++) {
            for (int j = 0; j < DIGITS + 1; j++) {
                if(i < ATTEMPTS && j < DIGITS){ //main fields
                    mMatrixLabels[i][j] = new MasterMindField(false);
                    add(mMatrixLabels[i][j]);
                }
                else if (i != ATTEMPTS && j == DIGITS) { //key fields
                    mKeyLabels[i] = new MasterMindField(true);
                    add(mKeyLabels[i]);
                } else{ //code fields
                    mCodeLabels[j] = new MasterMindField(false);
                    mCodeLabels[j].setHiddenCode();
                    add(mCodeLabels[j]);
                }
            }
        }
    }

    /***
     * method to update the board to present given data
     * @pre fills contains ATTEMPTS rows and DIGIT collumns that are not null
     * @pre blackKeys and whiteKeys contain ATTEMPTS elements
     * @pre 0 <= blackKeys[i] + whiteKeys[i] <= 4
     * @param fills the fill_matrix of the board
     * @param blackKeys the number of black keys per attempt
     * @param whiteKeys the number of white keys per attempt
     * @post the board shows the data asked
     */
    public void update_board(MasterMindMatrix.Colors[][] fills, int[] blackKeys, int[] whiteKeys)
    {
        for (int i = 0; i < ATTEMPTS + 1; i++) {
            for (int j = 0; j < DIGITS + 1; j++) {
                if(i < ATTEMPTS && j < DIGITS){
                    mMatrixLabels[i][j].update_fill(fills[i][j]);
                } else if (i != ATTEMPTS && j == DIGITS) {
                    mKeyLabels[i].update_key(blackKeys[i], whiteKeys[i]);
                }
            }
        }
    }

    /***
     * method to show a given code to the user
     * @pre the code is exactly DIGITS long and doesn't contain null
     * @param code the code to show on the board
     * @post the given code will be shown at the end of the board
     */
    public void show_code(MasterMindMatrix.Colors[] code)
    {
        for(int i = 0; i < DIGITS; ++i){
            mCodeLabels[i].update_fill(code[i]);
        }
    }

    /***
     * method to hide the code to search for from the players
     */
    public void hide_code()
    {
        for(int i = 0; i < DIGITS; ++i){
            mCodeLabels[i].setHiddenCode();
        }
    }

    /***
     * method to make board empty and ready for a new round
     * @post all fields of board show empty
     */
    public void reset_board()
    {
        for (int i = 0; i < ATTEMPTS + 1; i++) {
            for (int j = 0; j < DIGITS + 1; j++) {
                if(i < ATTEMPTS && j < DIGITS){
                    mMatrixLabels[i][j].update_fill(MasterMindMatrix.Colors.EMPTY);
                }
                else if (i != ATTEMPTS && j == DIGITS) {
                    mKeyLabels[i].update_key(0, 0);
                } else{
                    mCodeLabels[j].setHiddenCode();
                }
            }
        }
    }
}
