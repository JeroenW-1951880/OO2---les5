package Mastermind2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static Mastermind2.GameModel.ATTEMPTS;
import static Mastermind2.GameModel.DIGITS;


/***
 * this class contains all elements of the GUI of our mastermind game
 *
 * @author Jeroen Weltens
 */
public class GameView extends JFrame implements Observer{
    private GameModel mModel;
    private GameController mController;

    private BoardView mBoard;

    private JPanel mContent;

    private JPanel mUpperContent;
    private JPanel mLowerContent;

    private JLabel mP1ScoreBoard;
    private JLabel mP2ScoreBoard;

    private JRadioButton mVsPlayer;
    private JRadioButton mVsBot;

    private JLabel mInstruction;

    private ColorSpinner mDigit1;
    private ColorSpinner mDigit2;
    private ColorSpinner mDigit3;
    private ColorSpinner mDigit4;

    private JButton mSubmitCode;

    private JButton mStartGame;

    /***
     * polymorph update method to handle a notify from the model
     * makes the Mastermind board up to date according to the data of the model
     * @param o the mastermindmodel
     * @param arg nothing in this case
     */
    @Override
    public void update(Observable o, Object arg) {
        MasterMindMatrix.Colors[][] colors = new MasterMindMatrix.Colors[ATTEMPTS][DIGITS];
        int[] blackKeys = new int[ATTEMPTS];
        int[] whiteKeys = new int[ATTEMPTS];

        for(int i = 0; i < ATTEMPTS; i++){
            for(int j = 0; j < DIGITS; j++){
                colors[i][j] = mModel.get_matrixFill(i, j);
            }
            int b_counter = 0, w_counter = 0;
            for (int j = 0; j < DIGITS; ++j){
                if(mModel.get_keyMatrixFill(i,j) == KeyMatrix.Key.BLACK)
                    b_counter++;
                else if(mModel.get_keyMatrixFill(i,j) == KeyMatrix.Key.WHITE)
                    w_counter++;
            }
            blackKeys[i] = b_counter;
            whiteKeys[i] = w_counter;

        }
        mBoard.update_board(colors, blackKeys, whiteKeys);
    }

    /***
     * constructor of the UI of the game
     * @param model the gamemodel of the game
     * @post the UI with the gameboard is formed and shown to the user
     */
    public GameView(GameModel model)
    {
        model.addObserver(this);
        mModel = model;
        mController = new GameController(mModel, this);

        mContent = new JPanel();
        mContent.setLayout(new BoxLayout(mContent, BoxLayout.Y_AXIS));

        mUpperContent = new JPanel();
        addOpponentChooser();

        mBoard = new BoardView();

        mLowerContent = new JPanel();
        addStartGameButton();

        mContent.add(mUpperContent);
        mContent.add(mBoard);
        mContent.add(mLowerContent);

        getContentPane().add(mContent);
        pack();
        setVisible(true);
    }

    /***
     * method to add the part to the UI where the player can choose to play a bot or another player
     */
    private void addOpponentChooser()
    {
        mUpperContent.setLayout(new BoxLayout(mUpperContent, BoxLayout.X_AXIS));
        ButtonGroup choices = new ButtonGroup();
        mVsPlayer = new JRadioButton();
        mVsPlayer.setText("VS Player");
        mVsPlayer.setSelected(true);
        choices.add(mVsPlayer);
        mVsBot = new JRadioButton();
        mVsBot.setText("VS Bot");
        choices.add(mVsBot);
        mUpperContent.add(mVsPlayer);
        mUpperContent.add(mVsBot);
    }

    /**
     * method to add the button to the game to start the game
     */
    private void addStartGameButton()
    {
        mStartGame = new JButton("Start Game");
        mLowerContent.add(mStartGame);
        mStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean opp_isBot = mVsBot.isSelected();
                mController.onStartGame(opp_isBot);
                setup_UIForStart();
            }
        });
    }

    /**
     * method that calls other methods to make the UI ready to start the game
     */
    private void setup_UIForStart()
    {
        init_scoreboards();
        init_GameInput();
    }

    /**
     * Method to replace the opponentchooser with a scoreboard after the game is started
     */
    private void init_scoreboards()
    {
        mUpperContent.removeAll();
        mUpperContent.setLayout(new BoxLayout(mUpperContent, BoxLayout.X_AXIS));
        mP1ScoreBoard = new JLabel("Player1: ");
        mP2ScoreBoard = new JLabel("Player2: ");

        mUpperContent.add(mP1ScoreBoard);
        mUpperContent.add(Box.createHorizontalStrut(10));
        mUpperContent.add(mP2ScoreBoard);
        mUpperContent.revalidate();
        mUpperContent.repaint();
    }

    /**
     * mehod to setup Userinputfields and buttons to play the game
     */
    private void init_GameInput()
    {
        mLowerContent.removeAll();
        mLowerContent.setLayout(new BoxLayout(mLowerContent, BoxLayout.Y_AXIS));

        mInstruction = new JLabel("player 1 can make a code");

        mLowerContent.add(mInstruction);

        JPanel spinners = new JPanel();
        spinners.setLayout(new BoxLayout(spinners, BoxLayout.X_AXIS));

        mDigit1 = new ColorSpinner();
        mDigit2 = new ColorSpinner();
        mDigit3 = new ColorSpinner();
        mDigit4 = new ColorSpinner();

        spinners.add(mDigit1.getSpinner());
        spinners.add(mDigit2.getSpinner());
        spinners.add(mDigit3.getSpinner());
        spinners.add(mDigit4.getSpinner());

        mLowerContent.add(spinners);

        mSubmitCode = new JButton("submit code");

        mSubmitCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mController.onCodeSubmit(get_codeFromSpinners());
            }
        });

        mLowerContent.add(mSubmitCode);

        mLowerContent.revalidate();
        mLowerContent.repaint();

        pack();
    }

    /**
     * method to update the scoreboard
     */
    public void modify_scoreboard()
    {
        mP1ScoreBoard.setText("player 1: " + mModel.get_player1Turnsneeded());
        mP2ScoreBoard.setText("player 2: " + mModel.get_player2Turnsneeded());
    }

    /**
     * method to communicate to the players that a round is over via the UI
     */
    public void round_over()
    {
        mInstruction.setText("the round is over, the roles will be switched now");
        mBoard.show_code(mModel.get_code());

        mSubmitCode.setText("new round");
        for( ActionListener al : mSubmitCode.getActionListeners() ) {
            mSubmitCode.removeActionListener( al );
        }
        mSubmitCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mController.onNewRound();
            }
        });
    }


    /**
     * method to setup the UI to start a round of guessing by a player
     * @param is_p2Guesser true --> player 2 is guesser, false --> player 1
     */
    public void setup_guessRound(boolean is_p2Guesser)
    {
        mBoard.hide_code();
        if(is_p2Guesser){
            mInstruction.setText("player 2 is up to guess");
        } else{
            mInstruction.setText("player 1 is up to guess");
        }

        mSubmitCode.setText("submit guess");
        for( ActionListener al : mSubmitCode.getActionListeners() ) {
            mSubmitCode.removeActionListener( al );
        }
        mSubmitCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mController.onGuessSubmit(get_codeFromSpinners());
            }
        });
    }

    /**
     * method to collect the colors from the spinboxes to form a mastermind code
     */
    private MasterMindMatrix.Colors[] get_codeFromSpinners()
    {
        String[] values = new String[DIGITS];
        values[0] = (String)mDigit1.getSpinner().getValue();
        values[1] = (String)mDigit2.getSpinner().getValue();
        values[2] = (String)mDigit3.getSpinner().getValue();
        values[3] = (String)mDigit4.getSpinner().getValue();

        MasterMindMatrix.Colors[] code = new MasterMindMatrix.Colors[DIGITS];
        for(int i = 0; i < DIGITS; ++i){
            switch (values[i]) {
                case "blue":
                    code[i] = MasterMindMatrix.Colors.BLUE;
                    break;
                case "black":
                    code[i] = MasterMindMatrix.Colors.BLACK;
                    break;
                case "red":
                    code[i] = MasterMindMatrix.Colors.RED;
                    break;
                case "yellow":
                    code[i] = MasterMindMatrix.Colors.YELLOW;
                    break;
                case "white":
                    code[i] = MasterMindMatrix.Colors.WHITE;
                    break;
                case "orange":
                    code[i] = MasterMindMatrix.Colors.ORANGE;
                    break;
                default:
                    System.err.println(values[i] + "isnt recognized");
                    break;
            }
        }
        return code;
    }

    /**
     * method for when the game is over and there is a winner
     * @param winnernum the playernumber of the player who won
     * @post the button to continue the game is disabled, so the game is stopped
     */
    public void game_overWinner(int winnernum)
    {
        String congrats = "player " + winnernum + " has won!";
        mInstruction.setText(congrats);
        mSubmitCode.setEnabled(false);
        mBoard.show_code(mModel.get_code());
    }

    /**
     * method for when the game is over and there is no winner
     * @post the button to continue the game is disabled, so the game is stopped
     */
    public void game_overDraw()
    {
        mInstruction.setText("The game ended in a draw");
        mSubmitCode.setEnabled(false);
        mBoard.show_code(mModel.get_code());
    }

    /***
     * method to setup UI to receive a code for the round of mastermind
     */
    public void setup_codeInput() {
        mBoard.hide_code();
        mInstruction.setText("player 2 can make a code");
        mSubmitCode.setText("submit code");
        for( ActionListener al : mSubmitCode.getActionListeners() ) {
            mSubmitCode.removeActionListener( al );
        }
        mSubmitCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mController.onCodeSubmit(get_codeFromSpinners());
            }
        });

    }
}
