package gameManager;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Text;

public class HealthBar {
    private Text TextHealth;
    private Text healthText;
    private int health;// Texto que representa a vida

    private int maxHealth; // Vida máxima
    private int currentHealth; // Vida atual

    public HealthBar(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;

        // Inicializa o texto de vida
        TextHealth = new Text(50, 50,  "Health: "); // Posição inicial
        TextHealth.setColor(Color.RED); // Cor do texto
        TextHealth.grow(30, 30); // Tamanho do texto
        TextHealth.draw(); // Desenha o texto

        //vida em número
        healthText = new Text(150, 50,  String.valueOf(currentHealth)); // Posição inicial
        healthText.setColor(Color.RED); // Cor do texto
        healthText.grow(20, 20); // Tamanho do texto
        healthText.draw(); // Desenha o texto
    }
    public int getCurrentHealth(){
        return currentHealth;
    }

    public void update(int health) {
        this.currentHealth = health; // Atualiza a saúde atual
        healthText.setText(String.valueOf(currentHealth)); // Atualiza o texto com a vida atual
    }
}
