package gui.complexplane;

import javax.swing.*;

import utilities.ComplexNums;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphPanel extends JPanel {
  private int cellSize = 50;
  private final List<ComplexNums> points = new ArrayList<>();

  public GraphPanel() {
    setBackground(Color.WHITE);
  }

  public void plot(double real, double imaginary) {
    points.add(new ComplexNums(real, imaginary));
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawGrid(g);
    drawAxes(g);
    drawLabels(g);
    drawPoints(g);
  }

  private void drawGrid(Graphics g) {
    g.setColor(new Color(220, 220, 220));
    int width = getWidth();
    int height = getHeight();

    for (int x = 0; x <= width; x += cellSize) {
      g.drawLine(x, 0, x, height);
    }

    for (int y = 0; y <= height; y += cellSize) {
      g.drawLine(0, y, width, y);
    }
  }

  private void drawAxes(Graphics g) {
    g.setColor(Color.BLACK);
    int midX = getWidth() / 2;
    int midY = getHeight() / 2;
    g.drawLine(0, midY, getWidth(), midY); // X-axis
    g.drawLine(midX, 0, midX, getHeight()); // Y-axis
  }

  private void drawLabels(Graphics g) {
    g.setColor(Color.BLACK);
    g.setFont(new Font("SansSerif", Font.PLAIN, 12));
    int midX = getWidth() / 2;
    int midY = getHeight() / 2;

    for (int x = midX % cellSize; x <= getWidth(); x += cellSize) {
      int value = (x - midX) / cellSize;
      if (value != 0) g.drawString(Integer.toString(value), x - 5, midY + 15);
    }
    for (int x = midX - cellSize; x >= 0; x -= cellSize) {
      int value = (x - midX) / cellSize;
      g.drawString(Integer.toString(value), x - 5, midY + 15);
    }

    for (int y = midY % cellSize; y <= getHeight(); y += cellSize) {
      int value = (midY - y) / cellSize;
      if (value != 0) g.drawString(value + "i", midX + 5, y + 5);
    }
    for (int y = midY - cellSize; y >= 0; y -= cellSize) {
      int value = (midY - y) / cellSize;
      g.drawString(value + "i", midX + 5, y + 5);
    }
  }

  private void drawPoints(Graphics g) {
    int midX = getWidth() / 2;
    int midY = getHeight() / 2;

    g.setColor(Color.RED);

    for (ComplexNums p : points) {
      int x = midX + (int)(p.getVal() * cellSize);
      int y = midY - (int)(p.getIConst() * cellSize); // Y is inverted

      g.fillOval(x - 4, y - 4, 8, 8); // draw a red dot
    }
  }
}
