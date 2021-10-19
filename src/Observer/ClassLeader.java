package Observer;

public interface ClassLeader {
    void addStudent(Student student);
    void removeStudent(Student student);
    void notifyStudents(String msg);
}
