package cz.habrondrej.projekt.db;

import cz.habrondrej.projekt.db.repositories.EmployeeRepository;
import cz.habrondrej.projekt.model.Employee;
import cz.habrondrej.projekt.model.User;
import cz.habrondrej.projekt.model.utils.Login;
import cz.habrondrej.projekt.model.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService implements EmployeeRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public void insert(Employee employee) {
        String sql = "CALL `registrace_zamestnanec`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, employee.getBornDate());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getFirstname());
            ps.setString(4, employee.getLastname());
            ps.setString(5, employee.getPersonalIdentificationNumber());
            ps.setString(6, employee.getPhone());
            ps.setString(7, employee.getAddress().getCity());
            ps.setString(8, employee.getAddress().getCountry());
            ps.setString(9, employee.getAddress().getStreet());
            ps.setString(10, employee.getAddress().getZip().replaceAll("\\s+",""));
            ps.setString(11, employee.getLogin().getUsername());
            ps.setString(12, employee.getLogin().getPassword());
            ps.setInt(13, employee.getRole().getId());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
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
    public void update(Employee employee) {
        update((User) employee);
    }

    @Override
    public void update(User employee) {
        String sql = "UPDATE zamestnanci SET datum_narozeni = ?, email = ?, jmeno = ?, prijmeni = ?, rodne_cislo = ?, telefon = ?, role = ?  WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, employee.getBornDate());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getFirstname());
            ps.setString(4, employee.getLastname());
            ps.setString(5, employee.getPersonalIdentificationNumber());
            ps.setString(6, employee.getPhone());
            ps.setInt(7, employee.getRole().getId());
            ps.setInt(8, employee.getId());

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
    public void delete(Employee employee) {
        String sql = "DELETE FROM zamestnanci WHERE id = ?";
        String sql1 = "DELETE FROM adresa WHERE id = ?";
        String sql2 = "DELETE FROM login WHERE id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql1);

            ps.setInt(1, employee.getAddress().getId());

            ps.executeUpdate();
            ps.close();

            ps = conn.prepareStatement(sql2);

            ps.setInt(1, employee.getLogin().getId());

            ps.executeUpdate();
            ps.close();

            ps = conn.prepareStatement(sql);

            ps.setInt(1, employee.getId());

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
    public void deleteById(int id) {

    }

    @Override
    public List<Employee> getAll() {
        String sql = "SELECT z.*, r.nazev, r.role as r_role FROM zamestnanci z JOIN role r ON z.role = r.id";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Employee> employees = new ArrayList<>();

            while (rs.next()) {
                employees.add(new Employee(rs.getInt("id"), rs.getDate("datum_narozeni"), rs.getString("email"),
                        rs.getString("jmeno"), rs.getString("prijmeni"), rs.getString("rodne_cislo"),
                        rs.getString("telefon"), new Role(rs.getInt("role"), rs.getString("nazev"), rs.getString("r_role"))));
            }

            rs.close();
            ps.close();
            return employees;

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
    public Employee findById(int id) {
        String sql = "SELECT z.*, r.nazev, r.role as r_role, r.nazev FROM zamestnanci z JOIN role r ON z.role = r.id WHERE z.id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Employee employee = null;

            if (rs.next()) {
                Role role = new Role(rs.getInt("role"), rs.getString("nazev"), rs.getString("r_role"));
                employee = new Employee(rs.getInt("id"), rs.getDate("datum_narozeni"), rs.getString("email"),
                        rs.getString("jmeno"), rs.getString("prijmeni"), rs.getString("rodne_cislo"),
                        rs.getString("telefon"), role, rs.getInt("adresa"), rs.getInt("login"));
            }

            rs.close();
            ps.close();
            return employee;

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
    public Employee findUserByUsername(String username) {
        String sql = "SELECT z.*, r.nazev, r.role as r_role, r.nazev, l.* FROM zamestnanci z JOIN role r ON z.role = r.id JOIN login l ON z.login = l.id WHERE l.username = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            Employee employee = null;

            if (rs.next()) {
                Role role = new Role(rs.getInt("role"), rs.getString("nazev"), rs.getString("r_role"));
                employee = new Employee(rs.getInt("id"), rs.getDate("datum_narozeni"), rs.getString("email"),
                        rs.getString("jmeno"), rs.getString("prijmeni"), rs.getString("rodne_cislo"),
                        rs.getString("telefon"), role, rs.getInt("adresa"), rs.getInt("login"));

                employee.setLogin(new Login(rs.getString("username"), rs.getString("password")));
            }

            rs.close();
            ps.close();
            return employee;

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
