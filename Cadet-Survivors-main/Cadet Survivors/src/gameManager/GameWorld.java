package gameManager;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameWorld {
    private Player player;
    private List<Enemy> enemies;
    private HealthBar healthBar;
    private Weapon1 sword;
    private int enemyhealth;

    public GameWorld() {
        Picture rectangle = new Picture(0, 0, "rsc/grass_background.png");
        rectangle.grow(1430, 610);
        rectangle.draw();
        enemyhealth = 200;
        player = new Player(500, 500); // Centraliza o jogador no canvas
        enemies = new ArrayList<>();
        healthBar = new HealthBar(player.getHealth());


        // Passar a referência do jogador ao criar a arma
        sword = new Weapon1(100, player);
        // Chama o método para criar inimigos
    }

    public void update() throws InterruptedException {
        spawnEnemies();


        // Atualiza os inimigos
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update(); // Atualiza a posição do inimigo

            // Verifica se o jogador colidiu com o inimigo
            if (player.collidesWith(enemies.get(i))) {
                if (enemies.get(i).getHealth() > 0) {
                    if (Game.ticks >= 30) {
                        player.decreaseHealth(); // Reduz a saúde do jogador se houver colisão
                        healthBar.update(player.getHealth());
                        Game.ticks = 0;
                    }
                } else {
                    System.out.println(enemies.get(i).getHealth());
                   /* if (enemies.get(i).getEllipse() != null) {
                        enemies.get(i).getEllipse().delete();
                        player.setXp(70);
                        enemies.remove(i);

                    }*/
                    enemies.get(i).deleteXp();
                    player.gainXp(70,sword,healthBar);
                    enemies.remove(i);
                    player.updateXpBar();
                }
            }
        }

        // Atualiza a espada (arma) e verifica colisões entre projéteis e inimigos
        sword.update();
        checkSwordCollisions();
    }

    // Método para verificar colisões entre os projéteis da espada e os inimigos
    private void checkSwordCollisions() {
        List<SwordProjectile> projectiles = sword.getProjectiles();  // Obtém os projéteis da arma

        // Itera sobre os projéteis e inimigos para verificar colisões
        for (SwordProjectile projectile : projectiles) {
            for (Enemy enemy : enemies) {
                if (projectile.collidesWith(enemy)) {
                    if (enemy.getHealth() > 0) {
                        projectile.setInactive();
                    }// Verifica colisão com inimigo
                    enemy.takeDamage(sword.getDamage());
                }
            }
        }
    }

    private void spawnEnemies() {
        if (enemies.isEmpty()) {
            Random random = new Random();
            int canvasWidth = 2000;
            int canvasHeight = 2000;

            // Cria 10 inimigos nas bordas do canvas
            for (int i = 0; i < 10; i++) {
                int x = random.nextInt(canvasWidth);
                int y = random.nextInt(canvasHeight);
                enemies.add(new Enemy(x, y, player, 1.0, enemyhealth)); // Adiciona o inimigo à lista
            }
            enemyhealth += 50;
        }
    }
}
