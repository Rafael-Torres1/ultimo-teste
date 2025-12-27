import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

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
        idCounter++;

        System.out.print("type description: ");
        String description = enter.nextLine();

        Priority p = askForPriority();
        Status s = askForStatus();

        Task task = new Task();

        task.setDescription(description);
        task.setPriority(p);
        task.setStatus(s);
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
                t.setStatus(s);
                found = true;
                break;
            }
        }
        if (!found){
            System.out.println("error: task doesn't exist");
        }
    }

    public void priorityOrdem(){
        System.out.println("----- ordered by priority -----");
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

    public void showByStatus(Status status){
        if (taskList.isEmpty()){
            System.out.println("no task in list");
            return;
        }

        boolean found = false;

        for (Task t : taskList){
            if (t.getStatus() == status){
                System.out.println(t);
                found = true;
            }
        }
        if (!found){
            System.out.println("error: task doesn't exist");
        }
    }

    public void showByPriority(Priority priority){
        if (taskList.isEmpty()){
            System.out.println("no task in list");
            return;
        }

        boolean found = false;

        for(Task t : taskList){
            if(t.getPriority() == priority){
                System.out.println(t);
                found = true;
            }
        }
        if (!found){
            System.out.println("error: task doesn't exist with this status");
        }
    }

    public void saveTask(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter("tasks.json")){
            gson.toJson(taskList, writer);
            System.out.println("saved in JSON");
        }catch (IOException e){
            System.out.println("error in saved: " +e.getMessage());
        }
    }

    public void loadTask(){
        File file = new File("tasks.json");
        if(!file.exists()) return;

        Gson gson = new Gson();
        try (FileReader reader = new FileReader("tasks.json")){

            Type listType = new TypeToken<ArrayList<Task>>(){}.getType();

            taskList = gson.fromJson(reader, listType);

            if (!taskList.isEmpty()){
                idCounter = taskList.getLast().getId();
            }
        }catch (IOException e){
            System.out.println("error: "+ e.getMessage());
        }
    }
}