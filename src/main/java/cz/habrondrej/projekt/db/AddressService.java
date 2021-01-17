package cz.habrondrej.projekt.db;

import cz.habrondrej.projekt.db.repositories.Repository;
import cz.habrondrej.projekt.model.utils.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class AddressService implements Repository<Address> {

    @Autowired
    private DataSource dataSource;

    @Override
    public void insert(Address address) {

    }

    @Override
    public void update(Address address) {
        String sql = "UPDATE adresa SET mesto = ?, stat = ?, ulice = ?, psc = ? WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, address.getCity());
            ps.setString(2, address.getCountry());
            ps.setString(3, address.getStreet());
            ps.setString(4, address.getZip().replaceAll("\\s+",""));
            ps.setInt(5, address.getId());

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
    public void delete(Address address) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public List<Address> getAll() {
        return null;
    }

    @Override
    public Address findById(int id) {
        String sql = "SELECT * FROM adresa WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Address address = null;

            if (rs.next()) {
                address = new Address(rs.getInt("id"), rs.getString("mesto"), rs.getString("stat"), rs.getString("ulice"), rs.getString("psc"));
            }

            rs.close();
            ps.close();
            return address;

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
