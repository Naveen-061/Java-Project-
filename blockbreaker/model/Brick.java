package model;

import javax.swing.JButton;

public class Brick extends JButton
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1417304537087736135L;
	private int xCoo, yCoo;
    private String bName = new String();

    public Brick(int x, int y, String bName)
    {
        this.xCoo = x;
        this.yCoo = y;
        this.bName = bName;
    }

    public void setXCoo(int x)
    {
        this.xCoo = x;
    }

    public int getXCoo()
    {
        return this.xCoo;
    }

    public void setYCoo(int y)
    {
        this.yCoo = y;
    }

    public int getYCoo()
    {
        return this.yCoo;
    }

    public void setBname(String name)
    {
        this.bName = name;
    }

    public String getBname()
    {
        return this.bName;
    }
}