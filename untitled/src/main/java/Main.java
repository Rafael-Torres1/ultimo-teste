import java.util.Scanner;
import java.util.InputMismatchException;
public class Main {
     public static void main(String[] args) {
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
                 System.out.println("6 - exit");
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
                     case 6 -> System.out.println("leaving...");

                     default -> System.out.println("invalid number");

                 }


             }catch (InputMismatchException e){
                 System.out.println("type a valid number");
                 enter.nextLine();
             }
         }while (option != 6 );
         System.out.println("thanks so much, come back soon");
    }
}
