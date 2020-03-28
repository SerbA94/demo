/**
 *
 */
package com.demo.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.demo.db.DBManager;
import com.demo.db.constants.Fields;
import com.demo.db.entity.Role;
import com.demo.db.entity.User;

/**
 * Data access object for User entity.
 *
 * @author A.Serbin
 */
public class UserDAO implements EntityMapper<User> {

	private static final String SQL__FIND_USER_BY_LOGIN =
			"SELECT * FROM users "
					+ "JOIN roles ON users.role_id = roles.role_id "
					+ "WHERE users.login=?";

	private static final String SQL__FIND_USER_BY_EMAIL =
			"SELECT * FROM users "
					+ "JOIN roles ON users.role_id = roles.role_id "
					+ "WHERE users.email=?";

	private static final String SQL__FIND_USER_BY_ID =
			"SELECT * FROM users "
					+ "JOIN roles ON users.role_id = roles.role_id "
					+ "WHERE users.user_id=?";

	private static final String SQL_UPDATE_USER =
			"UPDATE users "
					+ "SET users.login=?, "
						+ "users.password=?, "
						+ "users.role_id=(SELECT roles.role_id FROM roles WHERE roles.role_title=?), "
						+ "users.locale_name=?, "
						+ "users.email=?, "
						+ "users.activation_token=? "
					+ "WHERE users.user_id=?";

	private static final String SQL_CREATE_USER =
			"INSERT INTO users "
					+ "(users.login, users.password, users.role_id, "
						+ "users.locale_name, users.email, users.activation_token) "
					+ "VALUES "
					+ "(?, ?, (SELECT roles.role_id FROM roles WHERE roles.role_title=?), ?, ?, ?)";

    /**
     * Returns user with given id
     *
     * @param id
     *     	User identifier.
     * @return User entity.
     */
	public User findUserById(Long id) {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_USER_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = mapRow(rs);
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

	/**
     * Returns user with given login
     *
     * @param login
     *     	User login.
     * @return User entity.
     */
	public User findUserByLogin(String login) {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_USER_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = mapRow(rs);
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

	/**
     * Returns user with given email
     *
     * @param email
     *     	User email.
     * @return User entity.
     *
	 * @throws SQLException
     */
	public User findUserByEmail(String email){
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL__FIND_USER_BY_EMAIL);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = mapRow(rs);
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

	/**
     * Update user.
     *
     * @param user
     *            User to update.
     */
	public User updateUser(User user) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateUser(con, user);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
			return null;
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return user;
	}

	/**
     * Update user.
     *
     * @param user
     *            User to update.
     * @param con
     *            Connection to db.
     * @throws SQLException
     */
	private void updateUser(Connection con, User user) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_USER);
		Role role = (Role) user.getRole().toArray()[0];
		int k = 1;
		pstmt.setString(k++, user.getLogin());
		pstmt.setString(k++, user.getPassword());
		pstmt.setString(k++, role.getTitle());
		pstmt.setString(k++, user.getLocaleName());
		pstmt.setString(k++, user.getEmail());
		pstmt.setString(k++, user.getActivationToken());
		pstmt.setLong(k, user.getId());
		pstmt.executeUpdate();
		pstmt.close();
	}

	/**
     * Create & return created user.
     *
     * @param user
     *            User to create.
     */
	public User createUser(User user) {
		Connection con = null;
		User createdUser = null;
		try {
			con = DBManager.getInstance().getConnection();
			createdUser = createUser(con, user);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return createdUser;
	}

	/**
     * Create & return created user.
     *
     * @param user
     *            User to create.
     * @param con
     *            Connection to db.
     * @throws SQLException
     */
	private User createUser(Connection con, User user) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(SQL_CREATE_USER,Statement.RETURN_GENERATED_KEYS);
		Role role = (Role) user.getRole().toArray()[0];
		int k = 1;
		pstmt.setString(k++, user.getLogin());
		pstmt.setString(k++, user.getPassword());
		pstmt.setString(k++, role.getTitle());
		pstmt.setString(k++, user.getLocaleName());
		pstmt.setString(k++, user.getEmail());
		pstmt.setString(k++, user.getActivationToken());
		pstmt.executeUpdate();

		Long id = null;
        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating room failed, no ID obtained.");
            }
        }finally{
        	pstmt.close();
        }
        user.setId(id);
        return user;
	}

	/**
     * Extracts user from the result set row.
     *
     * @param rs
     *        Result set row with data to extract.
     */
	@Override
	public User mapRow(ResultSet rs) {
		try {
			User user = new User();
			user.setId(rs.getLong(Fields.USER__USER_ID));
			user.setLogin(rs.getString(Fields.USER__LOGIN));
			user.setPassword(rs.getString(Fields.USER__PASSWORD));
			user.setLocaleName(rs.getString(Fields.USER__LOCALE_NAME));
			user.setEmail(rs.getString(Fields.USER__EMAIL));
			user.setRole(RoleDAO.getRoleSetByTitle(rs.getString(Fields.ROLE__TITLE)));
			user.setActivationToken(rs.getString(Fields.USER__ACTIVATION_TOKEN));
			return user;
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

}
