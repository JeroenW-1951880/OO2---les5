package Mastermind2;

import javax.swing.*;

/***
 * this class is a container for a single field on the mastermind board
 * @author Jeroen Weltens
 */
public class MasterMindField extends JLabel {

    /***
     * constructor --> a label of an empty field
     * @param isKey to indicate if field is a key-field or a regular field
     */
    public MasterMindField(boolean isKey)
    {
        if(isKey){
            setIcon(createImageIcon("sprites/empty_key.png"));
        } else{
            setIcon(createImageIcon("sprites/empty_fill.png"));
        }
    }

    /***
     * method to update regular MasterMind field
     * @param color the color of the fill for the field
     * @post the field will be changed to the given color
     */
    public void update_fill(MasterMindMatrix.Colors color)
    {
        switch (color){
            case BLACK:
                setIcon(createImageIcon("sprites/black_fill.png"));
                break;
            case BLUE:
                setIcon(createImageIcon("sprites/blue_fill.png"));
                break;
            case ORANGE:
                setIcon(createImageIcon("sprites/orange_fill.png"));
                break;
            case WHITE:
                setIcon(createImageIcon("sprites/white_fill.png"));
                break;
            case YELLOW:
                setIcon(createImageIcon("sprites/yellow_fill.png"));
                break;
            case RED:
                setIcon(createImageIcon("sprites/red_fill.png"));
                break;
            case EMPTY:
                setIcon(createImageIcon("sprites/empty_fill.png"));
                break;
            default:
                System.err.println("invalid color update for fill");
        }
    }

    /***
     * method to update MasterMind keyfield
     * @param blackKeys the amount of black keys needed on the field
     * @param whiteKeys the amount of white keys needed on the field
     * @post the label will be updated to a field with the specified amount of black and white keys
     */
    public void update_key(int blackKeys, int whiteKeys)
    {
        String path = "sprites/";
        if(blackKeys == 0 && whiteKeys == 0){
            path += "empty";
        } else if(blackKeys == 0){
            path += whiteKeys + "w";
        } else if(whiteKeys == 0){
            path += blackKeys + "b";
        } else {
            path += blackKeys + "b" + whiteKeys + "w";
        }
        path += "_key.png";
        setIcon(createImageIcon(path));
    }

    /***
     * method to make a field hidden for the user
     * @post changes the label of the field to a blanc field
     */
    public void setHiddenCode()
     {
         setIcon(createImageIcon("sprites/code_hidden.png"));
     }

    /***
     * stolen from VOR code :)
     * sets the label of a field to a requested image
     * @param path the path of the image
     * @return the label with the image
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MasterMindField.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }



}
