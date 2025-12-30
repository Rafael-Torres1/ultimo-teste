import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class TaskManager {
    // funções até o momento:
    // 6. listar tarefas por ordem,
    //  8. mostrar tarefa pelo status digitado, 9. mostrar tarefa pela prioridade digitada,
    // 10. completar tarefa pelo id, 11. função para mostrar os dados do banco

    // o que permanece: funções de perguntas enum, e tudo que não liga com banco de dados

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
        daoTask.listTask();
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
        daoTask.showByStatus(status);
    }

    public void showByPriority(Priority priority) {
        daoTask.showByPriority(priority);
    }

    public void completeTask(int id) {
        daoTask.completeTask(id);
    }
}