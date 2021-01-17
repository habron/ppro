package cz.habrondrej.projekt.db;

import cz.habrondrej.projekt.db.repositories.SubscriptionRepository;
import cz.habrondrej.projekt.model.Subscription;
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
public class SubscriptionService implements SubscriptionRepository {

    @Autowired
    DataSource dataSource;

    @Override
    public void insert(Subscription subscription) {
        String sql = "INSERT INTO typ_predobjednani (cena, doba_platnosti, nazev) VALUES (?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setFloat(1, subscription.getPrice());
            ps.setInt(2, subscription.getDays());
            ps.setString(3, subscription.getName());

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
    public void update(Subscription subscription) {
        String sql = "UPDATE typ_predobjednani SET cena = ?, doba_platnosti = ?, nazev = ? WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setFloat(1, subscription.getPrice());
            ps.setInt(2, subscription.getDays());
            ps.setString(3, subscription.getName());
            ps.setInt(4, subscription.getId());

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
    public void delete(Subscription subscription) {
        String sql = "DELETE FROM typ_predobjednani WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, subscription.getId());

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
    public List<Subscription> getAll() {
        String sql = "SELECT * FROM typ_predobjednani";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Subscription> subscriptions = new ArrayList<>();

            while (rs.next()) {
                subscriptions.add(new Subscription(rs.getInt("id"), rs.getFloat("cena"),
                        rs.getInt("doba_platnosti"), rs.getString("nazev")));
            }

            rs.close();
            ps.close();
            return subscriptions;

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
    public Subscription findById(int id) {
        String sql = "SELECT * FROM typ_predobjednani WHERE id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Subscription subscription = null;

            if (rs.next()) {
                subscription = new Subscription(rs.getInt("id"), rs.getFloat("cena"),
                        rs.getInt("doba_platnosti"), rs.getString("nazev"));
            }

            rs.close();
            ps.close();
            return subscription;

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
