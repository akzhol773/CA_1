package CA_1;



import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InputUtilities inputUtilities = new InputUtilities();
        String fileName = inputUtilities.askUserForText("Please enter the filename (filename only, not extension): ");

        try{

            Scanner file = new Scanner(new FileReader("src/main/java/"+fileName.toUpperCase() + ".txt"));
            System.out.println("File read successfully");

            //Read the data into arrays
            String[] bookTitles = new String[1000];
            int[] numPages = new int[1000];
            int index = 0;
            while (file.hasNextLine()){
                String line = file.nextLine();
                String[] parts = line.split(",(?=[^,]*$)");
                String bookTitle = parts[0].trim();
                int numPage = Integer.parseInt(parts[1].trim());
                bookTitles[index] = bookTitle;
                numPages[index] = numPage;
                index++;
            }


            //Prompting the user to enter one of the options
            System.out.println("================================================================");
            System.out.println("| Select option:                                                |");
            System.out.println("| 1. SORT                                                       |");
            System.out.println("| 2. SEARCH                                                     |");
            System.out.println("=================================================================");
            int choice = inputUtilities.askUserForInt("Do you wish to SORT or SEARCH: ", 1, 2);



            switch (choice) {
                case 1:
                    System.out.println(" Enter sorting option: ");
                    System.out.println(" 1. Sort by book title ");
                    System.out.println(" 2. Sort by number of pages");


                    int sortingChoice = inputUtilities.askUserForInt("Sort by: ", 1, 2);

                    System.out.println(" Enter choice: ");
                    System.out.println(" 1. Sort order Ascending ");
                    System.out.println(" 2. Sort order Descending");

                    int sortOrder = inputUtilities.askUserForInt("Sort order: ", 1, 2);
                    if (sortingChoice == 1) {
                        sortByTitle(bookTitles, numPages, sortOrder);

                    } else if (sortingChoice == 2) {
                        sortByPages(bookTitles, numPages, sortOrder);
                    } else {
                        System.out.println("Invalid sorting criteria");
                        return;
                    }
                    displayResults(bookTitles, numPages);
                    break;
                case 2:
                System.out.println(" Enter searching option: ");
                System.out.println(" 1. Search the book by title ");
                System.out.println(" 2. Search the book by number of pages");

                int searchingChoice = inputUtilities.askUserForInt("Search by: ", 1, 2);
                if (searchingChoice == 1) {
              String title = inputUtilities.askUserForText("Enter the book title: ");
                 searchByTitle(title, bookTitles, numPages);


                } else if (searchingChoice == 2) {
                  int page = inputUtilities.askUserForInt("Enter the number of pages: ");
                 searchByPages(page, bookTitles, numPages);
                
                } else {
                    System.out.println("Invalid sorting criteria");
                    return;
                }

                    break;
                default:
                    System.out.println("Invalid choice");
            }

        }catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private static void sortByPages(String[] bookTitles, int[] numPages, int sortOrder) {
        if(sortOrder == 1){
            mergeSortPagesAscending(bookTitles, numPages);
        }else{
            mergeSortPagesDescending(bookTitles, numPages);
        }

    }

    private static void mergeSortPagesDescending(String[] bookTitles, int[] numPages) {
        for (int i = 0; i < numPages.length - 1; i++) {
            for (int j = 0; j < numPages.length - i - 1; j++) {
                if (numPages[j] < numPages[j + 1]) { // Change the condition for descending order
                    // Swap numPages[j] and numPages[j+1]
                    int temp = numPages[j];
                    String tempS = bookTitles[j];
                    numPages[j] = numPages[j + 1];
                    bookTitles[j] = bookTitles[j + 1];
                    numPages[j + 1] = temp;
                    bookTitles[j + 1] = tempS;
                }
            }
        }
    }




    private static void mergeSortPagesAscending(String[] bookTitles, int[] numPages) {
        for (int i = 0; i < numPages.length-1; i++) {
            for (int j = 0; j < numPages.length-i-1; j++) {
                if (numPages[j] > numPages[j+1]) {
                    // Swap arr[j] and arr[j+1]
                    int temp = numPages[j];
                    String tempS = bookTitles[j];
                    numPages[j] = numPages[j+1];
                    bookTitles[j] = bookTitles[j+1];
                    numPages[j+1] = temp;
                    bookTitles[j+1] = tempS;
                }
            }
        }
    }

    //    Sort by title
    private static void sortByTitle(String[] bookTitles, int[] numPages, int sortOrder) {
        if(sortOrder == 1){
            mergeSortAscending(bookTitles, numPages);
        }else{
            mergeSortDescending(bookTitles, numPages);
        }
    }


//    Sort by title and descending order
    private static void mergeSortDescending(String[] bookTitles, int[] numPages) {
        for (int i = 0; i < bookTitles.length; i++) {
            for (int j = i + 1; j < bookTitles.length; j++) {
                // Compare book titles in descending order
                if (bookTitles[i].compareTo(bookTitles[j]) < 0) {
                    // Swap array elements
                    String tempTitle = bookTitles[i];
                    int tempPages = numPages[i];
                    bookTitles[i] = bookTitles[j];
                    numPages[i] = numPages[j];
                    bookTitles[j] = tempTitle;
                    numPages[j] = tempPages;
                }
            }
        }
    }

    //    Sort by title and ascending order
    private static void mergeSortAscending(String[] bookTitles, int[] numPages) {

        for(int i = 0; i<bookTitles.length; i++)
        {
            for (int j = i+1; j<bookTitles.length; j++)
            {
                if(bookTitles[i].compareTo(bookTitles[j])>0)
                {
                    //swapping array elements
                    String temp = bookTitles[i];
                    int tempPage = numPages[i];
                    bookTitles[i] = bookTitles[j];
                    numPages[i] = numPages[j];
                    bookTitles[j] = temp;
                    numPages[j] = tempPage;
                }
            }
        }
    }

                // Search book by title
private static void searchByTitle(String title, String[] titles, int[] pages) {
    boolean found = false;
    for (int i = 0; i < titles.length; i++) {
        if (titles[i].toLowerCase().contains(title.toLowerCase())) {
            System.out.println("Book found index position  " + i + ", and number of page is " + pages[i]);
          System.out.println("Book full title is  "+ titles[i]);
            found = true;
            break;
        }
    }
    if (!found) {
        System.out.println("Book not found.");
    }
}

  // Search book by number of pages
  private static void searchByPages(int page, String[] titles, int[] pagesArray) {
      // Ensure that the pagesArray and titles are sorted based on pagesArray
      mergeSortPagesAscending(titles, pagesArray);

      int left = 0;
      int right = pagesArray.length - 1;
      boolean found = false;

      while (left <= right) {
          int mid = left + (right - left) / 2;

          // Check if the middle element is the target page count
          if (pagesArray[mid] == page) {
              System.out.println("Book found at index position " + mid + ", and book full title is " + titles[mid]);
              found = true;
              break;
          }

          // If the target page count is greater, ignore the left half
          if (pagesArray[mid] < page) {
              left = mid + 1;
          }
          // If the target page count is smaller, ignore the right half
          else {
              right = mid - 1;
          }
      }

      if (!found) {
          System.out.println("Book not found.");
      }
  }




//    Display the results
    private static void displayResults(String[] bookTitles, int[] numPages) {
        for (int i = 0; i < bookTitles.length; i++) {
            System.out.println(bookTitles[i] + ", " + numPages[i]);
        }

    }


}
