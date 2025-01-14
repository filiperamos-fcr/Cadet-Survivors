package gameManager;

import org.academiadecodigo.simplegraphics.pictures.Picture;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Weapon1 {
    private int damage;
    private List<SwordProjectile> swords;  // Lista de espadas ativas
    private long lastShotTime;
    private long fireRate = 1000;  // Intervalo de 5 segundos entre disparos
    private Player player;// Referência ao jogador

    public Weapon1(int damage, Player player) {
        this.damage = damage;
        this.swords = new ArrayList<>();
        this.lastShotTime = System.currentTimeMillis();
        this.player = player;  // Atribui o jogador
    }

    // Dispara duas espadas
    public void shoot() throws InterruptedException {
        switch (player.getDirection()) {
            case 0:
                SwordProjectile swordProjectile = new SwordProjectile(player.getX()+30, player.getY(), 10, 0,"rsc/espada direita.png");
                swords.add(swordProjectile);

                swordProjectile = new SwordProjectile(player.getX(), player.getY(), 10, 0,"rsc/espada direita.png");
                swords.add(swordProjectile);

                break;
            case 1:
                swords.add(new SwordProjectile(player.getX()-30, player.getY(), -10, 0,"rsc/espada esquerda.png"));
                swords.add(new SwordProjectile(player.getX(), player.getY(), -10, 0,"rsc/espada esquerda.png"));
                break;
            case 3:
                swords.add(new SwordProjectile(player.getX(), player.getY()+30, 0, 10,"rsc/espada baixo.png"));  // Espada para a direita
                swords.add(new SwordProjectile(player.getX(), player.getY(), 0, 10,"rsc/espada baixo.png"));  // Espada para cima
                break;
            case 2:
                swords.add(new SwordProjectile(player.getX(), player.getY()-30, 0, -10,"rsc/espada cima.png"));  // Espada para a direita
                swords.add(new SwordProjectile(player.getX(), player.getY(), 0, -10,"rsc/espada cima.png"));  // Espada para cima
                break;

        }
    }

    // Atualiza a arma e os projéteis
    public void update() throws InterruptedException {
        long currentTime = System.currentTimeMillis();

        // Verifica se é hora de disparar novamente
        if (currentTime - lastShotTime >= fireRate) {
            shoot();  // Dispara as espadas
            lastShotTime = currentTime;
        }

        // Atualiza todos os projéteis
        for (int i = 0; i < swords.size(); i++) {
            SwordProjectile sword = swords.get(i);
            sword.update();

            // Remove projéteis que já saíram da tela
            if (!sword.isActive()) {
                swords.remove(i);
                i--;
            }
        }
    }

    // Retorna a lista de projéteis ativos
    public List<SwordProjectile> getProjectiles() {
        return swords;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
