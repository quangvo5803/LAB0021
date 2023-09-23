package Controller;

import java.util.ArrayList;
import java.util.Collections;

import Common.Validation;
import Model.Report;
import Model.Student;
import View.Menu;

public class Program extends Menu<String> {
    static String[] mainChoice ={"Create","Find and Sort","Update/Delete","Report","Exit"};
    ArrayList<Student> students;
    ArrayList<Report> reports;

    public Program(){
        super("WELCOME TO STUDENT MANAGER",mainChoice);
        students = new ArrayList<>();
        reports = new ArrayList<>();
    }

    public void execute(int n){
        switch(n){
            case 1:{
                createStudent();
                break;
            }
            case 2:{
                FindAndSort();
                break;
            }
            case 3:{
                UpdateAndDelete();
                break;
            }
            case 4:{
                report();
                break;
            }
            case 5:{
                System.exit(0);
                break;
            }
        }
    }

    public void createStudent(){
        System.out.println();
        //if number of student > 10 add to continue
        if(students.size()>10){
            if(Validation.getYesNo("Do you want to continue?(Y/N): ").equalsIgnoreCase("N")){
                return;
            }
        }
        //add student
            while(true){
                String id = Validation.getStudentID();
                String name = Validation.getString("Enter student name: ");
            for (Student s : students){
                while(s.getId().equalsIgnoreCase(id) && !s.getName().equalsIgnoreCase(name)){
                    System.out.println("This ID belongs to someone else! Enter again!!!");
                    id = Validation.getStudentID();
                    name = Validation.getString("Enter student name: ");
                }
            }
            String semester = Validation.getSemester();
            String course = Validation.getCourse();
            
        //check student exist
            for (Student s : students){
                if(s.getId().equalsIgnoreCase(id) 
                        && s.getName().equalsIgnoreCase(name) 
                        && s.getSemester().equalsIgnoreCase(semester) 
                        && s.getCourse().equalsIgnoreCase(course)){
                    System.out.println("Duplicate\n");
                    return;
            }
            }
        this.students.add(new Student(id, name, semester, course));
        //ask to continue
        String c  = Validation.getYesNo("Do you want to continue(Y/N): ");
        if(c.equalsIgnoreCase("Y")){
            createStudent();
            break;
        }else{
            System.out.println();
            return;
        }
        }
    }
    
    public void FindAndSort(){
        ArrayList<Student> findList = new ArrayList<>();
        String name = Validation.getString("Enter name want to find: ");
        for (Student s : students){
            if(s.getName().contains(name)){
                findList.add(s);
            }
        }
        if(findList.isEmpty()){
            System.out.println("Can not found");
        }else{
            Collections.sort(findList);
            System.out.println("---------------- Find List ---------------");
            findList.stream().forEach(s -> s.print());
            System.out.println("------------------------------------------\n");
        }
    }
    
    public void UpdateAndDelete(){
        if(students.isEmpty()){
            System.out.println("List empty");
            return;
        }
        ArrayList<Student> findList = new ArrayList<>();
        //Find student want to delete or update
        String id = Validation.getStudentID();
        for (Student s : students){
            if(s.getId().equalsIgnoreCase(id)){
                findList.add(s);
            }
        }
        //Update or Delete
        if(findList.isEmpty()){
            System.out.println("Can not found");
            return;
        }else{
            System.out.println("---------------LIST FOUND---------------");
            for(Student s : findList){
                System.out.printf("%-5s%-30s%-15s%-15s\n", findList.indexOf(s) + 1 , s.getName(), s.getSemester(), s.getCourse());
            }
            System.out.println("----------------------------------------");
            int choice = Validation.getInt("Enter student numerical order: ", 1, findList.size());
            Student student = findList.get(choice-1);
            String c = Validation.getUD("Do you want to Update(U) or Delete(D): ");
            if(c.equalsIgnoreCase("U")){
                String idUpdate = Validation.getStudentID();
                String nameUpdate = Validation.getString("Enter student name: ");
                String semester = Validation.getSemester();
                String course = Validation.getCourse();
                
                //Check change or not
                for (Student s : students){
                if(s.getId().equalsIgnoreCase(idUpdate) 
                        && s.getName().equalsIgnoreCase(nameUpdate) 
                        && s.getSemester().equalsIgnoreCase(semester) 
                        && s.getCourse().equalsIgnoreCase(course)){
                    System.out.println("Nothing change\n");
                }else{
                    student.setId(idUpdate);
                    student.setName(nameUpdate);
                    student.setSemester(semester);
                    student.setCourse(course);
                    System.out.println("Update success");
                }
                }
                return;
            }else{
                students.remove(student);
                System.out.println("Delete success");
                return;
            }
        }
    }
    
    public void report(){
        if(students.isEmpty()){
            System.out.println("List empty");
            return;
        }
        
        ArrayList<Report> reports = new ArrayList<>();

            for (Student s1 : students){
                String id = s1.getId();
                String name = s1.getName();
                String course = s1.getCourse();
                int total = 0;
                for(Student s2 : students){
                    if(id.equalsIgnoreCase(s2.getId()) && course.equalsIgnoreCase(s2.getCourse())){
                        total++;
                    }
                }
                boolean reportExist = false;
                for(Report r : reports){
                    if(name.equalsIgnoreCase(r.getName()) 
                            && course.equalsIgnoreCase(r.getCourse()) 
                            && total == r.getTotalCourse()){
                        reportExist = true;
                    }
                }
                
                if(!reportExist){
                    reports.add(new Report(name, course, total));
                }
            }
        
        
        for(int i =0;i<reports.size();i++){
            System.out.printf("%-15s|%-10s|%-5d\n",reports.get(i).getName(),reports.get(i).getCourse(),reports.get(i).getTotalCourse());
        }
    }
}
