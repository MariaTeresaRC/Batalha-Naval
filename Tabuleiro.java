package batalhanaval;

/**
 *
 * @author Maria Teresa
 */
public class Tabuleiro {
    private Quadrado[][] tabuleiro;
    private boolean escondido;      //um tabuleiro escondido é o tabuleiro do oponente, em que voce não sabe onde estão os navios

    public Tabuleiro(boolean escondido) {
        this.tabuleiro = new Quadrado[10][10];
        this.escondido = escondido;
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                tabuleiro[i][j] = new Quadrado(i, j, '~');      //a princípio todos os quadrados tem água
            }
        }
    }
     
    public boolean checarNaviosAfundados(){
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                if(tabuleiro[i][j].getTipo() == 'n'){   //se houver algum navio, significa que ele nao foi acertado
                    return false;                       //e o jogo ainda nao acabou
                }
            }
        }
        return true;
    }

    public boolean isEscondido() {
        return escondido;
    }

    public void setEscondido(boolean escondido) {
        this.escondido = escondido;
    }
    
    public Quadrado getQuadrado(int linha, int coluna){
        return tabuleiro[linha][coluna];
    }
        
    public static void print(Tabuleiro t1, Tabuleiro t2)        //método para printar o estado atual do jogo, para testes 
    {                                                           //antes da implementação da interface gráfica
        System.out.println("     - - V  O  C  E - -\t\t\t\t\t- O  P  O  N  E  N  T  E -");
        System.out.print("  ");
        for(int i=0;i<10;i++)
            System.out.print(i + "  ");
        
        System.out.print("\t\t  ");
            
        for(int i=0;i<10;i++)
            System.out.print(i + "  ");
            
        System.out.print("\n");
            
        for(int i=0;i<10;i++)
        {
            System.out.print(i + " ");
            
            for(int j=0;j<10;j++)
            {
                t1.tabuleiro[i][j].print(t1.escondido);
                System.out.print("  ");
            }
            
            System.out.print("\t\t" + i + " ");
                
            for(int k=0;k<10;k++)
            {
                t2.tabuleiro[i][k].print(t2.escondido);
                System.out.print("  ");
            }
                
            System.out.println();
        }
    }
}
