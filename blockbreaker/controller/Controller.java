package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import model.Brick;
import view.GraphicUI;



public class Controller 
{
    private GraphicUI gui;
    private ButtonListener bLis;
    private ArrayList<ArrayList<Brick>> bricks;
    private int level = 1;
    private int maxRows = 14 + (level-1)/2;
    private int maxCols = 12 + level/2;
    private int points = 0;
    private int pointThreshold = 80+(level*20);

    

    public Controller()
    {
        this.gui = new GraphicUI(maxRows, maxCols, level);
        this.bricks = this.gui.getBricks();
        this.bLis = new ButtonListener();
        this.AssignListeners();
    }
    
    

    private void AssignListeners()
    {
        for(int i=0; i<maxRows; i++)
        {
            for(int j=0; j<maxCols; j++)
            {
                this.bricks.get(i).get(j).addMouseListener(bLis);
            }
        }
    }

    private boolean ifChecked(ArrayList<CoordinateHolder> holder, int x, int y )
    {
        for(int i=0; i<holder.size(); i++)
        {
            if(x == holder.get(i).getX() && y == holder.get(i).getY())
            {
                return true;
            }
        }
        return false;
    }


    private ArrayList<CoordinateHolder> FindBricksThatWillBeEXploded(Brick btn, ArrayList<CoordinateHolder> holder)
    {

        int xPos = btn.getXCoo();
        int yPos = btn.getYCoo();


        if (xPos - 1 >= 0)
        {
            holder.add(new CoordinateHolder(xPos - 1, yPos));
            if (yPos - 1 >= 0 && yPos + 1 <= maxCols-1)
            {
                holder.add(new CoordinateHolder(xPos - 1, yPos - 1));
                holder.add(new CoordinateHolder(xPos - 1, yPos + 1));
            }
            else
            {
                if (yPos - 1 >= 0)
                {
                    holder.add(new CoordinateHolder(xPos - 1, yPos - 1));
                }
                if (yPos + 1 <= maxCols-1)
                {
                    holder.add(new CoordinateHolder(xPos - 1, yPos + 1));
                }
            }
        }
        if (xPos + 1 <= maxRows-1)
        {
            holder.add(new CoordinateHolder(xPos + 1, yPos));
            if (yPos - 1 >= 0 && yPos + 1 <= maxCols-1)
            {
                holder.add(new CoordinateHolder(xPos + 1, yPos - 1));
                holder.add(new CoordinateHolder(xPos + 1, yPos + 1));
            }
            else
            {
                if (yPos - 1 >= 0)
                {
                    holder.add(new CoordinateHolder(xPos + 1, yPos - 1));
                }
                if (yPos + 1 <= maxCols-1)
                {
                    holder.add(new CoordinateHolder(xPos + 1, yPos + 1));
                }
            }
        }
        else
        {
            if (yPos - 1 >= 0)
            {
                holder.add(new CoordinateHolder(xPos, yPos - 1));
            }
            if (yPos + 1 <= maxCols-1)
            {
                holder.add(new CoordinateHolder(xPos, yPos + 1));
            }
        }
        return holder;
    }


    private ArrayList<CoordinateHolder> CheckSelection(Brick btn, ArrayList<CoordinateHolder> holder)
    {
        if(btn == null)
        {
            return holder;
        }
        
        int X = btn.getXCoo();
        int Y = btn.getYCoo();

        for(int row=btn.getXCoo()+1; row<maxRows; row++)  /* Check all the bricks UNDER the selected brick. */
        {
            if(this.bricks.get(row).get(Y)==null || this.bricks.get(row).get(Y).getBackground() != btn.getBackground() )
            {
                break; 
            }
            else if(ifChecked(holder,this.bricks.get(row).get(Y).getXCoo(),  this.bricks.get(row).get(Y).getYCoo())
                    || ( this.bricks.get(row).get(Y).getXCoo() == btn.getXCoo() && this.bricks.get(row).get(Y).getYCoo() == btn.getYCoo()))
            {
                break;
            }
            else if(btn.getBackground() == this.bricks.get(row).get(btn.getYCoo()).getBackground())
            {
                holder.add(new CoordinateHolder(this.bricks.get(row).get(btn.getYCoo()).getXCoo(), this.bricks.get(row).get(btn.getYCoo()).getYCoo()));

                holder = CheckSelection(this.bricks.get(row).get(btn.getYCoo()), holder);
            }
        }
        for(int col=btn.getYCoo()+1; col<maxCols; col++) /* Check all the bricks RIGHT the selected brick. */
        {
            if(this.bricks.get(X).get(col)==null || this.bricks.get(X).get(col).getBackground() != btn.getBackground())
            {
                break;
            }
            else if(ifChecked(holder,this.bricks.get(X).get(col).getXCoo(),  this.bricks.get(X).get(col).getYCoo())
                    || ( this.bricks.get(X).get(col).getXCoo() == btn.getXCoo() && this.bricks.get(X).get(col).getYCoo() == btn.getYCoo()))
            {
                break;
            }
            else if(btn.getBackground() == this.bricks.get(X).get(col).getBackground())
            {
                holder.add(new CoordinateHolder(this.bricks.get(X).get(col).getXCoo(), this.bricks.get(X).get(col).getYCoo()));
                holder = CheckSelection(this.bricks.get(X).get(col), holder);
            }
        }
        for(int col=btn.getYCoo()-1; col>=0; col--) /* Check all the bricks LEFT the selected brick. */
        {
            if(this.bricks.get(X).get(col)==null || this.bricks.get(X).get(col).getBackground() != btn.getBackground())
            {
                break;
            }
            else if(ifChecked(holder,this.bricks.get(X).get(col).getXCoo(),  this.bricks.get(X).get(col).getYCoo())
                    || ( this.bricks.get(X).get(col).getXCoo() == btn.getXCoo() && this.bricks.get(X).get(col).getYCoo() == btn.getYCoo()))
            {
                break;
            }
            else if(btn.getBackground() == this.bricks.get(X).get(col).getBackground())
            {
                 holder.add(new CoordinateHolder(this.bricks.get(X).get(col).getXCoo(), this.bricks.get(X).get(col).getYCoo()));
                holder = CheckSelection(this.bricks.get(X).get(col), holder);
            }
        }
        for(int row=btn.getXCoo()-1; row>=0; row--)  /* Check all the bricks ABOVE the selected brick. */
        {
            if(this.bricks.get(row).get(Y)==null || this.bricks.get(row).get(Y).getBackground() != btn.getBackground())
            {
                break;
            }
            else if(ifChecked(holder,this.bricks.get(row).get(Y).getXCoo(),  this.bricks.get(row).get(Y).getYCoo())
                    || ( this.bricks.get(row).get(Y).getXCoo() == btn.getXCoo() && this.bricks.get(row).get(Y).getYCoo() == btn.getYCoo()))
            {
                break;
            }
            else if(btn.getBackground() == this.bricks.get(row).get(Y).getBackground())
            {
                 holder.add(new CoordinateHolder(this.bricks.get(row).get(Y).getXCoo(), this.bricks.get(row).get(Y).getYCoo()));
                holder = CheckSelection(this.bricks.get(row).get(Y), holder);
            }
        }
        return holder;         
    }


    private void swapBrickInfoVertical(CoordinateHolder current)
    {
        int y = current.getY();
        for( int row=current.getX(); row>0; row--)
        {
            boolean enabled = bricks.get(row).get(y).isEnabled();
            Color color = bricks.get(row).get(y).getBackground();
            String name = bricks.get(row).get(y).getBname();

            bricks.get(row).get(y).setEnabled(bricks.get(row-1).get(y).isEnabled());
            bricks.get(row).get(y).setBackground(bricks.get(row-1).get(y).getBackground());
            bricks.get(row).get(y).setBname(bricks.get(row-1).get(y).getBname());

            bricks.get(row-1).get(y).setEnabled(enabled);
            bricks.get(row-1).get(y).setBackground(color);
            bricks.get(row-1).get(y).setBname(name);
            this.gui.revalidate();
            this.gui.repaint();
        }      
    }
    
    

    private void swapBrickInfoHorizontal(CoordinateHolder current)
    {
        int y = current.getY();
        for( int i=0; i<maxRows; i++)
        {
            for(int col=y; col<maxCols-1; col++)
            {
                boolean enabled = bricks.get(i).get(col).isEnabled();
                Color color = bricks.get(i).get(col).getBackground();
                String name =  bricks.get(i).get(col).getBname();

                bricks.get(i).get(col).setEnabled(bricks.get(i).get(col+1).isEnabled());
                bricks.get(i).get(col).setBackground(bricks.get(i).get(col+1).getBackground());
                bricks.get(i).get(col).setBname(bricks.get(i).get(col+1).getBname());

                bricks.get(i).get(col+1).setEnabled(enabled);
                bricks.get(i).get(col+1).setBackground(color);
                bricks.get(i).get(col+1).setBname(name);
                this.gui.revalidate();
                this.gui.repaint();
            }
        }     
    }
    

    private class CoordinateHolder implements Comparable<CoordinateHolder>
    {
        private int x;
        private int y;
        

        private CoordinateHolder(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        

        private void setX(int x)
        {
            this.x = x;
        }
        

        private int getX()
        {
            return this.x;
        }
        

        private void setY(int y)
        {
            this.y = y;
        }
        

        private int getY()
        {
            return this.y;
        }
        

        @Override
        public int compareTo(CoordinateHolder holder)
        {
            int Xcoo=((CoordinateHolder)holder).getX();
            int X_result =  this.x - Xcoo;
            if(X_result == 0)
            {
                int Ycoo = ((CoordinateHolder)holder).getY();
                return Ycoo - this.y;
            }
            return X_result;
        }
    }

    private void makeBlank(CoordinateHolder current)
    {
        int x = current.getX();
        int y = current.getY();
        if( !this.bricks.get(x).get(y).isEnabled() )
        {
            return;
        }
        else
        {
            this.bricks.get(x).get(y).setEnabled(false);
            this.bricks.get(x).get(y).setBackground(Color.WHITE); /* By blank I mean, white. */
            this.bricks.get(x).get(y).setBname("Dead");
            this.gui.revalidate();
            this.gui.repaint();
        }
    }
    

    private ArrayList<CoordinateHolder> findBlankColumns(ArrayList<CoordinateHolder> holder)
    {
        for(int col=0; col<maxCols; col++)
        {
            if(!this.bricks.get(maxRows-1).get(col).isEnabled())
            {
                holder.add(new CoordinateHolder(0, col));
            }
        }
        return holder;
    }


    private void levelUp()
    {
        this.points = 0;
        this.level++;
        this.maxRows = 12 + level / 2;
        this.maxCols = 14 + (level - 1) / 2;
        this.pointThreshold = 80 + (level * 20);
        this.gui.dispose();
        this.gui = new GraphicUI(maxRows,maxCols,level);
        this.bricks = this.gui.getBricks();
        this.bLis = new ButtonListener();
        this.AssignListeners();
    }

    private boolean findIfBlocked()
    {
        int blocked = 0;
        int alive = 0;

        for (int i = 0;i < this.bricks.size();i++)
        {
            for (int j = 0;j < this.bricks.get(i).size();j++)
            {
                int canDestroy = 0;
                Brick choosen = this.bricks.get(i).get(j);
                if (choosen.getBname().equals("Bomb"))
                {
                    alive++;
                    if (choosen.getXCoo() - 1 >= 0)
                    {

                        if (!this.bricks.get(choosen.getXCoo() - 1).get(j).getBname().equals("Dead"))
                        {
                            canDestroy++;
                        }
                        if (choosen.getYCoo() - 1 >= 0)
                        {
                            if (!this.bricks.get(choosen.getXCoo() - 1)
                                .get(choosen.getYCoo() - 1).getBname().equals("Dead"))
                            {
                                canDestroy++;
                            }
                        }
                        if (choosen.getYCoo() + 1 <= maxCols - 1)
                        {
                            if (!this.bricks.get(choosen.getXCoo() - 1)
                                .get(choosen.getYCoo() + 1).getBname().equals("Dead"))
                            {
                                canDestroy++;
                            }
                        }
                    }
                    if (choosen.getXCoo() + 1 <= maxRows - 1)
                    {
                        if (!this.bricks.get(choosen.getXCoo() + 1).get(j).getBname().equals("Dead"))
                        {
                            canDestroy++;
                        }
                        if (choosen.getYCoo() - 1 >= 0)
                        {
                            if (!this.bricks.get(choosen.getXCoo() + 1)
                                .get(choosen.getYCoo() - 1).getBname().equals("Dead"))
                            {
                                canDestroy++;
                            }
                        }
                        if (choosen.getYCoo() + 1 <= maxCols - 1)
                        {
                            if (!this.bricks.get(choosen.getXCoo() + 1)
                                .get(choosen.getYCoo() + 1).getBname().equals("Dead"))
                            {
                                canDestroy++;
                            }
                        }
                    }
                    if (choosen.getYCoo() - 1 >= 0)
                    {
                        if (!this.bricks.get(choosen.getXCoo())
                            .get(choosen.getYCoo() - 1).getBname().equals("Dead"))
                        {
                            canDestroy++;
                        }
                    }
                    if (choosen.getYCoo() + 1 <= maxCols - 1)
                    {
                        if (!this.bricks.get(choosen.getXCoo())
                            .get(choosen.getYCoo() + 1).getBname().equals("Dead"))
                        {
                            canDestroy++;
                        }
                    }

                    if (canDestroy <= 0)
                    {
                        blocked++;
                    }
                }
                else if (choosen.getBname().equals("Simple"))
                {
                    Color choosenColor = choosen.getBackground();
                    alive++;
                    if (choosen.getXCoo() - 1 >= 0)
                    {
                        if (this.bricks.get(choosen.getXCoo() - 1).get(j).getBackground().equals(choosenColor))
                        {
                            canDestroy++;
                        }
                    }
                    if (choosen.getXCoo() + 1 <= maxRows - 1)
                    {
                        if (this.bricks.get(choosen.getXCoo() + 1).get(j).getBackground().equals(choosenColor))
                        {
                            canDestroy++;
                        }
                    }
                    if (choosen.getYCoo() - 1 >= 0)
                    {
                        if (this.bricks.get(i).get(choosen.getYCoo() - 1).getBackground().equals(choosenColor))
                        {
                            canDestroy++;
                        }
                    }
                    if (choosen.getYCoo() + 1 <= maxCols - 1)
                    {
                        if (this.bricks.get(i).get(choosen.getYCoo() + 1).getBackground().equals(choosenColor))
                        {
                            canDestroy++;
                        }
                    }
                    if (canDestroy <= 0)
                    {
                        blocked++;
                    }
                }
            }
        }
        System.out.println("BLOCKED: " + blocked);
        System.out.println("ALIVE: " + alive);
        return alive == blocked;
    }
    
    

    public class ButtonListener implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent me)
        {
            Brick btn = ((Brick) me.getSource());
            ArrayList<CoordinateHolder> holder = new ArrayList<CoordinateHolder>();
            holder.add(new CoordinateHolder(btn.getXCoo(), btn.getYCoo()));


            if (btn.getBname().equals("Bomb"))
            {
                if(btn.getYCoo()-1>=0)
                {
                    holder.add(new CoordinateHolder(btn.getXCoo(), btn.getYCoo() - 1));
                }
                if(btn.getYCoo()+1<=maxCols-1)
                {
                    holder.add(new CoordinateHolder(btn.getXCoo(), btn.getYCoo() + 1));
                }
                holder = FindBricksThatWillBeEXploded(btn, holder);
            }

            else
            {
                holder = CheckSelection(btn, holder);
            }

            if (holder.size() <= 1 && btn.getBname().equals("Simple"))
            {
                return;
            }
            else
            {
                Collections.sort(holder);

                for(int i=0; i<holder.size(); i++)
                {
                    if (!bricks.get(holder.get(i).getX()).get(holder.get(i).getY()).getBname().equals("Dead"))
                    {
                        points++;
                    }
                    makeBlank(holder.get(i));
                }

                if(findIfBlocked() && points <= pointThreshold)
                {
                    gui.dispose();
                    gui = new GraphicUI(-1,maxCols,level);
                }

                if (points >= pointThreshold)
                {
                    System.out.println("NEXT LEVEL");
                    levelUp();
                    return;
                }

                for(int i=0; i<holder.size(); i++)
                {
                    swapBrickInfoVertical(holder.get(i));
                }

                holder.clear();

                holder = findBlankColumns(holder);

                if(holder.size()>0)
                {

                    Collections.sort(holder);

                    for(int i=0; i<holder.size(); i++)
                    {
                        swapBrickInfoHorizontal(holder.get(i));
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent me){}
        @Override
        public void mouseReleased(MouseEvent me){}
        @Override
        public void mouseEntered(MouseEvent me){}
        @Override
        public void mouseExited(MouseEvent me){}
    } 
}