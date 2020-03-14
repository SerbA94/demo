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
import com.demo.db.entity.RoomClass;

/**
 * Data access object for RoomClass entity.
 *
 * @author A.Serbin
 */
public class RoomClassDAO {

    private static final String SQL__GET_ROOM_CLASS_ID = "SELECT id FROM room_class WHERE room_class_title=?";
    private static final String SQL__GET_ROOM_CLASS_BY_ID = "SELECT room_class_title FROM room_class WHERE id=?";

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

    public static Set<RoomClass> getRoomClassSet(String title) {
    	return Collections.singleton(getRoomClass(title));
    }

    public RoomClass findRoomClassByTitle(String title) {
    	return getRoomClass(title);
    }

    private static RoomClass getRoomClass(String title) {
		switch (title) {
		case "budgetary":
			return RoomClass.BUDGETARY;
		case "family":
			return RoomClass.FAMILY;
		case "bussiness":
			return RoomClass.BUSINESS;
		case "luxury":
			return RoomClass.LUXURY;
		default:
			break;
		}
		return null;
	}

}
