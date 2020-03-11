package com.demo.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

import com.demo.db.DBManager;
import com.demo.db.constants.Fields;
import com.demo.db.entity.Role;
import com.demo.db.entity.User;


/**
 * Data access object for User entity.
 *
 * @author A.Serbin
 */
public class UserDAO {

    private static final String SQL__FIND_USER_BY_LOGIN =
            "SELECT * FROM users JOIN roles ON users.role_id = roles.id WHERE login=?";

    private static final String SQL__FIND_USER_BY_EMAIL =
    		"SELECT * FROM users JOIN roles ON users.role_id = roles.id WHERE email=?";

    private static final String SQL__FIND_USER_BY_ID =
    		"SELECT * FROM users JOIN roles ON users.role_id = roles.id WHERE id=?";

    private static final String SQL_UPDATE_USER =
            "UPDATE users SET login=?, password=?, role_id=?, locale_name=?, email=?, activation_token=? WHERE id=?";

    private static final String SQL_CREATE_USER =
           "INSERT INTO users (login, password, role_id, locale_name, email, activation_token) VALUES (?, ?, ?, ?, ?, ?)";

    public User findUserById(Long id) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            pstmt = con.prepareStatement(SQL__FIND_USER_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = mapper.mapRow(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            DBManager.getInstance().rollbackAndClose(con);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return user;
    }

    public User findUserByLogin(String login) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            pstmt = con.prepareStatement(SQL__FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = mapper.mapRow(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return user;
    }


    public User findUserByEmail(String email) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            pstmt = con.prepareStatement(SQL__FIND_USER_BY_EMAIL);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = mapper.mapRow(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return user;
    }

    public void updateUser(User user) {
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            updateUser(con, user);
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    private void updateUser(Connection con, User user) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_USER);
        RoleDAO roleDao = new RoleDAO();
        Long roleId = roleDao.getRoleId((Role)user.getRole().toArray()[0]);
        int k = 1;
        pstmt.setString(k++, user.getLogin());
        pstmt.setString(k++, user.getPassword());
        pstmt.setLong(k++, roleId);
        pstmt.setString(k++, user.getLocaleName());
        pstmt.setString(k++, user.getEmail());
        pstmt.setString(k++, user.getActivationToken());
        pstmt.setLong(k, user.getId());
        pstmt.executeUpdate();
        pstmt.close();
    }

    public void createUser(User user) {
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            createUser(con, user);
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    private void createUser(Connection con, User user) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(SQL_CREATE_USER);
        RoleDAO roleDao = new RoleDAO();
        Long roleId = roleDao.getRoleId((Role)user.getRole().toArray()[0]);
        int k = 1;
        pstmt.setString(k++, user.getLogin());
        pstmt.setString(k++, user.getPassword());
        pstmt.setLong(k++, roleId);
        pstmt.setString(k++, user.getLocaleName());
        pstmt.setString(k++, user.getEmail());
        pstmt.setString(k++, user.getActivationToken());
        pstmt.executeUpdate();
        pstmt.close();
    }

    private static class UserMapper implements EntityMapper<User> {
        @Override
        public User mapRow(ResultSet rs) {
            RoleDAO roleDao = new RoleDAO();
            try {
                User user = new User();
                user.setId(rs.getLong(Fields.ENTITY__ID));
                user.setLogin(rs.getString(Fields.USER__LOGIN));
                user.setPassword(rs.getString(Fields.USER__PASSWORD));
                user.setLocaleName(rs.getString(Fields.USER__LOCALE_NAME));
                user.setEmail(rs.getString(Fields.USER__EMAIL));
                user.setRole(Collections.singleton(roleDao.getRoleByTitle(rs.getString(Fields.ROLE__TITLE))));
                user.setActivationToken(rs.getString(Fields.USER__ACTIVATION_TOKEN));
                return user;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
