package simulation;

import static api.CardinalDirection.EAST;
import static api.CardinalDirection.NORTH;
import static api.CardinalDirection.SOUTH;
import static api.CardinalDirection.WEST;

import api.Crossable;
import api.Path;
import hw4.CouplingLink;
import hw4.DeadEndLink;
import hw4.StraightLink;

public class TestTrack1 extends Track {
    public TestTrack1() {
	Path path1 = addPathType(PathTypes.pathType5, 5, 5, WEST, EAST);
	Path path2 = addPathType(PathTypes.pathType5, 6, 5, WEST, EAST);
	Path path3 = addPathType(PathTypes.pathType1, 7, 5, WEST, NORTH);
	Crossable link = new CouplingLink(path1.getHighpoint(), path2.getLowpoint());
	path1.setHighEndpointLink(link);
	path2.setLowEndpointLink(link);
	link = new CouplingLink(path2.getHighpoint(), path3.getLowpoint());
	path2.setHighEndpointLink(link);
	path3.setLowEndpointLink(link);
	Path path4 = addPathType(PathTypes.pathType6, 7, 4, SOUTH, NORTH);
	link = new CouplingLink(path3.getHighpoint(), path4.getHighpoint());
	path3.setHighEndpointLink(link);
	path4.setHighEndpointLink(link);
	Path path5 = addPathType(PathTypes.pathType4, 7, 3, SOUTH, WEST);
	link = new CouplingLink(path4.getLowpoint(), path5.getLowpoint());
	path4.setLowEndpointLink(link);
	path5.setLowEndpointLink(link);
    }
}
