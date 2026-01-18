import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static void insert(User user) {
        String sql = "INSERT INTO users(name, age, country) VALUES (?, ?, ?)";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setInt(2, user.getAge());
            ps.setString(3, user.getCountry());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<User>  findAll(){
        String findAllSql = "Select name, age, country from users";
                List<User> users = new ArrayList<>();
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(findAllSql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("country")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;

    }
}

