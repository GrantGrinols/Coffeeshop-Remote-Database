import unittest
import mysql.connector
import json
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
import time

class TestCoffeeShop(unittest.TestCase):

    def setUp(self):

        try:
            with open('[path to file]','r') as file:
                config = json.load(file)
        except Exception as e:

            self.fail(f"Error loading config: {e}")


        try:
            connection = mysql.connector.connect(
                host = config["host"],
                user = config["user"],
                password = config["password"],
                database =config["database"]
            )

            self.assertTrue(connection.is_connected())

        except Exception as e:
            self.fail(f"An error has occurred: {e}")
        
        self.connection = connection
        
    def tearDown(self):

        self.connection.close()

    def testSelect(self):

        cursor = self.connection.cursor()
        cursor.execute("SELECT * FROM CustomerTable")
        rows = cursor.fetchall()

        self.assertNotEqual(len(rows),0)

    def testInsert(self):


        cursor = self.connection.cursor()

        insert_query = "INSERT INTO CustomerTable (FirstName, LastName, Birthday, Email, FavoriteDrink) VALUES (%s, %s, %s, %s, %s)"

        data_to_insert = ('John','Doe','1996-04-09','johndoe@gmail.com','Coffee')
        
        cursor.execute(insert_query,data_to_insert)
        self.connection.commit()

        select_query = "SELECT * FROM CustomerTable WHERE FirstName = %s AND LastName = %s"
        data_to_insert = ('John','Doe')
        cursor.execute(select_query, data_to_insert)
        rows = cursor.fetchall()

        self.assertNotEqual(len(rows),0)

    def testWebsite(self):
        service = Service()
        options = webdriver.EdgeOptions()
        driver = webdriver.Edge(service=service,options=options)

        driver.get('http://localhost:8080/')
        time.sleep(0.3)
        self.assertEqual("Yeah Yeah Coffee",driver.title)
        driver.get('http://localhost:8080/birthday')
        time.sleep(0.3)
        self.assertEqual("Yeah Yeah Coffee",driver.title)


if __name__ == '__main__':
    unittest.main()