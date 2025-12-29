import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class TaskManager {
    Scanner enter = new Scanner(System.in);

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

    public void addTask() {

        System.out.print("type description: ");
        String description = enter.nextLine();

        Priority p = askForPriority();
        Status s = askForStatus();

        String sql = "INSERT INTO tasks (description, priority, status) VALUES (?, ?, ?)";

        try (Connection conn = DataBaseConfig.getConnetction();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, description);
            stmt.setString(2, p.name());
            stmt.setString(3, s.name());

            stmt.executeUpdate();
            System.out.println("task saved in database");

        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void showTask() {
        String sql = "SELECT id, description, priority, status FROM tasks";

        try (Connection conn = DataBaseConfig.getConnetction();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n----- list of tasks -----");

            while (rs.next()) {

                int id = rs.getInt("id");
                String desc = rs.getString("description");
                String prio = rs.getString("priority");
                String stats = rs.getString("status");

                System.out.printf("ID: %d | [%s] | %s (%s)%n", id, stats, desc, prio);
            }

        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void updateStatus(int id) {
        Status s = askForStatus();
        String sql = "UPDATE tasks SET status = ? WHERE id = ?";

        try (Connection conn = DataBaseConfig.getConnetction();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.name());
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("status update");
            } else {
                System.out.println("error: task not found");
            }

        } catch (SQLException e) {
            System.out.println("error:" + e.getMessage());
        }
    }

    public void priorityOrdem() {
        String sql = """
                SELECT * FROM tasks
                ORDER BY CASE priority
                   WHEN 'HIGH' THEN 1
                   WHEN 'MID' THEN 2
                   WHEN 'LOW' THEN 3
                END
                """;

        try (Connection conn = DataBaseConfig.getConnetction();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n ----- ordered by ordem -----");

            while (rs.next()) {
                int id = rs.getInt("id");
                String desc = rs.getString("description");
                String prio = rs.getString("priority");
                String stats = rs.getString("status");

                System.out.println("id: " +id+ " | description: " +desc+ " | priority: " +prio+ " | status: " + stats);
            }

        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void removeTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DataBaseConfig.getConnetction();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int deleteRows = stmt.executeUpdate();

            if (deleteRows > 0) {
                System.out.println("task removed");
            } else {
                System.out.println("task not found ");
            }

        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void showByStatus(Status status) {
        String sql = "SELECT id, description, priority, status FROM tasks WHERE status = ?";

        try (Connection conn = DataBaseConfig.getConnetction();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());

            try (ResultSet rs = stmt.executeQuery()) {

                System.out.println("\n ----- filtered by status: " + status + " -----");
                printResultSet(rs);
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

                System.out.println("\n ----- filtered by priority" + priority + " -----");
                printResultSet(rs);
            }

        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public void completeTask(int id) {
        String sql = "UPDATE tasks SET status = ? WHERE id = ?";

        try (Connection conn = DataBaseConfig.getConnetction();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, Status.DONE.name());
            stmt.setInt(2, id);

            if (stmt.executeUpdate() > 0) {
                System.out.println("task " + id + " marked as completed");
            } else {
                System.out.println("task not found ");
            }

        } catch (SQLException e) {
            System.out.println("error:" + e.getMessage());
        }
    }

    private void printResultSet(ResultSet rs) throws SQLException {
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
}