package main.bindroid.sdattendance;

/**
 * Created by vikassinghsuriyal on 9/4/15.
 */
public class FeedItem {

	private String empId;
	private String empName;
	private String empMobile;
	private String empDept;
	private String empSeat;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpSeat() {
		return empSeat;
	}

	public void setEmpSeat(String empSeat) {
		this.empSeat = empSeat;
	}

	public String getEmpDept() {
		return empDept;
	}

	public void setEmpDept(String empDept) {
		this.empDept = empDept;
	}

	public String getEmpMobile() {
		return empMobile;
	}

	public void setEmpMobile(String empMobile) {
		this.empMobile = empMobile;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
}
