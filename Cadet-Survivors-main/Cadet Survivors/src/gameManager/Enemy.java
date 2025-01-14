package gameManager;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Ellipse;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

// Classe Enemy que representa os inimigos
public class Enemy extends GameObject {
    private Picture rectangle;
    private double speed;
    private Player player;
    private int health;
    private Ellipse ellipse;
    // Referência ao jogador

    public Enemy(int x, int y, Player player, double speed, int health) {
        super(x, y, 50, 50);
        this.player = player; // Inicializa a referência ao jogador
        this.speed = speed;
        this.health = health;


        // Criação do inimigo
        rectangle = new Picture(x, y,"rsc/Untitled.png");
      rectangle.draw();
        ellipse = new Ellipse(rectangle.getX(), rectangle.getY(), 10, 10);
    }

    public void update() {
        if (health > 0) {
            // Lógica de movimento do inimigo (exemplo simples)
            int dx = (int) (speed * Math.signum(player.getX() - getX()));
            int dy = (int) (speed * Math.signum(player.getY() - getY()));
            translate(dx, dy);
        } else {
            ellipse.translate(getX() - ellipse.getX(), getY()-ellipse.getY());
            rectangle.delete();
            ellipse.setColor(Color.YELLOW);
            ellipse.fill();

        }

    }

    public void deleteXp() {
        ellipse.delete();
    }

    private void translate(int dx, int dy) {
        if (health > 0) {
            rectangle.translate(dx, dy); // Move a imagem do inimigo
            // Atualiza a posição do GameObject
            setX(getX() + dx);
            setY(getY() + dy);
        }
    }

    public void takeDamage(int damage) {
        health -= damage;

    }


    public Picture getRectangle() {
        return rectangle;
    }

    public int getHealth() {
        return health;
    }

    public Ellipse getEllipse() {
        return ellipse;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
