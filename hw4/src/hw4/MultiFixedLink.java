package hw4;

import api.Point;
import api.PointPair;
import api.PositionVector;

public class MultiFixedLink extends MultiLinks {
    private PointPair[] connect;
 
    public MultiFixedLink(PointPair[] connections) {
	this.connect = connections;
    }

    @Override
    public Point getConnectedPoint(Point point) {
	return super.findGetConnectedPoint(connect, point);
    }

    @Override
    public int getNumPaths() {
	// TODO Auto-generated method stub
	return connect.length;
    }

    @Override
    public void shiftPoints(PositionVector positionVector) {
	super.findShiftPoints(connect, positionVector);
    }
}
