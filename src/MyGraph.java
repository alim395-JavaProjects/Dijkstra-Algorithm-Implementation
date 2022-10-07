import java.util.*;

/**
 * A representation of a graph. Assumes that we do not have negative cost edges
 * in the graph.
 */
public class MyGraph implements Graph {
	// you will need some private fields to represent the graph
	// you are also likely to want some private helper methods

	private Set<Vertex> v; //Use Set to automatically ignore duplicates
	private Set<Edge> e; //Use Set to automatically ignore duplicates
	
	private Map<Vertex, Set<Edge>> g;

	public MyGraph(Collection<Vertex> v, Collection<Edge> e) {
		//Check if empty
		if(v == null || e == null) {
			throw new IllegalArgumentException("Parameters are null");
		}
		
		//Initialize private variables
		this.v = new HashSet<Vertex>();
		this.e = new HashSet<Edge>();
		this.g = new HashMap<Vertex, Set<Edge>>();
		
		//Put all vertices in the graph
		for(Vertex vertex : v) {
			this.v.add(vertex);
		}
		
		//Check the edges before adding it to e and g
		checkEdges(e);
		
		
	}
	private void checkEdges(Collection<Edge> e2) {
		for (Edge thisE : e2) {
			 //Check if graph contains the two vertices of the edge in question.
			if (!g.containsKey(thisE.getSource()) || !g.containsKey(thisE.getDestination())) {
				throw new IllegalArgumentException("The edge " + thisE.getSource() + " is not in the graph.");
			}

			//Check if edge weight is negative.
			if (thisE.getWeight() < 0) {
				throw new IllegalArgumentException("The edge " + thisE.getSource() + "'s weight is not valid.");
			}
			
			for (Edge thatE : e2) {
				if (thisE.getSource().equals(thatE.getSource()) && thisE.getDestination().equals(thatE.getDestination())
						&& thisE.getWeight() != thatE.getWeight()) {
					throw new IllegalArgumentException();
				}
				e.add(thisE);
				g.get(thisE.getSource()).add(thisE);
			}
		}
		
	}

	@Override
	public Collection<Vertex> vertices() {
		return this.v;
	}

	@Override
	public Collection<Edge> edges() {
		return this.e;
	}

	@Override
	public Collection<Vertex> adjacentVertices(Vertex v) {
		// If v does not exist.
		if (!g.containsKey(v)) {
			throw new IllegalArgumentException();
		}
		// Using a set to avoid duplicates
		Set<Vertex> adjV = new HashSet<Vertex>();
		// Using myGraphs to find all vertices adjacent
		for (Edge edge : g.get(v)) {
			// Add the Vertex that is the destination of the edge
			adjV.add(edge.getDestination());
		}
		return adjV;
	}

	@Override
	public int edgeCost(Vertex a, Vertex b) {
		// If a or b do not exist, throw an exception.
		if (!g.containsKey(a) || !g.containsKey(b)) {
			throw new IllegalArgumentException();
		}
		int eCost = -1;
		for (Edge edge : g.get(a)) {
			// if the Vertex that is the destination of the edge is equal to b
			if (edge.getDestination().equals(b)) {
				eCost = edge.getWeight();
			}
		}
		return eCost;
	}

	//Returns the shortest path from a to b in the graph, or null if there is none.
	public Path shortestPath(Vertex a, Vertex b) {
		
		//If a or b does not exist.
		if (!g.containsKey(a) || !g.containsKey(b)) {
			throw new IllegalArgumentException();
		}
		
		List<Vertex> vertices = new ArrayList<Vertex>();
		
		//If it is self edge then the cost is 0;
		if(a.equals(b)){
			vertices.add(a);
			return new Path(vertices,0);
		}
		
		for(Vertex vertex: vertices()){
			vertex.updateCost(Integer.MAX_VALUE);
			vertex.updateStatus(false);
		}
		
		PriorityQueue<Vertex> pQ =new PriorityQueue<Vertex>();
		// Set the cost of start vertex to 0;
		a.updateCost(0);
		pQ.add(a);
		
		// Build_heap with all vertexes
		while(!pQ.isEmpty()){  // heap is not empty
			Vertex target = pQ.poll(); // smallest cost.
			target.updateStatus(true); // Set known.
			
			for(Vertex adjacentVertice : this.adjacentVertices(target)){
				//Only in myVertices list's vertices have cost.
				for(Vertex vertex: vertices()){
					if(vertex.equals(adjacentVertice)){
						adjacentVertice =vertex;
						break;
					}
				}
				// If the vertex is unknown.
				if(!adjacentVertice.getStatus()){
					int newCost=target.getCost()+edgeCost(target,adjacentVertice);
					int oldCost=adjacentVertice.getCost();
					if(newCost<oldCost){
						// update the cost and the path of adjacentVertice.
						pQ.remove(adjacentVertice);
						adjacentVertice.updateCost(newCost);
						adjacentVertice.upPath(target);
						pQ.add(adjacentVertice);						
						// Check it the adjacentVertice is b
						if(adjacentVertice.equals(b)){
							b.updateCost(adjacentVertice.getCost());
							b.upPath(adjacentVertice.getPath());
						}
					}
				}
			}			
		}
		
		
		List<Vertex> result = new ArrayList<Vertex>();
		result.add(b);
		Vertex node = b;
		while(node.getPath()!=null){
			result.add(node.getPath());
			node= node.getPath();
		}
		
		Collections.reverse(result);
		return new Path(result, b.getCost());
	}

}

