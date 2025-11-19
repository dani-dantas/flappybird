import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

/**
 * A classe FlappyBird representa o painel principal do jogo,
 * onde toda a lógica e renderização acontecem.
 * Ela gerencia o estado do jogo, os objetos (pássaro, canos) e a interação do usuário.
 */
public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    // Dimensões da janela do jogo
    int larguraBorda = 360;
    int alturaBorda = 640;

    // Imagens usadas no jogo
    Image birdImage;
    Image backgroundImage;
    Image bottomPipImage;
    Image topPipImage;

    // Constantes para a posição inicial e dimensões do pássaro
    int birdX = larguraBorda / 8;
    int birdY = alturaBorda / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    /**
     * Classe interna para representar o pássaro.
     * Contém sua posição, dimensões e a imagem.
     */
    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        /**
         * Construtor da classe Bird.
         * @param img A imagem a ser usada para o pássaro.
         */
        Bird (Image img) {
            this.img = img;
        }
    }

    // Constantes para a posição inicial e dimensões dos canos
    int pipeX = larguraBorda;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    /**
     * Classe interna para representar um cano.
     * Contém sua posição, dimensões, imagem e um marcador se o pássaro já passou por ele.
     */
    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false; // Flag para contar a pontuação

        /**
         * Construtor da classe Pipe.
         * @param img A imagem a ser usada para o cano.
         */
        Pipe (Image img) {
            this.img = img;
        }
    }

    Bird bird;
    int velocityX = -4;
    int velocityY = 0; // Velocidade de queda do pássaro
    int gravity = 1; // Força da gravidade aplicada ao pássaro

    // Estruturas de dados do jogo
    ArrayList<Pipe> pipes;
    Random random = new Random();

    // Timers para o loop do jogo e para a geração de canos
    Timer gameLoop;
    Timer placePipesTimer;

    // Variáveis de estado do jogo
    boolean gameOver = false;
    double score = 0;

    /**
     * Construtor da classe FlappyBird.
     * Inicializa o painel, carrega as imagens, configura os timers e os listeners de eventos.
     */
    FlappyBird() {
        setPreferredSize(new Dimension(larguraBorda, alturaBorda));
        setFocusable(true); // Permite que o painel receba eventos de teclado
        addKeyListener(this);

        backgroundImage = new ImageIcon("assets/flappybirdbg.png").getImage();
        birdImage = new ImageIcon("assets/flappybird.png").getImage();
        topPipImage = new ImageIcon("assets/toppipe.png").getImage();
        bottomPipImage = new ImageIcon("assets/bottompipe.png").getImage();

        // Inicializa os objetos do jogo
        bird = new Bird(birdImage);
        pipes = new ArrayList<Pipe>();

        // Configura o timer para adicionar novos canos a cada 1.5 segundos
        placePipesTimer = new Timer(1500, new ActionListener() { // 1500ms = 1.5 segundos
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipesTimer.start();

        // Configura o loop principal do jogo para rodar a 60 FPS
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    /**
     * Método padrão do Swing, chamado para desenhar o componente.
     * @param g O contexto gráfico usado para desenhar.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Cria um novo par de canos (superior e inferior) em uma posição vertical aleatória
     * e os adiciona à lista de canos do jogo.
     */
    public void placePipes() {
        // Calcula uma posição Y aleatória para o conjunto de canos
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = alturaBorda/4; // Espaço entre o cano superior e o inferior

        Pipe topPipe = new Pipe(topPipImage);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipImage);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace; // Posiciona o cano inferior
        pipes.add(bottomPipe);
    }

    /**
     * Desenha todos os elementos visuais do jogo na tela.
     * Isso inclui o fundo, o pássaro, os canos e a pontuação.
     * @param g O contexto gráfico usado para desenhar.
     */
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, larguraBorda, alturaBorda, null);
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        for (Pipe pipe : pipes) {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // Desenha a pontuação ou a mensagem de "Game Over"
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    /**
     * Atualiza o estado do jogo a cada frame.
     * Move o pássaro e os canos, verifica colisões e atualiza a pontuação.
     */
    public void move() {
        // Aplica a gravidade ao pássaro
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0); // Impede que o pássaro saia pelo topo da tela

        // Move os canos e verifica colisões/pontuação
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            // Verifica se o pássaro passou pelo cano para incrementar a pontuação
            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                pipe.passed = true;
                score += 0.5; // 0.5 para cada cano (superior e inferior), totalizando 1 ponto por par
            }

            // Verifica colisão com o cano atual
            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        // Verifica se o pássaro caiu no chão
        if (bird.y > alturaBorda) {
            gameOver = true;
        }
    }

    /**
     * Verifica se dois retângulos (pássaro e cano) estão colidindo.
     * Utiliza o método de detecção de colisão AABB (Axis-Aligned Bounding Box).
     * @param a O pássaro.
     * @param b O cano.
     * @return true se houver colisão, false caso contrário.
     */
    boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y + b.height &&
               a.y + a.height > b.y;
    }

    /**
     * Chamado pelo timer `gameLoop` a cada frame.
     * É o coração do loop do jogo.
     * @param e O evento de ação.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint(); // Redesenha a tela com as posições atualizadas
        if (gameOver) {
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    /**
     * Chamado quando uma tecla é pressionada.
     * Usado para fazer o pássaro pular e para reiniciar o jogo.
     * @param e O evento de teclado.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9; // Aplica uma força para cima (pulo)

            // Se o jogo acabou, reinicia o estado do jogo
            if (gameOver) {
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }
        }
    }

    // Métodos não utilizados da interface KeyListener
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
