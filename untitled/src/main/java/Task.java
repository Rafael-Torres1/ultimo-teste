public class Task {
    private int id;
    private String description;
    private Priority priority;
    private Status status;


    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString(){
        return "id: " +id+ " | description: " +description+ " | priority: " +priority+ " | status: " + status;
    }

}