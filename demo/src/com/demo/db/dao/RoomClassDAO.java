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
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.demo.db.DBManager;
import com.demo.db.constants.Fields;
import com.demo.db.entity.RoomClass;

/**
 * Data access object for RoomClass entity.
 *
 * @author A.Serbin
 */
public class RoomClassDAO {

    private static final String SQL__GET_ROOM_CLASS_ID =
    		"SELECT room_class.room_class_id FROM room_class WHERE room_class.room_class_title=?";

    private static final String SQL__GET_ROOM_CLASS_BY_ID =
    		"SELECT room_class.room_class_title FROM room_class WHERE room_class.room_class_id=?";

    private static final String SQL__FIND_ALL_ROOM_CLASSES =
    		"SELECT room_class.room_class_title FROM room_class";


	/**
     * Returns list of all room classes.
     *
     * @return List of room class enums.
     */
	public List<RoomClass> findAllRoomClasses() {
		List<RoomClass> roomClass = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL__FIND_ALL_ROOM_CLASSES);
			while (rs.next()) {
				roomClass.add(getRoomClass(rs.getString(Fields.ROOM_CLASS__TITLE)));
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return roomClass;
	}

    /**
     * Returns room class id.
     *
     * @param roomClass
     *     	Room class enum.
     * @return Room class identifier.
     */
    public Long findRoomClassId(RoomClass roomClass) {
    	Long id = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__GET_ROOM_CLASS_ID);
            pstmt.setString(1, roomClass.getTitle());
            rs = pstmt.executeQuery();
            if (rs.next())
                id = rs.getLong(Fields.ROOM_CLASS__ROOM_CLASS_ID);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return id;
    }

    /**
     * Returns room class enum with given id.
     *
     * @param id
     *     	Room class identifier.
     * @return Room class enum.
     */
    public RoomClass findRoomClassById(Long id) {
    	String title = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__GET_ROOM_CLASS_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next())
            	title = rs.getString(Fields.ROOM_CLASS__TITLE);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    	return getRoomClass(title);
    }

    /**
     * Returns room class enum set by enum title.
     *
     * @param title
     *     	Room class enum title.
     * @return Room class enum set.
     */
    public static Set<RoomClass> getRoomClassSet(String title) {
    	return Collections.singleton(getRoomClass(title));
    }

    /**
     * Returns room class enum by enum title.
     *
     * @param title
     *     	Room class enum title.
     * @return Room class enum.
     */
    public RoomClass findRoomClassByTitle(String title) {
    	return getRoomClass(title);
    }

    /**
     * Matches Room class title with enum.
     *
     * @param title
     *     	Room class enum title.
     * @return Room class enum.
     */
    private static RoomClass getRoomClass(String title) {
		switch (title) {
		case "budgetary":
			return RoomClass.BUDGETARY;
		case "family":
			return RoomClass.FAMILY;
		case "business":
			return RoomClass.BUSINESS;
		case "luxury":
			return RoomClass.LUXURY;
		default:
			break;
		}
		return null;
	}

}
