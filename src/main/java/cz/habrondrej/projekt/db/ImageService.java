package cz.habrondrej.projekt.db;

import cz.habrondrej.projekt.db.repositories.ImageRepository;
import cz.habrondrej.projekt.model.utils.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class ImageService implements ImageRepository {

    @Autowired
    DataSource dataSource;

    @Override
    public void insert(Image image) {

        String sql = "INSERT INTO profilove_obrazky (obrazek, pripona, zakaznik, nazev) VALUES (?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBlob(1, new ByteArrayInputStream(image.getImageData()));
            ps.setString(2, image.getType());
            ps.setInt(3, image.getUserID());
            ps.setString(4, image.getName());

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
    public void update(Image image) {

    }

    @Override
    public void delete(Image image) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public List<Image> getAll() {
        return null;
    }

    @Override
    public Image findById(int id) {
        String sql = "SELECT * FROM profilove_obrazky WHERE zakaznik = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            Image image = null;

            if (rs.next()) {
                image = new Image(rs.getInt("id"), rs.getString("pripona"),  rs.getInt("zakaznik"), rs.getBytes("obrazek"), rs.getString("nazev"));
            }

            rs.close();
            ps.close();

            return image;

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
