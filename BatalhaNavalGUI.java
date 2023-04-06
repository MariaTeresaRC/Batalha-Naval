package batalhanaval;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Maria Teresa
 */
public class BatalhaNavalGUI extends JFrame{
    private JPanel texto = new JPanel();    //bloco de texto da tela inicial, com os dois JLabels abaixo
    private JLabel texto1 = new JLabel("BATALHA NAVAL", SwingConstants.CENTER);
    private JLabel texto2 = new JLabel("Aperte o botão para começar", SwingConstants.CENTER);
    private JPanel botoes = new JPanel();   //bloco com os botões para selecionar single ou multi player
    private JButton single = new JButton("START");
    private JPanel tabAliado = new JPanel();    //bloco que corresponde ao seu tabuleiro
    private JPanel tabInimigo = new JPanel();   //bloco que corresponde ao tabuleiro do seu oponente
    private Botao[][] butTabAliado = new Botao[10][10];     //matriz de botoes do seu tabuleiro
    private Botao[][] butTabInimigo = new Botao[10][10];    //matriz de botoes do tabuleiro oponente
    private JPanel controles = new JPanel();        //bloco onde serão mostradas as instruções do jogo, e os botões para posicionar os navios por exemplo
    private JButton startPos = new JButton("START");    //botão que starta o setup do jogo 
    private JButton startGame = new JButton("START");   //após os navios serem posicionados, botão que starta o jogo de fato
    private JButton rodar;      //botão para alterar a orientação do navio
    private JLabel instrucoes = new JLabel();   //bloco de texto que mostrará as instruções do jogo
    private JButton[] navios = new JButton[10];     //botões que representam cada navio a ser posicionado
    private int naviosPosicionados = 0;     
    private Navio navio;        //navio a ser posicionado
    private Servidor server = new Servidor();
    private Cliente client = new Cliente();
    
    public BatalhaNavalGUI(BatalhaNaval jogo){
        super("Batalha Naval");
        this.setLayout(new BorderLayout());
        
        texto.setLayout(new GridLayout(2,1));
        texto.add(texto1);
        texto.add(texto2);
        
        botoes.setLayout(new GridLayout(2,1));
        botoes.add(single);
        
        this.add(texto, BorderLayout.NORTH);
        this.add(botoes, BorderLayout.CENTER);
        this.pack();
        this.setSize(300, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        tabAliado.setLayout(new GridLayout(10, 10));
        tabInimigo.setLayout(new GridLayout(10, 10));
        Icon icon = new ImageIcon("rotate.png"); 
        rodar = new JButton(icon);
        
        single.addActionListener(new ActionListener() {     //abre o modo singleplayer

            @Override
            public void actionPerformed(ActionEvent e) {
                jogo.setPlayer2(new Computador(2, true));
                novaJanelaSingle(jogo);
            }

        });
    }
    
    public void novaJanelaSingle(BatalhaNaval jogo){        //JFrame que representa o jogo singleplayer
        JFrame janela = new JFrame("Batalha Naval");
        jogo.getPlayer2().posicionarTodosNavios();          //posiciona os navios do computador aleatoriamente
        
        janela.addWindowListener(new java.awt.event.WindowAdapter() {   //quando fechar a janela, o menu inicial volta a aparecer
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                setVisible(true);
                janela.dispose();
            }
        });
        
        ActionListener bomba = new ActionListener() {       //ActionListener que é implementado nos botões do tabuleiro oponente
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JButton) {
                    Botao botao = (Botao)e.getSource();
                    int i = botao.getLinha();       //posição do botão apertado
                    int j = botao.getColuna();
                    jogo.getPlayer1().bombardearGUI(jogo.getPlayer2().getMeuTabuleiro(), i, j);     //bombardeia a posição
                    if(jogo.getPlayer2().getMeuTabuleiro().getQuadrado(i, j).getTipo()=='X'){
                        botao.setBackground(Color.RED);         //se acerto, muda a cor para vermelho
                    }else if(jogo.getPlayer2().getMeuTabuleiro().getQuadrado(i, j).getTipo()=='O'){
                        botao.setBackground(Color.CYAN);        //se erro, muda a cor para azul claro
                    }
                    desligarBotao(butTabInimigo);       //espera a ação do computador antes de poder jogar de novo
                    if(!jogo.getPlayer2().getMeuTabuleiro().checarNaviosAfundados()){
                        instrucoes.setText("<html>Vez do computador</html>");
                        computadorBomba(jogo);      //computador joga
                        if(!jogo.getPlayer1().getMeuTabuleiro().checarNaviosAfundados()){   //se o jogo não acabou, continuam as rodadas
                            ligarBotao(butTabInimigo);      //então podemos apertar os botões novamente
                            instrucoes.setText("<html>Sua vez</html>");
                        }else{
                            instrucoes.setText("<html>O computador ganhou!</html>");
                        }
                    }else{
                        instrucoes.setText("<html>Você ganhou!</html>");
                    }
                }
            }
        };
        
        ActionListener posicionar = new ActionListener() {      //action listener implementado nos botões do seu tabuleiro
            @Override                                           //posiciona o navio selecionado com base no botao apertado
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JButton){
                    Botao botao = (Botao)e.getSource();
                    int i = botao.getLinha();
                    int j = botao.getColuna();
                    try{
                        if(jogo.getPlayer1().posicionarNavio(navio, navio.getOrientacao(), i, j)){
                            desligarBotao(butTabAliado);
                            if(navio.getOrientacao() == 'h'){
                                for(int n=j; n<j+navio.getTamanho(); n++){
                                    butTabAliado[i][n].setBackground(Color.DARK_GRAY);
                                }
                            }else{
                                for(int n=i; n<i+navio.getTamanho(); n++){
                                    butTabAliado[n][j].setBackground(Color.DARK_GRAY);
                                }
                            }
                            if(naviosPosicionados==10){     //se form posicionados os 10 navios, o jogo começa
                                instrucoes.setText("<html>Agora clique no botão para começar!</html>");
                                rodar.setVisible(false);
                                startGame.setVisible(true);
                            }
                        }
                    }catch(Exception ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }
        };
        
        ActionListener botaoPortaAviao = new ActionListener() {     //para selecionar o navio Porta Avioes
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JButton){
                    navio = new PortaAvioes();
                    ligarBotao(butTabAliado);
                    JButton botao = (JButton)e.getSource();
                    botao.setVisible(false);
                    naviosPosicionados++;
                }
            }
        };
        ActionListener botaoNavioTanque = new ActionListener() {    //para selecionar o navio Navio Tanque
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JButton){
                    navio = new NavioTanque();
                    ligarBotao(butTabAliado);
                    JButton botao = (JButton)e.getSource();
                    botao.setVisible(false);
                    naviosPosicionados++;
                }
            }
        };
        
        ActionListener botaoContratorpedeiro = new ActionListener() {   //para selecionar o navio Contratorpedeiro
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JButton){
                    navio = new Contratorpedeiro();
                    ligarBotao(butTabAliado);
                    JButton botao = (JButton)e.getSource();
                    botao.setVisible(false);
                    naviosPosicionados++;
                }
            }
        };
        
        ActionListener botaoSubmarino = new ActionListener() {      //para selecionar o navio Submarino
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JButton){
                    navio = new Submarino();
                    ligarBotao(butTabAliado);
                    JButton botao = (JButton)e.getSource();
                    botao.setVisible(false);
                    naviosPosicionados++;
                }
            }
        };
        
        janela.setLayout(new GridLayout(1, 3));     //aqui é apenas o setup da janela e de seus painéis e botões
        
        rodar.setVisible(false);
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                butTabAliado[i][j] = new Botao(i, j, "");
                tabAliado.add(butTabAliado[i][j]);
                butTabAliado[i][j].setBackground(Color.CYAN);
                butTabAliado[i][j].addActionListener(posicionar);
                butTabInimigo[i][j] = new Botao(i, j, "");
                butTabInimigo[i][j].setBackground(Color.LIGHT_GRAY);
                butTabInimigo[i][j].addActionListener(bomba);
                tabInimigo.add(butTabInimigo[i][j]);
            }
        }
        JPanel ladoAliado = new JPanel();
        ladoAliado.setLayout(new BorderLayout());
        ladoAliado.add(new JLabel("VOCÊ", SwingConstants.CENTER), BorderLayout.NORTH);
        ladoAliado.add(tabAliado, BorderLayout.CENTER);
        ladoAliado.setBorder(new EmptyBorder(0, 0, 0, 20));
        janela.add(ladoAliado); 
        
        JPanel ladoInimigo = new JPanel();
        ladoInimigo.setLayout(new BorderLayout());
        ladoInimigo.add(new JLabel("OPONENTE", SwingConstants.CENTER), BorderLayout.NORTH);
        ladoInimigo.add(tabInimigo, BorderLayout.CENTER);
        ladoInimigo.setBorder(new EmptyBorder(0, 0, 0, 20));
        janela.add(ladoInimigo);   
        
        JPanel botoesNavio = new JPanel();
        botoesNavio.setLayout(new GridLayout(5,2));
        for(int i=0; i<10; i++){
            navios[i] = new JButton("");
            botoesNavio.add(navios[i]);
        }
        Icon portaAviao = new ImageIcon("5.png");
        Icon navioTanque = new ImageIcon("4.png");
        Icon contratorp = new ImageIcon("3.png");
        Icon subm = new ImageIcon("2.png");
        navios[0].setIcon(portaAviao);
        navios[0].addActionListener(botaoPortaAviao);
        navios[1].setIcon(navioTanque);
        navios[1].addActionListener(botaoNavioTanque);
        navios[2].setIcon(navioTanque);
        navios[2].addActionListener(botaoNavioTanque);
        navios[3].setIcon(contratorp);
        navios[3].addActionListener(botaoContratorpedeiro);
        navios[4].setIcon(contratorp);
        navios[4].addActionListener(botaoContratorpedeiro);
        navios[5].setIcon(contratorp);
        navios[5].addActionListener(botaoContratorpedeiro);
        navios[6].setIcon(subm);
        navios[6].addActionListener(botaoSubmarino);
        navios[7].setIcon(subm);
        navios[7].addActionListener(botaoSubmarino);        
        navios[8].setIcon(subm);
        navios[8].addActionListener(botaoSubmarino);
        navios[9].setIcon(subm);
        navios[9].addActionListener(botaoSubmarino);
        botoesNavio.setVisible(false);

        instrucoes.setText("<html>Bem-vindo ao jogo Batalha Naval!<br/>Clique no botão ao lado para começar</html>");
        controles.add(instrucoes);
        controles.add(startPos);
        controles.add(startGame);
        startGame.setVisible(false);
        controles.add(rodar);
        controles.add(botoesNavio);
        controles.setBorder(new EmptyBorder(0, 0, 0, 20));
        janela.add(controles);
        
        janela.setSize(1100, 350);
        janela.setVisible(true);
        janela.setLocationRelativeTo(null);
        this.setVisible(false);
        desligarBotao(butTabAliado);
        desligarBotao(butTabInimigo);
        
        startPos.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                startPos.setVisible(false);
                rodar.setVisible(true);
                botoesNavio.setVisible(true);
                instrucoes.setText("<html>Primeiro posicione os seus navios<br/>Use o botão para rotacioná-los se necessário<br/>Lembrando que o padrão é a  horizontal</html>");
            }

        });
        
        rodar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(navio != null){
                    if(navio.getOrientacao() == 'h'){
                        navio.setOrientacao('v');
                    }else{
                        navio.setOrientacao('h');
                    }
                }
            }

        });
        
        startGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                startGame.setVisible(false);
                ligarBotao(butTabInimigo);
                instrucoes.setText("<html>Sua vez</html>");
            }

        });
    }
    
    public void computadorBomba(BatalhaNaval jogo){     //método que chama o método de bombardear do computador, e altera os botões conforme a ação
        jogo.getPlayer2().bombardear(jogo.getPlayer1().getMeuTabuleiro());
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                if(jogo.getPlayer1().getMeuTabuleiro().getQuadrado(i, j).getTipo()=='X'){
                    butTabAliado[i][j].setBackground(Color.RED);
                }else if(jogo.getPlayer1().getMeuTabuleiro().getQuadrado(i, j).getTipo()=='O'){
                    butTabAliado[i][j].setBackground(Color.BLUE);
                }
            }
        }
    }
    
    public void desligarBotao(Botao[][] botoes){    //desliga a matriz de botoes dos tabuleiros, para que o jogador não faça uma ação quando não é permitido
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                botoes[i][j].setEnabled(false);
            }
        }
    }
    
    public void ligarBotao(Botao[][] botoes){       //liga novamente os botões do tabuleiro, para que o jogador possa realizar a ação
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                botoes[i][j].setEnabled(true);
            }
        }
    }
}

class Botao extends JButton{        //classe auxiliar utilizada para que possamos associar a posição do botão na matriz 
    private int linha;              //com o respectivo quadrado no tabuleiro
    private int coluna;
        
    public Botao(int linha, int coluna, String texto){
        super(texto);
        this.linha = linha;
        this.coluna = coluna;
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
}