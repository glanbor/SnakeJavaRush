package Snake;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Room {
    public static Room game;
    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
        game = this;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Snake getSnake() {
        return snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public static void main(String[] args) {
        Snake snake = new Snake(10, 10);
        snake.setDirection(SnakeDirection.DOWN);
        game = new Room(20,20, snake);
        game.createMouse();
        game.run();
    }
    void run() {
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();
        //try {Thread.sleep(500);}
        //catch (InterruptedException e) {e.printStackTrace();}
        while (snake.isAlive()) {
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                if (event.getKeyChar() == 'q') return;
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.setDirection(SnakeDirection.LEFT);
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.setDirection(SnakeDirection.RIGHT);
                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    snake.setDirection(SnakeDirection.UP);
                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.setDirection(SnakeDirection.DOWN);
            }
            snake.move();
            print();
            sleep();
        }
        System.out.println("Game Over!");
    }
    void sleep() {
        int sleepTime = 520 - (snake.getSections().size());
        try {if (snake.getSections().size() < 16) Thread.sleep(sleepTime);
            else Thread.sleep(200);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    void print() {
        int[][] matrix = new int[height][width];
        ArrayList<SnakeSection> sections = new ArrayList<SnakeSection>(snake.getSections());
        for (SnakeSection snakeSection : sections) {
            matrix[snakeSection.getY()][snakeSection.getX()] = 1;
        }
        matrix[snake.getY()][snake.getX()] = snake.isAlive() ? 2 : 4;
        matrix[mouse.getY()][mouse.getX()] = 3;
        String[] symbols = {".", "x", "X", "^_^", "RIP"};
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(symbols[matrix[y][x]]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }
    void createMouse(){
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        mouse = new Mouse(x, y);
    }
    public void eatMouse() {
        createMouse();
    }
}
