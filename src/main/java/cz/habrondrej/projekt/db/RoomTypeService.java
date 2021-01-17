package cz.habrondrej.projekt.db;

import cz.habrondrej.projekt.db.repositories.RoomTypeRepository;
import cz.habrondrej.projekt.model.RoomType;
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
public class RoomTypeService implements RoomTypeRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public void insert(RoomType roomType) {

        String sql = "INSERT INTO typ_mistnosti " +
                "(nazev, popis, provozni_rad) VALUES (?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, roomType.getName());
            ps.setString(2, roomType.getDescription());
            ps.setString(3, roomType.getOperatingRules());

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
    public void update(RoomType roomType) {
        String sql = "UPDATE typ_mistnosti SET nazev = ?, popis = ?, provozni_rad = ?" +
                " WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, roomType.getName());
            ps.setString(2, roomType.getDescription());
            ps.setString(3, roomType.getOperatingRules());
            ps.setInt(4, roomType.getId());

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
    public void delete(RoomType roomType) {
        String sql = "DELETE FROM typ_mistnosti WHERE id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, roomType.getId());

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
    public List<RoomType> getAll() {
        String sql = "SELECT * FROM typ_mistnosti";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<RoomType> roomTypes = new ArrayList<>();

            while (rs.next()) {
                roomTypes.add(new RoomType(rs.getInt("id"), rs.getString("nazev"),
                        rs.getString("popis"), rs.getString("provozni_rad")));
            }

            rs.close();
            ps.close();
            return roomTypes;

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
    public RoomType findById(int id) {
        String sql = "SELECT * FROM typ_mistnosti WHERE id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            RoomType roomType = null;
            if (rs.next()) {
                roomType = new RoomType(rs.getInt("id"), rs.getString("nazev"),
                        rs.getString("popis"), rs.getString("provozni_rad"));
            }

            rs.close();
            ps.close();

            return roomType;
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
