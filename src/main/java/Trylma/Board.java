package Trylma;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
/*
 * Klasa zarzadzajaca plansza
 */
public class Board extends JPanel{
	private int rows=17;
	private int columns=13;
	private int WINDOW_HEIGHT=850;
	private int WINDOW_WIDTH=650;
	private List<Pawn> pawns= new ArrayList<Pawn>();
	private Pawn currentPawn;
	private List<Pawn> possibleMoves= new ArrayList<Pawn>();
	private int prevX;
	private int prevY;
	private int currentX;
	private int currentY;
	private int[][] board = {
			//Keep in mind that
			//i%2=1, offset=d/2 where d=diameter of a single circle
           {8,8,8,8,8,8,6,8,8,8,8,8,8},
            {8,8,8,8,8,6,6,8,8,8,8,8,8},
           {8,8,8,8,8,6,6,6,8,8,8,8,8},
            {8,8,8,8,6,6,6,6,8,8,8,8,8},
           {5,5,5,5,0,0,0,0,0,4,4,4,4},
            {5,5,5,0,0,0,0,0,0,4,4,4,8},
           {8,5,5,0,0,0,0,0,0,0,4,4,8},
            {8,5,0,0,0,0,0,0,0,0,4,8,8},
           {8,8,0,0,0,0,0,0,0,0,0,8,8},
            {8,3,0,0,0,0,0,0,0,0,2,8,8},
           {8,3,3,0,0,0,0,0,0,0,2,2,8},
            {3,3,3,0,0,0,0,0,0,2,2,2,8},
           {3,3,3,3,0,0,0,0,0,2,2,2,2},
            {8,8,8,8,1,1,1,1,8,8,8,8,8},
           {8,8,8,8,8,1,1,1,8,8,8,8,8},
            {8,8,8,8,8,1,1,8,8,8,8,8,8},
           {8,8,8,8,8,8,1,8,8,8,8,8,8},
			};
	public Board()
	{
		JFrame frame=new JFrame("Trylma");
		frame.add(this);
		frame.setSize(700,900);
		frame.setLayout(new GridLayout());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		boardInit();
		moveAttemptListener();
	}
    private void moveAttemptListener() {
    	//TODO: improve move attempt
    	addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				for(Pawn pawn:pawns)
				{
					if(pawn.contains(e.getPoint()) && (pawn.getPlayerId()==0 || pawn.getPlayerId()==8))
					{
						clickedEmpty();
						break;
					}
					else if(pawn.contains(e.getPoint()) && pawn.getPlayerId()!=0 && pawn.getPlayerId()!=8)
					{
						System.out.println(pawn.getPlayerId());
						movement(e.getX(),e.getY(),pawn,pawn.getRow(),pawn.getColumn());
						break;
							
					}
				}
				removeMouseListener(this);
			}
		});
		
	}
    private void movement(int x, int y, Pawn pawnToMove, int row, int column)
    {
    	//TODO:finish this method
    	clean();
    	for(int i=-1;i<2;i++)
    	{
    		for(int j=-1;j<2;j++)
    		{
    			if(i==0 && j==0)
    			{
    				
    			}
    			else if(board[row+i][column+j]==0)
    			{
    				int a=row+i;
    				int b=column+j;
    				for(Pawn pawn:pawns)
    				{
    					if(pawn.getPlayerId()!=0)
    					{
    						continue;
    					}
    					else
    					{
	    					if(pawn.getRow()==a && pawn.getColumn()==b)
	    					{
	    						if(row%2==1)
	    						{
	    							if((i==1 && j==-1) || (i==-1 && j==-1))
	    							{
	    							}
	    							else
	    							{
	    		    					possibleMoves.add(pawn);
	    		    					pawn.setCurrentlyModified(true);
	    		    					pawn.setColor(Color.LIGHT_GRAY);
	    		    					repaint();
	    							}
	    						}
	    						else
	    						{
	    							if((i==1 && j==1)||(i==-1 && j==1))
	    							{
	    							}
	    							else
	    							{
	    								possibleMoves.add(pawn);
	    								pawn.setCurrentlyModified(true);
	    		    					pawn.setColor(Color.LIGHT_GRAY);
	    		    					repaint();
	    							}
	    						}
	    					}
    					}
    				}
    			}
    		}
    	}
    	acceptMovement(possibleMoves,pawnToMove);
    }
    public void acceptMovement(List<Pawn> possibleMoves,Pawn pawnToMove)
    {
    	addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				for(Pawn pawn:possibleMoves)
				{
					if(pawn.contains(e.getPoint()) && pawn.getPlayerId()!=8)
					{
						swapPosition(pawnToMove,pawn);
						removeMouseListener(this);
						moveAttemptListener();
							
					}
				}
			}
		});
    }
    public void swapPosition(Pawn pawnToMove, Pawn pawn)
    {
    	int firstRow=pawnToMove.getRow();
    	int secondRow=pawn.getRow();
    	int firstId=pawnToMove.getPlayerId(); //6
    	int secondId=pawn.getPlayerId(); //0
    	int firstColumn=pawnToMove.getColumn();
    	int secondColumn=pawn.getColumn();
    	int firstX=pawnToMove.getX();
    	int secondX=pawn.getX();
    	int firstY=pawnToMove.getY();
    	int secondY=pawn.getY();
    	pawnToMove.setX(secondX);
    	pawnToMove.setY(secondY);
    	pawnToMove.setRow(secondRow);
    	pawnToMove.setColumn(secondColumn);
    	pawn.setX(firstX);
    	pawn.setY(firstY);
    	pawn.setRow(firstRow);
    	pawn.setColumn(firstColumn);
    	repaint();
    	clean();
    }
    public void clean()
    {
    	for(Pawn pawn:pawns)
    	{
    		pawn.setCurrentlyModified(false);
    		repaint();
    	}
    }
    public void clickedEmpty()
    {
    	addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				removeMouseListener(this);
				moveAttemptListener();
			}
		});
    }
	public void boardInit()
    {
        	for(int i=0;i<this.rows;i++)
        	{
        		for(int j=0;j<this.columns;j++)
        		{
        			if(i%2==1)
        			{
        				addPawn(new Pawn(25+j*WINDOW_WIDTH/columns,i*WINDOW_HEIGHT/rows,WINDOW_HEIGHT/rows,WINDOW_WIDTH/columns,board[i][j],i,j));
        			}
        			else
        			{
        				addPawn(new Pawn(j*WINDOW_WIDTH/columns,i*WINDOW_HEIGHT/rows,WINDOW_HEIGHT/rows,WINDOW_WIDTH/columns,board[i][j],i,j));
        			}
        		}
        	}
    } 
    public void addPawn(Pawn pawn) 
	{
      		pawns.add(pawn);
      		repaint();
   	}
    @Override
    protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		for(Pawn pawn:pawns)
		{
			Graphics2D g2d = (Graphics2D) g.create();
			pawn.paint(g2d);
			g2d.dispose();
		}
	}

}
