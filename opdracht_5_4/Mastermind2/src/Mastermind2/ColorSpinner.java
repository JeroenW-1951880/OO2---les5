package Mastermind2;

import javax.print.DocFlavor;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/***
 * class to contain a Jspinner having values matching the colors of the Mastermind game
 * @author Jeroen Weltens
 */
public class ColorSpinner {
    private JSpinner spinner;

    /***
     * constructor --> adds all colors to the spinner
     * (initial intended for the spinner to contain images)
     */
    public ColorSpinner()
    {

        List<String> elems = Arrays.asList(new String[]{
                "black", "blue", "orange", "red", "white", "yellow"
        });
        ;

        SpinnerListModel colormodel = new SpinnerListModel(elems);

        spinner = new JSpinner(colormodel);
    }

    /** getter for the spinner */
    public JSpinner getSpinner(){
        return spinner;
    }
}
