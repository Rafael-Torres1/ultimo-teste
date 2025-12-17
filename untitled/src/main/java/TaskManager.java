import java.util.*;

public class TaskManager {
    List<Task> taskList = new ArrayList<>();
    Scanner enter = new Scanner(System.in);
    int idCounter;

    private String askForStatus(){
        System.out.println("1 - to do");
        System.out.println("2 - in progress");
        System.out.println("3 - done");
        System.out.print("choose the status: ");
        int stats = enter.nextInt();
        enter.nextLine();

        Status status = switch (stats){
            case 1 -> Status.TODO;
            case 2 -> Status.INPROGRESS;
            case 3 -> Status.DONE;
            default -> Status.TODO; // Mesmo problema da primeira parte
        };
        return null;
    }

    public void addTask(){
        idCounter ++;
        System.out.print("type description: ");
        String description = enter.nextLine();

        System.out.println("1 - high");
        System.out.println("2 - mid");
        System.out.println("3 - low");
        System.out.print("choose the priority: ");
        int choose = enter.nextInt();
        enter.nextLine();

        Priority priority = switch (choose) {
            case 1 -> Priority.HIGH;
            case 2 -> Priority.MID;
            case 3 -> Priority.LOW;
            default -> Priority.LOW; // O Java obriga o default aqui
        };

        askForStatus();

        Task task = new Task();
        task.setDescription(description);
        task.setPriority(priority);
        task.setId(idCounter);

        taskList.add(task);
    }

    public void showTask(){
        for (Task i : taskList){
            System.out.println(i);
        }
    }

    public void updateStatus(int id){
        boolean found = false;
        String newStats = "";
        for(Task t : taskList){
            if(id == t.getId()){

            }
        }
        if (!found ){
            System.out.println("error: id task not found");
        }
    }

    public void priorityOrdem(){

        System.out.println("--- tasks ordered by priority ---");
        taskList.sort(Comparator.comparing(Task::getPriority));
        showTask();

    }
}
