package spaceinvaders;

import java.awt.Color;
import java.awt.Graphics2D;

public class Tiro {
    
    private int x, y; //Variáveis para controle da posição 
    private int tempoDeTiro; //Variável para controle de espaço entre um tiro e outro
    private int tamX = 3; //variável para largura
    private int tamY = 15; //variável para altura do tiro
    
    //quando o tiro for criado
    public Tiro(int inicioX, int inicioY){        
        this.x = inicioX;
        this.y = inicioY;
        tempoDeTiro = 10;     
    }
    
    public void pintar(Graphics2D g){ //Desenha o tiro na tela  
        g.setColor(Color.red);
        g.fillRect(x,y, tamX, tamY);        
    }

    void atualiza() { //método para decrementar o tempo de tiro
        y -= tempoDeTiro;        
    }

    public boolean destroi() {        
        return y < 0; //se y for menor que 0 é pq o tiro passou da altura da tela.
    }

    boolean colide(Inimigo inimigo) { //Método para verificar se o tiro colidiu com o inimigo
        
        if(x >= inimigo.getX() && x + tamX <= inimigo.getX() + inimigo.getTamanho()){ //se o tiro atingir o tamanho horizontal do inimigo
            if(y <= inimigo.getY() + inimigo.getTamanho()){ // verifica se o tiro não passou da altura do inimigo
                return true;
            }
        }
        return false;
    }
    
}
