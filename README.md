# project1
Simple Web store with some functionality based on Spring framework

To run this application, you need following tools:
1) JRE,JDK v.8
2) MySQL v.5.5 or MariaDB v.10
2) Apache Maven 3.3.9
3) Eclipse IDE or IntelliJ IDEA

Pay attention, some changes required, take a look into this file 'src/main/resources/application.properties', change following fields: 'spring.datasource.username', 'spring.datasource.password' and 'spring.jpa.hibernate.dialect' to corresponding values of your DB.

After running the app will automatically create 'laptopDB2' database, and folder 'images'  in your home directory.

There is a security level, you can register new user or use admin account with username and password 'r'
