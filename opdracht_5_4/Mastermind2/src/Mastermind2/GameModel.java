package Mastermind2;

import java.util.Random;

/***
 * class that contains all modules of gamelogic
 * @author Jeroen Weltens
 */
public class GameModel extends java.util.Observable{
    private Player mPlayer1;
    private Player mPlayer2;

    private int mTurnNumber;

    private boolean mPlayer1Codemaker;

    private MasterMindMatrix mMatrix;
    private KeyMatrix mKeyMatrix;

    /***
     * this override allows notifyObservers(), without calling setChanged first
     */
    @Override
    public void notifyObservers(){
        setChanged();
        super.notifyObservers();
    }

    /** some globals to use across the code */
    public static int ATTEMPTS = 12;
    public static int DIGITS = 4;

    /***
     * constructor --> initiates player 1, and the matrices to keep gamedata
     */
    public GameModel() {
        mMatrix = new MasterMindMatrix();
        mKeyMatrix = new KeyMatrix();
        mPlayer1 = new Player(false);
    }

    /***
     * this method is used to start a game
     * @param p2_bot to indicate if player 2 is a CPU or a player
     * @post player 2 is initiated and player 1 is set to begin as codemaker
     */
    public void init_game(boolean p2_bot)
    {
        mPlayer2 = new Player(p2_bot);
        mPlayer1Codemaker = true;
    }

    /***
     * this method is used to start a new round after a round is done
     * @post the matrices are reset and player 1 & 2 switched roles
     */
    public void start_newRound()
    {
        mMatrix.reset_matrix();
        mKeyMatrix.reset();

        if (mPlayer1Codemaker){
            mPlayer1Codemaker = false;
        } else{
            mPlayer1Codemaker = true;
        }

        notifyObservers();
    }

    /***
     * method to pass a code given for a turn to the matrix
     * @pre code must be exactly DIGITS long
     * @param code the code given fot the turn
     */
    public void accept_code(MasterMindMatrix.Colors[] code)
    {
        mMatrix.set_code(code);
        mTurnNumber = 0;
        notifyObservers();
    }

    public MasterMindMatrix.Colors[] get_code()
    {
        return mMatrix.get_code();
    }

    /***
     * method to pass a guess for the code for a turn to the matrix
     * @pre guess must be exactly DIGITS long
     * @param guess the code guess fot the turn
     * @throws RoundOverException when all guesses are used or the code is guessed correctly
     */
    public void accept_guess(MasterMindMatrix.Colors[] guess)
    {
        mMatrix.set_row(guess, mTurnNumber);
        try{
            mKeyMatrix.set_row(mTurnNumber, mMatrix.calc_numOf_blackKeys(mTurnNumber), mMatrix.calc_numOf_whiteKeys(mTurnNumber));
            ++mTurnNumber;
            if(mTurnNumber == ATTEMPTS){ //all guesses are up
                if(mPlayer1Codemaker){
                    mPlayer2.set_turnsneeded(mTurnNumber + 1);
                } else{
                    mPlayer1.set_turnsneeded(mTurnNumber + 1);
                }
                notifyObservers();
                throw new RoundOverException("guesses up"); //let know that the round is over
            } else {
                notifyObservers();
            }
        } catch (RoundOverException e){
            if(e.getMessage() == "guesses up"){
                throw e;
            }
            mKeyMatrix.set_row(mTurnNumber, DIGITS, 0);//the code is guessed correctly
            notifyObservers();
            if(mPlayer1Codemaker){
                mPlayer2.set_turnsneeded(mTurnNumber + 1);
            } else{
                mPlayer1.set_turnsneeded(mTurnNumber + 1);
            }
            throw e; //rethrow
        }
    }

    /***
     * method to get specific element from the Mastermind matrix
     * @pre 0 <= row <= ATTEMPTS
     * @pre 0 <= column <= DIGITS
     * @param row the row of the element in the matrix
     * @param column the column of the element in the matrix
     * @return the specified element
     */
    public MasterMindMatrix.Colors get_matrixFill(int row, int column)
    {
        return mMatrix.get_Fill(row, column);
    }

    /***
     * method to get specific element from the Key matrix
     * @pre 0 <= row <= ATTEMPTS
     * @pre 0 <= column <= DIGITS
     * @param row the row of the element in the matrix
     * @param column the column of the element in the matrix
     * @return the specified element
     */
    public KeyMatrix.Key get_keyMatrixFill(int row, int column)
    {
        return mKeyMatrix.get_Fill(row, column);
    }

    /***
     * method to check which player cracked the code in the least amount of guesses
     * @throws DrawException when it's a draw
     * @return 1 if player1 wins, 2 if player 2 wins
     */
    public int get_winningPlayernumber()
    {
        if(mPlayer1.getmTurnsNeeded() < mPlayer2.getmTurnsNeeded()){
            return 1;
        } else if(mPlayer1.getmTurnsNeeded() < mPlayer2.getmTurnsNeeded()){
            return 2;
        } else {
            throw new DrawException("draw");
        }
    }

    public boolean isPlayer1Codemaker() {
        return mPlayer1Codemaker;
    }

    public int get_player1Turnsneeded()
    {
        return mPlayer1.getmTurnsNeeded();
    }

    public int get_player2Turnsneeded()
    {
        return mPlayer2.getmTurnsNeeded();
    }

    public boolean ismPlayer2Bot(){ return mPlayer2.isBot(); }

    /***
     * this method does a round of randomly guessing the code
     * @throws RoundOverException when the round is over
     */
    public void bot_guessRound() {
        Random rand = new Random();
        try{
            while(true){
                accept_guess(generate_randomCode(rand));
            }
        }catch (RoundOverException e){
            throw e;
        }
    }

    /***
     * Let's the program generate a code for the game if the player is playing against a bot
     */
    public void bot_makeCode()
    {
        Random rand = new Random();
        accept_code(generate_randomCode(rand));
    }

    /***
     * method that generates a code of DIGITS colors
     * @param rand the random object for the code to be generated
     * @return the generated code
     */
    private MasterMindMatrix.Colors[] generate_randomCode(Random rand)
    {
        MasterMindMatrix.Colors[] guess = new MasterMindMatrix.Colors[DIGITS];
        for(int i = 0; i < DIGITS; i++){
            switch (rand.nextInt(6)){
                case 0:
                    guess[i] = MasterMindMatrix.Colors.BLACK;
                    break;
                case 1:
                    guess[i] = MasterMindMatrix.Colors.BLUE;
                    break;
                case 2:
                    guess[i] = MasterMindMatrix.Colors.RED;
                    break;
                case 3:
                    guess[i] = MasterMindMatrix.Colors.ORANGE;
                    break;
                case 4:
                    guess[i] = MasterMindMatrix.Colors.WHITE;
                    break;
                case 5:
                    guess[i] = MasterMindMatrix.Colors.YELLOW;
                    break;
                default:
                    System.err.println("invalid random number");
                    break;
            }
        }
        return guess;
    }
}
