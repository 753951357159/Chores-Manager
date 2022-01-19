import java.io.Serializable;
import java.time.LocalDate;

public class Report implements Serializable {

	private static final long serialVersionUID = -4278719060171825833L;
	private final LocalDate date;		// The specified day that the work was done on
	private final int userOneWork;		// The amount of work (in percent) done by person one on this day
	private final int userTwoWork;		// The amount of work (in percent) done by person two on this day
	
	/**
	 * Creates a new Report on how much work is done for the specified day.
	 * @param day - The day that the work was completed on
	 * @param one - The amount of work (in percent) completed by user one
	 * @param two - The amount of work (in percent) completed by user two
	 */
	public Report(LocalDate day, int one, int two) {
		this.date = day;
		this.userOneWork = one;
		this.userTwoWork = two;	
	}
	
	/**
	 * Getter method for date.
	 * @return The date the work for this report was done on.
	 */
	public LocalDate getDate() {
		return date;
	}
	
	/**
	 * Getter method for userOneWork.
	 * @return The amount of work that user one did for the specified day in this report.
	 */
	public int getUserOneWork() {
		return this.userOneWork;
	}
	
	/**
	 * Getter method for userTwoWork.
	 * @return The amount of work that user two did for the specified day in this report.
	 */
	public int getUserTwoWork() {
		return this.userTwoWork;
	}
}
