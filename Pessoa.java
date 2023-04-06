package batalhanaval;

import java.util.Scanner;

/**
 *
 * @author Maria Teresa
 */
public class Pessoa extends Jogador {

    public Pessoa(int id, boolean escondido) {
        super(id, escondido);
    }
    
    @Override
    public void posicionarTodosNavios(){        //método para quando não havia interface gráfica, pois pede as posições no terminal
        Scanner sc = new Scanner(System.in);
        
        Navio[] navios = new Navio[10];     //declara todos os navios, como dado no enunciado
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
        
        int linha, coluna;
        char orientacao;
        
        for(int i=0; i<10; i++){
            do{
                System.out.println("Navio " + i + " de tamanho " + navios[i].getTamanho() + "\nInsira a linha e a coluna da posicao inicial, e a orientacao");
                linha = sc.nextInt();
                coluna = sc.nextInt();
                orientacao = sc.next().charAt(0);
            }while(!posicionarNavio(navios[i], orientacao, linha, coluna));     //posiciona o navio na posição dada se for válida
        }
    }
    
    @Override
    public void bombardear(Tabuleiro oponente){         //método para quando não havia interface gráfica, pois pede as posições no terminal
        Scanner sc = new Scanner(System.in);
        
        int linha, coluna;
            do{
                System.out.println("qual linha e coluna voce quer bombardear");
                linha = sc.nextInt();
                coluna = sc.nextInt();
            }while(linha < 0 || linha > 9 || coluna < 0 || coluna > 9);
        
        if(oponente.getQuadrado(linha, coluna).getTipo()=='~'){
            System.out.println("Errou!");
            oponente.getQuadrado(linha, coluna).setTipo('O');
        }else if(oponente.getQuadrado(linha, coluna).getTipo()=='n'){
            System.out.println("Acertou!");
            oponente.getQuadrado(linha, coluna).setTipo('X');
            acertos++;
            faltam--;
        }else{
            System.out.println("Voce ja atirou nessa posicao, tente novamente");
            bombardear(oponente);
        }
    }
    
    @Override
    public void bombardearGUI(Tabuleiro oponente, int linha, int coluna){
        
        if(oponente.getQuadrado(linha, coluna).getTipo()=='~'){
            oponente.getQuadrado(linha, coluna).setTipo('O');
        }else if(oponente.getQuadrado(linha, coluna).getTipo()=='n'){
            oponente.getQuadrado(linha, coluna).setTipo('X');
            acertos++;
            faltam--;
        }else{
            System.out.println("Voce ja atirou nessa posicao, tente novamente");
        }
    }
}
