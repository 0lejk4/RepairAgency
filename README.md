# Repair Agency pet project
>### _Project description_
Fully functional repair agency. 

There is 4 roles with it`s own permissions:
* User
* Manager
* Admin
* Master

    User can create orders.
 Manager can accept or decline order giving the price and description. 
 Master can enroll on order and then eventually finish it.
 Then User can leave review for master that done his order. 
 Also there is Admin that can register user with roles different 
 than user and he can see all orders and users information.
___
>### _Technology stack_
* JDBC
* PostgreSQL
* JUnit
* Mockito
* Log4j
* Tomcat
* Servlets, JSP, JSTL
* Maven
* Jasypt for password encryption
* Apache commons
___
>### _Architecture features_
* Own Jsp Tags for validation, security and pagination
* Annotations for validation(@Nullable, @Valid), security(@PreAuthorize) and RequestMethod limiting(@GetMapping, @PostMapping)
* Request processing using Strategy pattern with
 use of Router that decides which Handler should be 
 executed and also secures access to Handlers by Role, Permission and RequestMethod
* Handlers are mapped to router using router.properties file
* Automatic form validation with alert creation for each error in form
* JdbcTemplate that simplify operations in DAO.
* Transaction management using DataSourceWrapper and ConnectionWrapper

___
>### Installation guide:
Before installation you need to have:
* Tomcat 9.0.6
* PostgreSQL
* Maven

First clone repository:
`git clone https://github.com/0lejk4/RepairAgency.git`

Then create new user in PostgreSQL:
`CREATE ROLE db_api LOGIN
   ENCRYPTED PASSWORD 'md5bed26abc8a4c61df77b5045451bc3a9d'
   SUPERUSER INHERIT CREATEDB NOCREATEROLE NOREPLICATION;`
   
Password for this user is `MTIzNHF3ZXI=`.
After this create 2 databases:

1. For deploy:
`CREATE DATABASE "RepairAgencyDB"
     WITH OWNER = db_api
          ENCODING = 'UTF8'
          TABLESPACE = pg_default
          LC_COLLATE = 'en_US.UTF-8'
          LC_CTYPE = 'en_US.UTF-8'
          CONNECTION LIMIT = -1;`
          
2. For Testing:
`CREATE DATABASE "TestRepairAgencyDB"
   WITH OWNER = db_api
        ENCODING = 'UTF8'
        TABLESPACE = pg_default
        LC_COLLATE = 'en_US.UTF-8'
        LC_CTYPE = 'en_US.UTF-8'
        CONNECTION LIMIT = -1;`
Next step is to run `SetupDB.sql` on created databases.

After this run `mvn package` in root folder and run packaged `.war` file using `Tomcat`
___
>## Testing out:

Every existing user has password `1234qwer`.
This is list of available users:

> Admin:
* odubinskiy<span>@ukr.net</span>
> Users:
* business_kateruna1992<span>@stukr.net</span>
* business_semen2001<span>@stukr.net</span>
* pavlo_volod89<span>@stukr.net</span>
> Manager:
* sergiy_such<span>@ukr.net</span>
> Masters:
* denis_parkhomenko<span>@ukr.net</span>
* vitaliy_pavlenko<span>@ukr.net</span>
* anton_muronuk<span>@ukr.net</span>
* ilon_musk<span>@ukr.net</span>
* mister_cat<span>@ukr.net</span>
