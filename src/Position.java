
/**
 * Created by eliasr on 2018-03-01.
 */
public class Position {

    public final int ROW;
    public final int COLUMN;

    public Position(int row, int column){
        this.ROW = row;
        this.COLUMN = column;
    }


    public int stepsTo(Position other){
        return Math.abs(ROW-other.ROW)+Math.abs(COLUMN-other.COLUMN);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Position){
            Position other = (Position) o;
            return ROW == other.ROW && COLUMN == other.COLUMN;
        }
        return false;
    }

    @Override
    public String toString(){
        return "[R:" + ROW + ", C:" + COLUMN + "]";
    }

}
