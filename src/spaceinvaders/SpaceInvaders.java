package spaceinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

//Classe que contém as lógicas gerais do jogo
public class SpaceInvaders extends JPanel implements Runnable, KeyListener{ 
    
    private Font minhaFont = new Font("Consolas", Font.BOLD, 20); //Criação de uma fonte
    
    private Nave nave;
    private int direcao = 0;
    private ArrayList<Tiro> tiros; //Define uma lista que funcionará como uma fila, pois o primeiro a desaparecer será o primeiro a ser projetado
    private ArrayList<Inimigo> inimigos;
    private ArrayList<Explosao> explosoes;
    private PlanoDeFundo fundo;
    private boolean ganhou;
    private boolean perdeu;
    private BufferedImage imagemExplosao;
    
    public SpaceInvaders(){
        
        nave = new Nave(); //criação do objeto nave
        tiros = new ArrayList<>(); //criação de uma lista com objetos do tipo Tiro
        inimigos = new ArrayList(); //criação de uma lista com objetos do tipo Inimigo
        explosoes = new ArrayList(); //criação de uma lista com objetos do tipo Explosao
        fundo = new PlanoDeFundo(); //criação do objeto fundo, que controlará a imagem de fundo da tela
        ganhou = false; //variável para controle de ganho
        perdeu = false; //variável para controle de perda
        BufferedImage imagemInimigo = null; //iniciando a imagem com null
        
        
        try{
            imagemInimigo = ImageIO.read(new File("imagens/inimigo1.png")); //imagem para inimigos
            imagemExplosao = ImageIO.read(new File("imagens/explosao.png")); //imagem das explosoes
        } catch(IOException e){
            System.out.println("Não foi possível encontrar a imagem");
        }
        
        for(int i = 0; i < 60; i++){ //laço para criar os 60 inimigos
                                     //imagem, posição horizontal, posicao vertical, direcao(para a direta)
            inimigos.add(new Inimigo (imagemInimigo, 50 + i%20 * 50, 50 + i/20 * 50, 1));
        }
        
        Thread lacoDoJogo = new Thread(this); //Thread para iniciar o jogo
        lacoDoJogo.start(); //dando start na thread
    }

    @Override
    public void run() { //método que será executado enquanto o jogo estiver rodando

        while(true){
           
           update();
           repaint();
           dorme();
           
        }
    }
    
    private void update(){ //método para atualizar estados do jogo
        
        if(inimigos.size() == 0){ //condição para ganhar
            ganhou = true;
        }
        
        nave.movimento(direcao); //seta no método movimento da classe Nave do objeto nave a direção definida por leitura de evento no teclado
        
        //VERIFICAÇÃO DE PERDA
        for(int i = 0; i < inimigos.size(); i++){ //percorre todos os inimigos
            inimigos.get(i).atualizar(); //atualiza o estado do inimigo
            
            if(inimigos.get(i).getY() >= MainJogo.monitor.getDisplayMode().getHeight() - 250){ //condição de perda, se os inimigos passarem de -250 da altura da tela
                perdeu = true;
            }
            
        }
        
        //VERIFICAÇÃO DE COLISÃO TIRO COM INIMIGO
        for(int i = 0; i < tiros.size(); i++){//percorre a fila de tiros 
            tiros.get(i).atualiza(); //atualiza o estado do tiro para deixa-los espaçados
            
            if(tiros.get(i).destroi()){ //se o tiro for finalizado, seja por atingir um inimigo ou sumir da tela
                tiros.remove(i); //ele será removido
                i--; //decrementa i para não haver erro
            } else { //Só testa pra os tiros que não foram destruidos
            
                for(int j = 0; j < inimigos.size(); j++){ //percorre todos os inimigos
                    if(tiros.get(i).colide(inimigos.get(j))){ //a colisão for verdadeira
                        //Uma nova Explosão é criada(imagem, localização horizontal, localização vertical) 
                        explosoes.add(new Explosao(imagemExplosao, inimigos.get(j).getX(), inimigos.get(j).getY()));
                        
                        inimigos.remove(j); //o inimigo será removido
                        j--; //j será decrementado para não haver errors
                        tiros.remove(i); //o tiro será removido
                        break; //o if será finalizado
                    }
                }
            }
            
        }      
        
        //TROCA DE DIREÇÃO DOS INIMIGOS
        for(int i = 0; i < inimigos.size(); i++){ //Percorre todos os inimigos
            if(inimigos.get(i).getX() <= 0 || inimigos.get(i).getX() >= 1366 - 50){ //se o os inimigos tocarem o inicio ou o final da tela
                for(int j = 0; j < inimigos.size(); j++){ //para cada iniimigo
                    inimigos.get(j).trocaDirecao(); //a direção será trocada
                }
                break;
            }
        }
        
        //ELIMINAÇÃO DAS EXPLOSÕES
        for(int i = 0; i < explosoes.size(); i++){ //Percorre todas as explosões ativas
            explosoes.get(i).atualizar(); //Atualiza o estado das explosoes
            
            if(explosoes.get(i).acabou()){ //se a explosão for finalizada
                explosoes.remove(i); //remove a explosão do arraylist
                i--; //decrementa i para não haver erros
            }
        }
        
    }
    
    //DESENHAR ITENS NA TELA
    @Override    
    public void paintComponent(Graphics g2){ //Sobreescrevendo o método
        super.paintComponent(g2); //limpar tela        
        
        //Antialiasing é para tirar o serrilhado da tela
        Graphics2D g = (Graphics2D) g2.create(); 
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        fundo.pinta(g);
        
        for(int i = 0; i < explosoes.size(); i++){ //Percorre o array de explosões 
            explosoes.get(i).pintar(g); //E desenha as explosões na tela
        }
        
        //desenha os inimigos
        for(int i = 0; i < inimigos.size(); i++){ //Percorre o array de inimigos 
            inimigos.get(i).pintar(g); //e os desenha na tela
        }
        
        //desenha a nave
        nave.pinta(g);
        
        //desenha os tiros
        for(int i = 0; i < tiros.size(); i++){
            tiros.get(i).pintar(g);
        }
        
        //MENSAGEM DE GANHOU
        if(ganhou){
            g.setColor(Color.white);
            g.setFont(minhaFont);
            g.drawString("Você ganhou!!", (MainJogo.monitor.getDisplayMode().getWidth()/2 - 100), (MainJogo.monitor.getDisplayMode().getHeight())/2);
        }
        
        //MENSAGEM DE PERDEU
        if(perdeu){
            g.setColor(Color.white);
            g.setFont(minhaFont);
            g.drawString("Game Over!", (MainJogo.monitor.getDisplayMode().getWidth()/2 - 100), (MainJogo.monitor.getDisplayMode().getHeight())/2);
        }
    }
    
    private void dorme(){
        
        try{
            Thread.sleep(16);
        } catch(InterruptedException e){
        }
    
    }

    @Override
    public void keyTyped(KeyEvent e) { //**
        
    }

    @Override
    public void keyPressed(KeyEvent e) { //evento para clique no teclado
        //quando pressionar uma tecla, a nave se movimenta ou atira
        if(e.getKeyCode() == KeyEvent.VK_D){
            direcao = 1;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_A){
            direcao = -1;
        }
         
        if(e.getKeyCode() == KeyEvent.VK_SPACE && nave.podeAtirar()){
            tiros.add(nave.atirar());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { //Evento de soltar a tecla
        //Se soltar a tecla a direção será 0 para a nave parar
        if(e.getKeyCode() == KeyEvent.VK_D){
            direcao = 0;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_A){
            direcao = 0;
        }
        
    }
        
        
    
}
