package hw4;

import api.Point;
import api.PositionVector;

public class DeadEndLink extends AbstractLink {
    public DeadEndLink() {

    }

    @Override
    public void shiftPoints(PositionVector positionVector) {
    }

    @Override
    public Point getConnectedPoint(Point point) {
	return null;
    }

    @Override
    public int getNumPaths() {
	// TODO Auto-generated method stub
	return 1;
    }

}
