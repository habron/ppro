package cz.habrondrej.projekt.db;

import cz.habrondrej.projekt.db.repositories.LoginRepository;
import cz.habrondrej.projekt.model.utils.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class LoginService implements LoginRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public void updateUsername(Login login) {
        String sql = "UPDATE login SET username = ? WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, login.getUsername());
            ps.setInt(2, login.getId());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    @Override
    public void updatePassword(Login login) {
        String sql = "UPDATE login SET password = ? WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, login.getPassword());
            ps.setInt(2, login.getId());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    @Override
    public void insert(Login login) {

    }

    @Override
    public void update(Login login) {
        String sql = "UPDATE login SET username = ?, password = ? WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, login.getUsername());
            ps.setString(2, login.getPassword());
            ps.setInt(3, login.getId());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    @Override
    public void delete(Login login) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public List<Login> getAll() {
        return null;
    }

    @Override
    public Login findById(int id) {

        String sql = "SELECT * FROM login WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Login login = null;

            if (rs.next()) {
                login = new Login(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            }

            rs.close();
            ps.close();
            return login;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }
}
