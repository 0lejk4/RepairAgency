package com.gelo.util.constants;

/**
 * Contains constants that contains SQL queries that are used in DAO
 */
public interface Queries {
    String PERMISSION_FIND_BY_ROLE_ID = "SELECT  permission_id,  permissions.type permission_type FROM role_permission " +
            " INNER JOIN permissions ON permission_id = permissions.id WHERE role_id = ?;";
    String REVIEW_SELECT = "SELECT * from reviews";
    String REVIEW_COUNT = "SELECT count(*) from reviews";
    String REVIEW_CREATE = "INSERT INTO reviews ( master_id, author_id, title, text, rating ) VALUES ( ?, ? ,? ,? , CAST(? AS star) );";
    String REVIEW_UPDATE = "UPDATE reviews SET rating = ?, text = ?, title = ? where id = ? ";
    String CAN_REVIEW = "SELECT reviews_count < orders_count as can_leave_review " +
            "from (select count(*) from reviews where reviews.master_id = ? and reviews.author_id = ?) reviews_count," +
            " (select count(*) from orders where orders.master_id = ? and orders.author_id = ? and orders.done = true) orders_count;";
    String ORDER_SELECT = "SELECT * FROM orders";
    String ORDER_COUNT = "SELECT count(*) FROM orders";
    String ORDER_CREATE = "INSERT into orders(author_id, author_description) VALUES (?,?);";
    String ORDER_UPDATE = "UPDATE public.orders SET author_id = ?, author_description = ?, price = ?," +
            " master_id = ?, done = ?, manager_id = ?, manager_description = ?, accepted = ? WHERE id = ?;";
    String USER_SELECT = "SELECT users.id, email, name, password, country, active_orders_count ,summary_orders_count, role_id, type " +
            "FROM users INNER JOIN roles ON users.role_id = roles.id ";
    String USER_CREATE  = "INSERT INTO users(name,email,country, password,role_id) VALUES (?,?,?,?,?);";
    String USER_UPDATE = "UPDATE users set summary_orders_count = ?, active_orders_count = ? where id = ?;";
    String USER_FIND_BY_EMAIL = "SELECT users.id, email, name, password, country, active_orders_count ,summary_orders_count,role_id, type " +
            "FROM users INNER JOIN roles ON users.role_id = roles.id WHERE email=? LIMIT 1;";
    String USER_CHECK_EMAIL = "SELECT email FROM users WHERE email=? LIMIT 1;";
}
