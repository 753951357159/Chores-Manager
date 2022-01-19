import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReportManager {
	
	private static ReportManager instance;
	private final String csv = "data/reports.txt";  // File path for storing data
	private ObservableList<Report> reports;			// List of all reports to date
	
	private int totalDays;							// The total number of work days
	
	private float currentAmount;					// The total current amount of money earned by both parties
	private float totalAmount;						// The amount of money given at the end of one year to both parties
	
	private String userOneName;						// The name of user one to be displayed
	private float userOneWork;						// The average amount of work (in percent) done by user one across the year
	private float userOneCash;						// The amount of money earned with respect to the average amount of work done by user one
	
	private String userTwoName;						// The name of user two to be displayed
	private float userTwoWork;						// The average amount of work (in percent) done by user two across the year
	private float userTwoCash;						// The amount of money earned with respect to the average amount of work done by user two
	
	private ReportManager() {
		this.reports = FXCollections.observableArrayList();
		this.setDefault();
	}
	
	public static synchronized ReportManager getInstance() {
		if (instance == null) {
			instance = new ReportManager();
		}
		return instance;
	}
	
	/**
	 * Getter method for reports.
	 * @return The list of reports generated to date.
	 */
	public ObservableList<Report> getReports() {
		return reports;
	}
	
	/**
	 * Adds a report to the current list of reports and updates all statistics.
	 * @param report - The report to add
	 */
	public void addReport(Report report) {
		this.reports.add(report);
		this.updateStatistics();
	}
	
	/**
	 * Gets the last inserted report. Error if list is null or empty.
	 * @return The last (most recent) report.
	 */
	public Report getLastReport() {
		return this.reports.get(this.reports.size() - 1);
	}
	
	/**
	 * Resets the Report Manager back to it's default state. The total amount of cash is set back to 300. All cash
	 * gained is set to 0.00, all work done is set to 0.0, and all reports are cleared (deleted). The number of
	 * work days is reset to 365.
	 */
	public void setDefault() {
		this.reports.clear();
		
		this.totalDays = 365;
		
		this.currentAmount = 0;
		this.totalAmount = 100;
		
		this.userOneName = "User 1";
		this.userOneWork = 0;
		this.userOneCash = 0;
		
		this.userTwoName = "User 2";
		this.userTwoWork = 0;
		this.userTwoCash = 0;
	}
	
	/**
	 * Getter for totalDays.
	 * @return The total number of days the parties need to work in order to get full cash amount.
	 */
	public int getTotalDays() {
		return this.totalDays;
	}
	
	/**
	 * Setter for totalDays.
	 * @param newDays - The new total number of days that both parties need to work
	 */
	public void setTotalDays(int newDays) {
		this.totalDays = newDays;
		this.updateStatistics();
	}
	
	/**
	 * Getter for currentAmount.
	 * @return The total amount of cash that has been earned so far.
	 */
	public float getCurrentAmount() {
		return this.currentAmount;
	}
	
	/**
	 * Recalculates and updates currentAmount.
	 */
	private void updateCurrentAmount() {
		currentAmount = this.getUserOneCash() + this.getUserTwoCash();
	}
	
	/**
	 * Getter for totalAmount.
	 * @return The total amount of cash that will be given at the end of the year.
	 */
	public float getTotalAmount() {
		return this.totalAmount;
	}
	
	/**
	 * Setter for totalAmount.
	 * @param total - The new total amount of cash that will be given at the end of the year.
	 */
	public void setTotalAmount(float total) {
		this.totalAmount = total;
		this.updateStatistics();
	}
	
	/**
	 * Getter for userOneName.
	 * @return The name of user one.
	 */
	public String getUserOneName() {
		return this.userOneName;
	}
	
	/**
	 * Setter for userOneName
	 * @param name - The new name of user one
	 */
	public void setUserOneName(String name) {
		this.userOneName = name;
	}
	
	/**
	 * Getter for userOneWork.
	 * @return The average amount of work (in percent) done by user one.
	 */
	public float getUserOneWork() {
		return this.userOneWork;
	}
	
	/**
	 * Recalculates and updates userOneWork.
	 */
	private void updateUserOneWork() {
		if (this.reports.isEmpty()) {
			this.userOneWork = 0;
		} else {
			float sum = 0;
			
			for (Report report : this.reports) {
				sum += report.getUserOneWork();
			}
			this.userOneWork = sum / this.reports.size();
		}
	}
	
	/**
	 * Getter for userOneCash.
	 * @return The amount of cash that user one is suppose to get.
	 */
	public float getUserOneCash() {
		return this.userOneCash;
	}
	
	/**
	 * Recalculates and updates userOneCash.
	 */
	private void updateUserOneCash() {
		if (this.reports.isEmpty()) {
			this.userOneCash = 0;
		} else {
			this.userOneCash = (this.totalAmount / this.totalDays) * this.reports.size() * (this.userOneWork / 100);
		}		
	}
	
	/**
	 * Getter for userTwoName.
	 * @return The name of user two.
	 */
	public String getUserTwoName() {
		return this.userTwoName;
	}
	
	/**
	 * Setter for userTwoName
	 * @param name - The new name of user two
	 */
	public void setUserTwoName(String name) {
		this.userTwoName = name;
	}
	
	/**
	 * Getter for userTwoWork.
	 * @return The average amount of work (in percent) done by user two.
	 */
	public float getUserTwoWork() {
		return this.userTwoWork;
	}
	
	/**
	 * Recalculates and updates userTwoWork.
	 */
	private void updateUserTwoWork() {
		if (this.reports.isEmpty()) {
			this.userTwoWork = 0;
		} else {
			float sum = 0;
			
			for (Report report : this.reports) {
				sum += report.getUserTwoWork();
			}
			this.userTwoWork = sum / this.reports.size();
		}
	}
	
	/**
	 * Getter for userTwoCash.
	 * @return The amount of cash that user two is suppose to get.
	 */
	public float getUserTwoCash() {
		return this.userTwoCash;
	}
	
	/**
	 * Recalculates and updates userTwoCash.
	 */
	private void updateUserTwoCash() {
		if (this.reports.isEmpty()) {
			this.userTwoCash = 0;
		} else {
			this.userTwoCash = (this.totalAmount / this.totalDays) * this.reports.size() * (this.userTwoWork / 100);
		}
	}
	
	/**
	 * Updates all statistics of each person. This includes the amount of work done and the cash amount received.
	 */
	public void updateStatistics() {
		this.updateUserOneWork();
		this.updateUserOneCash();
		this.updateUserTwoWork();
		this.updateUserTwoCash();
		this.updateCurrentAmount();
	}
	
	/**
	 * Checks for whether this is a clean new application or not
	 * @return Returns True if this program has not been saved before, False otherwise.
	 */
	protected boolean emptyReportFile() {
		File check = new File(csv);
		return check.length() == 0;
	}
	
	protected void saveToFile() {
		OutputStream file;
		
		try {
			file = new FileOutputStream(csv);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			
			output.writeObject((new ArrayList<Report>(reports)));
			output.writeObject(this.totalDays);
			output.writeObject(this.totalAmount);
			output.writeObject(this.userOneName);
			output.writeObject(this.userTwoName);
			output.close();
			file.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void readFromFile() {
		InputStream file;
		
		try {
			file = new FileInputStream(csv);
			
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			
			List<Report> report = (List<Report>) input.readObject();
			this.reports = FXCollections.observableArrayList(report);
			this.totalDays = (int) input.readObject();
			this.totalAmount = (float) input.readObject();
			this.userOneName = (String) input.readObject();
			this.userTwoName = (String) input.readObject();
			input.close();
			file.close();
			
			this.updateStatistics();
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
}
