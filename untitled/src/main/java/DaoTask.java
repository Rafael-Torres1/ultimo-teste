import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class DaoTask {

    public void addTask(String description, Priority priority, Status status) {

        String sql = "INSERT INTO tasks (description, priority, status) VALUES (?, ?, ?)";

        try (Connection conn = DataBaseConfig.getConnetction();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, description);
            stmt.setString(2, priority.name());
            stmt.setString(3, status.name());
            stmt.executeUpdate();
            System.out.println("task saved ");


        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public List<Task> TaskInList(){
        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT id, description, priority, status FROM tasks";

        try (Connection conn = DataBaseConfig.getConnetction();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                Task task = new Task();

                task.setId(rs.getInt("id"));
                task.setDescription(rs.getString("description"));
                task.setPriority(Priority.valueOf(rs.getString("priority")));
                task.setStatus(Status.valueOf(rs.getString("status")));
                taskList.add(task);
            }
        }catch (SQLException e){
            System.out.println("error: " +e.getMessage());
        }
        return taskList;
    }

    public void updateStatus(int id, Status status) {

        String sql = "UPDATE tasks SET status = ? WHERE id = ? ";

        try (Connection conn = DataBaseConfig.getConnetction();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("updated task");
            } else {
                System.out.println("error: task not found");
            }

        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DataBaseConfig.getConnetction();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int deleteRows = stmt.executeUpdate();

            if (deleteRows > 0) {
                System.out.println("delete task " + id);
            } else {
                System.out.println("error: task not found");
            }
        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void ordemPriority() {
        String sql = """
                SELECT * FROM tasks
                ORDER BY CASE priority
                    WHEN 'HIGH' THEN 1
                    WHEN 'MID' THEN 2
                    WHEN 'LOW' THEN 3
                END
                """;

        try (Connection conn = DataBaseConfig.getConnetction();
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n ----- ordered by priority");

            while (rs.next()) {

                int id = rs.getInt("id");
                String desc = rs.getString("description");
                String prio = rs.getString("priority");
                String stats = rs.getString("status");

                System.out.println("id: " + id + " | description: " + desc + " | priority: " + prio + " | status: " + stats);
            }
        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void showByPriority(Priority priority) {
        String sql = "SELECT id, description, priority, status FROM tasks WHERE priority = ?";

        try (Connection conn = DataBaseConfig.getConnetction();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, priority.name());

            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\n ----- filtered by priority " + priority + " -----");
                printRS(rs);
            }

        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
    }


    public void printRS(ResultSet rs) throws SQLException {
        boolean found = false;

        while (rs.next()) {
            found = true;

            int id = rs.getInt("id");
            String desc = rs.getString("description");
            String prio = rs.getString("priority");
            String stats = rs.getString("status");

            System.out.printf("ID: %d | [%s] | %s (%s)%n", id, stats, desc, prio);
        }
        if (!found) {
            System.out.println("error: task not found");
        }
    }

    public void completeTask(int id){
        updateStatus(id, Status.DONE);
        System.out.println("task: " +id+ " done");
    }
}
