
/**
 * @author Ryan Hansen
 *
 * Insulation Project class.
 * Used for creating a new insulation project.
 */
public class InsulationProject extends AbstractProject {

	/**
	 * default version ID for serialization
	 */
	private static final long serialVersionUID = -1950641840941379037L;

	// Class specific variable used for
	// determining the insulating properties
	// of the materials being used.
	private int rValue;
	
	// constructor sets the water usage to -1.0 because
	// there is no way for a insulation project
	// to have an impact on water consumption.
	public InsulationProject(int theRValue) {
		this.rValue = theRValue;
		this.waterUsage = 1.0;
	}

	public int getRValue() {
		return this.rValue;
	}
	
}