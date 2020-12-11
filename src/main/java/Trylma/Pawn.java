package Trylma;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Graphics;

public class Pawn {
	private int x;
	private int y;
	private int row;
	private int column;
	private int width;
	private int height;
	private Color color;
	private String filling;
	private int playerId;
	private boolean currentlyModified;
	public Pawn(int x, int y,int height, int width,int playerId,int row, int column)
	{
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.setPlayerId(playerId);
		this.setRow(row);
		this.setColumn(column);
		this.setCurrentlyModified(false);
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	public void setFilling(String filling)
	{
		this.filling=filling;
	}
	public String getFilling()
	{
		return filling;
	}
	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}
	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	public void paint(Graphics2D g2d) 
	{
		if(isCurrentlyModified())
		{
			
		}
		else
		{
		switch(getPlayerId())
		{
			case 0:
				g2d.setColor(Color.GRAY);
				break;
			case 1:
				g2d.setColor(Color.YELLOW);
				break;
			case 2:
				g2d.setColor(Color.BLUE);
				break;
			case 3:
				g2d.setColor(Color.BLACK);
				break;
			case 4:
				g2d.setColor(Color.ORANGE);
				break;
			case 5:
				g2d.setColor(Color.RED);
				break;
			case 6:
				g2d.setColor(Color.GREEN);
				break;
			case 8:
				g2d.setColor(Color.WHITE);
				break;
			}
		}
		g2d.fillOval(getX(), getY(), getWidth(), getHeight());
	}
	boolean contains(Point point)
	{
		/** sprawdzamy czy dany punkt lezy w mniejszej odleglosci od srodka okregu niz promien*/
		int radius = getHeight()/2;
                int centerX = getX() + radius;
                int centerY = getY() + radius;
                int distance = (int)Math.sqrt((Math.pow(point.x - centerX, 2)) + (Math.pow(point.y - centerY, 2)));
                if (distance > radius)
                {
                        return false;
                } 
                return true;
	}
	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}
	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}
	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}
	/**
	 * @param column the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	/**
	 * @return the currentlyModified
	 */
	public boolean isCurrentlyModified() {
		return currentlyModified;
	}
	/**
	 * @param currentlyModified the currentlyModified to set
	 */
	public void setCurrentlyModified(boolean currentlyModified) {
		this.currentlyModified = currentlyModified;
	}
	
}
