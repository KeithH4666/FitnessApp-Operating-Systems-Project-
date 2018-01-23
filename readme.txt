Operating Systems Projects:

I was unable to get the server running to I did it locally.

Design Explenation:

For the registeration I simply write the PPSN and age to a file for log in purposes. This file
is included in the server project and called "PPSN.txt".

When the user logs in , It first checks to see if the PPSN exists then asks the user to enter 
the corresponding age. If the age doesnt match the PPSN the user is refused entry.

When the user adds a fitness action/Meal the PPSN , Fitness info/Meal info are written to files
called "MealRecord.txt"/"records.txt". These are also included server side.

When the user wants to display either records, the PPSN number is used as a reference to check
which files to print in the documents.








