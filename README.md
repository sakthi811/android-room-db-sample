
# android-room-db-sample
This repo contains the sample code for implementing CRUD model with room database

# Steps to Implement Room DB
Step1 : Implement an entity(data class) which will represent a table in the database

Step2 : Implement a Dao(Interface) which acts as a interaction tool with the database

Step3 : Create a Database class(abstract) which inherits from RoomDatabase 

Step4 : Create an Event class handles events related to Database manipulations

Step5 : Create an Enum to keep track of user's choice of arranging the Contacts in specific order

Step6 : Create a State class to maintain the state of various changes happening as a result of user interaction

Step7 : Create a ViewModel class which will be used to maintain the state of UI and perform database manipulations
