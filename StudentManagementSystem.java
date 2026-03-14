import java.util.*;

class Student {
    private int id;
    private String name;
    private int age;
    private String course;
    
    public Student(int id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public int getId() { 
        return id; 
    }
    public String getName() { 
        return name;
    }
    public int getAge() { 
        return age; 
    }
    public String getCourse() { 
        return course; 
    }

    public void setName(String name) { 
        this.name = name; 
    }
    public void setAge(int age) { 
        this.age = age; 
    }
    public void setCourse(String course) { 
        this.course = course; 
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + 
               ", Age: " + age + ", Course: " + course;
    }
}

public class StudentManagementSystem {
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> searchStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> {
                    System.out.println("Exiting... Thank you!");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        students.add(new Student(id, name, age, course));
        System.out.println("Student added successfully!");
    }

    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }

        System.out.println("\n=== Student List ===");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void searchStudent() {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();

        for (Student s : students) {
            if (s.getId() == id) {
                System.out.println("Student Found:");
                System.out.println(s);
                return;
            }
        }

        System.out.println("Student not found!");
    }

    private static void updateStudent() {
        System.out.print("Enter Student ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Student s : students) {
            if (s.getId() == id) {

                System.out.print("Enter new Name: ");
                s.setName(sc.nextLine());

                System.out.print("Enter new Age: ");
                s.setAge(sc.nextInt());
                sc.nextLine();

                System.out.print("Enter new Course: ");
                s.setCourse(sc.nextLine());

                System.out.println("Student updated successfully!");
                return;
            }
        }
        System.out.println("Student not found!");
    }

    private static void deleteStudent() {
        System.out.print("Enter Student ID to Delete: ");
        int id = sc.nextInt();

        for (Student s : students) {
            if (s.getId() == id) {
                students.remove(s);
                System.out.println("Student removed successfully!");
                return;
            }
        }

        System.out.println("Student not found!");
    }
}