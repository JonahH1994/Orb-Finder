package student;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import game.ScramState;
//import student.Paths.SFdata;
import game.HuntState;
import game.Explorer;
import game.Node;
import game.NodeStatus;

public class Indiana extends Explorer {

	private long startTime= 0;    // start time in milliseconds
	private int threshHold = 3 ;

	/** Get to the orb in as few steps as possible. Once you get there, 
	 * you must return from the function in order to pick
	 * it up. If you continue to move after finding the orb rather 
	 * than returning, it will not count.
	 * If you return from this function while not standing on top of the orb, 
	 * it will count as a failure.
	 * 
	 * There is no limit to how many steps you can take, but you will receive
	 * a score bonus multiplier for finding the orb in fewer steps.
	 * 
	 * At every step, you know only your current tile's ID and the ID of all 
	 * open neighbor tiles, as well as the distance to the orb at each of these tiles
	 * (ignoring walls and obstacles). 
	 * 
	 * In order to get information about the current state, use functions
	 * currentLocation(), neighbors(), and distanceToOrb() in HuntState.
	 * You know you are standing on the orb when distanceToOrb() is 0.
	 * 
	 * Use function moveTo(long id) in HuntState to move to a neighboring 
	 * tile by its ID. Doing this will change state to reflect your new position.
	 * 
	 * A suggested first implementation that will always find the orb, but likely won't
	 * receive a large bonus multiplier, is a depth-first search. Some
	 * modification is necessary to make the search better, in general.*/
	@Override public void huntOrb(HuntState state) {
		//TODO 1: Get the orb

		HashSet<Long> visited = new HashSet<Long>();
		long beginning = state.currentLocation() ;

		visited.add(beginning) ;
		DFS(state, beginning, visited, 0 ) ;		
	} 

	private void DFS( HuntState nextState, Long prevID, HashSet<Long> visited, int count ) {

		/*
		 * DFS Specification:
		 * When entering into DFS, the nextState node has already been inserted into 
		 * map visited. If the nextState is that Indiana is on the orb, just return.
		 * If we get to a wall or only nodes around Indiana are visited, then return to 
		 * previously visited node.
		 */

		if ( nextState.distanceToOrb() == 0 ) 
			return ;

		// nextState == currentState
		
		
		long currID = nextState.currentLocation() ;
		int dist = nextState.neighbors().iterator().next().getDistanceToTarget() ;
		Heap<NodeStatus> smallestDistNode = new Heap<NodeStatus>(false) ;
		NodeStatus next = nextState.neighbors().iterator().next() ;
		int prevDist = nextState.distanceToOrb() ;
		for( NodeStatus next1 : nextState.neighbors() ) {

			if (next1.getDistanceToTarget() < dist ) {// && !visited.contains(next1.getId())) {
				dist = next1.getDistanceToTarget() ;
				next = next1;
			}
			
			smallestDistNode.add(next1, next1.getDistanceToTarget());
		}

		while( smallestDistNode.size() != 0) {
			next = smallestDistNode.poll() ;
			
			if ( !visited.contains(next.getId())) {

				//System.out.println("Current Dist: " + nextState.distanceToOrb() + " NextState: " + next.getDistanceToTarget());
				//if ( next.getDistanceToTarget() <= nextState.distanceToOrb() ) {
				visited.add(next.getId()) ;
				nextState.moveTo(next.getId());
				if (nextState.distanceToOrb() > prevDist )
					count = count + 1 ;
				else
					count = 0 ;
				
				if ( count > threshHold ) {
					Heap<NodeStatus> smallDist = new Heap<NodeStatus>(false) ;
					
					for( NodeStatus next1 : nextState.neighbors() ) {
						
						smallDist.add(next1, next1.getDistanceToTarget());
					}
					if (smallDist.poll().getDistanceToTarget() > prevDist )
						return;
				}
				
				DFS( nextState, currID, visited, count ) ;
				//} //else if(1)) {
				//nextState.
				//}
			}

			if ( nextState.distanceToOrb() == 0 ) {
				return;
			} else if ( nextState.currentLocation() != currID) {
				nextState.moveTo(currID);
			}
			
		}

		//}

		return ;

	}

	/** 
	 * Get out the cavern before the ceiling collapses, trying to collect as much
	 * gold as possible along the way. Your solution must ALWAYS get out before time runs
	 * out, and this should be prioritized above collecting gold.
	 * 
	 * You now have access to the entire underlying graph, which can be accessed through ScramState.
	 * currentNode() and getExit() will return Node objects of interest, and getNodes()
	 * will return a collection of all nodes on the graph. 
	 * 
	 * Note that the cavern will collapse in the number of steps given by getStepsRemaining(),
	 * and for each step this number is decremented by the weight of the edge taken. You can use
	 * getStepsRemaining() to get the time still remaining, pickUpGold() to pick up any gold
	 * on your current tile (this will fail if no such gold exists), and moveTo() to move
	 * to a destination node adjacent to your current node.
	 * 
	 * You must return from this function while standing at the exit. Failing to do so before time
	 * runs out or returning from the wrong location will be considered a failed run.
	 * 
	 * You will always have enough time to escape using the shortest path from the starting
	 * position to the exit, although this will not collect much gold. For this reason, using 
	 * Dijkstra's to plot the shortest path to the exit is a good starting solution    */
	@Override public void scram(ScramState state) {
		//TODO 2: Get out of the cavern before it collapses, picking up gold along the way

		//List<Node> path = Paths.shortestPath(state.currentNode(), state.getExit(), state);

		List<Node> path = Paths.shortestPath7(state.currentNode(), state.getExit(), state);
		//List<Node> path = Paths.shortestPath2(state.currentNode(), state.getExit(), state);
		//List<Node> path = Paths.shortestPath5(state.currentNode(), state.getExit(), state);
		
		try {
			state.grabGold(); 
		} catch (Exception e) {
			
		}
		// state.grabGold(); 

		for( Node next : path ) {
			if ( next.getId() != state.currentNode().getId() ) {
				state.moveTo(next);
//				System.out.println("Gold on tile: " + next.getTile().gold());
				if (next.getTile().gold() != 0)
					state.grabGold(); 
			}
		}

	}

}
