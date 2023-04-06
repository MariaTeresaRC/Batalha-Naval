package batalhanaval;

/**
 *
 * @author Maria Teresa
 */
public class ExcecaoForaDoTabuleiro extends Exception{
    public ExcecaoForaDoTabuleiro(){
    }
    
    public ExcecaoForaDoTabuleiro(String msg){
        super(msg);
    }
}
