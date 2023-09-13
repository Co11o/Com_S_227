package hw4;

import api.Point;
import api.PointPair;
import api.PositionVector;

public class MultiSwitchLink extends MultiLinks {

    private PointPair[] connect;

    public MultiSwitchLink(PointPair[] connections) {
	this.connect = connections;

    }

    public void switchConnection(PointPair pointPair, int index) {
	connect[index] = pointPair;
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
