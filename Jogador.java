package batalhanaval;

/**
 *
 * @author Maria Teresa
 */
public abstract class Jogador {
    protected int id;
    protected Tabuleiro meuTabuleiro;
    protected int acertos;
    protected int faltam;

    public Jogador(int id, boolean escondido) {
        this.id = id;
        this.meuTabuleiro = new Tabuleiro(escondido);
        this.acertos = 0;
        this.faltam = 30;
    }

    public int getId() {
        return id;
    }

    public Tabuleiro getMeuTabuleiro() {
        return meuTabuleiro;
    }
    
    public boolean posicionarNavio(Navio navio, char orientacao, int linha, int coluna){
        return navio.posicionar(meuTabuleiro.getQuadrado(linha, coluna), orientacao, meuTabuleiro);
    }
    
    public abstract void posicionarTodosNavios();
    
    public abstract void bombardear(Tabuleiro oponente);

    public abstract void bombardearGUI(Tabuleiro meuTabuleiro, int i, int j);
}
