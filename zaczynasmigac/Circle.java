import java.awt.*;

public class Circle {
    private int x;
    private int y;
    private int diameter;
    private Color color;

    public Circle(int x, int y, int diameter, Color color){
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.color = color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    public void draw(Graphics g){
        g.setColor(color);
        g.fillOval(x, y, diameter, diameter);
    }

    public boolean contains(int pointX, int pointY)
    {
        int radius = diameter/2;
        int centerX = x + radius;
        int centerY = y + radius;                         // ||x - y|| = sqrt[(x1 - y1)^2 + (x2 - y2)^2 + ...]
        int distance = (int) Math.sqrt((Math.pow(pointX - centerX, 2)) + (Math.pow(pointY - centerY, 2)));
        if (distance > radius)
        {
            return false;
        }
        return true;
    }
}

