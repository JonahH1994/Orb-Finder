package student;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import game.Edge;
import game.Node;
import game.ScramState;
//import student.Paths.SFdata;




/** This class contains Dijkstra's shortest-path algorithm and some other methods. */
public class Paths {

    /** Return a list of the nodes on the shortest path from start to 
     * end, or the empty list if a path does not exist.
     * Note: The empty list is NOT "null"; it is a list with 0 elements. */
    public static List<Node> shortestPath(Node start, Node end, ScramState state) {
        /* TODO Implement Dijkstras's shortest-path algorithm presented
         in the slides titled "Final Algorithm" (slide 37 or so) in the slides
         for lecture 20.
         In particular, a min-heap (as implemented in assignment A6) should be
         used for the frontier set. We provide a declaration of the frontier.
         We will make our solution to min-heap available as soon as the deadline
         for submission has passed.

         Maintaining information about shortest paths will require maintaining
         for each node in the settled and frontier sets the backpointer for
         that node, as described in the A7 handout, along with the length of the
         shortest path (thus far, for nodes in the frontier set). For this
         purpose, we provide static class SFdata. You have to declare
         the HashMap local variable for it and describe carefully what it
         means. WRITE A PRECISE DEFINITION OF IT.

         Note 1: Read the notes on pages 2..3 of the handout carefully.
         Note 2: The method MUST return as soon as the shortest path to node
                 end has been calculated. In that case, do NOT continue to find
                 shortest paths. You will lost 15 points if you do not do this.
         Note 3: the only graph methods you may call are these:

            1. n.getExits():  Return a List<Edge> of edges that leave Node n.
            2. e.getOther(n): n must be one of the Nodes of Edge e.
                              Return the other Node.
            3. e.length():    Return the length of Edge e.

         Method pathDistance uses one more: n1.getEdge(n2)
         */

        // The frontier set, as discussed in lecture 20
        Heap<Node> F= new Heap<Node>(false);
        HashMap<Node, SFdata> map = new HashMap<Node, SFdata>() ;
        
        F.add(start, 0);
        map.put(start, new SFdata(0, null)) ;
        
        while( F.size() != 0 ) {
        	
        	Node w = F.poll() ;
        	
        	
        	// If w is the exit node and the steps that will be taken are less than the alloted amount
        	if ( w.getId() == end.getId()  ) { //&& map.get(w).distance <= state.ste2psLeft()) {
        		return constructPath(end, map) ;
        	}
        	
        	//Set<Edge> edges = w.getExits() ;
        	
        	Set<Node> neighbors = w.getNeighbors() ;
        	
        	for ( Node ed : neighbors ) {
        		
        		if ( !map.containsKey(ed) ) {
        			
        			int edgeLength = ed.getEdge(w).length() ;
        			int dist = map.get(w).distance ;
        			
        			F.add(ed, edgeLength + dist);
        			map.put(ed, new SFdata(dist + edgeLength, w) ) ;
        			
        		} else {
        			
        			int dist = map.get(w).distance ;
					int prevDist = map.get(ed).distance ;
        			
					if( dist + ed.getEdge(w).length() < prevDist ) {

						// if we have, change the distance in the frontier set.
						F.changePriority(ed, dist + ed.getEdge(w).length());
						map.replace(ed, new SFdata(dist+ed.getEdge(w).length(),w)) ;
					}
					
        		}
        		
        	}
        	
        	
        	
//        	for ( Edge ed : edges ) {
//        		
//        		Node currNode =  ed.getOther(w) ;
//        		
//        		if ( !map.containsKey(currNode) ){
//        			
//        			int edgeLength = ed.length ;
//					int dist = map.get(w).distance ;
//					
//					F.add(currNode, edgeLength+dist);
//					map.put(currNode, new SFdata(dist + edgeLength, w) ) ;
//        			
//        		} else {
//        			
//        			int dist = map.get(w).distance ;
//					int prevDist = map.get(currNode).distance ;
//
//					// check to see if we have found a shorter path to the current node
//					if( dist + ed.length < prevDist ) {
//
//						// if we have, change the distance in the frontier set.
//						F.changePriority(currNode, dist + ed.length);
//						map.replace(currNode, new SFdata(dist+ed.length,w)) ;
//					}
//        			
//        		}
//        	}
        	
        }
        
        return new LinkedList<Node>(); // no path found
    }
    
    public static List<Node> shortestPath1(Node start, Node end, ScramState state) {
        /* TODO Implement Dijkstras's shortest-path algorithm presented
         in the slides titled "Final Algorithm" (slide 37 or so) in the slides
         for lecture 20.
         In particular, a min-heap (as implemented in assignment A6) should be
         used for the frontier set. We provide a declaration of the frontier.
         We will make our solution to min-heap available as soon as the deadline
         for submission has passed.

         Maintaining information about shortest paths will require maintaining
         for each node in the settled and frontier sets the backpointer for
         that node, as described in the A7 handout, along with the length of the
         shortest path (thus far, for nodes in the frontier set). For this
         purpose, we provide static class SFdata. You have to declare
         the HashMap local variable for it and describe carefully what it
         means. WRITE A PRECISE DEFINITION OF IT.

         Note 1: Read the notes on pages 2..3 of the handout carefully.
         Note 2: The method MUST return as soon as the shortest path to node
                 end has been calculated. In that case, do NOT continue to find
                 shortest paths. You will lost 15 points if you do not do this.
         Note 3: the only graph methods you may call are these:

            1. n.getExits():  Return a List<Edge> of edges that leave Node n.
            2. e.getOther(n): n must be one of the Nodes of Edge e.
                              Return the other Node.
            3. e.length():    Return the length of Edge e.

         Method pathDistance uses one more: n1.getEdge(n2)
         */

        // The frontier set, as discussed in lecture 20
        Heap<Node> F= new Heap<Node>(true); // Is max heap
        HashMap<Node, SFdata> map = new HashMap<Node, SFdata>() ;
        
        F.add(start, start.getTile().gold());
        map.put(start, new SFdata(0, null, start.getTile().gold())) ;
        
        while( F.size() != 0 ) {
        	
        	Node w = F.poll() ;
        	
        	
        	// If w is the exit node and the steps that will be taken are less than the alloted amount
        	if ( w.getId() == end.getId()  ) { //&& map.get(w).distance <= state.ste2psLeft()) {
        		
        		if( map.get(w).distance < state.stepsLeft())
        			return constructPath(w, map) ;
        		else {
        			F.add(w, 0);
        			map.replace(w, new  SFdata(Integer.MAX_VALUE, null)) ;
        			w = F.poll();
        		}
        	}
        	
        	//Set<Edge> edges = w.getExits() ;
        	
        	Set<Node> neighbors = w.getNeighbors() ;
        	
        	for ( Node ed : neighbors ) {
        		
        		if ( !map.containsKey(ed) ) {
        			
        			int edgeLength = ed.getEdge(w).length() ;
        			int dist = map.get(w).distance ;
        			int goldAmount = ed.getTile().gold() ;
        			int goldSoFar =  map.get(w).gold ;
        			
        			//F.add(ed, edgeLength + dist);
        			F.add(ed, goldAmount + goldSoFar);
        			map.put(ed, new SFdata(dist + edgeLength, w, goldAmount + goldSoFar) ) ;
        			
        		} else {
        			
        			int dist = map.get(w).distance ;
					//int prevDist = map.get(ed).distance ;
					int gold = map.get(w).gold ;
					int prevGold = map.get(ed).gold ;
					int goldOnTile = ed.getTile().gold() ;
					
					if ( map.get(w).backPointer == ed || map.get(ed).backPointer == null )
						goldOnTile = 0 ;
        			
					//System.out.println("Back Pointer: " + map.get(ed).backPointer.getId());
					//System.out.println("Current Node: " + w.getId());
					//if( dist + ed.getEdge(w).length() < prevDist ) {
					if ( gold + goldOnTile < prevGold ) {
						
					//} else if (gold+goldOnTile == 0 || prevGold == 0 ) {
						
				//	} else if ((goldOnTile+gold)/prevGold < 5) {
						// if we have, change the distance in the frontier set.
						//F.changePriority(ed, dist + ed.getEdge(w).length());
						try {
							F.changePriority(ed, gold + goldOnTile);
						} 
						catch (Exception e) {
							F.add(ed, gold + goldOnTile);
						}
						//map.replace(ed, new SFdata(dist+ed.getEdge(w).length(),w)) ;
						map.replace(ed, new SFdata(dist+ed.getEdge(w).length(),w,gold+ed.getTile().gold())) ;
					}
					
        		}
        		
//        		if (map.get(w).gold < 20000)
//        			System.out.println("Gold Considering: "+map.get(ed).gold);
        	}
        }
        
        return shortestPath(start,end,state);
        //return new LinkedList<Node>(); // no path found
    }
    
    public static List<Node> shortestPath2(Node start, Node end, ScramState state) {
        /* TODO Implement Dijkstras's shortest-path algorithm presented
         in the slides titled "Final Algorithm" (slide 37 or so) in the slides
         for lecture 20.
         In particular, a min-heap (as implemented in assignment A6) should be
         used for the frontier set. We provide a declaration of the frontier.
         We will make our solution to min-heap available as soon as the deadline
         for submission has passed.

         Maintaining information about shortest paths will require maintaining
         for each node in the settled and frontier sets the backpointer for
         that node, as described in the A7 handout, along with the length of the
         shortest path (thus far, for nodes in the frontier set). For this
         purpose, we provide static class SFdata. You have to declare
         the HashMap local variable for it and describe carefully what it
         means. WRITE A PRECISE DEFINITION OF IT.

         Note 1: Read the notes on pages 2..3 of the handout carefully.
         Note 2: The method MUST return as soon as the shortest path to node
                 end has been calculated. In that case, do NOT continue to find
                 shortest paths. You will lost 15 points if you do not do this.
         Note 3: the only graph methods you may call are these:

            1. n.getExits():  Return a List<Edge> of edges that leave Node n.
            2. e.getOther(n): n must be one of the Nodes of Edge e.
                              Return the other Node.
            3. e.length():    Return the length of Edge e.

         Method pathDistance uses one more: n1.getEdge(n2)
         */

        // The frontier set, as discussed in lecture 20
        Heap<Node> F= new Heap<Node>(false); // Is max heap
        HashMap<Node, SFdata> map = new HashMap<Node, SFdata>() ;
        
        F.add(start, state.stepsLeft());
        map.put(start, new SFdata(state.stepsLeft(), null, start.getTile().gold())) ;
        
        while( F.size() != 0 ) {
        	
        	Node w = F.poll() ;
        	
        	
        	// If w is the exit node and the steps that will be taken are less than the alloted amount
        	if ( w.getId() == end.getId()  ) { //&& map.get(w).distance <= state.ste2psLeft()) {
        		
        		if( map.get(w).distance > 0)
        			return constructPath(end, map) ;
        		else {
        			//F.add(w, 0);
        			//map.replace(w, new  SFdata(Integer.MAX_VALUE, null)) ;
        			map.remove(w) ;
        			w = F.poll();
        		}
        	}
        	
        	//Set<Edge> edges = w.getExits() ;
        	
        	Set<Node> neighbors = w.getNeighbors() ;
        	
        	for ( Node ed : neighbors ) {
        		
        		if ( !map.containsKey(ed) ) {
        			
        			int edgeLength = ed.getEdge(w).length() ;
        			int dist = map.get(w).distance ;
        			int goldAmount = ed.getTile().gold() ;
        			int goldSoFar =  map.get(w).gold ;
        			
        			F.add(ed, state.stepsLeft() - (edgeLength + dist));
        			//F.add(ed, goldAmount + goldSoFar);
        			map.put(ed, new SFdata(dist + edgeLength, w, goldAmount + goldSoFar) ) ;
        			
        		} else {
        			
        			int dist = map.get(w).distance ;
					int prevDist = map.get(ed).distance ;
					int gold = map.get(w).gold ;
					int prevGold = map.get(ed).gold ;
					int goldOnTile = ed.getTile().gold() ;
					
					if ( map.get(ed).backPointer != w )
						goldOnTile = 0 ;
        			
					if( state.stepsLeft()-(dist + ed.getEdge(w).length()) > prevDist ) {
					//if ( gold + goldOnTile < prevGold ) {

						// if we have, change the distance in the frontier set.
						//F.changePriority(ed, dist + ed.getEdge(w).length());
						try {
							//F.changePriority(ed, gold + goldOnTile);
							F.changePriority(ed, state.stepsLeft()-(dist+ed.getEdge(w).length()));
						} 
						catch (Exception e) {
							//F.add(ed, gold + goldOnTile);
							F.add(ed, state.stepsLeft()-(dist+ed.getEdge(w).length()));
						}
						//map.replace(ed, new SFdata(dist+ed.getEdge(w).length(),w)) ;
						map.replace(ed, new SFdata( state.stepsLeft()-(dist+ed.getEdge(w).length()),
								w,gold+ed.getTile().gold())) ;
					}
					
        		}
        		
//        		if (map.get(w).gold < 20000)
//        			System.out.println("Gold Considering: "+map.get(ed).gold);
        	}
        }
        
        return new LinkedList<Node>(); // no path found
    }
    
    public static List<Node> shortestPath4(Node start, Node end, ScramState state) {
        /* TODO Implement Dijkstras's shortest-path algorithm presented
         in the slides titled "Final Algorithm" (slide 37 or so) in the slides
         for lecture 20.
         In particular, a min-heap (as implemented in assignment A6) should be
         used for the frontier set. We provide a declaration of the frontier.
         We will make our solution to min-heap available as soon as the deadline
         for submission has passed.

         Maintaining information about shortest paths will require maintaining
         for each node in the settled and frontier sets the backpointer for
         that node, as described in the A7 handout, along with the length of the
         shortest path (thus far, for nodes in the frontier set). For this
         purpose, we provide static class SFdata. You have to declare
         the HashMap local variable for it and describe carefully what it
         means. WRITE A PRECISE DEFINITION OF IT.

         Note 1: Read the notes on pages 2..3 of the handout carefully.
         Note 2: The method MUST return as soon as the shortest path to node
                 end has been calculated. In that case, do NOT continue to find
                 shortest paths. You will lost 15 points if you do not do this.
         Note 3: the only graph methods you may call are these:

            1. n.getExits():  Return a List<Edge> of edges that leave Node n.
            2. e.getOther(n): n must be one of the Nodes of Edge e.
                              Return the other Node.
            3. e.length():    Return the length of Edge e.

         Method pathDistance uses one more: n1.getEdge(n2)
         */

        // The frontier set, as discussed in lecture 20
        Heap<Node> F= new Heap<Node>(false); // Is max heap
        HashMap<Node, SFdata> map = new HashMap<Node, SFdata>() ;
        
        F.add(start, state.stepsLeft());
        map.put(start, new SFdata(state.stepsLeft(), null, start.getTile().gold())) ;
        
        while( F.size() != 0 ) {
        	
        	Node w = F.poll() ;
        	
        	
        	// If w is the exit node and the steps that will be taken are less than the alloted amount
        	if ( w.getId() == end.getId()  ) { //&& map.get(w).distance <= state.ste2psLeft()) {
        		
        		if( map.get(w).distance > 0)
        			return constructPath(end, map) ;
        		else {
        			//F.add(w, 0);
        			//map.replace(w, new  SFdata(Integer.MAX_VALUE, null)) ;
        			map.remove(w) ;
        			w = F.poll();
        		}
        	}
        	
        	//Set<Edge> edges = w.getExits() ;
        	
        	Set<Node> neighbors = w.getNeighbors() ;
        	
        	for ( Node ed : neighbors ) {
        		
        		if ( !map.containsKey(ed) ) {
        			
        			int edgeLength = ed.getEdge(w).length() ;
        			int dist = map.get(w).distance ;
        			int goldAmount = ed.getTile().gold() ;
        			int goldSoFar =  map.get(w).gold ;
        			
        			F.add(ed, state.stepsLeft() - (edgeLength + dist));
        			//F.add(ed, goldAmount + goldSoFar);
        			map.put(ed, new SFdata(dist + edgeLength, w, goldAmount + goldSoFar) ) ;
        			
        		} else {
        			
        			int dist = map.get(w).distance ;
					int prevDist = map.get(ed).distance ;
					int gold = map.get(w).gold ;
					int prevGold = map.get(ed).gold ;
					int goldOnTile = ed.getTile().gold() ;
					
					if ( map.get(ed).backPointer != w )
						goldOnTile = 0 ;
        			
					if( state.stepsLeft()-(dist + ed.getEdge(w).length()) > prevDist ) {
					//if ( gold + goldOnTile < prevGold ) {

						// if we have, change the distance in the frontier set.
						//F.changePriority(ed, dist + ed.getEdge(w).length());
						try {
							//F.changePriority(ed, gold + goldOnTile);
							F.changePriority(ed, state.stepsLeft()-(dist+ed.getEdge(w).length()));
						} 
						catch (Exception e) {
							//F.add(ed, gold + goldOnTile);
							F.add(ed, state.stepsLeft()-(dist+ed.getEdge(w).length()));
						}
						//map.replace(ed, new SFdata(dist+ed.getEdge(w).length(),w)) ;
						map.replace(ed, new SFdata( state.stepsLeft()-(dist+ed.getEdge(w).length()),
								w,gold+ed.getTile().gold())) ;
					}
					
        		}
        		
//        		if (map.get(w).gold < 20000)
//        			System.out.println("Gold Considering: "+map.get(ed).gold);
        	}
        }
        
        return new LinkedList<Node>(); // no path found
    }
    
    public static List<Node> shortestPath5(Node start, Node end, ScramState state) {
    	
    	Heap<Node> front = new Heap<Node>(false) ;
    	HashMap<Node,SFdata> mapFront = new HashMap<Node,SFdata>() ;
    	
    	Heap<Node> back = new Heap<Node>(false) ;
    	HashMap<Node,SFdata> mapBack = new HashMap<Node,SFdata>() ;
    	
    	front.add(start, state.stepsLeft());
    	mapFront.put(start, new SFdata(state.stepsLeft(),null,start.getTile().gold())) ;
    	
    	back.add(end, 0);
    	mapBack.put(end, new SFdata(0,null)) ;
    	
    	while( front.size() != 0 && back.size() != 0 ) {
    		
    		if (front.size() != 0 ) {
    			Node first = front.poll() ;
    			
    			if( first.getId() == end.getId() || mapBack.containsKey(first) ) {
    				
    				Node temp = mapBack.get(first).backPointer;
    				//Node backPt = 
    				int distanceSoFar = mapFront.get(first).distance ;
    				
    				while (temp != end) {
    					
    					int dist = 0 ;
    					if ( mapBack.containsKey(first) ) {
    						dist = first.getEdge(temp).length() ;
    					}
    					
    					//mapFront.replace(temp, new SFdata(distanceSoFar+dist,))
    					mapFront.put(temp, new SFdata(distanceSoFar+dist,first)) ;
    					Node temp1 = temp ;
    					first = temp ;
    					temp = mapBack.get(temp1).backPointer ;
    				}
    				
    				return constructPath(end, mapBack) ;
    				
    			}
    			
    			for (Node neighbor : first.getNeighbors()) {
    				
    				if (!mapFront.containsKey(neighbor)) {
    					
    					int gold = neighbor.getTile().gold() ;
    					int goldSoFar = mapFront.get(first).gold ;
    					int distance = mapFront.get(first).distance + neighbor.getEdge(first).length() ;
    					
    					front.add(neighbor, state.stepsLeft()-distance);
    					mapFront.put(neighbor, new SFdata(state.stepsLeft() - distance, first, gold+goldSoFar)) ;
    					
    				} else {
    					
    					int goldOnTile = neighbor.getTile().gold() ;
    					int prevGold = mapFront.get(neighbor).gold ;
    					int goldSoFar = mapFront.get(first).gold ;
    					int distance = mapFront.get(first).distance + neighbor.getEdge(first).length() ;
    					
    					if (mapFront.get(first).backPointer == neighbor || mapFront.get(neighbor).backPointer == null)
    						goldOnTile = 0 ;
    					
    					if (goldOnTile + goldSoFar < prevGold) {
    						
    						try {
    							front.changePriority(neighbor, state.stepsLeft()-distance);
    						} catch (Exception e) {
    							front.add(neighbor, state.stepsLeft()-distance);
    						}
    						
    						mapFront.replace(neighbor, new SFdata(state.stepsLeft() - distance, first, prevGold+1)) ;
    					}
    				}
    			}
    		}
    		
    		if (back.size() != 0 ) {
    			Node last = back.poll() ;
    			
    			if( last.getId() == start.getId() || mapFront.containsKey(last) ) {
    				
    				Node temp = mapBack.get(last).backPointer;
    				//Node backPt = 
    				int distanceSoFar = mapFront.get(last).distance ;
    				
    				while (temp != end) {
    					
    					int dist = 0 ;
    					if ( mapBack.containsKey(last) ) {
    						dist = last.getEdge(temp).length() ;
    					}
    					
    					//mapFront.replace(temp, new SFdata(distanceSoFar+dist,))
    					mapFront.put(temp, new SFdata(distanceSoFar+dist,last)) ;
    					Node temp1 = temp ;
    					last = temp ;
    					temp = mapBack.get(temp1).backPointer ;
    				}
    				
    				return constructPath(end, mapBack) ;
    				
    			}
    			
    			for (Node neighbor : last.getNeighbors()) {
    				
    				if (!mapBack.containsKey(neighbor)) {
    					
    					int gold = neighbor.getTile().gold() ;
    					int goldSoFar = mapBack.get(last).gold ;
    					int distance = mapBack.get(last).distance + neighbor.getEdge(last).length() ;
    					
    					front.add(neighbor, distance);
    					mapBack.put(neighbor, new SFdata(distance, last, gold+goldSoFar)) ;
    					
    				} else {
    					
    					int goldOnTile = neighbor.getTile().gold() ;
    					int prevGold = mapBack.get(neighbor).gold ;
    					int goldSoFar = mapBack.get(last).gold ;
    					int distance = mapBack.get(last).distance + neighbor.getEdge(last).length() ;
    					
    					if (mapBack.get(last).backPointer == neighbor || mapBack.get(neighbor).backPointer == null)
    						goldOnTile = 0 ;
    					
    					if (goldOnTile + goldSoFar < prevGold) {
    						
    						try {
    							back.changePriority(neighbor, prevGold+1);
    						} catch (Exception e) {
    							back.add(neighbor, prevGold+1);
    						}
    						
    						mapBack.replace(neighbor, new SFdata(distance, last, prevGold+1)) ;
    					}
    				}
    			}
    		}
    		
    	}
    	
    	return new LinkedList<Node>() ;
    	
    }
    
    public static List<Node> shortestPath7(Node start, Node end, ScramState state) {
        /* TODO Implement Dijkstras's shortest-path algorithm presented
         in the slides titled "Final Algorithm" (slide 37 or so) in the slides
         for lecture 20.
         In particular, a min-heap (as implemented in assignment A6) should be
         used for the frontier set. We provide a declaration of the frontier.
         We will make our solution to min-heap available as soon as the deadline
         for submission has passed.

         Maintaining information about shortest paths will require maintaining
         for each node in the settled and frontier sets the backpointer for
         that node, as described in the A7 handout, along with the length of the
         shortest path (thus far, for nodes in the frontier set). For this
         purpose, we provide static class SFdata. You have to declare
         the HashMap local variable for it and describe carefully what it
         means. WRITE A PRECISE DEFINITION OF IT.

         Note 1: Read the notes on pages 2..3 of the handout carefully.
         Note 2: The method MUST return as soon as the shortest path to node
                 end has been calculated. In that case, do NOT continue to find
                 shortest paths. You will lost 15 points if you do not do this.
         Note 3: the only graph methods you may call are these:

            1. n.getExits():  Return a List<Edge> of edges that leave Node n.
            2. e.getOther(n): n must be one of the Nodes of Edge e.
                              Return the other Node.
            3. e.length():    Return the length of Edge e.

         Method pathDistance uses one more: n1.getEdge(n2)
         */

        // The frontier set, as discussed in lecture 20
        Heap<Node> F= new Heap<Node>(true); // Is max heap
        HashMap<Node, SFdata> map = new HashMap<Node, SFdata>() ;
        Heap<List<Node>> paths = new Heap<List<Node>>(true) ;
        
        F.add(start, 1);
        map.put(start, new SFdata(0, null, start.getTile().gold())) ;
        
        while( F.size() != 0 ) {
        	
        	Node w = F.poll() ;
        	
        	
        	// If w is the exit node and the steps that will be taken are less than the alloted amount
        	if ( w.getId() == end.getId()  ) { //&& map.get(w).distance <= state.ste2psLeft()) {
        		
        		if( map.get(w).distance < state.stepsLeft())
        			//return constructPath(w, map) ;
        			paths.add(constructPath(w,map), map.get(w).gold);
        		//else {
        			F.add(w, 1);
        			map.replace(w, new  SFdata(Integer.MAX_VALUE, null)) ;
        			w = F.poll();
        		//}
        	}
        	
        	//Set<Edge> edges = w.getExits() ;
        	
        	Set<Node> neighbors = w.getNeighbors() ;
        	
        	for ( Node ed : neighbors ) {
        		
        		if ( !map.containsKey(ed) ) {
        			
        			int edgeLength = ed.getEdge(w).length() ;
        			int dist = map.get(w).distance ;
        			int goldAmount = ed.getTile().gold() ;
        			int goldSoFar =  map.get(w).gold ;
        			
        			//F.add(ed, edgeLength + dist);
        			int amount = 0 ;
        			if ( goldAmount + goldSoFar == 0 )
        				amount = Integer.MAX_VALUE ;
        			else 
        				amount = (dist+edgeLength)/(goldAmount + goldSoFar) ;
        			F.add(ed, 1);
        			map.put(ed, new SFdata(dist + edgeLength, w, goldAmount + goldSoFar) ) ;
        			
        		} else {
        			
        			int dist = map.get(w).distance ;
					//int prevDist = map.get(ed).distance ;
					int gold = map.get(w).gold ;
					int prevGold = map.get(ed).gold ;
					int goldOnTile = ed.getTile().gold() ;
					
					int distance = map.get(w).distance ;
					int prevDist = map.get(ed).distance ;
					int edgeDist = ed.getEdge(w).length() ;
					
					if ( map.get(w).backPointer == ed || map.get(ed).backPointer == null )
						goldOnTile = 0 ;
        			
					//System.out.println("Back Pointer: " + map.get(ed).backPointer.getId());
					//System.out.println("Current Node: " + w.getId());
					//if( dist + ed.getEdge(w).length() < prevDist ) {
					
					int amount1 = 0 ;
					int amount2 = 0 ;
					if ( gold + goldOnTile == 0 )
						amount1 = Integer.MAX_VALUE ;
					else {
						amount1 = (distance+edgeDist)/(gold + goldOnTile) ;
					}
					
					if ( prevGold == 0 ) 
						amount2 = Integer.MAX_VALUE ;
					else 
						amount2 = prevDist/prevGold ;
					
					//if ( amount1 > amount2 ) {
					if ( gold+goldOnTile < prevGold) {
						
					//} else if (gold+goldOnTile == 0 || prevGold == 0 ) {
						
				//	} else if ((goldOnTile+gold)/prevGold < 5) {
						// if we have, change the distance in the frontier set.
						//F.changePriority(ed, dist + ed.getEdge(w).length());
						try {
							F.changePriority(ed, 1 );
						} 
						catch (Exception e) {
							F.add(ed, 1 );
						}
						//map.replace(ed, new SFdata(dist+ed.getEdge(w).length(),w)) ;
						map.replace(ed, new SFdata(dist+ed.getEdge(w).length(),w,gold+ed.getTile().gold())) ;
					}
					
        		}
        		
//        		if (map.get(w).gold < 20000)
//        			System.out.println("Gold Considering: "+map.get(ed).gold);
        	}
        }
        
        if(paths.size() != 0 ) 
        	return paths.poll() ;
        
        return shortestPath(start,end,state);
        //return new LinkedList<Node>(); // no path found
    }

    /** Return the path from the start node to node end.
     *  Precondition: nData contains all the necessary information about
     *  the path. */
    public static List<Node> constructPath(Node end, HashMap<Node, SFdata> nData) {
        LinkedList<Node> path= new LinkedList<Node>();
        Node p= end;
        // invariant: All the nodes from p's successor to the end are in
        //            path, in reverse order.
        while (p != null) {
            path.addFirst(p);
            p= nData.get(p).backPointer;
        }
        return path;
    }

    /** Return the sum of the weights of the edges on path path. */
    public static int pathDistance(List<Node> path) {
        if (path.size() == 0) return 0;
        synchronized(path) {
            Iterator<Node> iter= path.iterator();
            Node p= iter.next();  // First node on path
            int s= 0;
            // invariant: s = sum of weights of edges from start to p
            while (iter.hasNext()) {
                Node q= iter.next();
                s= s + p.getEdge(q).length;
                p= q;
            }
            return s;
        }
    }

    /** An instance contains information about a node: the previous node
     *  on a shortest path from the start node to this node and the distance
     *  of this node from the start node. */
    private static class SFdata {
        private Node backPointer; // backpointer on path from start node to this one
        private int distance; // distance from start node to this one
        private int gold ;

        /** Constructor: an instance with distance d from the start node and
         *  backpointer p.*/
        private SFdata(int d, Node p) {
            distance= d;     // Distance from start node to this one.
            backPointer= p;  // Backpointer on the path (null if start node)
            gold = 0;
        }
        
        private SFdata(int d, Node p, int g) {
        	this(d,p) ;
        	gold = g ;
        }

        /** return a representation of this instance. */
        public String toString() {
            return "dist " + distance + ", bckptr " + backPointer;
        }
    }
}
