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
import com.demo.db.entity.BookingRequestStatus;

/**
 * Data access object for BookingRequestStatus entity.
 *
 * @author A.Serbin
 */
public class BookingRequestStatusDAO {

    private static final String SQL__GET_BOOKING_REQUEST_STATUS_ID =
    		"SELECT booking_request_status.booking_request_status_id "
    		+ "FROM booking_request_status WHERE booking_request_status.booking_request_status_title=?";

    private static final String SQL__GET_BOOKING_REQUEST_STATUS_BY_ID =
    		"SELECT booking_request_status.booking_request_status_title "
    		+ "FROM booking_request_status WHERE booking_request_status.booking_request_status_id=?";


    /**
     * Returns booking request status id.
     *
     * @param bookingRequestStatus
     *     	Booking request status enum.
     * @return Booking request status identifier.
     */
    public Long findBookingRequestStatusId(BookingRequestStatus bookingRequestStatus) {
    	Long id = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__GET_BOOKING_REQUEST_STATUS_ID);
            pstmt.setString(1, bookingRequestStatus.getTitle());
            rs = pstmt.executeQuery();
            if (rs.next())
                id = rs.getLong(Fields.BOOKING_REQUEST_STATUS__BOOKING_REQUEST_STATUS_ID);
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
     * Returns booking request status enum with given id.
     *
     * @param id
     *     	Booking request status identifier.
     * @return Booking request status enum.
     */
    public BookingRequestStatus findBookingRequestStatusById(Long id) {
    	String title = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__GET_BOOKING_REQUEST_STATUS_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next())
            	title = rs.getString(Fields.BOOKING_REQUEST_STATUS__TITLE);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    	return getBookingRequestStatus(title);
    }

    /**
     * Returns booking request status enum set by enum title.
     *
     * @param title
     *     	Enum title.
     * @return Booking request status enum set.
     */
    public static Set<BookingRequestStatus> getBookingRequestStatusSet(String title) {
    	return Collections.singleton(getBookingRequestStatus(title));
    }

    /**
     * Returns booking request status enum by enum title.
     *
     * @param title
     *     	Enum title.
     * @return Booking request status enum.
     */
    public BookingRequestStatus findBookingRequestStatusByTitle(String title) {
    	return getBookingRequestStatus(title);
    }

    /**
     * Matches booking request status title with enum.
     *
     * @param title
     *     	Enum title.
     * @return Booking request status enum.
     */
    private static BookingRequestStatus getBookingRequestStatus(String title) {
		switch (title) {
		case "active":
			return BookingRequestStatus.ACTIVE;
		case "inactive":
			return BookingRequestStatus.INACTIVE;
		default:
			break;
		}
		return null;
	}

}
