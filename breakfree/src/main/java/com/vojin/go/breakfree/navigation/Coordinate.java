package com.vojin.go.breakfree.navigation;



/**
 * 
 * @author Vojin Nikolic
 *
 * This class takes two possible constructions: either a string or two integers.
 * 
 */
public class Coordinate {
    public final int x;
    public final int y;
    

    /**
     * Create an 2D coordinate based on a String.
     *
     * @param rawCoordinate - A String containing two numbers, separated by a comma,
     * 
     */
    public Coordinate(String rawCoordinate) {
        String[] parts = rawCoordinate.split(",");
        this.x = Integer.parseInt(parts[0]);
        this.y = Integer.parseInt(parts[1]);
    }

    /**
     * Create an 2D coordinate based on a String.
     * 
     * @param x - The X position
     * @param y - The Y position
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate getBorderingCoordinate(Direction direction) {
        return new Coordinate(x + direction.getDx(), y + direction.getDy());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    } 
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
       
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = x;
        result = 88 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}
