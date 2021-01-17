package cz.habrondrej.projekt.db;

import cz.habrondrej.projekt.db.repositories.Repository;
import cz.habrondrej.projekt.model.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService implements Repository<Role> {

    @Autowired
    private DataSource dataSource;

    @Override
    public void insert(Role role) {

    }

    @Override
    public void update(Role role) {

    }

    @Override
    public void delete(Role role) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public List<Role> getAll() {
        String sql = "SELECT * FROM role";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Role> roles = new ArrayList<>();

            while (rs.next()) {
                roles.add(new Role(rs.getInt("id"), rs.getString("nazev"), rs.getString("role")));
            }

            rs.close();
            ps.close();
            return roles;

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

    public List<Role> getAll(String role) {

        String sql;
        if (role.equals("ROLE_ADMIN")) {
            sql = "SELECT * FROM role WHERE role != 'ROLE_CUSTOMER'";
        } else if (role.equals("ROLE_MANAGER")) {
            sql = "SELECT * FROM role WHERE role != 'ROLE_CUSTOMER' AND role != 'ROLE_ADMIN' AND role != 'ROLE_MANAGER'";
        } else {
            return new ArrayList<>();
        }


        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Role> roles = new ArrayList<>();

            while (rs.next()) {
                roles.add(new Role(rs.getInt("id"), rs.getString("nazev"), rs.getString("role")));
            }

            rs.close();
            ps.close();
            return roles;

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
    public Role findById(int id) {
        String sql = "SELECT * FROM role WHERE id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Role role = null;

            if (rs.next()) {
                role = new Role(rs.getInt("id"), rs.getString("nazev"), rs.getString("role"));
            }

            rs.close();
            ps.close();
            return role;

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
