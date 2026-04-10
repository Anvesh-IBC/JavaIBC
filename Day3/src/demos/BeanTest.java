package demos;

public class BeanTest {

	public static void main(String[] args) {

		EmployeeBean emp = new EmployeeBean();
		emp.setEmpId(1001);
		emp.setEmpName("Scott");

		System.out.println(emp.getEmpId());
		System.out.println(emp.getEmpName());
	}
}
