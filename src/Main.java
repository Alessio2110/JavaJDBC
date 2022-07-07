//import

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    //    String username = "BOOTCAMP";
//    String passowrd = "bootcamp";
    public static final String url = "jdbc:oracle:thin:BOOTCAMP/bootcamp@localhost:1521/XEPDB1";

    //    static {
//        OracleDriver od = new OracleDriver();
//        DriverManager.registerDriver(od);
//    }
    public static void main(String[] args) {
//        Optional<Classroom> classroom = badFindById(1);
//        Optional<Classroom> classroom2 = findById(1);
//        System.out.println("Bad find: " + classroom.get());
//        System.out.println("Good find: " + classroom2.get());
//        Classroom classroom = new Classroom(10, "Pippo class", 100, "Pippo software", false, true, false, true);
//        insertClassroom(classroom);
//        System.out.println(deleteClassroom(1));
        Classroom classroom2 =  new Classroom(4, "Not Pippo class", 100, "Not Pippo software", true, false, true, false);
        updateClassroom(classroom2);
//        Iterable<Classroom> classes = getAllClassrooms();
    }

    public static Iterable<Classroom> getAllClassrooms() {
//        jdbc:oracl:thin:[USERNAME]/[passowrd]@[IP]:[port]/[Service]
        String query = "SELECT id, name, capacity, software, projector, main_pc, is_computerized, is_virtual FROM CLASSROOM";
        try (
                Connection c = createConnection(); // Simple factory idiom
                Statement statement = c.createStatement(); // Factory method
                ResultSet rs = statement.executeQuery(query);
        ) {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Connection acquired");
            System.out.println("The class of the driver is: " + c.getClass().getName());
            System.out.println("Class of statement is: " + statement.getClass().getName());

            System.out.println("Class of result set is: " + rs.getClass().getName());
            List<Classroom> classes = new ArrayList<>();

            while (rs.next()) {
                classes.add(createClassroom(rs));
            }

//            classes.stream().forEach(System.out::println);
            return classes;

        } // end try
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    /**
     * The good version of find by id
     *
     * @param id
     * @return
     */
    public static Optional<Classroom> findById(long id) {
        String query = "SELECT id, name, capacity, software, projector, main_pc, is_computerized, is_virtual FROM CLASSROOM " +
                "WHERE ID = ?";

        try (
                Connection c = createConnection(); // Simple factory idiom
                PreparedStatement statement = c.prepareStatement(query);
        ) {
            statement.setLong(1, id);

            try (
                    ResultSet rs = statement.executeQuery();
            ) {
                if (rs.next()) {
                    Classroom cl = createClassroom(rs);
                    Optional<Classroom> classroom = Optional.of(cl);
                    return classroom;
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Never do this, bad for readability/writability, security with sql injections, performance issues (multiple execution plans for same query)
     *
     * @param id
     * @return
     */
    public static Optional<Classroom> badFindById(long id) {
        String query = "SELECT id, name, capacity, software, projector, main_pc, is_computerized, is_virtual FROM CLASSROOM " +
                "WHERE ID = " + id;

        try (
                Connection c = createConnection(); // Simple factory idiom
                Statement statement = c.createStatement(); // Factory method
                ResultSet rs = statement.executeQuery(query);
        ) {
            if (rs.next()) {
                Classroom cl = createClassroom(rs);
                Optional<Classroom> classroom = Optional.of(cl);
                return classroom;
            } else {
                return Optional.empty();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Classroom createClassroom(ResultSet rs) throws SQLException {
        long id = rs.getLong("ID");
        String name = rs.getString("NAME");
        Integer capacity = rs.getInt("CAPACITY"); // It may be null
        String software = rs.getString("SOFTWARE"); // It may be null
        Boolean projector = rs.getBoolean("PROJECTOR"); // It may be null
        Boolean main_pc = rs.getBoolean("MAIN_PC"); // It may be null
        Boolean is_computerized = rs.getBoolean("IS_COMPUTERIZED"); // It may be null
        boolean is_virtual = rs.getBoolean("IS_VIRTUAL");

        return new Classroom(id, name, capacity, software, main_pc, projector, is_computerized, is_virtual);
    }

    public static boolean deleteClassroom(long id) {
        String query = "DELETE FROM CLASSROOM " +
                "WHERE ID = ?";

        try (
                Connection c = createConnection(); // Simple factory idiom
                PreparedStatement statement = c.prepareStatement(query);
        ) {
            statement.setLong(1, id);
            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateClassroom(Classroom classroom){
        String query = "UPDATE CLASSROOM SET NAME = ?, CAPACITY = ?, SOFTWARE = ?, PROJECTOR = ?, IS_COMPUTERIZED = ?, IS_VIRTUAL = ?, MAIN_PC = ? " +
                "WHERE ID = ?";
        try (
                Connection c = createConnection(); // Simple factory idiom
                PreparedStatement statement = c.prepareStatement(query);
        ) {
            statement.setString(1, classroom.getName());
            statement.setInt(2, classroom.getCapacity());
            statement.setString(3, classroom.getSoftware());
            statement.setBoolean(4, classroom.isProjector());
            statement.setBoolean(5, classroom.isIs_computerized());
            statement.setBoolean(6, classroom.isIs_virtual());
            statement.setBoolean(7, classroom.isMain_pc());
            statement.setLong(8, classroom.getId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//    INSERT INTO CLASSROOM   (ID, NAME, CAPACITY, SOFTWARE, PROJECTOR, IS_COMPUTERIZED, IS_VIRTUAL,MAIN_PC)
//    VALUES (1000, 'Y', 100, 'N', NULL, 0, 0, 1)
    public static void insertClassroom(Classroom classroom){
        String query = "INSERT INTO CLASSROOM   (ID, NAME, CAPACITY, SOFTWARE, PROJECTOR, IS_COMPUTERIZED, IS_VIRTUAL,MAIN_PC)    VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String idQuery = "SELECT CLASSROOM_SEQUENCE.nextval as ID FROM DUAL";
        try (
                Connection c = createConnection(); // Simple factory idiom
                Statement idStatement = c.createStatement();
                ResultSet idRs = idStatement.executeQuery(idQuery);
                PreparedStatement statement = c.prepareStatement(query);
        ) {
            idRs.next();
            long id = idRs.getLong("ID");
            statement.setLong(1, id);
            statement.setString(2, classroom.getName());
            statement.setInt(3, classroom.getCapacity());
            statement.setString(4, classroom.getSoftware());
            statement.setBoolean(5, classroom.isProjector());
            statement.setBoolean(6, classroom.isIs_computerized());
            statement.setBoolean(7, classroom.isIs_virtual());
            statement.setBoolean(8, classroom.isMain_pc());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
