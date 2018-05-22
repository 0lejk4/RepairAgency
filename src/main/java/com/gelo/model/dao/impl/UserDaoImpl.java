package com.gelo.model.dao.impl;

import com.gelo.model.dao.UserDao;
import com.gelo.model.dao.generic.GenericDao;
import com.gelo.model.domain.User;
import com.gelo.model.mapper.BooleanMapper;
import com.gelo.model.mapper.UserMapper;
import com.gelo.persistance.ConnectionManager;
import com.gelo.persistance.JdbcTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * The type User dao.
 */
public class UserDaoImpl implements UserDao, GenericDao<User, Long> {
    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper = new UserMapper();
    private final BooleanMapper booleanMapper = new BooleanMapper();

    private static final String USER_SELECT = "SELECT users.id, email, name, password, country, active_orders_count ,summary_orders_count, role_id, type " +
            "FROM users INNER JOIN roles ON users.role_id = roles.id ";
    private static final String USER_CREATE = "INSERT INTO users(name,email,country, password,role_id) VALUES (?,?,?,?,?);";
    private static final String USER_UPDATE = "UPDATE users set summary_orders_count = ?, active_orders_count = ? where id = ?;";
    private static final String USER_FIND_BY_EMAIL = "SELECT users.id, email, name, password, country, active_orders_count ,summary_orders_count,role_id, type " +
            "FROM users INNER JOIN roles ON users.role_id = roles.id WHERE email=? LIMIT 1;";
    private static final String USER_CHECK_EMAIL = "SELECT count(email) = 1 FROM users WHERE email=? LIMIT 1;";
    private static final String FIND_BY_ID = USER_SELECT + " where users.id = ?";
    private static final String FIND_FIVE_BEST = USER_SELECT + " where roles.type = 'ROLE_MASTER' order by users.summary_orders_count DESC limit 5";

    /**
     * Instantiates a new User dao.
     *
     * @param connectionManager the connection manager
     */
    public UserDaoImpl(ConnectionManager connectionManager) {
        jdbcTemplate = new JdbcTemplate(connectionManager);
    }

    @Override
    public Long persist(User user) {
        Long id = jdbcTemplate.insert(USER_CREATE,
                user.getName(),
                user.getEmail(),
                user.getCountry(),
                user.getPassword(),
                user.getRole() == null
                        ? 1
                        : user.getRole().getId());
        user.setId(id);
        return id;
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(USER_UPDATE,
                user.getSummaryOrdersCount(),
                user.getActiveOrdersCount(),
                user.getId());
    }

    @Override
    public void delete(User object) {
        throw new NotImplementedException();
    }

    @Override
    public User findByEmail(String email) {
        return jdbcTemplate.queryObject(USER_FIND_BY_EMAIL, userMapper, email);
    }

    @Override
    public User findByPK(Long id) {
        return jdbcTemplate.queryObject(FIND_BY_ID, userMapper, id);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.queryObjects(USER_SELECT, userMapper);
    }


    @Override
    public List<User> findFiveBestMasters() {
        return jdbcTemplate.queryObjects(FIND_FIVE_BEST, userMapper);
    }

    @Override
    public boolean emailTaken(String email) {
        return jdbcTemplate.queryObject(USER_CHECK_EMAIL, booleanMapper, email);
    }
}
