/**
 * Representation of a graph vertex
 */
public class Vertex {
	// label attached to this vertex
	private String label;
	private int vCost;
	private boolean vStat;
	private Vertex vPath;

	/**
	 * Construct a new vertex
	 * 
	 * @param label
	 *            the label attached to this vertex
	 */
	public Vertex(String label) {
		if (label == null)
			throw new IllegalArgumentException("null");
		this.label = label;
	}

	/**
	 * Get a vertex label
	 * 
	 * @return the label attached to this vertex
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Update vertex cost.
	 * @param cost the cost for this vertex.
	 * 
	 */
	public void updateCost(int cost){
		vCost = cost;
		
	}
	
	/**
	 * Return vertex cost.
	 * @return vCost the cost of this vertex.
	 */
	public int getCost(){
		return vCost;
	}
	
	/**
	 * Update vertex status.
	 * @param status the vertex know or unknown.
	 * 
	 */
	public void updateStatus(boolean status){
		vStat = status;
		
	}
	
	/**
	 * Return my status 
	 * @return boolean this vertex is know or unknown.
	 */
	public boolean getStatus(){
		return vStat;
	}
	
	/**
	 * Update this vertex's path.
	 * @param path the path.
	 * 
	 */
	public void upPath(Vertex path){
		vPath = path;
	}

	/**
	 * Get this vertex's path.
	 *@return myPath.
	 */
	public Vertex getPath(){
		return vPath;
	}
	
	//Compare the cost of vertices
	public int compareTo(Vertex vert) {
		return (int) Math.signum(this.vCost - vert.vCost);
	}

	/**
	 * A string representation of this object
	 * 
	 * @return the label attached to this vertex
	 */
	public String toString() {
		return label;
	}

	// auto-generated: hashes on label
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	// auto-generated: compares labels
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Vertex other = (Vertex) obj;
		if (label == null) {
			return other.label == null;
		} else {
			return label.equals(other.label);
		}
	}

}

