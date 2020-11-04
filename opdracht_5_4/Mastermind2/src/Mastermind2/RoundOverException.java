package Mastermind2;

public class RoundOverException extends RuntimeException {
    public String message;

    public RoundOverException(String message){
        this.message = message;
    }

    // Overrides Exception's getMessage()
    @Override
    public String getMessage(){
        return message;
    }
}
