package cz.habrondrej.projekt.db;

import cz.habrondrej.projekt.db.repositories.RoomsRepository;
import cz.habrondrej.projekt.model.Room;
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
public class RoomsService implements RoomsRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public void insert(Room room) {
        String sql = "INSERT INTO mistnost " +
                "(kapacita, typ_mistnosti) VALUES (?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, room.getCapacity());
            ps.setInt(2, room.getRoomTypeId());

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
    public void update(Room room) {
        String sql = "UPDATE mistnost SET kapacita = ?, typ_mistnosti = ?" +
                " WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, room.getCapacity());
            ps.setInt(2, room.getRoomTypeId());
            ps.setInt(3, room.getId());

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
    public void delete(Room room) {
        String sql = "DELETE FROM mistnost WHERE id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, room.getId());

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
    public List<Room> getAll() {
        String sql = "SELECT * FROM mistnost_view";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Room> rooms = new ArrayList<>();

            while (rs.next()) {
                RoomType roomType = new RoomType(rs.getInt("id"), rs.getString("nazev"),
                        rs.getString("popis"), rs.getString("provozni_rad"));

                rooms.add(new Room(rs.getInt("m_id"), rs.getInt("kapacita"), roomType));
            }

            rs.close();
            ps.close();
            return rooms;

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
    public Room findById(int id) {
        String sql = "SELECT * FROM mistnost_view WHERE m_id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Room room = null;

            if (rs.next()) {
                RoomType roomType = new RoomType(rs.getInt("id"), rs.getString("nazev"),
                        rs.getString("popis"), rs.getString("provozni_rad"));

                room = new Room(rs.getInt("m_id"), rs.getInt("kapacita"), roomType);
            }

            rs.close();
            ps.close();
            return room;

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
