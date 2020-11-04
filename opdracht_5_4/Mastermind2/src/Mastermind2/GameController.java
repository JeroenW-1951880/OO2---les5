package Mastermind2;

/***
 * controller for the game of mastermind
 * controls most communication from view to model
 *
 * @author Jeroen Weltens
 */
public class GameController {
    private GameModel mModel;
    private GameView mView;

    /**
     * constructor --> ads the model and view of the game
     * @pre model and view are initialized
     * @param model model of the mastermindgame
     * @param view view of the mastermindgame
     */
    public GameController(GameModel model, GameView view)
    {
        mModel = model;
        mView = view;
    }

    /***
     * method to tell the model to start the game
     * @param p2_bot param to tell if player 2 is a bot or a person
     */
    public void onStartGame(boolean p2_bot)
    {
        mModel.init_game(p2_bot);
    }

    /***
     * method to pass a code entered by the user to the model
     * @pre code contains exactly DIGITS digits and is nowhere empty
     * @param code the code to pass to the model
     */
    public void onCodeSubmit(MasterMindMatrix.Colors[] code)
    {
        mModel.accept_code(code);
        if(mModel.ismPlayer2Bot() && mModel.isPlayer1Codemaker()){
            try{
                mModel.bot_guessRound();
            } catch (RoundOverException e){
                mView.round_over();
                mView.modify_scoreboard();
            }
        } else{
            mView.setup_guessRound(mModel.isPlayer1Codemaker());
        }
    }

    /***
     * method to pass a guess entered by the user to the model
     * @pre code contains exactly DIGITS digits and is nowhere empty
     * @param guess the guess to pass to the model
     */
    public void onGuessSubmit(MasterMindMatrix.Colors[] guess)
    {
        try{
            mModel.accept_guess(guess);
        } catch (RoundOverException e) {
            mView.modify_scoreboard();
            if (mModel.isPlayer1Codemaker()){
                mView.round_over();
                //notify players round is over
            } else{
                try{
                    int winnerNumber = mModel.get_winningPlayernumber();
                    mView.game_overWinner(winnerNumber);
                    //notify players that game is done
                } catch (DrawException ex){
                    mView.game_overDraw();
                    //notify players of draw
                }
            }
        }
    }

    /***
     * method to signal the model to start the new round of the game after the first is done
     */
    public void onNewRound()
    {
        mModel.start_newRound();
        if(mModel.ismPlayer2Bot()){
            mModel.bot_makeCode();
            mView.setup_guessRound(false);
        } else{
            mView.setup_codeInput();
        }
    }

}
