package tjava.siritori2;

public class InvalidWordException extends IllegalArgumentException {
    public InvalidWordException(String msg,String word){
        super(msg+"___"+word);
    }
}
