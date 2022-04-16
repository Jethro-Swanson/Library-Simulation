Library Simulation

Description: 
	Simpulates the basic functionality of a libraries book loaning system. When the program is run it will prompt the user for
	a file of instructions. A sample file called SampleInput.txt has been provided to demonstrate the possible instructions and
	there formating. The following are valid instructions

	ADD - Adds a new book with its associated author to the libraries catalog
	SEARCHA - Searches the libraries database for a author
	SEARCHT - Searches the libraries database for a book title
	GETBOOK - Attempts to loan out a book, if it is available
	RETURNBOOK - returns a previously loaned out book

	Usage:

	ADD LastName, FirstName(this space may be left empty), BookTitle
	SEARCHA LastName
	SEARCHT BookTitle
	GETBOOK LastName, FirstName(this space may be left empty), BookTitle
	RETURNBOOK LastName, FirstName(this space may be left empty), BookTitle

	All commands must be on a seperate line in the instruction file.

How to use:
	1. Compile the program
	 - javac LibrarySim.java
	2. Run the program
 	 - java LibrarySim.java
	3. When promted input the name of the input file you wish to use
	   (this file must be a text file in the same directory as the program)
	 - SampleInput.txt
	4. The program will then execute all comands in the file printing out
	   some information such as the results of searches for books/authors
	   and when books are loaned/returned.

Author - Jethro Swanson