package batalhanaval;

/**
 *
 * @author Maria Teresa
 */
public class Quadrado {
    private int linha;
    private int coluna;
    private char tipo;      //'~' para agua; 'n' para navio; 'X' para acerto; 'O' para erro

    public Quadrado(int linha, int coluna, char tipo) {
        this.linha = linha;
        this.coluna = coluna;
        this.tipo = tipo;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }
    
    public void print(boolean escondido){       //método para printar o tipo do quadrado, para que possamos printar o jogo todo
        switch(tipo){                           //usada quando não havia interface gráfica
            case '~':
                System.out.print("~");
                break;
            case 'n':
                if(escondido){
                    System.out.print("~");
                }else{
                    System.out.print("n");
                }
                break;
            case 'X':
                System.out.print("X");
                break;
            case 'O':
                System.out.print("O");
                break;
        }
    }
}
