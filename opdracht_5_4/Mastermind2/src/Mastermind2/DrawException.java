package Mastermind2;

public class DrawException extends RuntimeException {
    public String message;

    public DrawException(String message){
        this.message = message;
    }

    // Overrides Exception's getMessage()
    @Override
    public String getMessage(){
        return message;
    }
}