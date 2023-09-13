package hw4;

import api.Point;
import api.PositionVector;
import api.Crossable;
import api.Traversable;

public class TurnLink extends ThreePointLinks {
    private Point point1;
    private Point point2;
    private Point point3;

    public TurnLink(Point endpointA, Point endpointB, Point endpointC) {
	this.point1 = endpointA;
	this.point2 = endpointB;
	this.point3 = endpointC;
    }

    /**
     * Finds if point is shares the same index and coordinates as the endpoints. If
     * point isn't endpoints 1 or 3 then a method gets called to find if point is
     * endpoint 2 if not returns null
     */
    @Override
    public Point getConnectedPoint(Point point) {
	Point tempPoint = null;
	if (point.getX() == point3.getPath().getLowpoint().getX()
		&& point.getY() == point3.getPath().getLowpoint().getY()
		&& point3.getPointIndex() == point.getPointIndex()) {
	    tempPoint = point1;
	} else if (point.getX() == point3.getPath().getHighpoint().getX()
		&& point.getY() == point3.getPath().getHighpoint().getY()
		&& point3.getPointIndex() == point.getPointIndex()) {
	    tempPoint = point1;
	} else if (point.getX() == point1.getPath().getLowpoint().getX()
		&& point.getY() == point1.getPath().getLowpoint().getY()
		&& point1.getPointIndex() == point.getPointIndex()) {
	    tempPoint = point3;
	} else if (point.getX() == point1.getPath().getHighpoint().getX()
		&& point.getY() == point1.getPath().getHighpoint().getY()
		&& point1.getPointIndex() == point.getPointIndex()) {
	    tempPoint = point3;
	} else {
	    tempPoint = super.point2Connect(point, point1, point2);
	}
	return tempPoint;
    }

    @Override
    public void shiftPoints(PositionVector positionVector) {
	super.findShiftPoints(positionVector, point1, point2, point3);
    }
}
