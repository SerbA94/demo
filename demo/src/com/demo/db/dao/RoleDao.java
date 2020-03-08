package com.demo.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.demo.db.DBManager;
import com.demo.db.constants.Fields;

public class RoleDao {

    private static final String SQL__GET_ROLE_ID = "SELECT id FROM roles WHERE role=?;";

    public int getRoleId(String role) {
    	int id = -1;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SQL__GET_ROLE_ID);
            pstmt.setString(1, role);
            rs = pstmt.executeQuery();
            if (rs.next())
                id = rs.getInt(Fields.ENTITY__ID);
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

}
