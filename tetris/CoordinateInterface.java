package tetris;

public interface CoordinateInterface {

    // public mutators
    void setRow(int x);

    void setCol(int y);

    // public accessors
    int getRow();

    int getCol();

    /**------------------------------------------------------------------------
     * comparison method
     * 
     * @param x
     *            coordinate to be compared to this
     * @return whether this coordinate equals x
     *-----------------------------------------------------------------------*/
    boolean equals(Coordinate x);

}