package batalhanaval;

/**
 *
 * @author Maria Teresa
 */
public abstract class Navio {
    protected int tamanho;
    protected char orientacao;
    protected Quadrado posInicial;
    
    public boolean posicionar(Quadrado posInicial, char orientacao, Tabuleiro tabuleiro){
        this.posInicial = posInicial;
        this.orientacao = orientacao;
        
        try{
            estaNoTabuleiro();          //caso a posicao do navio seja invalida, nao deve ser possivel
            estaSobreposto(tabuleiro);  //posicionar o navio, entao o metodo retorna falso
            
            int linha = posInicial.getLinha();
            int coluna = posInicial.getColuna();
            
            if(orientacao == 'h'){      //navio na horizontal, altera as casas a direita
                for(int i = coluna; i<coluna+tamanho; i++){
                    tabuleiro.getQuadrado(linha, i).setTipo('n');
                }
            }else{                      //navio na vertical, altera as casas abaixo
                for(int i = linha; i<linha+tamanho; i++){
                    tabuleiro.getQuadrado(i, coluna).setTipo('n');
                }
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public void estaNoTabuleiro() throws ExcecaoForaDoTabuleiro{
        if(orientacao == 'v'){
            if(posInicial.getLinha()<0 || posInicial.getLinha()+tamanho-1>9 || posInicial.getColuna()<0 || posInicial.getColuna()>9){
                throw new ExcecaoForaDoTabuleiro("Navio esta fora do tabuleiro");
            }
        }else{
            if(posInicial.getLinha()<0 || posInicial.getLinha()>9 || posInicial.getColuna()<0 || posInicial.getColuna()+tamanho-1>9){
                throw new ExcecaoForaDoTabuleiro("Navio esta fora do tabuleiro");
            }
        }
    }
    
    public void estaSobreposto(Tabuleiro tabuleiro) throws ExcecaoSobreposto{
        int linha = posInicial.getLinha();
        int coluna = posInicial.getColuna();
        if(orientacao == 'h'){
            for(int i = coluna; i<coluna+tamanho; i++){
                if(tabuleiro.getQuadrado(linha, i).getTipo() == 'n'){
                    throw new ExcecaoSobreposto("Navio esta sobrepondo outro navio");
                }
            }
        }else{
            for(int i = linha; i<linha+tamanho; i++){
                if(tabuleiro.getQuadrado(i, coluna).getTipo() == 'n'){
                    throw new ExcecaoSobreposto("Navio esta sobrepondo outro navio");
                }
            }
        }
    }

    protected Navio(int tamanho) {
        this.tamanho = tamanho;
        this.orientacao = 'h';      //a orientacao padrao e horizontal, mas pode ser alterada depois
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public char getOrientacao() {
        return orientacao;
    }

    public void setOrientacao(char orientacao) {
        this.orientacao = orientacao;
    }

    public Quadrado getPosInicial() {
        return posInicial;
    }

    public void setPosInicial(Quadrado posInicial) {
        this.posInicial = posInicial;
    }
}