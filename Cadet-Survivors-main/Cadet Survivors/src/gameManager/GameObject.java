package gameManager;

// Classe GameObject que serve como base para Player e Enemy
public abstract class GameObject {
    private int x;
    private int y;
    private int width;
    private int height;

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x; // Retorna a coordenada X do objeto
    }

    public int getY() {
        return y; // Retorna a coordenada Y do objeto
    }

    public int getWidth() {
        return width; // Retorna a largura do objeto
    }

    public int getHeight() {
        return height; // Retorna a altura do objeto
    }

    public void setX(int x) {
        this.x = x; // Atualiza a coordenada X do objeto
    }

    public void setY(int y) {
        this.y = y; // Atualiza a coordenada Y do objeto
    }
}
