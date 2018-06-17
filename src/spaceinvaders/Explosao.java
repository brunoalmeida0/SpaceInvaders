package spaceinvaders; 

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

class Explosao {
    
    BufferedImage imagem; //variável pra armazenar a imagem
    private int x, y; // variáveis de controle de posição 
    private int duracao; //variável para controlar a duração 
    private int animacaoTotal; //variável para controlar a animação
    private int linha, coluna;
    
    public Explosao(BufferedImage imagem, int x, int y){
        this.imagem = imagem;
        this.x = x;
        this.y = y;
        
        animacaoTotal = 30;
        duracao = 0;
        
        linha = 0;
        coluna = 0;
    }
    
    public void atualizar(){
        
        duracao++; 
        
        linha = duracao / 6; //anda na imagem na vertical para obter as outras partes da imagem
        coluna = duracao % 5;      
        
    }
    
    public void pintar(Graphics2D g){
        
        g.drawImage(
                imagem, //imagem
                x, //posição que ia imagem irá começar horizontalmente
                y, //posição que a imagem irá começar verticalmente
                x+50, //posição que a imagem irá terminar, nesse caso ela terá largura de 50
                y+50, //posição que a imagem irá terminar, nesse caso ela terá altura de 50
                192*coluna, //margem inicial horizontal da imagem
                192 * linha,//margem inicial vertical da imagem
                192*coluna + 192, //margem final horizontal da imagem
                192 * linha + 192, //margem final vertical da imagem
                null
        );
        
    }
    
    public boolean acabou(){ //finaliza a animação
        if(duracao >= animacaoTotal){
            return true;
        } else{
            return false;
        }
    }
    
}
