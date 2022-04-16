/**
 * LibrarySim
 *
 * @author   Jethro Swanson
 * @version  May 24 / 2021
 * 
 * PURPOSE: Creates a representation of a library where an input file can be read and commands such as adding books,
 * searching for books, and loaning out books can be done from commands in the input file
 */


import java.util.Scanner;
import java.io.*;

public class LibrarySim{
  
  //class constants representing to possible actions a user can request in the input text file
  private static final String ADD = "ADD";
  private static final String SEARCHA = "SEARCHA";
  private static final String SEARCHT = "SEARCHT";
  private static final String GETBOOK = "GETBOOK";
  private static final String RETURNBOOK = "RETURNBOOK";
  
  //holds the max number of identifying paramaters a book has such as author first and last name as well as title
  private static final int PARAMETER_COUNT_FULL = 3;
  //holds the number of parameters used for SEARCHA AND SEARCHT
  private static final int PARAMETER_COUNT_ONE = 1;
  
  //holds the string for use when a book is added to the library with an empty parameter
  private static final String UNKNOWN = "unknown";
  
  public static void main(String[] args){      
    
    Library lib2 = new Library();//creates the library to house the user input books
    
    //gets input text file from the user
    System.out.println("Please enter the input file name (.txt file only):");
    Scanner inputFile = new Scanner(System.in);
    String fileName = inputFile.nextLine();
    
    proccessFile(fileName, lib2);//executes all commands in the input file 
    
    System.out.println("Program terminated normally");
  }
  
  //Reads a file from the user and executs all commands inside said file 
  //accepts a String reperesenting the name of the file containing the commands and a Library object to execute 
  //the commands on
  public static void proccessFile(String inputFile, Library lib){
    System.out.println("\n Processing file " + inputFile + "...");//informs user the preccesing has started
    
    try{
      BufferedReader file = new BufferedReader(new FileReader(inputFile));
      String nextLine = file.readLine();
      
      //runs through each line executing the requested operation
      while(nextLine != null){
        Scanner lineReader = new Scanner(nextLine);
        String requestedAction;
        

        
        if(lineReader.hasNext()){
          requestedAction = lineReader.next();
          
          if(requestedAction.compareTo(ADD) == 0){
            fileAdd(nextLine, lib, requestedAction);//helper function to run the ADD command
          }
          
          else if(requestedAction.compareTo(SEARCHA) == 0){
            System.out.println("");//ensurs there are appropriate spaces between each call
            fileSearchAorT(nextLine, lib, requestedAction);//helper function to run the SEARCHA command
          }
          
          else if(requestedAction.compareTo(SEARCHT) == 0){
            System.out.println("");//ensurs there are appropriate spaces between each call
            fileSearchAorT(nextLine, lib, requestedAction);//helper function to run the SEARCHT command
          }
          
          else if(requestedAction.compareTo(GETBOOK) == 0){
            System.out.println("");//ensurs there are appropriate spaces between each call
            fileGetOrReturnBook(nextLine, lib, requestedAction);//helper function to run the GETBOOK command
          }
          
          else if(requestedAction.compareTo(RETURNBOOK) == 0){
            System.out.println("");//ensurs there are appropriate spaces between each call
            fileGetOrReturnBook(nextLine, lib, requestedAction);//helper function to run the RETURNBOOK command
          }
          
          else{
            System.out.println(requestedAction + " is not a recognized command");
          }
        }
        
        nextLine = file.readLine();
      }
      
      file.close();
    }
    //catch(xception e){
      //System.out.println("Failed to proccess file: " + e.getMessage());
   // }
    catch(IOException ioe){
      System.out.println("Failed to proccess file: " + ioe.getMessage());
    }
   // catch(){
      
   // }

  }
  
  //helper function that adds a book the the requested library 
  //accepts a String of the line from the file requesting the action and containing all relevent parameters and a
  //Library object that the book will be added to. Also accepts a String of the requested action wich should be "ADD"
  private static void fileAdd(String nextLine, Library lib, String requestedAction){
    String[] parameters; //holds the parametsrs for the add action
          
    //creates a string with the requested Action removed 
    String[] userInputLine = nextLine.split(requestedAction);
    parameters = userInputLine[1].split(",");//takes the string that isnt the requested action as the parameters
          
    //proceedes only if correct number of parameters were given, removes extra white space and then proceeds 
    //to replace any emptry spots with "unknown"
    if(parameters.length == PARAMETER_COUNT_FULL){
      for(int i=0; i<parameters.length; i++){
        parameters[i] = parameters[i].trim();
              
        if(parameters[i].compareTo("") == 0){
          parameters[i] = UNKNOWN;
        }
              
      }
            
      //creates the book using the parameters given in the file in the form (latname, fristname ,title)
      //hence the need for 2, 1 ,0 to access the variables in the correct order
      Book newBook = new Book(parameters[2], parameters[1] , parameters[0]);
            
      lib.addBook(newBook);
            
    }
    else{
     System.out.println("Incorrect Parameter Count, Library was not modified");
    }
  }
  
  //helper function that handles both the SEARCHA and SEARCHT commands, searches through the library to find a book
  //either by title or authors last name
  //accepts a String representing the line of the file we are currently trying to execute, a Library object that the 
  //command will be executed on, and a String representing the action being taken, should be either "SEARCHA" or
  //"SEARCHT"
  private static void fileSearchAorT(String nextLine, Library lib, String requestedAction){
    String[] parameters; //holds the parametsrs for the add action
          
    //creates a string with the requested Action removed 
    String[] userInputLine = nextLine.split(requestedAction);
    parameters = userInputLine[1].split(",");//takes the string that isnt the requested action as the parameters
          
    //proceedes only if correct number of parameters were given, removes extra white space and then proceeds 
    //to replace any emptry spots with "unknown"
    if(parameters.length == PARAMETER_COUNT_ONE){
      for(int i=0; i<parameters.length; i++){
        parameters[i] = parameters[i].trim();
      }
            
      //lists the books by the author or title,  wich is stored in the first, and only index of the
      //parameters arrary
      if(requestedAction.compareTo(SEARCHA)==0){
        System.out.println(lib.listByAuthor(parameters[0]));
      }
      else if(requestedAction.compareTo(SEARCHT) == 0){
        System.out.println(lib.listByTitle(parameters[0]));
      }
    }
    else{
      System.out.println("Incorrect Parameter Count, Library was not modified");
    }
  }
  
  //helper function that handles the GETBOOK and RETURNBOOK commands in the user input file either loaning out or 
  //returning a book to the library
  //Accepts a String representing the line of the file we are currently trying to execute, a Library object that the 
  //command will be executed on, and a String representing the action being taken, should be either "GETBOOK" or
  //"RETURNBOOK"
  private static void fileGetOrReturnBook(String nextLine, Library lib, String requestedAction){
     String[] parameters; //holds the parametsrs for the add action
     
     //creates a string with the requested Action removed 
     String[] userInputLine = nextLine.split(requestedAction);
     
     parameters = userInputLine[1].split(",");//takes the string that isnt the requested action as the parameters
          
     //proceedes only if correct number of parameters were given, removes extra white space and then proceeds 
     //to replace any emptry spots with "unknown"
     if(parameters.length == PARAMETER_COUNT_FULL){
       for(int i=0; i<parameters.length; i++){
         parameters[i] = parameters[i].trim();
       }
            
       //creates the book using the parameters given in the file in the form (latname, fristname ,title)
       //hence the need for 2, 1 ,0 to access the variables in the correct order
       Book newBook = new Book(parameters[2], parameters[1] , parameters[0]);
            
       //attempts to loan or return the book depending on requested action and then reports the outcome via
       //the console, the individual parameters must still be passed to the loan and return book methods as they were
       //to Book except this time in ververs order as mandated by the methods
       if(requestedAction.compareTo(GETBOOK)==0){
         boolean loanSuccesful = lib.loanBook(parameters[0], parameters[1] , parameters[2]);
         if(loanSuccesful){
           System.out.println("Book loaned:\n"+ newBook);
         }
         else{
           System.out.println("The following book is unavailable to be loaned:\n" + newBook);
         }
       }
       else if(requestedAction.compareTo(RETURNBOOK) == 0){
         boolean returnSuccesful = lib.returnBook(parameters[0], parameters[1] , parameters[2]);
         if(returnSuccesful){
           System.out.println("Book returned:\n"+ newBook);
         }
         else{
           System.out.println("The following book was unable to be returned:\n" + newBook);
         }
       }           
     }
     else{
       System.out.println("Incorrect Parameter Count, Library was not modified");
     }
  }
}

//Represents a book inside a library
class Book{
  //instance variables
  private String title;
  private String authorFirstName; //contains the authors first name or initials
  private String authorLastName; //contains the authors last name or surname
  private boolean isLoaned; //records whether the book is currently loaned out
  
  //construcor
  //creates a book with the given title and author and assumes the new book is not yet loaned out
  public Book(String title, String authorFirstName, String authorLastName){
    this.title = title;
    this.authorFirstName = authorFirstName;
    this.authorLastName = authorLastName;
    isLoaned = false;
  }
  
  //instance accesor methods for all instance variables
  
  public String getTitle(){
    return title;
  }
  
  public String getAuthorFirstName(){
    return authorFirstName;
  }
  
  public String getAuthorLastName(){
    return authorLastName;
  }
  
  public boolean getIsLoaned(){
    return isLoaned;
  }
  
  //mutator method for the isLoaned boolean variable
  public void setIsLoaned(boolean status){
    isLoaned = status;
  }
  
  //creates a string representation of the book in the form "Last name, Fist name, Title"
  public String toString(){
    return (authorLastName+ ", " + authorFirstName + ", " + title);
  }
}

  
  
//Represents a library containing books and allowing for basic functions like searching for books and loaning books out
class Library{
  //instance variables 
  private Book[] bookList;//records all the books in the library, sorted by author last name, first name, and title
  private int bookCount;// current number of books in library
  
  //class constants
  private final static int STARTING_SIZE = 4; //the starting number of book slots in the library, represented as array size
  
  //constructor
  //creates an empty library
  public Library(){
    bookList = new Book[STARTING_SIZE];
  }
  
  //instance methods
  
  //adds a book to the list of books in the library in sorter order, sorted alphabetically by last name, first name,
  //and then title.
  //accepts a single Book object to be added to the library
  public void addBook(Book bookToAdd){
    
    //if the library is full, doubls the size of the book array  
    if(bookCount >= bookList.length){
      Book[] bookListCopy = new Book[bookList.length * 2];
      System.arraycopy(bookList, 0, bookListCopy, 0, bookList.length);
      bookList = bookListCopy;
    }
    
    //does the actual ordering
    int currentIndex = bookCount;
    
    //stops when the first index is reached and grabs the next book in line by using -1
    while((currentIndex >  0) && (!areOrdered(bookList[currentIndex - 1], bookToAdd))){
      bookList[currentIndex] = bookList[--currentIndex];
    }
    bookList[currentIndex] = bookToAdd;
    bookCount++; //records another book has been added to the library
  }
  
  
  //determins if two books are in the proper order, that is if the first book comes before the second. This is done by
  //comparing the Authors last name, first name, and book title alphabetically until one is fount to come before the 
  //other. Identical books are considerd properly orderd
  //Accepts 2 Book objects as parameters to be compared
  //returns a boolean of false if the books are unorderd or true if they are orderd or if they are identical
  public boolean areOrdered(Book firstBook, Book secondBook){
    boolean areOrdered = false;
    
    //checks if the books authoer names and title are alphabetical (via helper method), uses compareTo to vet 
    //occurences where the strings are equal and the next test must be done

    if(firstBook.getAuthorLastName().compareTo(secondBook.getAuthorLastName()) <= 0){

      if(firstBook.getAuthorLastName().compareTo(secondBook.getAuthorLastName()) == 0){
        
        if(firstBook.getAuthorFirstName().compareTo(secondBook.getAuthorFirstName()) <= 0){

          if(firstBook.getAuthorFirstName().compareTo(secondBook.getAuthorFirstName()) == 0){
           
            if(firstBook.getTitle().compareTo(secondBook.getTitle())<=0){
         
              areOrdered = true;
            }
          }
          else{
            areOrdered = true;
          }
        }
      }
      else{
        areOrdered = true;
      }
    }
    
    return areOrdered;
  }
  
  //list books by a particular author (based on last name) in order, 1 per line
  //accepts a String represending the requested author 
  //returns a string of all the authors books contained in the library 
  public String listByAuthor(String author){
    String authorsBooks = "Books by " + author + ":";
    
    int currentIndex = binarySearchForAuthor(author, 0, bookCount -1);//effinently finds an occurance of the author
    
    //goes back through list to find the fist inscance of this author
    while((currentIndex > 0) && (bookList[currentIndex -1].getAuthorLastName().compareTo(author) == 0)){
      currentIndex--;
    }
    
    //adds each book into the final string on a new line
    while((currentIndex<bookCount) && (currentIndex>=0) 
           && (bookList[currentIndex].getAuthorLastName().compareTo(author) == 0)){
      
      authorsBooks += ("\n" + bookList[currentIndex]);
      currentIndex++;
    }
    
    return authorsBooks;
  }
  
  
  //binary search helper function that efficently finds an occurance of the author within the bookList
  //does not nessesarily find the first or last occurance
  //accepts a string of the required author, and the upper and lower bounds to search for the author within
  //returns the first discoverd index containing the author or -1 if the author is never found(not in the list)
  private int binarySearchForAuthor(String author, int lowerBound, int upperBound){
    
    if(lowerBound > upperBound){
      return -1;
    }
    
    else{
      int mid = lowerBound + ((upperBound -lowerBound )/ 2);
       

      if(bookList[mid].getAuthorLastName().compareTo(author) == 0){
        return mid;
      }
      else if(bookList[mid].getAuthorLastName().compareTo(author) > 0){
        return binarySearchForAuthor(author,lowerBound, mid-1);
      }
      else{
        return binarySearchForAuthor(author, mid+1, upperBound);
      }
    }
  }
  
  
  
  //list books with a particular title in order, 1 per line
  //accepts a String represending the requested title
  //returns a String of all the books with that tile contained in the library 
  public String listByTitle(String title){
    String booksWithTitle = "Books named " + title + ":";
    
    for(int i=0; i<bookCount; i++){
      if(bookList[i].getTitle().compareTo(title) == 0){
        booksWithTitle += ("\n" + bookList[i]);
      }
    }
    return booksWithTitle;
  }
  
  //loans out a book by calling a helper method and indicating the requested action is loaning(true)
  //accepts 3 Strings for authors last and first names and then title of the desired book
  //returns a boolean indicating if the operation was successful
  public boolean loanBook(String authorLastName, String authorFirstName, String title){
    return loanOrReturn(authorLastName, authorFirstName, title, true);
  }
  
  //returns a loaned book by calling a helper method and indicating the requested action is returning(false)
  //accepts 3 Strings for authors last and first names and then title of the desired book
  //returns a boolean indicating if the operation was successful
  public boolean returnBook(String authorLastName, String authorFirstName, String title){
    return loanOrReturn(authorLastName, authorFirstName, title, false);
  }
  
  //helper method that either loans out a book, or returns it based on parameters input. 
  //accepts 3 Strings for authors last and first names and then title of the desired book. Also accepts 1 boolean
  //indicating if the book should be loaned out (true) or returned (false)
  //returns a boolean indicating if the book was successfully loaned out/retuned (true) or not (false)
  private boolean loanOrReturn(String authorLastName, String authorFirstName, String title, Boolean loanOut){
    boolean loanOrReturnSuccesful = false; //tracks if the book has succesfully been loaned or returned
    
    //efficiently finds an occurance of the authors last name if it exists within the library
    int currentIndex = binarySearchForAuthor(authorLastName, 0, bookCount-1);

    //ignors the case where the author is not listed in the library
    if(currentIndex >= 0){
      //goes back through list to find the fist inscance of this author
      while((currentIndex > 0) && (bookList[currentIndex -1].getAuthorLastName().compareTo(authorLastName) == 0)){
        currentIndex--;
      }
      
      
      boolean firstNameFound = false;//tracks whether we have found a first name matching the one requested
      
      //searches all linstances of the last name of the author to attempt to find a matching first name to our 
      //requested one 
      while((currentIndex<bookCount) && (bookList[currentIndex].getAuthorLastName().compareTo(authorLastName) == 0)
            && (!firstNameFound)){
        
        if(bookList[currentIndex].getAuthorFirstName().compareTo(authorFirstName) == 0){
          firstNameFound = true;
        }
        else{
          currentIndex++;
        }
      }
      
      if(firstNameFound){
        
        boolean titleFound = false;//traks if we have found a matching tile or not
        
        //searches all instances of the matching author name for the correct book title
        while((currentIndex<bookCount) && (bookList[currentIndex].getAuthorFirstName().compareTo(authorFirstName) == 0) 
                && (!titleFound)){
          
          if(bookList[currentIndex].getTitle().compareTo(title) == 0){
            titleFound = true;
          }
          else{
            currentIndex++;
          }
        }
        
        if(titleFound){
          
          boolean correctCopyFound = false; //tracks if we have found a copy to loan/return

          //searches through all copys of the book we have at the library to see if we can loan/return any
          while((currentIndex<bookCount) && (bookList[currentIndex].getTitle().compareTo(title) == 0) 
                && (!correctCopyFound)){
            
            if(!loanOut){
            }
            //checks if this copy can be either loaned or returned depending on our needs
            if(bookList[currentIndex].getIsLoaned() != loanOut){
              correctCopyFound = true;
              bookList[currentIndex].setIsLoaned(loanOut);
              loanOrReturnSuccesful = true;
              
            }
            else{
              currentIndex++;
            }
          }
        }
      }
    }
    
    return loanOrReturnSuccesful; 
  }
    
  //creates and returns a string representation of the library as a list of books contained in that library
  //in the form "[book1, book2, book3]"
  public String toString(){
    String returnString = "";
    
    returnString += "[";
      
    for(int i =0; i<bookCount; i++){
      //adds the last book without a comma
      if(i == bookCount -1){
        returnString += bookList[i];
      }
      //adds all other books normally
      else{
        returnString += bookList[i] + ", ";
      }
    }
    
    returnString += "]";
    return returnString;
  }
}