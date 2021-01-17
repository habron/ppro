package cz.habrondrej.projekt.db;

import cz.habrondrej.projekt.db.repositories.CustomerRepository;
import cz.habrondrej.projekt.model.Customer;
import cz.habrondrej.projekt.model.User;
import cz.habrondrej.projekt.model.utils.Login;
import cz.habrondrej.projekt.model.utils.Role;
import cz.habrondrej.projekt.model.utils.Subscribe;
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
public class CustomerService implements CustomerRepository {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private ImageService imageService;

    @Override
    public void insert(Customer customer) {
        String sql = "CALL `registrace_zakaznik`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, customer.getBornDate());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getFirstname());
            ps.setString(4, customer.getLastname());
            ps.setString(5, customer.getPersonalIdentificationNumber());
            ps.setString(6, customer.getPhone());
            ps.setString(7, customer.getAddress().getCity());
            ps.setString(8, customer.getAddress().getCountry());
            ps.setString(9, customer.getAddress().getStreet());
            ps.setString(10, customer.getAddress().getZip().replaceAll("\\s+",""));
            ps.setString(11, customer.getLogin().getUsername());
            ps.setString(12, customer.getLogin().getPassword());

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
    public void update(Customer customer) {
        update((User) customer);
    }

    @Override
    public void update(User customer) {
        String sql = "UPDATE zakaznik SET datum_narozeni = ?, email = ?, jmeno = ?, prijmeni = ?, rodne_cislo = ?, telefon = ?  WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, customer.getBornDate());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getFirstname());
            ps.setString(4, customer.getLastname());
            ps.setString(5, customer.getPersonalIdentificationNumber());
            ps.setString(6, customer.getPhone());
            ps.setInt(7, customer.getId());

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
    public void delete(Customer customer) {
        String sql = "DELETE FROM zakaznik WHERE id = ?";
        String sql1 = "DELETE FROM adresa WHERE id = ?";
        String sql2 = "DELETE FROM login WHERE id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql1);

            ps.setInt(1, customer.getAddress().getId());

            ps.executeUpdate();
            ps.close();

            ps = conn.prepareStatement(sql2);

            ps.setInt(1, customer.getLogin().getId());

            ps.executeUpdate();
            ps.close();

            ps = conn.prepareStatement(sql);

            ps.setInt(1, customer.getId());

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
    public List<Customer> getAll() {
        String sql = "SELECT z.*, r.role as r_role, r.nazev FROM zakaznik z join role r on z.role = r.id";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Customer> customers = new ArrayList<>();

            while (rs.next()) {
                customers.add(new Customer(rs.getInt("id"), rs.getDate("datum_narozeni"), rs.getString("email"),
                        rs.getString("jmeno"), rs.getString("prijmeni"), rs.getString("rodne_cislo"),
                        rs.getString("telefon"), new Role(rs.getInt("role"), rs.getString("nazev"), rs.getString("r_role")), rs.getDate("platnost_do")));
            }

            rs.close();
            ps.close();
            return customers;

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
    public Customer findById(int id) {
        String sql = "SELECT z.*, r.role as r_role, r.nazev FROM zakaznik z join role r on z.role = r.id WHERE z.id = ?";
        String sql1 = "SELECT zakaznik_aktivni(?) AS aktivni";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, id);
            ResultSet rs1 = ps1.executeQuery();

            Customer customer = null;

            if (rs.next()) {
                Role role = new Role(rs.getInt("role"), rs.getString("nazev"), rs.getString("r_role"));
                customer = new Customer(rs.getInt("id"), rs.getDate("datum_narozeni"), rs.getString("email"),
                        rs.getString("jmeno"), rs.getString("prijmeni"), rs.getString("rodne_cislo"),
                        rs.getString("telefon"), role, rs.getInt("adresa"), rs.getInt("login"));
                customer.setActiveDate(rs.getDate("platnost_do"));
                customer.setSubscribes(getSubscribes(customer.getId()));
                customer.setImage(imageService.findById(customer.getId()));
                if (rs1.next()) {
                    customer.setActive(rs1.getBoolean("aktivni"));
                }
            }

            rs.close();
            ps.close();
            rs1.close();
            ps1.close();
            return customer;

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
    public Customer findUserByUsername(String username) {
        String sql = "SELECT z.*, r.role as r_role, r.nazev, l.* FROM zakaznik z JOIN role r ON z.role = r.id JOIN login l ON z.login = l.id WHERE l.username = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            Customer customer = null;

            if (rs.next()) {
                Role role = new Role(rs.getInt("role"), rs.getString("nazev"), rs.getString("r_role"));
                customer = new Customer(rs.getInt("id"), rs.getDate("datum_narozeni"), rs.getString("email"),
                        rs.getString("jmeno"), rs.getString("prijmeni"), rs.getString("rodne_cislo"),
                        rs.getString("telefon"), role, rs.getInt("adresa"), rs.getInt("login"));

                customer.setLogin(new Login(rs.getString("username"), rs.getString("password")));
            }

            rs.close();
            ps.close();
            return customer;

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

    private List<Subscribe> getSubscribes(int id) {
        String sql = "SELECT * FROM predobjednano WHERE zakaznik = ? ORDER BY objednano DESC";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            List<Subscribe> subscribes = new ArrayList<>();

            while (rs.next()) {
                subscribes.add(new Subscribe(rs.getInt("id"), rs.getFloat("cena"),
                        rs.getTimestamp("objednano"), subscriptionService.findById(rs.getInt("typ_predobjednani"))));
            }

            rs.close();
            ps.close();
            return subscribes;

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

    public void insertSubscribe(Subscribe subscribe) {
        String sql = "CALL aktivace_predplatne(?, ?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, subscribe.getSubscriptionID());
            ps.setInt(2, subscribe.getCustomerID());
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
}
