package cz.habrondrej.projekt.db;

import cz.habrondrej.projekt.db.repositories.LockerRepository;
import cz.habrondrej.projekt.model.Locker;
import cz.habrondrej.projekt.model.utils.LockerHistory;
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
public class LockerService implements LockerRepository {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private CustomerService customerService;

    @Override
    public void insert(Locker locker) {
        String sql = "INSERT INTO satna (cislo_skrine) VALUES (?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, locker.getNumber());

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

    public void insert(LockerHistory lockerHistory) {
        String sql = "INSERT INTO satna_historie (satna, zakaznik, do) VALUES (?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, lockerHistory.getEntityID());
            ps.setInt(2, lockerHistory.getUser().getId());
            ps.setInt(3, lockerHistory.getTimeTo());

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
    public void update(Locker locker) {
        String sql = "UPDATE satna SET cislo_skrine = ? WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, locker.getNumber());
            ps.setInt(2, locker.getId());

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
    public void delete(Locker locker) {
        String sql = "DELETE FROM satna WHERE id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, locker.getId());

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
    public List<Locker> getAll() {
        String sql = "SELECT * FROM satna";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Locker> lockers = new ArrayList<>();
            Locker locker;

            while (rs.next()) {
                locker = new Locker(rs.getInt("id"), rs.getInt("cislo_skrine"));
                locker.setLockerHistory(getHistory(locker.getId()));
                lockers.add(locker);
            }

            rs.close();
            ps.close();
            return lockers;

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
    public Locker findById(int id) {
        String sql = "SELECT * FROM satna WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

             Locker locker = null;

            if (rs.next()) {
                locker = new Locker(rs.getInt("id"), rs.getInt("cislo_skrine"));
                locker.setLockerHistory(getHistory(locker.getId()));
            }

            rs.close();
            ps.close();
            return locker;

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

    public List<LockerHistory> getHistory(int id) {
        String sql = "SELECT * FROM satna_historie WHERE satna = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            List<LockerHistory> lockerHistories = new ArrayList<>();

            LockerHistory lockerHistory = null;

            while (rs.next()) {
                lockerHistory = new LockerHistory(rs.getInt("id"), rs.getTimestamp("datum"),
                        customerService.findById(rs.getInt("zakaznik")), rs.getInt("do"), rs.getInt("satna"));
                lockerHistories.add(lockerHistory);
            }

            rs.close();
            ps.close();
            return lockerHistories;

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
