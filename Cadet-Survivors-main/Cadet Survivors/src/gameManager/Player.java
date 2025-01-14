package gameManager;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.graphics.Text;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;

// Classe Player que representa o jogador e suas interações
public class Player extends GameObject implements KeyboardHandler {
    private int health;
    private int xp;
    private int maxXp = 500;
    private int level = 0;
    private Keyboard keyboard;
    private Picture rectangle;
    private Rectangle xpBarBackground;
    private Rectangle xpBarForeground;
    private Text xpText;
    private int direction;
    private Weapon1 sword;

    private int damage;  // esse status é atualizado - cada arma aumenta o seu valor que se inicia em 1.


    public Player(int x, int y) {
        super(x, y, 50, 50);
        this.health = 100;
        this.xp = 0;

        // Saúde inicial do jogador

        // Criação do jogador
        rectangle = new Picture(x, y,"rsc/moveDown0.png");
        rectangle.draw();
        rectangle.grow(10,10);

        // Desenha o jogador

        xpBarBackground = new Rectangle(200, 50, 200, 20);
        xpBarBackground.setColor(Color.GRAY);
        xpBarBackground.fill();

        xpBarForeground = new Rectangle(200, 50, 0, 20); // Initially 0 width
        xpBarForeground.setColor(Color.BLUE);
        xpBarForeground.fill();

        // Initialize the xpText and draw it on the screen
        xpText = new Text(100, 80, "XP: 0 / " + maxXp);
        xpText.setColor(Color.WHITE);
        xpText.draw();



        initKeyboard(); // Iniciar o teclado para o movimento do personagem
    }
    public void gainXp(int amount,Weapon1 sword,HealthBar healthBar) {
        xp += amount;  // Adiciona a quantidade de XP recebida

        // Verifica se o XP alcançou ou passou do máximo
        if (xp >= maxXp) {
            // Aumenta o nível do jogador
            level++;
            health += 10;
            healthBar.update(health);
            sword.setDamage(sword.getDamage() + 50);
            // Reseta o XP para 0 e ajusta o maxXp para o próximo nível
            xp = 0;  // Reset XP
            maxXp += 200;  // Aumenta o limite de XP para o próximo nível (ajuste conforme necessário)
        }
        updateXpBar();
    }
    public void updateXpBar() {
        // Calcula a porcentagem de XP
        double xpPercentage = (double) xp / maxXp;

        // Apaga e redesenha a barra de XP
        xpBarForeground.delete();
        xpBarForeground = new Rectangle(
                xpBarBackground.getX(),
                xpBarBackground.getY(),
                (int) (xpBarBackground.getWidth() * xpPercentage),
                xpBarBackground.getHeight()
        );
        xpBarForeground.setColor(Color.BLUE);
        xpBarForeground.fill();

        // Atualiza o texto da barra de XP
        if (xpText != null) {
            xpText.setText("XP: " + xp + " / " + maxXp + " | Nível: " + level);
        }}
    private void initKeyboard() {
        this.keyboard = new Keyboard(this);

        // Configuração dos eventos do teclado
        KeyboardEvent moveRight = new KeyboardEvent();
        moveRight.setKey(KeyboardEvent.KEY_RIGHT);
        moveRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveRight);

        KeyboardEvent moveLeft = new KeyboardEvent();
        moveLeft.setKey(KeyboardEvent.KEY_LEFT);
        moveLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveLeft);

        KeyboardEvent moveUp = new KeyboardEvent();
        moveUp.setKey(KeyboardEvent.KEY_UP);
        moveUp.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveUp);

        KeyboardEvent moveDown = new KeyboardEvent();
        moveDown.setKey(KeyboardEvent.KEY_DOWN);
        moveDown.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveDown);
        KeyboardEvent moveW = new KeyboardEvent();
        moveW.setKey(KeyboardEvent.KEY_W);
        moveW.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveW);

        KeyboardEvent moveA = new KeyboardEvent();
        moveA.setKey(KeyboardEvent.KEY_A);
        moveA.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveA);

        KeyboardEvent moveS = new KeyboardEvent();
        moveS.setKey(KeyboardEvent.KEY_S);
        moveS.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveS);

        KeyboardEvent moveD = new KeyboardEvent();
        moveD.setKey(KeyboardEvent.KEY_D);
        moveD.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveD);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        int moveAmount = 10; // Quantidade de movimento por tecla pressionada

        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_RIGHT:
            case KeyboardEvent.KEY_D:
                direction=0;
                if(rectangle.getX() < 1890)
                translate(moveAmount, 0);  // Move o jogador para a direita
                break;
            case KeyboardEvent.KEY_LEFT:
            case KeyboardEvent.KEY_A:
                direction= 1;
                if(rectangle.getX() > 0)
                translate(-moveAmount, 0);  // Move o jogador para a esquerda
                break;
            case KeyboardEvent.KEY_UP:
            case KeyboardEvent.KEY_W:
                direction = 2;
                if(rectangle.getY() > 0)
                translate(0, -moveAmount);  // Move o jogador para cima
                break;
            case KeyboardEvent.KEY_DOWN:
            case KeyboardEvent.KEY_S:
                direction = 3;
                if(rectangle.getY() < 950)
                translate(0, moveAmount);  // Move o jogador para baixo
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        // Pode ser implementado se necessário
    }

    public void decreaseHealth() {
        this.health -= 10; // Decrementa a saúde em 10
        if (health <= 0) {
            System.exit(0);
            System.out.println("Player has died.");
        }
    }

    public boolean collidesWith(Enemy enemy) {
        // Checa a colisão entre o jogador e um inimigo
        return (getX() < enemy.getX() + enemy.getWidth() &&
                getX() + getWidth() > enemy.getX() &&
                getY() < enemy.getY() + enemy.getHeight() &&
                getY() + getHeight() > enemy.getY());
    }

    // Implementa métodos para mover e atualizar a posição do jogador
    private void translate(int dx, int dy) {
        rectangle.translate(dx, dy); // Move a imagem do jogador
        // Atualiza a posição do GameObject
        setX(getX() + dx);
        setY(getY() + dy);
    }
    public void leveUp(){
        if(xp >= maxXp){
            level++;
            maxXp += 500;
            updateXpBar();
        }

    }

    @Override
    public int getX() {
        return super.getX();
    }

    public void setXp(int xp) {
        this.xp += xp;
    }

    public int getDirection() {
        return direction;
    }

    public int getHealth() {
        return health;
    }
}
