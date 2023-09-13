package hw4;

import api.Point;
import api.PointPair;
import api.PositionVector;

public abstract class MultiLinks extends AbstractLink {

    /**
     * Both of the multi endpoint links have the same way to find connected points
     * 
     * @param connect - the PointPair array given in constructor
     * @param point   - point given in getConnectPoint
     * @return - tempPoint - the connected point
     */
    public Point findGetConnectedPoint(PointPair[] connect, Point point) {
	Point tempPoint = null;

	if (super.getTurn() == false) {
	    for (int i = 0; i < connect.length; i++) {
		if (point.getX() == connect[i].getPointA().getPath().getHighpoint().getX()
			&& point.getY() == connect[i].getPointA().getPath().getHighpoint().getY()
			&& connect[i].getPointA().getPointIndex() == point.getPointIndex()) {
		    tempPoint = connect[i].getPointB();
		} else if (point.getX() == connect[i].getPointB().getPath().getHighpoint().getX()
			&& point.getY() == connect[i].getPointB().getPath().getHighpoint().getY()
			&& connect[i].getPointB().getPointIndex() == point.getPointIndex()) {
		    tempPoint = connect[i].getPointA();
		}
	    }
	}
	return tempPoint;
    }

    /**
     * Both of the multi endpoint links have the same way to shift points
     * 
     * @param connect        - the PointPair array given in constructor
     * @param positionVector - the two points given in shiftPoints method
     */
    public void findShiftPoints(PointPair[] connect, PositionVector positionVector) {

	Point pointA = positionVector.getPointA();
	Point pointB = positionVector.getPointB();

	int travel = 0;
	if (pointA.getPointIndex() < pointB.getPointIndex()) {
	    travel = 1;
	} else {
	    travel = -1;
	}
	for (int i = 0; i < connect.length; i++) {
	    if (connect[i].getPointA() == getConnectedPoint(pointB)) {
		positionVector.setPointA(connect[i].getPointA());
		positionVector.setPointB(connect[i].getPointA().getPath()
			.getPointByIndex(positionVector.getPointA().getPointIndex() + travel));
	    }
	    if (connect[i].getPointB() == getConnectedPoint(pointB)) {
		positionVector.setPointA(connect[i].getPointB());
		positionVector.setPointB(connect[i].getPointB().getPath()
			.getPointByIndex(positionVector.getPointA().getPointIndex() + travel));
	    }
	}
    }
}
