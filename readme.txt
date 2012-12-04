'Excel 2000'
Authors: Luke Jolly, Heath Schaner, Bjorn Stange, Austin Almond, Ruben Sotolongo
This program is designed to allow users to work with an academic database, containing tables for students, courses, and grades of students in courses.

Compile and run the main class, CommandLineInterface.java, to interact with the program.

To import tables from CSV files, select "Initialize Database" -> "Load table from file" -> "Import CSV file", then choose whether the file contains a table for students, courses, or grades.
Type the name of the file containing the table (including the ".csv" extension is optional, and will be added if omitted).

To save the tables once you are finished using the program, do the following for each table you want to save:
Select "Initialize Database" -> "Save table to CSV file", then choose which table to save. Type the name of the file in which you want the table to be saved. Again, the ".csv" extension is optional, and will be added if omitted. Note that saving will overwrite any file sharing the same name.

For testing purposes, you are able to directly interact with tables using standard SQL syntax - SELECT, INSERT, UPDATE, and DELETE.
To access this feature, select "Initialize Database" -> "Direct Data Entry". To exit this part of the program, simply press enter after an empty line.