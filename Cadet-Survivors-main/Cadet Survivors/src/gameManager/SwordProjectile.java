package gameManager;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class SwordProjectile {
    private int x, y;  // Posição da espada
    private int speedX, speedY;  // Velocidade da espada em cada direção
    private Picture picture;
    private boolean active;  // Verifica se a espada ainda está na tela

    public SwordProjectile(int x, int y, int speedX, int speedY,String imagepath) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.picture = new Picture(x, y, imagepath);
        this.picture.draw();
        this.active = true;  // Marca como ativo ao ser criado
    }

    // Atualiza a posição da espada
    public void update() {
        if (!active) return;  // Não atualiza se já saiu da tela

        // Movimenta a espada
        picture.translate(speedX, speedY);
        x += speedX;
        y += speedY;

        // Verifica se saiu dos limites da tela (exemplo de limites 800x600)
        if (x > 1920 || x < 0 || y > 1080 || y < 0) {
            active = false;  // Marca como inativo se sair da tela
            picture.delete();  // Remove a espada da tela
        }
    }

    // Verifica colisão com inimigos
    public boolean collidesWith(Enemy enemy) {
        return isTouching(enemy.getRectangle());


    }

    // Define o projétil como inativo
    public void setInactive() {
        this.active = false;
        picture.delete();
    }

    // Verifica se a espada está ativa (ou seja, visível na tela)
    public boolean isActive() {
        return active;
    }
    private double[] SwordCenter() {
        double SwordCenterX = x + picture.getWidth();
        double SwordCenterY = y + picture.getHeight();

        return new double[]{SwordCenterX, SwordCenterY};
    }

    private double[] nearestPoint(Picture enemy, double[] SwordCenter) {
        // Find the nearest point of the ball limits
        double nearestX = Math.max(enemy.getX(), Math.min(SwordCenter[0], enemy.getX() + enemy.getWidth()));
        double nearestY = Math.max(enemy.getY(), Math.min(SwordCenter[1], enemy.getY() + enemy.getHeight()));

        return new double[]{nearestX, nearestY};
    }

    private double[] calculateDistance(double[] SwordCenter, double[] nearestPoint) {
        double distanceX = SwordCenter[0] - nearestPoint[0];
        double distanceY = SwordCenter[1] - nearestPoint[1];

        return new double[]{distanceX, distanceY};
    }

    private boolean isTouching(Picture enemy) {

        double[] swordCenter = SwordCenter();
        double[] nearestPoint = nearestPoint(enemy, swordCenter);
        double[] distance = calculateDistance(swordCenter, nearestPoint);
        double distanceSquared = distance[0] * distance[0] + distance[1] * distance[1];

        // If the distance is less than or equal to the ball's radius, they are touching
        return distanceSquared <= (picture.getWidth()) * (picture.getHeight());
    }

    public Picture getPicture() {
        return picture;
    }
}
