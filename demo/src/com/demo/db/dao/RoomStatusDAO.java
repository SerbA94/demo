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
import com.demo.db.entity.RoomStatus;

/**
 * Data access object for RoomStatus entity.
 *
 * @author A.Serbin
 */
public class RoomStatusDAO {

	private static final String SQL__FIND_ROOM_STATUS_ID =
			"SELECT id FROM room_status WHERE room_status_title=?";

    private static final String SQL__FIND_ROOM_STATUS_BY_ID =
    		"SELECT room_status_title FROM room_status WHERE id=?";

    private static final String SQL__FIND_ALL_ROOM_STATUSES =
    		"SELECT room_status_title FROM room_status";


	/**
     * Returns list of all room statuses.
     *
     * @return List of room status enums.
     */
	public List<RoomStatus> findAllRoomStatuses() {
		List<RoomStatus> roomStatuses = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL__FIND_ALL_ROOM_STATUSES);
			while (rs.next()) {
				roomStatuses.add(getRoomStatus(rs.getString(Fields.ROOM_STATUS__TITLE)));
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return roomStatuses;
	}

    /**
     * Returns room status id.
     *
     * @param roomStatus
     *     	Room status enum.
     * @return Room status identifier.
     */
    public Long findRoomStatusId(RoomStatus roomStatus) {
    	Long id = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__FIND_ROOM_STATUS_ID);
            pstmt.setString(1, roomStatus.getTitle());
            rs = pstmt.executeQuery();
            if (rs.next())
                id = rs.getLong(Fields.ENTITY__ID);
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
     * Returns room status enum with given id.
     *
     * @param id
     *     	Room status identifier.
     * @return Room status enum.
     */
    public RoomStatus findRoomStatusById(Long id) {
    	String title = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__FIND_ROOM_STATUS_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next())
            	title = rs.getString(Fields.ROOM_STATUS__TITLE);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    	return getRoomStatus(title);
    }

    /**
     * Returns room status enum set by enum title.
     *
     * @param title
     *     	Room status enum title.
     * @return Room status enum set.
     */
    public static Set<RoomStatus> getRoomStatusSet(String title) {
    	return Collections.singleton(getRoomStatus(title));
    }

    /**
     * Returns room status enum by enum title.
     *
     * @param title
     *     	Room status enum title.
     * @return Room status enum.
     */
    public RoomStatus findRoomStatusByTitle(String title) {
    	return getRoomStatus(title);
    }

    /**
     * Matches Room status title with enum.
     *
     * @param title
     *     	Room status enum title.
     * @return Room status enum.
     */
    private static RoomStatus getRoomStatus(String title) {
		switch (title) {
		case "booked":
			return RoomStatus.BOOKED;
		case "free":
			return RoomStatus.FREE;
		case "inaccesible":
			return RoomStatus.INACCESSIBLE;
		case "occupied":
			return RoomStatus.OCCUPIED;
		default:
			break;
		}
		return null;
	}

}
