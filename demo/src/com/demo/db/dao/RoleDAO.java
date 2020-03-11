/**
 *
 */
package com.demo.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.demo.db.DBManager;
import com.demo.db.constants.Fields;
import com.demo.db.entity.Role;


/**
 * Data access object for Role entity.
 *
 * @author A.Serbin
 */
public class RoleDAO {

    private static final String SQL__GET_ROLE_ID = "SELECT id FROM roles WHERE role=?";
    private static final String SQL__GET_ROLE_BY_ID = "SELECT role FROM roles WHERE id=?";


    public Long getRoleId(Role role) {
    	Long id = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__GET_ROLE_ID);
            pstmt.setString(1, role.getTitle());
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

    public Role getRoleById(Long id) {
    	String title = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__GET_ROLE_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next())
            	title = rs.getString(Fields.ROLE__TITLE);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    	return getRole(title);
    }

    public Role getRoleByTitle(String title) {
    	return getRole(title);
    }

    private static Role getRole(String title) {
		switch (title) {
		case "admin":
			return Role.ADMIN;
		case "customer":
			return Role.CUSTOMER;
		case "inactive":
			return Role.INACTIVE;
		case "manager":
			return Role.MANAGER;
		default:
			break;
		}
		return null;
	}

}
