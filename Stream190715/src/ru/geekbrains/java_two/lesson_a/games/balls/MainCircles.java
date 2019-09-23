package ru.geekbrains.java_two.lesson_a.games.balls;

import ru.geekbrains.java_two.lesson_a.games.common.CanvasListener;
import ru.geekbrains.java_two.lesson_a.games.common.GameCanvas;
import ru.geekbrains.java_two.lesson_a.games.common.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
	Полностью разобраться с кодом
	Прочитать методичку к следующему уроку
	Написать класс Бэкграунд, изменяющий цвет канвы в зависимости от времени
	 * Реализовать добавление новых кружков по клику используя ТОЛЬКО массивы
	 ** Реализовать по клику другой кнопки удаление кружков (никаких эррейЛист)
* */

public class MainCircles extends JFrame implements CanvasListener {

    private static final int POS_X = 600;
    private static final int POS_Y = 200;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    private GameObject[] gameObjects = new GameObject[1];
    private int objectsCount;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainCircles();
            }
        });
    }

    MainCircles() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Circles");

        GameCanvas gameCanvas = new GameCanvas(this);
        gameCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                    addGameObject(new Ball(e.getX(), e.getY()));
                else if (e.getButton() == MouseEvent.BUTTON3)
                    removeSprite();
            }
        });
        initApplication();
        add(gameCanvas, BorderLayout.CENTER);
        setVisible(true);
    }

    private void addGameObject(GameObject s) {
        if (objectsCount == gameObjects.length) {
            GameObject[] temp = new GameObject[gameObjects.length * 2];
            System.arraycopy(gameObjects, 0, temp, 0, gameObjects.length);
            gameObjects = temp;
        }
        gameObjects[objectsCount++] = s;
    }

    private void removeSprite() {
        if (objectsCount > 1) objectsCount--;
    }

    private void initApplication() {
        addGameObject(new Background());
    }

    @Override
    public void onDrawFrame(GameCanvas canvas, Graphics g, float deltaTime) {
        update(canvas, deltaTime);
        render(canvas, g);
    }

    private void update(GameCanvas canvas, float deltaTime){
        for (int i = 0; i < objectsCount; i++) {
            gameObjects[i].update(canvas, deltaTime);
        }
    }

    private void render(GameCanvas canvas, Graphics g) {
        for (int i = 0; i < objectsCount; i++) {
            gameObjects[i].render(canvas, g);
        }
    }
}
