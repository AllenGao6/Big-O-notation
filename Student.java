public class Student{
	private String firstName;
	private String lastName;
	private int age;
	
	public Student(String firstName, String lastName, int age){
		this.age = age;
		this.firstName = firstName;
		this.lastName = lastName;
	}	
	
	public String getLast(){
		return lastName;
	}
	
	public String toString(){
		return lastName + ", " + firstName + ": " + age;
	}
}
