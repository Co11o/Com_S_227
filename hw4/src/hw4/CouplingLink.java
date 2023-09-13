package hw4;

import api.Crossable;
import api.Path;
import api.Point;
import api.PositionVector;

public class CouplingLink extends AbstractLink {

    private Point point1;
    private Point point2;

    public CouplingLink(Point endpoint1, Point endpoint2) {
	this.point1 = endpoint1;
	this.point2 = endpoint2;
    }

    @Override
    public void shiftPoints(PositionVector positionVector) {

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
		    .setPointB(point2.getPath().getPointByIndex(positionVector.getPointA().getPointIndex() - travel));
	}
	if (point2 == getConnectedPoint(pointB)) {
	    positionVector.setPointA(point2);
	    positionVector
		    .setPointB(point2.getPath().getPointByIndex(positionVector.getPointA().getPointIndex() + travel));
	}
    }

    @Override
    public Point getConnectedPoint(Point point) {

	if (point.getX() == point2.getPath().getHighpoint().getX()
		&& point.getY() == point2.getPath().getHighpoint().getY()
		&& point2.getPointIndex() == point.getPointIndex()) {
	    return point1;
	} else if (point.getX() == point1.getPath().getLowpoint().getX()
		&& point.getY() == point1.getPath().getLowpoint().getY()
		&& point1.getPointIndex() == point.getPointIndex()) {
	    return point1;
	} else if (point.getX() == point1.getPath().getHighpoint().getX()
		&& point.getY() == point1.getPath().getHighpoint().getY()
		&& point1.getPointIndex() == point.getPointIndex()) {
	    return point2;
	} else if (point.getX() == point2.getPath().getLowpoint().getX()
		&& point.getY() == point2.getPath().getLowpoint().getY()
		&& point2.getPointIndex() == point.getPointIndex()) {
	    return point2;
	} else {
	    return null;
	}

    }

    @Override
    public int getNumPaths() {
	// TODO Auto-generated method stub
	return 2;
    }
}
