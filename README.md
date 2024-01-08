# Coffeeshop Remote Database
 A full-stack project that connects to a remote database. Powered by the Springboot framework
 ## About
 This project uses a Mariadb Database that is located on a remote location. The remote location is a Raspberry Pi 4. By having the database in a remote location rather then just having it on a local machine, increases security by storing the database in a more secure location, and it allows multiple verified users to access the same database without having the need to set it up in the cloud.
 ## Setup

 This project does require additional setup. 
 1. Please add a JSON file that has the database's user and password and the IP of the remote system.
 2. Set up the MariaDB on the remote system. Here's one guide on how to do it on Linux: https://www.digitalocean.com/community/tutorials/how-to-install-mariadb-on-ubuntu-22-04
 3. When creating a new user, make sure make the user has permission to access the database from any ip (or just the ip of the client for a more secure client)
CREATE USER '<username>'@'<ip_address>' IDENTIFIED BY '<password>';
GRANT ALL PRIVILEGES ON *.* TO '<username>'@'<ip_address>';
4. The database is called COFFEEDATABASE, and the table we are using is called CustomerTable. Just copy and paste createcoffeetable.txt found in this project and put it in the mariadb command line and the table is created.
5. Some files need to be modifed to allow remote access. In the command line, enter:
sudo nano /etc/mysql/my.cnf

And add the lines
[mysqld]
bind-address = 0.0.0.0

Note: Some people are saying bind-address = :: works with all ips, including IPv6 addresses. This proposition didn't work for me, but may work for other systems.

Then, do the command:

sudo nano /etc/mysql/mariadb.conf.d/50-server.cnf

comment out the bind-address with #
and add the line:

sql-mode="NO_ENGINE_SUBSTITUTION"


You should be good to go!
