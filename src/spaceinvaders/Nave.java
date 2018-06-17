package spaceinvaders;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Nave {
    
    BufferedImage desenho; //variável que irá armazenar a imagem da nave
    
    private int x; //variável de controle da posição da nave
    private int velocidade; //variável de controle de velocidade 
    private int recarregando; //variável para controlar o tempo que poderá atirar novamente
    boolean podeAtirar; //variável de controle para pdoer atirar ou não
    
    public Nave(){
        try{
            desenho = ImageIO.read(new File("imagens/nave.png"));
        } catch(IOException e){
            System.out.println("Não foi possível encontrar a imagem");
        }
        
        x = 683; //posição x inicial da nave
        velocidade = 3; 
        podeAtirar = true;
        recarregando = 0;
    }

    void pinta(Graphics2D g) {
      
        g.drawImage(
                desenho, //imagem
                x, //posição que ia imagem irá começar horizontalmente
                MainJogo.monitor.getDisplayMode().getHeight() - 150, //posição que a imagem irá começar verticalmente
                x + 100, //posição que a imagem irá terminar, nesse caso ela terá largura de 100
                MainJogo.monitor.getDisplayMode().getHeight() - 50, //posição que a imagem irá terminar, nesse caso ela terá altura de 100
                0, //margem inicial horizontal da imagem
                0, //margem inicial vertical da imagem
                desenho.getWidth(),  //margem final horizontal da imagem
                desenho.getHeight(),  //margem final vertical da imagem
                null
        );
        
    }
    
    public Tiro atirar(){ //método para ajustar o tiro da nave
        podeAtirar = false;
        recarregando = 0;
        //Criação de um novo tiro (Posição horizontal que o tiro irá ser criado, e posição vertical)
        Tiro novoTiro = new Tiro(x + 50, MainJogo.monitor.getDisplayMode().getHeight() - 150); 
        return novoTiro;
        
    }
    
    public void movimento(int valor){
        //Se for positivo move para direita
        //se for negativo move para esquerda e = 0 fica parada
        if(valor == 1){
            x += velocidade;
        } else if (valor == -1){
            x -= velocidade;
        }
        
        if(recarregando >= 10){
            podeAtirar = true;
            recarregando = 0;
        }
        
        recarregando++;
    
    }
    
    public boolean podeAtirar(){    
        return podeAtirar;        
    }
    
    
    
    
}
