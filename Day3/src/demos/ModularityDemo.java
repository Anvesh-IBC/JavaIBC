package demos;

public class ModularityDemo {

	public static void main(String[] args) {

		Department d = new Department();
		d.setDeptName("IT");

		Employee e = new Employee();
		e.setEmpId(101);
		e.setEmpName("Scott");
		e.setDept(d);

		System.out.println(e.getEmpName() + " works in " + e.getDept().getDeptName());
	}
}