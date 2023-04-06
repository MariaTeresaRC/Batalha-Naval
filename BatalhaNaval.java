package batalhanaval;

import java.util.Scanner;
import javax.swing.JFrame;

/**
 *
 * @author Maria Teresa
 */
public class BatalhaNaval{
    private Pessoa player1;
    private Jogador player2;
    private BatalhaNavalGUI gui;
    private Servidor server;
    private Cliente client;

    public Servidor getServer() {
        return server;
    }

    public Cliente getClient() {
        return client;
    }

    public void setServer(Servidor server) {
        this.server = server;
    }

    public void setClient(Cliente client) {
        this.client = client;
    }
    
    public Jogador getPlayer1() {
        return player1;
    }

    public Jogador getPlayer2() {
        return player2;
    }

    public void setPlayer2(Jogador player2) {
        this.player2 = player2;
    }
    
    public BatalhaNaval(){
        this.gui = new BatalhaNavalGUI(this);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
        this.server = null;
        this.client = null;
        this.player1 = new Pessoa(1, false);
        
        //turno();      //essa era o método de execução do jogo para quando não havia uma interface gráfica
    }
    
    public void turno(){
        while(!player1.getMeuTabuleiro().checarNaviosAfundados() && !player2.getMeuTabuleiro().checarNaviosAfundados()){    //enquanto o jogo não acabou
                       
            player1.bombardear(player2.getMeuTabuleiro());      //player1 joga
            Tabuleiro.print(player1.getMeuTabuleiro(), player2.getMeuTabuleiro());
            
            if(player2.getMeuTabuleiro().checarNaviosAfundados()) break;    //checa se o jogo acabou
            
            player2.bombardear(player1.getMeuTabuleiro());      //player2 joga
            
            Tabuleiro.print(player1.getMeuTabuleiro(), player2.getMeuTabuleiro());      //checa se o jogo acabou
                        
            if(player1.getMeuTabuleiro().checarNaviosAfundados()) break;
        }
    }

    public static void main(String[] args) {
        BatalhaNaval jogo = new BatalhaNaval();
    }
}