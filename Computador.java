package batalhanaval;

import java.util.Random;

/**
 *
 * @author Maria Teresa
 */
public class Computador extends Jogador {
    private Quadrado ultimoAcerto;
    private int rodadasDesdeAcerto;
    private Random r = new Random();

    public Computador(int id, boolean escondido) {
        super(id, escondido);
        this.rodadasDesdeAcerto = 0;
    }
    
    @Override
    public void posicionarTodosNavios(){        //posiciona os navios aleatoriamente 
        Navio[] navios = new Navio[10];
        navios[0] = new PortaAvioes();
        navios[1] = new NavioTanque();
        navios[2] = new NavioTanque();
        navios[3] = new Contratorpedeiro();
        navios[4] = new Contratorpedeiro();
        navios[5] = new Contratorpedeiro();
        navios[6] = new Submarino();
        navios[7] = new Submarino();
        navios[8] = new Submarino();
        navios[9] = new Submarino();
        
        int linha, coluna, aux;
        char orientacao;
        
        for(int i=0; i<10; i++){
            do{
                linha = r.nextInt(10);
                coluna = r.nextInt(10);
                aux = r.nextInt(2);
                if(aux == 0){
                    orientacao = 'h';
                }else orientacao = 'v';
            }while(!posicionarNavio(navios[i], orientacao, linha, coluna));
        }
    }
    
    @Override
    public void bombardear(Tabuleiro oponente){     //escolhe aleatoriamente uma casa para bombardear
        int linha, coluna;                          //caso ocorra um acerto, atira nas casas adjacentes
        
        if(rodadasDesdeAcerto>4){       //significa que já checou as quatro casas adjacentes ao acerto, e volta a escolher aleatoriamente
            rodadasDesdeAcerto = 0;
        }
        
        if(rodadasDesdeAcerto == 0){    //apenas escolhe um quadrado aleatório
            linha = r.nextInt(10);
            coluna = r.nextInt(10);
            switch(oponente.getQuadrado(linha, coluna).getTipo()){
                case '~':
                    oponente.getQuadrado(linha, coluna).setTipo('O');
                    break;
                case 'n':
                    oponente.getQuadrado(linha, coluna).setTipo('X');
                    ultimoAcerto = oponente.getQuadrado(linha, coluna);
                    rodadasDesdeAcerto = 1;
                    acertos++;
                    faltam--;
                    break;
                default:
                    bombardear(oponente);
                    break;
            }
        }else{
            switch(rodadasDesdeAcerto){
            case 1:     //primeiro tenta a casa a esquerda
                linha = ultimoAcerto.getLinha();
                coluna = ultimoAcerto.getColuna()+1;
                break;
            case 2:     //depois tenta a casa acima  
                linha = ultimoAcerto.getLinha()-1;
                coluna = ultimoAcerto.getColuna();
                break;
            case 3:     //depois a direita
                linha = ultimoAcerto.getLinha();
                coluna = ultimoAcerto.getColuna()-1;
                break;
            case 4:     //e finalmente a de baixo
                linha = ultimoAcerto.getLinha()+1;
                coluna = ultimoAcerto.getColuna();
                break;
            default:
                linha = ultimoAcerto.getLinha();
                coluna = ultimoAcerto.getColuna();
            }
            if(linha>=0 && linha < 10 && coluna>=0 && coluna < 10){     //se é uma posição válida, bomba
                switch(oponente.getQuadrado(linha, coluna).getTipo()){
                    case '~':
                        oponente.getQuadrado(linha, coluna).setTipo('O');
                        rodadasDesdeAcerto++;
                        break;
                    case 'n':
                        oponente.getQuadrado(linha, coluna).setTipo('X');
                        ultimoAcerto = oponente.getQuadrado(linha, coluna);
                        rodadasDesdeAcerto = 1;
                        acertos++;
                        faltam--;
                        break;
                    default:
                        rodadasDesdeAcerto++;
                        bombardear(oponente);
                        break;
                }
            }else{      //se não for uma posição válida, chama o método para tentar novamente
                rodadasDesdeAcerto++;
                bombardear(oponente);
            }
        }
    }
    
    @Override
    public void bombardearGUI(Tabuleiro oponente, int linha, int coluna){
        bombardear(oponente);
    }
}
