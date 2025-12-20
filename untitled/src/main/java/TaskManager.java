import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    List<Task> taskList = new ArrayList<>();
    Scanner enter = new Scanner(System.in);
    int idCounter;

    public Priority askForPriority(){
        System.out.println("1 - HIGH");
        System.out.println("2 - MID");
        System.out.println("3 - LOW");
        System.out.print("type priority of task: ");
        int choosePriority = enter.nextInt();
        enter.nextLine();

        return switch (choosePriority){
            case 1 -> Priority.HIGH;
            case 2 -> Priority.MID;
            default -> Priority.LOW;
        };
    }

    public Status askForStatus(){
        System.out.println("1 - to do");
        System.out.println("2 - in progress");
        System.out.println("3 - done");
        System.out.print("type Status of task: ");
        int chooseStatus = enter.nextInt();
        enter.nextLine();

        return switch (chooseStatus){
            case 1 -> Status.TODO;
            case 2 -> Status.INPROGRESS;
            default -> Status.DONE;
        };
    }

    public void addTask(){
        idCounter ++;

        System.out.print("type description: ");
        String description = enter.nextLine();

        Priority p = askForPriority();
        Status s = askForStatus();

        Task task = new Task();

        task.setDescription(description);
        task.setPriority(p);
        task.setTaskDone(s);
        task.setId(idCounter);

        taskList.add(task);
    }

    public void showTask(){
        for(Task t : taskList){
            System.out.println(t);
        }
    }

    public void updateStatus(int id){
        if (taskList.isEmpty()){
            System.out.println("no task in list");
            return;
        }

        boolean found = false;

        for (Task t : taskList){
            if (t.getId() == id){
                Status s = askForStatus();
                t.setTaskDone(s);
                found = true;
                break;
            }
        }
        if (!found){
            System.out.println("error: task doesn't exist");
        }
    }

    public void priorityOrdem(){
        System.out.println("----- ordered by priority");
        taskList.sort(Comparator.comparing(Task::getPriority));
        showTask();
    }

    public void removeTask(int id){
        if (taskList.isEmpty()){
            System.out.println("no task in list");
            return;
        }
        boolean removed = taskList.removeIf(task -> task.getId() == id);
        if(removed){
            System.out.println("removed succesfully");
        }else {
            System.out.println("error: task no found");
        }
    }



}