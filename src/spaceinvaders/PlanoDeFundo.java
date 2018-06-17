package spaceinvaders;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class PlanoDeFundo { //Classe para criar a imagem de fundo
    
    BufferedImage imagem;
    
    public PlanoDeFundo(){
        
        try{
            imagem = ImageIO.read(new File("imagens/background.jpg"));
        } catch (IOException e){
            System.out.println("Não foi possível carregar a imagem");
        }
        
    }
    
    public void pinta(Graphics2D g){
        g.drawImage(imagem, 0, 0, 1366, imagem.getHeight(), null);
    }
    
}
