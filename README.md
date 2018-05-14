# Repair Agency pet-project
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

>### _Architecture features_
* Own Tags, Annotations and Exceptions for validation(@Nullable, @Valid) and security(@PreAuthorize)
* Request processing using Strategy pattern with
 use of Router that decides which Handler should be 
 executed and also secures access to Handlers
* Handlers are mapped to router using router.properties file
* Automatic form validation with alerts creation that are shown using toast.tag
* Generic class for DAO that simplifies simple crud operations


>### Installation guide:
First clone repository:
`git clone https://github.com/0lejk4/RepairAgency.git`
___
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

After this run `mvn package` in root folder and run packaged `.war` file using `Tomcat` (<small>9.0.6 is preferable</small>)

>## Testing out:

Every existing user has password `1234qwer`.
This is list of available users:

> Admin:
* odubinskiy@ukr.net
> Users:
* business_kateruna1992@stukr.net
* business_semen2001@stukr.net
* pavlo_volod89@stukr.net
> Manager:
* sergiy_such@ukr.net
> Masters:
* denis_parkhomenko@ukr.net
* vitaliy_pavlenko@ukr.net
* anton_muronuk@ukr.net
* ilon_musk@ukr.net
* mister_cat@ukr.net
