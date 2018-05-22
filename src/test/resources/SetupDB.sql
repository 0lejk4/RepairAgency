-- login with root to create user, DB and table and provide grants
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS role_permission;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS permissions;
DROP TYPE IF EXISTS PERMISSION_TYPE;
DROP TYPE IF EXISTS ROLE_TYPE;
DROP TYPE IF EXISTS STAR;

CREATE TYPE PERMISSION_TYPE AS ENUM (
  'LEAVE_REVIEW', 'CREATE_ORDER',
  'ANSWER_ORDER', 'CHECK_ORDER_HISTORY',
  'CHECK_MANAGE_HISTORY','CHECK_WORK_HISTORY',
  'FINISH_ORDER','ENROLL_ORDER', 'REGISTER_USER',
'CHECK_ALL_ORDERS', 'CHECK_ALL_USERS');

CREATE TYPE STAR AS ENUM ('ONE', 'TWO', 'THREE', 'FOUR', 'FIVE');


CREATE TABLE permissions
(
  id   SERIAL
    PRIMARY KEY,
  type PERMISSION_TYPE NOT NULL
);

CREATE TYPE ROLE_TYPE AS ENUM ('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_MASTER', 'ROLE_USER');

CREATE TABLE roles
(
  id   SERIAL
    PRIMARY KEY,
  type ROLE_TYPE NOT NULL
);

CREATE TABLE role_permission
(
  id            SERIAL
    PRIMARY KEY,
  permission_id BIGINT NOT NULL
    CONSTRAINT role_permission_permission_id_fk
    REFERENCES permissions,
  role_id       BIGINT NOT NULL
    CONSTRAINT role_permission_role_id_fk
    REFERENCES roles
);

CREATE TABLE users
(
  id                   SERIAL
    PRIMARY KEY,
  email                VARCHAR(255) UNIQUE
                                    NOT NULL                 DEFAULT '',
  active_orders_count  INT                                   DEFAULT 0,
  summary_orders_count INT                                   DEFAULT 0,
  name                 VARCHAR(255) NOT NULL                 DEFAULT '',
  password             VARCHAR(255) NOT NULL                 DEFAULT '',
  country              VARCHAR(20)                           DEFAULT 'Ukraine',
  role_id              BIGINT       NOT NULL
    CONSTRAINT user_role_id_fk
    REFERENCES roles
);


CREATE TABLE orders
(
  id                  SERIAL
    PRIMARY KEY,
  author_id           BIGINT
    CONSTRAINT order_user_id_fk
    REFERENCES users,
  author_description  VARCHAR(255) NOT NULL,
  price               INT,
  master_id           BIGINT
    CONSTRAINT order_master_id_fk
    REFERENCES users,
  done                BOOLEAN,
  manager_id          BIGINT
    CONSTRAINT order_manager_id_fk
    REFERENCES users,
  manager_description VARCHAR(255),
  accepted            BOOLEAN
);

CREATE TABLE reviews
(
  id        SERIAL
    PRIMARY KEY,
  master_id BIGINT       NOT NULL
    CONSTRAINT review_master_id_fk
    REFERENCES users,
  author_id BIGINT       NOT NULL
    CONSTRAINT review_author_id_fk
    REFERENCES users,
  title     VARCHAR(55)  NOT NULL,
  text      VARCHAR(255) NOT NULL,
  rating    STAR         NOT NULL
);

INSERT INTO public.permissions (type) VALUES ('LEAVE_REVIEW');
INSERT INTO public.permissions (type) VALUES ('CREATE_ORDER');
INSERT INTO public.permissions (type) VALUES ('ANSWER_ORDER');
INSERT INTO public.permissions (type) VALUES ('CHECK_ORDER_HISTORY');
INSERT INTO public.permissions (type) VALUES ('CHECK_MANAGE_HISTORY');
INSERT INTO public.permissions (type) VALUES ('CHECK_WORK_HISTORY');
INSERT INTO public.permissions (type) VALUES ('FINISH_ORDER');
INSERT INTO public.permissions (type) VALUES ('ENROLL_ORDER');
INSERT INTO public.permissions (type) VALUES ('REGISTER_USER');
INSERT INTO public.permissions (type) VALUES ('CHECK_ALL_USERS');
INSERT INTO public.permissions (type) VALUES ('CHECK_ALL_ORDERS');

-- Roles
INSERT INTO public.roles (type) VALUES ('ROLE_USER');
INSERT INTO public.roles (type) VALUES ('ROLE_MANAGER');
INSERT INTO public.roles (type) VALUES ('ROLE_MASTER');
INSERT INTO public.roles (type) VALUES ('ROLE_ADMIN');

-- Users: 1, 2, 3
INSERT INTO public.users ( email, active_orders_count, summary_orders_count, name, password, country, role_id) VALUES ('business_kateruna1992@stukr.net', 1, 3, 'Катерина Віталіївна', 'pDu2BrIr6Tt8kkHDGMbjjbRSNV/aRRFv', 'Україна', 1);
INSERT INTO public.users ( email, active_orders_count, summary_orders_count, name, password, country, role_id) VALUES ('business_semen2001@stukr.net', 1, 2, 'Семен Вікторович', 'pDu2BrIr6Tt8kkHDGMbjjbRSNV/aRRFv', 'Україна', 1);
INSERT INTO public.users ( email, active_orders_count, summary_orders_count, name, password, country, role_id) VALUES ('pavlo_volod89@stukr.net', 0, 3, 'Петро Володимирович', 'pDu2BrIr6Tt8kkHDGMbjjbRSNV/aRRFv', 'Україна', 1);
-- Manager 4
INSERT INTO public.users ( email, active_orders_count, summary_orders_count, name, password, country, role_id) VALUES ('sergiy_such@ukr.net', 1, 8, 'Сергій Сич', '1gsxWhmgP0zlRHo0Pq2aJTVpzNnvGT3N', 'Україна', 2);
-- Masters 5, 6, 7, 8, 9
INSERT INTO public.users ( email, active_orders_count, summary_orders_count, name, password, country, role_id) VALUES ('denis_parkhomenko@ukr.net', 0, 2, 'Денис Пархоменко', '1gsxWhmgP0zlRHo0Pq2aJTVpzNnvGT3N', 'Україна', 3);
INSERT INTO public.users ( email, active_orders_count, summary_orders_count, name, password, country, role_id) VALUES ('vitaliy_pavlenko@ukr.net', 0, 2, 'Віталій Павленко', '1gsxWhmgP0zlRHo0Pq2aJTVpzNnvGT3N', 'Україна', 3);
INSERT INTO public.users ( email, active_orders_count, summary_orders_count, name, password, country, role_id) VALUES ('anton_muronuk@ukr.net', 0, 2, 'Антон Миронюк', '1gsxWhmgP0zlRHo0Pq2aJTVpzNnvGT3N', 'Україна', 3);
INSERT INTO public.users ( email, active_orders_count, summary_orders_count, name, password, country, role_id) VALUES ('ilon_musk@ukr.net', 0, 1, 'Ilon Musk', '1gsxWhmgP0zlRHo0Pq2aJTVpzNnvGT3N', 'America', 3);
INSERT INTO public.users ( email, active_orders_count, summary_orders_count, name, password, country, role_id) VALUES ('mister_cat@ukr.net', 0, 0, 'Містер Кет', '1gsxWhmgP0zlRHo0Pq2aJTVpzNnvGT3N', 'Україна', 3);
-- Admin 10
INSERT INTO public.users ( email, active_orders_count, summary_orders_count, name, password, country, role_id) VALUES ( 'odubinskiy@ukr.net', 0, 0, 'Олег Дубинський', 'Oi6AcLMsCQ62cabv4mPKlp1geX6RBawZ', 'Україна', 4);



-- Roles - Permissions:
-- User Permissions
INSERT INTO public.role_permission (permission_id, role_id) VALUES (1, 1);
INSERT INTO public.role_permission (permission_id, role_id) VALUES (2, 1);
INSERT INTO public.role_permission (permission_id, role_id) VALUES (4, 1);
-- Admin Permissions
INSERT INTO public.role_permission (permission_id, role_id) VALUES (9, 4);
INSERT INTO public.role_permission (permission_id, role_id) VALUES (10, 4);
INSERT INTO public.role_permission (permission_id, role_id) VALUES (11, 4);

-- Master Permissions
INSERT INTO public.role_permission (permission_id, role_id) VALUES (6, 3);
INSERT INTO public.role_permission (permission_id, role_id) VALUES (7, 3);
INSERT INTO public.role_permission (permission_id, role_id) VALUES (8, 3);
-- Manager Permissions
INSERT INTO public.role_permission (permission_id, role_id) VALUES (3, 2);
INSERT INTO public.role_permission (permission_id, role_id) VALUES (5, 2);

INSERT INTO public.orders ( author_id, author_description, price, master_id, done, manager_id, manager_description, accepted) VALUES (1, 'Вітаю, здається зламався акамулятор.', 547, 6, true, 4, 'Справді зламався акамулятор. Ми досить швидко відремонтуємо його.', true);
INSERT INTO public.orders ( author_id, author_description, price, master_id, done, manager_id, manager_description, accepted) VALUES (2, 'Гідравліка трохи тяготіє на ліве заднє колесо. Досить часто заносить через це. Ви зможете це відремонтувати?', 5047, 8, true, 4, 'Це досить важко буде полагодити, десь через 2 тижні все буде готово. Саме таку гідравліку ми не зможемо поставити, можемо поставити гідравліку старшої моделі в лінійці', true);
INSERT INTO public.orders ( author_id, author_description, price, master_id, done, manager_id, manager_description, accepted) VALUES (3, 'Дякую, за попередні замовлення. На цей раз хочу встановити нові покришки з середньої цінової категорії', 747, null, false, 4, 'Ваші покришки в досить непоганому стані, тому ми зробимо вам знижку 15%', true);
INSERT INTO public.orders ( author_id, author_description, price, master_id, done, manager_id, manager_description, accepted) VALUES (1, 'Зламався двигун Audi A8 D2 4.2 ABZ. Чи можливо його відремонтувати?', 7147, 6, true, 4, 'Зазвичай двигуни з таким великим пробігом не зустрінеш, ми відремонтуємо його, але попереджаємо, що більше ніж три місяці він не буде працювати та знову зламається.', true);
INSERT INTO public.orders ( author_id, author_description, price, master_id, done, manager_id, manager_description, accepted) VALUES (2, 'Зламався диск на одному з коліс. Поміняйте тільки цей диск на такий , як на інших, якщо це неможливо, то поставте найдешевші диски на машину.', 347, 7, true, 4, 'Не хвилюйтеся, у нас є в наявності диски, як на вашому авто.', true);
INSERT INTO public.orders ( author_id, author_description, price, master_id, done, manager_id, manager_description, accepted) VALUES (2, 'Коробка передач барахлить, коли вмикаю задню. Досить часто ледве не потрапляв в аварію через такі випадки.', null, null, null, null, null, null);
INSERT INTO public.orders ( author_id, author_description, price, master_id, done, manager_id, manager_description, accepted) VALUES (3, 'Перестав працювати кондиціонер. Ось уже два тижні не працює. Причину не знаю.', 347, 5, true, 4, 'Причиною був мертвий пацюк, що застряг в кондиціонері. Довелось змінити кондиціонер.', true);
INSERT INTO public.orders ( author_id, author_description, price, master_id, done, manager_id, manager_description, accepted) VALUES (1, 'Проблеми з обшивкою. Можливо замінити її частково чи повністю або реставрувати стару?', null, null, null, null, null, null);
INSERT INTO public.orders ( author_id, author_description, price, master_id, done, manager_id, manager_description, accepted) VALUES (1, 'Проблеми з очисниками вікон. В зимовий період зовсім не можуть очистити обмерзлі вікна. Чи є можливі нові моделі, які могли б працювати справно за будь-яких умов?', 2347, 7, true, 4, 'Так, нам нещодавно привезли очисників вікон, що здатні розтоплювати замерзлий лід на склі.', true);
INSERT INTO public.orders ( author_id, author_description, price, master_id, done, manager_id, manager_description, accepted) VALUES ( 3, 'Салон так і не перестав смердіти. Прошу поміняйте весь салон і зробіть хім. чистку.', 2147, 5, true, 4, 'Добре, у нас наразі є досить ходова обшивка для машин вашої моделі. Разом з хім.чисткою все буде готово за тиждень.', true);

INSERT INTO public.reviews (master_id, author_id, title, text, rating) VALUES ( 6, 1, 'Чудова робота. Просто золоті руки.', 'Пишу відгук через 3 місяці з моменти ремонту, хоча менеджер попереджував, що більше ніж 3 місяці двигун не протримається він досі працює справно.', 'FOUR');
INSERT INTO public.reviews (master_id, author_id, title, text, rating) VALUES ( 6, 1, 'Швидко працює', 'Вдруге цей майстер дуже гарно впорався з роботою, при цьому робота була виконана дуже швидко.', 'FIVE');
INSERT INTO public.reviews (master_id, author_id, title, text, rating) VALUES ( 7, 1, 'Завжди в тренді', 'Цей майстер встановив відносно нову технологію для України та зробив це з такою легкістю, наче просто протер лобове скло.', 'FIVE');
INSERT INTO public.reviews (master_id, author_id, title, text, rating) VALUES ( 8, 2, 'Чудово ремонтує, проте не знає українську мову.', 'Я залишився задовлений встановленою гідравлікою та ціною, яку за неї заплатив, проте відсутність знання української мови досить заважала спілкуванню з майстром.', 'THREE');
INSERT INTO public.reviews (master_id, author_id, title, text, rating) VALUES ( 7, 2, 'Швидко, якісно та дешево', 'Проста зміна шин пройшла дуже швидко. Ще й не довелося міняти всі диски, а лише пошкоджений', 'FIVE');
INSERT INTO public.reviews (master_id, author_id, title, text, rating) VALUES ( 5, 3, 'Гарний майстер, що швидко працює.', 'Дуже швидко знайшов причину поламки кондиціонера та неприємного запаху в салоні, проте запах все ж не зник. Звернусь знову і залишу гарний відгук, якщо запах зникне.', 'THREE');
INSERT INTO public.reviews (master_id, author_id, title, text, rating) VALUES ( 5, 3, 'Старанний та терпеливий', 'Минулого разу поставив лише 3 зірочки, але цей раз ставлю заслужений максимум. Щиро дякую, салон тепер пахне хіба лише ялинками з альп)', 'FIVE');