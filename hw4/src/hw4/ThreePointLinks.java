package hw4;

import api.Point;
import api.PositionVector;

public abstract class ThreePointLinks extends AbstractLink {

    /**
     * For all links with 3 endpoints, shifting the points acts in the same way
     * (Straight, Switch, Turn)
     * 
     * @param positionVector - the two points given in shiftPoints method
     * @param point1         - endpoint 1 given in constructor
     * @param point2         - endpoint 2 given in constructor
     * @param point3         - endpoint 3 given in constructor
     */
    public void findShiftPoints(PositionVector positionVector, Point point1, Point point2, Point point3) {
	Point pointA = positionVector.getPointA();
	Point pointB = positionVector.getPointB();

	int travel = 0;
	if (pointA.getPointIndex() < pointB.getPointIndex()) {
	    travel = 1;
	} else {
	    travel = -1;
	}

	if (point1 == getConnectedPoint(pointB)) {
	    positionVector.setPointA(point1);
	    positionVector
		    .setPointB(point1.getPath().getPointByIndex(positionVector.getPointA().getPointIndex() + travel));
	}
	if (point2 == getConnectedPoint(pointB)) {
	    positionVector.setPointA(point2);
	    positionVector
		    .setPointB(point2.getPath().getPointByIndex(positionVector.getPointA().getPointIndex() + travel));
	}
	if (point3 == getConnectedPoint(pointB)) {
	    positionVector.setPointA(point3);
	    positionVector
		    .setPointB(point3.getPath().getPointByIndex(positionVector.getPointA().getPointIndex() + travel));
	}
    }

    /**
     * unlike endpoints 1 and 3, endpoiont 2 always connects to endpoint 1 for links
     * with 3 endpoints.
     * 
     * @param point  - point given by the getConnectedPoint method
     * @param point1 - endpoint 1 given in constructor
     * @param point2 - endpoint 2 given in constructor
     * @return point 1 if a match is found, else null
     */
    public Point point2Connect(Point point, Point point1, Point point2) {
	Point tempPoint = null;
	if (point.getX() == point2.getPath().getHighpoint().getX()
		&& point.getY() == point2.getPath().getHighpoint().getY()
		&& point2.getPointIndex() == point.getPointIndex()) {
	    tempPoint = point1;
	} else if (point.getX() == point2.getPath().getLowpoint().getX()
		&& point.getY() == point2.getPath().getLowpoint().getY()
		&& point2.getPointIndex() == point.getPointIndex()) {
	    tempPoint = point1;
	}
	return tempPoint;
    }

    /**
     * All classes that extend this one have 3 endpoints and 3 paths
     */
    public int getNumPaths() {
	return 3;
    }
}
