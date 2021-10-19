package Observer;

import java.util.ArrayList;
import java.util.List;

public class Eric implements  ClassLeader{
    private List<Student> students = new ArrayList<>();

    public void notifyNextClass(String nextClass) {
        System.out.println("반장 : 다음 수업은 " + nextClass + " 입니다.");
        notifyStudents("다음 수업 : " + nextClass + " 확인했습니다. ||");
    }

    @Override
    public void addStudent(Student student) {
        students.add(student);
    }

    @Override
    public void removeStudent(Student student) {
        students.remove(student);
    }

    @Override
    public void notifyStudents(String msg) {
        for (Student stu : students) {
            stu.getMessage(msg);
        }
    }
}
