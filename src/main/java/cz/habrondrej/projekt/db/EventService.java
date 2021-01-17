package cz.habrondrej.projekt.db;

import cz.habrondrej.projekt.db.repositories.EventRepository;
import cz.habrondrej.projekt.model.*;
import cz.habrondrej.projekt.model.utils.History;
import cz.habrondrej.projekt.model.utils.Note;
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
public class EventService implements EventRepository {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;

    @Override
    public void insert(Event event) {
        String sql = "INSERT INTO udalost (od, do, kapacita, mistnost, nazev, popis, zamestnanci) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, event.getDateFrom());
            ps.setDate(2, event.getDateTo());
            ps.setInt(3, event.getCapacity());
            ps.setInt(4, event.getRoomId());
            ps.setString(5, event.getName());
            ps.setString(6, event.getDescription());
            ps.setInt(7, event.getUserCreate().getId());

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
    public void update(Event event) {
        String sql = "UPDATE udalost SET od = ?, do = ?, kapacita = ?, mistnost = ?, nazev = ?, popis = ?  WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, event.getDateFrom());
            ps.setDate(2, event.getDateTo());
            ps.setInt(3, event.getCapacity());
            ps.setInt(4, event.getRoomId());
            ps.setString(5, event.getName());
            ps.setString(6, event.getDescription());
            ps.setInt(7, event.getId());

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
    public void delete(Event event) {
        String sql = "DELETE FROM udalost WHERE id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, event.getId());

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
    public List<Event> getAll() {
        String sql = "SELECT * FROM udalost_view";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Event> events = new ArrayList<>();

            Event event;
            Room room;
            RoomType roomType;

            while (rs.next()) {
                event = new Event(rs.getInt("id"), rs.getDate("od"), rs.getDate("do"),
                        rs.getInt("kapacita"), rs.getInt("mistnost"), rs.getString("nazev"), rs.getString("popis"));

                roomType = new RoomType(rs.getInt("tm_id"), rs.getString("tm_nazev"), rs.getString("tm_popis"), rs.getString("provozni_rad"));
                room = new Room(rs.getInt("m_id"), rs.getInt("kapacita"), roomType);

                event.setRoom(room);
                event.setLoginUsers(getLoginUsers(event.getId()));
                event.setUserCreate(employeeService.findById(rs.getInt("zamestnanci")));

                events.add(event);
            }

            rs.close();
            ps.close();
            return events;

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

    private List<User> getLoginUsers(int id) {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM udalost_zakaznik WHERE udalost = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            User user = null;

            if (rs.next()) {
                user = customerService.findById(rs.getInt("zakaznik"));
                users.add(user);
            }

            rs.close();
            ps.close();
            return users;

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

    private List<Note> getNotes(int id) {
        List<Note> notes = new ArrayList<>();

        String sql = "SELECT * FROM udalost_poznamky WHERE udalost = ? ORDER BY datum DESC";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Note note = null;

            while (rs.next()) {
                note = new Note(rs.getInt("id"), rs.getString("text"), employeeService.findById(rs.getInt("zamestnanci")), rs.getTimestamp("datum"));
                notes.add(note);
            }

            rs.close();
            ps.close();
            return notes;

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

    private List<History> getHistory(int id) {
        List<History> history = new ArrayList<>();

        String sql = "SELECT * FROM udalost_historie WHERE udalost = ? ORDER BY datum DESC";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            History hist = null;

            while (rs.next()) {
                hist = new History(rs.getInt("id"), rs.getString("text"), rs.getTimestamp("datum"), employeeService.findById(rs.getInt("zamestnanci")));
                history.add(hist);
            }

            rs.close();
            ps.close();
            return history;

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
    public Event findById(int id) {
        String sql = "SELECT * FROM udalost WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Event event = null;

            if (rs.next()) {
                event = new Event(rs.getInt("id"), rs.getDate("od"), rs.getDate("do"),
                        rs.getInt("kapacita"), rs.getInt("mistnost"), rs.getString("nazev"), rs.getString("popis"));
                event.setLoginUsers(getLoginUsers(event.getId()));
                event.setNotes(getNotes(event.getId()));
                event.setHistory(getHistory(event.getId()));
                event.setUserCreate(employeeService.findById(rs.getInt("zamestnanci")));
            }

            rs.close();
            ps.close();
            return event;

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

    public void signInUser(Event event, User user) {

        String sql;

        if (event.isUserSigned(user)) {
            sql = "DELETE FROM udalost_zakaznik WHERE udalost = ? AND zakaznik = ?";
        } else {
            sql = "INSERT INTO udalost_zakaznik (udalost, zakaznik) VALUES (?, ?)";
        }

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, event.getId());
            ps.setInt(2, user.getId());

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

    public void insertNote(Note note) {
        String sql = "INSERT INTO udalost_poznamky (udalost, zamestnanci, text) VALUES (?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, note.getEventID());
            ps.setInt(2, note.getEmployee().getId());
            ps.setString(3, note.getText());

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

    public List<Event> getView() {
        String sql = "SELECT * FROM prehled_view";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Event> events = new ArrayList<>();

            Event event;
            Room room;
            RoomType roomType;

            while (rs.next()) {
                event = new Event(rs.getDate("od"), rs.getDate("do"),
                        rs.getInt("kapacita"), rs.getString("nazev"), rs.getString("popis"));

                roomType = new RoomType();
                roomType.setName(rs.getString("mistnost"));
                room = new Room();
                room.setCapacity(rs.getInt("kapacita") - rs.getInt("prihlaseni"));
                room.setRoomType(roomType);

                event.setRoom(room);
                Employee employee = new Employee();
                employee.setFirstname(rs.getString("zamestnanec"));
                event.setUserCreate(employee);

                events.add(event);
            }

            rs.close();
            ps.close();
            return events;

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
