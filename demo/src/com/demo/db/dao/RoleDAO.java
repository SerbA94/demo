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
import com.demo.db.entity.Role;

/**
 * Data access object for Role entity.
 *
 * @author A.Serbin
 */
public class RoleDAO {

    private static final String SQL__GET_ROLE_ID =
    		"SELECT roles.role_id FROM roles WHERE roles.role_title=?";

    private static final String SQL__GET_ROLE_BY_ID =
    		"SELECT roles.role_title FROM roles WHERE roles.role_id=?";


    /**
     * Returns role id.
     *
     * @param role
     *     	Role enum.
     * @return Role identifier.
     */
    public Long findRoleId(Role role) {
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
                id = rs.getLong(Fields.ROLE__ROLE_ID);
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
     * Returns role enum with given id.
     *
     * @param id
     *     	Role identifier.
     * @return Role enum.
     */
    public Role findRoleById(Long id) {
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

    /**
     * Returns role enum set by enum title.
     *
     * @param title
     *     	Role enum title.
     * @return Role enum set.
     */
    public static Set<Role> getRoleSetByTitle(String title) {
    	return Collections.singleton(getRole(title));
    }

    /**
     * Returns role enum by enum title.
     *
     * @param title
     *     	Role enum title.
     * @return Role enum.
     */
    public Role findRoleByTitle(String title) {
    	return getRole(title);
    }

    /**
     * Matches role title with enum.
     *
     * @param title
     *     	Role enum title.
     * @return Role enum.
     */
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
