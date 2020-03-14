/**
 *
 */
package com.demo.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
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

	private static final String SQL__GET_ROOM_STATUS_ID = "SELECT id FROM room_status WHERE room_status_title=?";
    private static final String SQL__GET_ROOM_STATUS_BY_ID = "SELECT room_status_title FROM room_status WHERE id=?";

    public Long findRoomStatusId(RoomStatus roomStatus) {
    	Long id = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__GET_ROOM_STATUS_ID);
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

    public RoomStatus findRoomStatusById(Long id) {
    	String title = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__GET_ROOM_STATUS_BY_ID);
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

    public static Set<RoomStatus> getRoomStatusSet(String title) {
    	return Collections.singleton(getRoomStatus(title));
    }

    public RoomStatus findRoomStatusByTitle(String title) {
    	return getRoomStatus(title);
    }

    private static RoomStatus getRoomStatus(String title) {
		switch (title) {
		case "booked":
			return RoomStatus.BOOKED;
		case "free":
			return RoomStatus.FREE;
		case "inaccessible":
			return RoomStatus.INACCESSIBLE;
		case "occupied":
			return RoomStatus.OCCUPIED;
		default:
			break;
		}
		return null;
	}

}
