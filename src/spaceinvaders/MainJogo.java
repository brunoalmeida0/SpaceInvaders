package spaceinvaders;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;

public class MainJogo {
    //obtem informações sobre o monitor do usuário
    static GraphicsDevice monitor = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    public static void main(String[] args) {
        
        JFrame janela = new JFrame("Space Invaders"); //Criação de um objeto janela com o título "Space Invaders"
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH); //Definição da janela como fullscreen
        janela.setUndecorated(true); //deixando a janela sem decoração (sem barra de fechar, minimizar e maximizar
        janela.setLayout(null); //sem layout predefinido
        janela.setLocationRelativeTo(null); //Sem localização relativa, vai aparecer no meio da tela
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Definição para quando o jogo for fechado, o programa parar de executar
        
        SpaceInvaders invasaoAlienigena = new SpaceInvaders(); //Criação do objeto do jogo em si
        invasaoAlienigena.setBounds(0, 0, monitor.getDisplayMode().getWidth(), monitor.getDisplayMode().getHeight()); //setando as margens e o tamanho do jogo
        
        janela.add(invasaoAlienigena); //adicionando o jogo em si na janela criada
        janela.addKeyListener(invasaoAlienigena); //adicionando eventos de teclado na janela que contém o jogo
        
        janela.setVisible(true); //deixando janela visível 
        
    }
    
}
