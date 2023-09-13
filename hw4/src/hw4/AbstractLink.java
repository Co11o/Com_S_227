package hw4;

import api.Crossable;
import api.Point;

public abstract class AbstractLink implements Crossable {
    public AbstractLink() {

    }

    /**
     * Design Choices
     * 
     * AbstractLink - For all classes in hw4 has methods that are general and apply
     * to every class without modification
     * 
     * ThreePointLink - Abstract Class extending AbstractLink - For all 3 endpoint
     * links Classes that extend ThreePointLink - StraightLink, SwitchLink and
     * TurnLink Contains two helper methods. One for finding connected points that
     * are the same regardless of three point link type and the other for
     * calculating the shift point ands one for finding connected points.
     * ThreePointLink also has the getter getNumPaths() since all classes that
     * extend ThreePointLink have 3 paths
     * 
     * MultiLink - Abstract Class extending AbstractLink - For all multi links
     * Classes that extend MultiLink - MultiFixedLink and MultiSwitchLink Contains
     * two helper methods. One for finding connected points that are the same
     * regardless of multi link type and the other for calculating the shift point
     * ands one for finding connected points. getNumPaths() method could've been
     * added to MultiLink, but would've been a net increase in lines of code.
     * 
     * Methods not part of an Abstract class
     * 
     * For a most classes getConnectedPoints() is partly in an abstract class but
     * since most Links in the same family have slight different connecting links
     * limit the ability to consolidate
     * 
     * Class - DeadLink Methods - All - has no duplicated code outside of the methods
     * below in AbstractLink
     * 
     * Class - CouplingLink Method - All - is similar to all other links outside of
     * DeadLink, but couldn't be grouped with ThreePointLink or MultiLink. Would've
     * been waste of time to write an Abstract class for just one class, that being
     * CouplingLink.
     * 
     * Class - SwitchLink Methods - setTurn - Only appears in SwitchLink
     * 
     * Class - MultiFixedLink Methods - getNumPaths - mentioned above for multiLink
     * type
     * 
     * Class - MultiSwitchLink Methods - switchConnection - Only appears in
     * MultiSwitchLink - getNumPaths - mentioned above for multiLink type
     */

    private boolean inTurn;// Variable for methods below to keep tract of train
			   // turning

    // Same for every class in hw4
    public void trainEnteredCrossing() {
	inTurn = true;
    }

    // Same for every class in hw4
    public void trainExitedCrossing() {
	inTurn = false;
    }

    // Created Getter for inTurn
    protected boolean getTurn() {
	return inTurn;
    }
}
