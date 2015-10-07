package com.vojin.go.breakfree.navigation;



import com.vojin.go.breakfree.BreakFree;

/**
 * 
 * @author Vojin Nikolic
 *
 *
 * Enumerator which represents directions in {@link BreakFree} game. 
 * String is game displayed and integers are used for {@link Coordinate} 
 *
 */
public enum Direction {
    NORTH("To the North", 0, 1),
    SOUTH("To the South", 0, -1),
    EAST("To the East", 1, 0),
    WEST("To the West", -1, 0);
    
    private final String description;
    private final int dx;
    private final int dy;
    

    private Direction(String description, int dx, int dy) {
        this.description = description;
        this.dx = dx;
        this.dy = dy;
    }

    public String getDescription() {
        return description;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

}
