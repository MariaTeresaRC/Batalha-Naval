package batalhanaval;

/**
 *
 * @author Maria Teresa
 */
public class ExcecaoSobreposto extends Exception{
    public ExcecaoSobreposto(){
    }
    
    public ExcecaoSobreposto(String msg){
        super(msg);
    }
}
