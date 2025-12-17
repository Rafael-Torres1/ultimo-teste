public class Task {
    private int id;
    private String description;
    private Priority priority;
    private Status taskDone;


    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setTaskDone(Status taskDone) {
        this.taskDone = taskDone;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getTaskDone() {
        return taskDone;
    }

    @Override
    public String toString(){
        return "id: " +id+ " | description: " +description+ " | priority: " +priority+ " | status: " +taskDone;
    }
}