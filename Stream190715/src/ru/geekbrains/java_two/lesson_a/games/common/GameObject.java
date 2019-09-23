package ru.geekbrains.java_two.lesson_a.games.common;

import java.awt.*;

public interface GameObject {
    void update(GameCanvas canvas, float deltaTime);
    void render(GameCanvas canvas, Graphics g);
}
