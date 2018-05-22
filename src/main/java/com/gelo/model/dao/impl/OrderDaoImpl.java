package com.gelo.model.dao.impl;

import com.gelo.model.dao.OrderDao;
import com.gelo.model.dao.generic.GenericDao;
import com.gelo.model.domain.Order;
import com.gelo.model.domain.User;
import com.gelo.model.mapper.LongMapper;
import com.gelo.model.mapper.OrderMapper;
import com.gelo.persistance.ConnectionManager;
import com.gelo.persistance.JdbcTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Optional;


/**
 * The type Order dao.
 */
public class OrderDaoImpl implements OrderDao, GenericDao<Order, Long> {
    private final JdbcTemplate jdbcTemplate;
    private final OrderMapper orderMapper = new OrderMapper();
    private final LongMapper longMapper = new LongMapper();

    private static final String FIND_ALL_BY_AUTHOR = "SELECT * FROM orders where orders.author_id = ?";
    private static final String COUNT_ALL_BY_AUTHOR = "SELECT count(*) FROM orders where orders.author_id = ?;";
    private static final String FIND_ALL_BY_MASTER = "SELECT * FROM orders where orders.master_id = ? ";
    private static final String COUNT_ALL_BY_MASTER = "SELECT count(*) FROM orders where orders.master_id = ?;";
    private static final String FIND_ALL_BY_MANAGER = "SELECT * FROM orders where orders.manager_id = ? ";
    private static final String COUNT_ALL_BY_MANAGER = "SELECT count(*) FROM orders where orders.manager_id = ?;";
    private static final String FIND_ALL_AWAITING_ANSWER = "SELECT * FROM orders where orders.manager_id IS NULL ";
    private static final String COUNT_ALL_AWAITING_ANSWER = "SELECT count(*) FROM orders where orders.manager_id IS NULL;";
    private static final String FIND_ALL_AWAITING_MASTER = "SELECT * FROM orders where orders.master_id IS NULL AND orders.manager_id IS NOT NULL ";
    private static final String COUNT_ALL_AWAITING_MASTER = "SELECT count(*) FROM orders where orders.master_id IS NULL AND orders.manager_id IS NOT NULL;";
    private static final String FIND_BY_ID = "SELECT * FROM orders where orders.id = ?";
    private static final String ORDER_SELECT = "SELECT * FROM orders";
    private static final String ORDER_CREATE = "INSERT into orders(author_id, author_description) VALUES (?,?);";
    private static final String ORDER_UPDATE = "UPDATE public.orders SET author_id = ?, author_description = ?, price = ?, master_id = ?, done = ?, manager_id = ?, manager_description = ?, accepted = ? WHERE id = ?;";

    /**
     * Instantiates a new Order dao.
     *
     * @param connectionManager the connection manager
     */
    public OrderDaoImpl(ConnectionManager connectionManager) {
        jdbcTemplate = new JdbcTemplate(connectionManager);
    }


    @Override
    public List<Order> findAllByAuthor(Long authorId, String orderField, boolean ascending, int limit, int offset) {
        return jdbcTemplate.queryObjectsPaginated(FIND_ALL_BY_AUTHOR, orderMapper, orderField, ascending, limit, offset, authorId);
    }

    @Override
    public Long countByAuthor(Long authorId) {
        return jdbcTemplate.queryObject(COUNT_ALL_BY_AUTHOR, longMapper, authorId);
    }

    @Override
    public List<Order> findAllByMaster(Long masterId, String orderField, boolean ascending, int limit, int offset) {
        return jdbcTemplate.queryObjectsPaginated(FIND_ALL_BY_MASTER, orderMapper, orderField, ascending, limit, offset, masterId);
    }

    @Override
    public Long countByMaster(Long masterId) {
        return jdbcTemplate.queryObject(COUNT_ALL_BY_MASTER, longMapper, masterId);
    }

    @Override
    public List<Order> findAllByManager(Long managerId, String orderField, boolean ascending, int limit, int offset) {
        return jdbcTemplate.queryObjectsPaginated(FIND_ALL_BY_MANAGER, orderMapper, orderField, ascending, limit, offset, managerId);
    }

    @Override
    public Long countByManager(Long managerId) {
        return jdbcTemplate.queryObject(COUNT_ALL_BY_MANAGER, longMapper, managerId);

    }

    @Override
    public List<Order> findAllAwaitingAnswer(String orderField, boolean ascending, int limit, int offset) {
        return jdbcTemplate.queryObjectsPaginated(FIND_ALL_AWAITING_ANSWER, orderMapper, orderField, ascending, limit, offset);
    }

    @Override
    public Long countAwaitingAnswer() {
        return jdbcTemplate.queryObject(COUNT_ALL_AWAITING_ANSWER, longMapper);
    }

    @Override
    public List<Order> findAllAwaitingMaster(String orderField, boolean ascending, int limit, int offset) {
        return jdbcTemplate.queryObjectsPaginated(FIND_ALL_AWAITING_MASTER, orderMapper, orderField, ascending, limit, offset);
    }

    @Override
    public Long countAwaitingMaster() {
        return jdbcTemplate.queryObject(COUNT_ALL_AWAITING_MASTER, longMapper);
    }

    @Override
    public Long persist(Order order) {
        Long id = jdbcTemplate.insert(ORDER_CREATE, order.getAuthor().getId(), order.getAuthorDescription());
        order.setId(id);
        return id;
    }

    @Override
    public Order findByPK(Long key) {
        return jdbcTemplate.queryObject(FIND_BY_ID, orderMapper, key);
    }

    @Override
    public void update(Order order) {
        Long authorId = Optional.ofNullable(order.getAuthor()).map(User::getId).orElse(null);
        Long masterId = Optional.ofNullable(order.getMaster()).map(User::getId).orElse(null);
        Long managerId = Optional.ofNullable(order.getManager()).map(User::getId).orElse(null);

        jdbcTemplate.update(ORDER_UPDATE,
                authorId,
                order.getAuthorDescription(),
                order.getPrice(),
                masterId,
                order.isDone(),
                managerId,
                order.getManagerDescription(),
                order.isAccepted(),
                order.getId());
    }

    @Override
    public void delete(Order object) {
        throw new NotImplementedException();
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.queryObjects(ORDER_SELECT, orderMapper);
    }
}
