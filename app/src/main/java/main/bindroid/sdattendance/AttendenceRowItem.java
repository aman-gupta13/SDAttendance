package main.bindroid.sdattendance;

/**
 * Created by vikassinghsuriyal on 9/4/15.
 */
public class AttendenceRowItem implements Comparable<AttendenceRowItem> {

	private String date;
	private String loginTime;

	public String getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}

	private String logoutTime;

	public String getNoOfHours() {
		return noOfHours;
	}

	public void setNoOfHours(String noOfHours) {
		this.noOfHours = noOfHours;
	}

	private String noOfHours;

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int compareTo(AttendenceRowItem another) {
		return another.getDate().compareToIgnoreCase(getDate());
	}
}
