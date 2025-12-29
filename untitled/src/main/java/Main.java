import java.util.Scanner;
import java.util.InputMismatchException;
public class Main {
     public static void main(String[] args) {
         DataBaseConfig.createTable();
         Scanner enter = new Scanner(System.in);
         int option = 0;
         TaskManager task = new TaskManager();



         do{
             try {
                 System.out.println("----- TASK MENU -----");
                 System.out.println("1 - add task");
                 System.out.println("2 - show task list");
                 System.out.println("3 - update task status");
                 System.out.println("4 - show in order of priority");
                 System.out.println("5 - remove task");
                 System.out.println("6 - show by status");
                 System.out.println("7 - show by priority");
                 System.out.println("8 - complete task");
                 System.out.println("9 - exit ");
                 System.out.print("type your choice: ");
                 option = enter.nextInt();
                 enter.nextLine();

                 switch (option){
                     case 1 -> task.addTask();


                     case 2 -> task.showTask();


                     case 3 -> {

                         System.out.print("type id to update status: ");
                         int id = enter.nextInt();
                         enter.nextLine();

                         task.updateStatus(id);
                     }

                     case 4 -> task.priorityOrdem();

                     case 5 -> {
                         System.out.print("type id to remove task: ");
                         int id = enter.nextInt();
                         enter.nextLine();

                         task.removeTask(id);
                     }

                     case 6 -> {
                         Status s = task.askForStatus();

                         task.showByStatus(s);
                     }

                     case 7 -> {
                         Priority p = task.askForPriority();

                         task.showByPriority(p);
                     }

                     case 8 -> {
                         System.out.println("----- TASK COMPLETED -----");
                         System.out.print("type id task completed: ");
                         int id = enter.nextInt();
                         enter.nextLine();

                         task.completeTask(id);
                     }

                     case 9 -> System.out.println("saved data and leaving...");



                     default -> System.out.println("invalid number");

                 }


             }catch (InputMismatchException e){
                 System.out.print("error: is a not number: ");
                 enter.nextLine();
             }
         }while (option != 9);
         System.out.println("thanks so much, come back soon");
    }
}
