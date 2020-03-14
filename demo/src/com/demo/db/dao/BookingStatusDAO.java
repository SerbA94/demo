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
import com.demo.db.entity.BookingStatus;

/**
 * Data access object for BookingStatus entity.
 *
 * @author A.Serbin
 */
public class BookingStatusDAO {

    private static final String SQL__GET_BOOKING_STATUS_ID = "SELECT id FROM booking_status WHERE booking_status_title=?";
    private static final String SQL__GET_BOOKING_STATUS_BY_ID = "SELECT booking_status_title FROM booking_status WHERE id=?";

    public Long findBookingStatusId(BookingStatus bookingStatus) {
    	Long id = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__GET_BOOKING_STATUS_ID);
            pstmt.setString(1, bookingStatus.getTitle());
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

    public BookingStatus findBookingStatusById(Long id) {
    	String title = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__GET_BOOKING_STATUS_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next())
            	title = rs.getString(Fields.BOOKING_STATUS__TITLE);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    	return getBookingStatus(title);
    }

    public static Set<BookingStatus> getBookingStatusSet(String title) {
    	return Collections.singleton(getBookingStatus(title));
    }

    public BookingStatus findBookingStatusByTitle(String title) {
    	return getBookingStatus(title);
    }

    private static BookingStatus getBookingStatus(String title) {
		switch (title) {
		case "active":
			return BookingStatus.ACTIVE;
		case "expired":
			return BookingStatus.EXPIRED;
		case "not paid":
			return BookingStatus.NOT_PAID;
		case "unconfirmed":
			return BookingStatus.UNCONFIRMED;
		default:
			break;
		}
		return null;
	}

}
