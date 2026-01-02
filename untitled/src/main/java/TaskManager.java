import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class TaskManager {


    Scanner enter = new Scanner(System.in);
    DaoTask daoTask = new DaoTask();

    public Priority askForPriority() {
        System.out.println("1 - HIGH");
        System.out.println("2 - MID");
        System.out.println("3 - LOW");
        System.out.print("type priority of task: ");
        int choosePriority = enter.nextInt();
        enter.nextLine();

        return switch (choosePriority) {
            case 1 -> Priority.HIGH;
            case 2 -> Priority.MID;
            default -> Priority.LOW;
        };
    }

    public Status askForStatus() {
        System.out.println("1 - to do");
        System.out.println("2 - in progress");
        System.out.println("3 - done");
        System.out.print("type Status of task: ");
        int chooseStatus = enter.nextInt();
        enter.nextLine();

        return switch (chooseStatus) {
            case 1 -> Status.TODO;
            case 2 -> Status.INPROGRESS;
            default -> Status.DONE;
        };
    }

    public void addTask(String description) {
        Priority p = askForPriority();
        Status s = askForStatus();
        daoTask.addTask(description, p, s);

    }

    public void showTask() {
        displayTask();
    }

    public void updateStatus(int id) {
        Status s = askForStatus();

        daoTask.updateStatus(id, s);
    }

    public void priorityOrdem() {
        daoTask.ordemPriority();
    }

    public void removeTask(int id) {
        daoTask.deleteTask(id);
    }

    public void showByStatus(Status status) {
        daoTask.TaskInList().stream()
                .filter(t -> t.getStatus() == status)
                .forEach(task -> System.out.println("id: " + task.getId()+ " | description: " +task.getDescription()+
                        " | priority: " +task.getPriority()+ " | status: " + task.getStatus()));
    }

    public void showByPriority(Priority priority) {
        daoTask.showByPriority(priority);
    }

    public void completeTask(int id) {
        daoTask.completeTask(id);
    }

    public void displayTask(){
        List<Task> allTasks = daoTask.TaskInList();

        System.out.println("\n === tasks list ===");
        for (Task t : allTasks){
            System.out.println(t);
        }
    }


    public void showReport() {
        List<Task> allTasks = daoTask.TaskInList();

        long total = allTasks.size();
        long done = allTasks.stream()
                .filter(t -> t.getStatus() == Status.DONE).count();

        double percents = (total > 0) ? (double) (done * 100) / total : 0;

        System.out.println("\n ----- productivy report -----");
        System.out.println("total tasks: " +total+ " tasks done: " +done+ " percentage task done: " +percents);
    }
}