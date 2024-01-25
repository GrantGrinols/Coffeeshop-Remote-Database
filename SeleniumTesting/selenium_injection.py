from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.support.select import Select
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
import time
import csv
import random


#Just a teeny selenium script that injects random names into the database through the website.


service = Service()
options = webdriver.EdgeOptions()
driver = webdriver.Edge(service=service, options=options)

wait = WebDriverWait(driver,3)

nameCount = 100 #How many names we are injecting
currentCount = 0

csv_path = 'namegenderdataset.csv'
names = []
surnames = ['Brown','William','Grinols','Mercer','Smith','June','Darling','Regis','Dancer','Wakeford']

with open(csv_path, 'r') as file:
    reader = csv.DictReader(file)

    for row in reader:
        names.append(row['ï»¿Name'])

names = random.sample(names,nameCount)

driver.get('http://localhost:8080/')
wait.until(EC.presence_of_element_located((By.TAG_NAME,'body')))#finds all the elements by ID
dropbox = Select(driver.find_element(By.ID, 'drink'))
firstName = driver.find_element(By.ID,'firstName')
lastName = driver.find_element(By.ID,'lastName')
email = driver.find_element(By.ID,'email')
datepicker = driver.find_element(By.ID,'birthday')
drinklength = len(dropbox.options)
button = driver.find_element(By.ID,'button')

print("number of drink choices:" + str(drinklength))

while(currentCount < nameCount):
    surname = random.choice(surnames)
    firstName.send_keys(names[currentCount])
    lastName.send_keys(surname)
    email.send_keys(names[currentCount]+surname+'@gmail.com')
    dropbox.select_by_index(random.randint(0,drinklength - 1))

    random_year = str(random.randint(1924,2023))
    random_month = str(random.randint(1,12))
    random_day = str(random.randint(1,29))
    if(len(random_day)==1):
        random_day = "0" + random_day

    if(len(random_month)==1):
        random_month = "0" + random_month
    random_past_date = random_month + "/" + random_day + "/" + random_year
    datepicker.send_keys(random_past_date)
    print("random date:" + random_past_date)
    breakpoint()
    button.click()
    currentCount += 1
    time.sleep(0.5)

print(nameCount + "inserted")
driver.close()


    