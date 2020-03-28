/**
 *
 */
package com.demo.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.demo.db.DBManager;
import com.demo.db.constants.Fields;
import com.demo.db.entity.Image;
import com.demo.db.entity.Room;

/**
 * Data access object for Image entity.
 *
 * @author A.Serbin
 */
public class ImageDAO implements EntityMapper<Image> {

	private static final String SQL__FIND_IMAGE_BY_ID =
			"SELECT * FROM images WHERE images.image_id=?";

	private static final String SQL__FIND_IMAGE_BY_NAME =
			"SELECT * FROM images WHERE images.name=?";

	private static final String SQL__FIND_ALL_IMAGES =
			"SELECT images.image_id, images.name, images.room_id FROM images";

	private static final String SQL__FIND_ROOM_IMAGES =
			"SELECT images.image_id, images.name, images.room_id FROM images WHERE images.room_id=?";

	private static final String SQL__CREATE_IMAGE =
			"INSERT INTO images (images.name, images.data, images.room_id) VALUES (?, ?, ?)";

	private static final String SQL__UPDATE_IMAGE =
			"UPDATE images SET images.name=?, images.data=? WHERE images.image_id=?";

	private static final String SQL__DELETE_IMAGE =
			"DELETE FROM images WHERE images.image_id=?";

	/**
     * Returns image with given id
     *
     * @param id
     *     	Image identifier.
     * @return Image entity.
     */
	public Image findImageById(Long id) {
		Image image = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_IMAGE_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				image = mapRow(rs);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			DBManager.getInstance().rollbackAndClose(con);
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return image;
	}

	/**
     * Returns image with given name
     *
     * @param name
     *     	Image name.
     * @return Image entity.
     */
	public Image findImageByName(String name) {
		Image image = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_IMAGE_BY_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				image = mapRow(rs);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			DBManager.getInstance().rollbackAndClose(con);
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return image;
	}

	/**
     * Returns list of all images.
     *
     * @return List of image entities.
     */
	public List<Image> findAllImages() {
		List<Image> images = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL__FIND_ALL_IMAGES);
			while (rs.next()) {
				Image image = new Image();
				image.setId(rs.getLong(Fields.IMAGE__IMAGE_ID));
				image.setName(rs.getString(Fields.IMAGE__NAME));
				image.setRoomId(rs.getLong(Fields.IMAGE__ROOM_ID));
				images.add(image);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return images;
	}

	/**
     * Returns list of images of the given room
     *
     * @param room
     *     	Room entity.
     * @return List of image entities.
     */
	public List<Image> findRoomImages(Room room) {
		List<Image> images = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_ROOM_IMAGES);
			pstmt.setLong(1, room.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Image image = new Image();
				image.setId(rs.getLong(Fields.IMAGE__IMAGE_ID));
				image.setName(rs.getString(Fields.IMAGE__NAME));
				image.setRoomId(rs.getLong(Fields.IMAGE__ROOM_ID));
				images.add(image);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return images;
	}

	/**
     * Create image.
     *
     * @param image
     *            image to create.
     * @param con
     *            Connection to db.
     *
     * @return Created image entity.
     */
	public Image createImage(Image image) {
		Connection con = null;
		Image createdImage = null;
		try {
			con = DBManager.getInstance().getConnection();
			createdImage = createImage(con, image);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return createdImage;
	}

	/**
     * Create image.
     *
     * @param image
     *            image to create.
     * @param con
     *            Connection to db.
     *
     * @return Created image entity.
     *
     * @throws SQLException
     */
	private Image createImage(Connection con, Image image) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(SQL__CREATE_IMAGE);
		int k = 1;
		pstmt.setString(k++, image.getName());
		pstmt.setBytes(k++, image.getData());
		pstmt.setLong(k++, image.getRoomId());
		pstmt.executeUpdate();

		Long id = null;
        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating room failed, no ID obtained.");
            }
        }finally{
        	pstmt.close();
        }
        image.setId(id);
        return image;
	}

	/**
     * Update image.
     *
     * @param image
     *            Image to update.
     */
	public void updateImage(Image image) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateImage(con, image);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	/**
     * Update image.
     *
     * @param image
     *            Image to update.
     * @param con
     *            Connection to db.
     * @throws SQLException
     */
	private void updateImage(Connection con, Image image) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(SQL__UPDATE_IMAGE);
		int k = 1;
		pstmt.setString(k++, image.getName());
		pstmt.setBytes(k++, image.getData());
		pstmt.setLong(k, image.getId());
		pstmt.executeUpdate();
		pstmt.close();
	}

	/**
     * Delete image.
     *
     * @param id
     *            Id of image to delete.
     */
	public void deleteImage(Long id) {
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__DELETE_IMAGE);
			pstmt.setLong(1, id);
			pstmt.execute();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	/**
     * Extracts image from the result set row.
     *
     * @param rs
     *        Result set row with data to extract.
     */
	@Override
	public Image mapRow(ResultSet rs) {
		try {
			Image image = new Image();
			image.setId(rs.getLong(Fields.IMAGE__IMAGE_ID));
			image.setName(rs.getString(Fields.IMAGE__NAME));
			image.setData(rs.getBytes(Fields.IMAGE__DATA));
			image.setRoomId(rs.getLong(Fields.IMAGE__ROOM_ID));
			return image;
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

}
