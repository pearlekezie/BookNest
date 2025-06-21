import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * BookSearchAPI handles connection to the Google Books API,
 * processes the response, extracts book information,
 * and returns formatted results for display in the UI.
 *
 * It:
 * - Connects to the API
 * - Fetches and reads the response
 * - Parses book details
 * - Display book covers
 */

public class BookSearchAPI {


   // Declaring variables so as to be able to use them in the other files
   static String title;
   static String author;
   static String description;
   static String yearOfPublication;
   static String genre;

   static String keywordd;
   static String thumbnail = "";
   static JLabel coverLabel;

  public static ArrayList<Book> results = new ArrayList<>(); // dynamic array list that holds all the book objects that are found in the search results


    // Function to make a http connection
    /**
     * Connects to the Google Books API using the keyword provided.
     *
     * @param keyword the search term used to look for books
     * @return a HttpURLConnection object that connects to the Google Books API URL
     */

   public static HttpURLConnection connectToApi(String keyword) {
       // I'm using try for error handling. These implementations are very prone to A LOT of errors lol!
       try {
           keyword.replace(" ", "+"); // To replace whitespaces with the plus sign for proper url formatting
          
           // The API URL to search for books using the Google Books API
           String apiURL = "https://www.googleapis.com/books/v1/volumes?q=" + keyword.replace(" ", "+"); // url generated upon search


           URL url = new URL(apiURL); // make the generated url an official object
           HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); // makes the official connection
           httpURLConnection.setRequestMethod("GET");


           return httpURLConnection;
       }
       catch (Exception e) {
           // All just error handling
           e.printStackTrace();
           return null;
       }


   }


   // Function to read java response
    /**
     * Reads and returns the JSON response from the given HttpURLConnection.
     *
     * @param httpURLConnection the active HTTP connection to the API
     * @return a String containing the raw JSON data from the response
     */ 
   public static String getResponse(HttpURLConnection httpURLConnection) {
       try{
           BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())); //like a scanner but for apis therefore to read input from the api


           // Will figure these out tomorrow
           StringBuilder json = new StringBuilder(); // building an array to store all json characters
           String line; // json element


           // Condition for when a json element is not null
           while ((line = reader.readLine()) != null) {
               json.append(line); // while not null, keep adding to the json general array
           }


           reader.close(); // to avoid resource leakage due to unexpected events
           return json.toString(); // return raw json string


       }
       catch (Exception e) {
           e.printStackTrace();
           return null;
       }






   }


   // Function to get the cover page by loading the image from the url
   /**
     * Loads and returns a book cover image as a JLabel from the given image URL.
     *
     * @param imageUrl the URL of the book's cover image
     * @return a JLabel containing the image icon, or a "No Cover" label if loading fails
     */
   public static JLabel getCoverPage(String imageUrl) {
       // using exception handling
       JLabel label;
       try {
           URL url = new URL(imageUrl); // declares the url as an official url object
           ImageIcon icon = new ImageIcon(url); // loads the image from the url and stores it in this variable("icon")'
           label = new JLabel(icon); // puts the retrieved image in a Jlabel


           return label; // gets label so it can be added to the ui
       } catch (Exception e) {
           e.printStackTrace();
           return new JLabel("No Cover");
       }
    }



   // Function to parse json data to extract book information
   /**
     * Parses the raw JSON string received from the API to extract book information,
     * creates Book objects, and adds them to the results list.
     *
     * @param json the raw JSON string returned from the API
     */
   public static void parseBookInfo(String json) {
        results.clear(); // clears the results list before adding new results to avoid duplicates

       // To parse the entire json string into a json object
       JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

       // To get the array of books under the item key and make the item key a json array
       JsonArray items = jsonObject.getAsJsonArray("items");

       // to check if array exists and has at least 1 value(at least 1 search result)
       if (items != null && items.size() > 0) {
           // loop to show all search results instead of one
           for (int i = 0; i < items.size(); i++) {

               // to get the all books in the list
               JsonObject Onebook = items.get(i).getAsJsonObject();


               // to get the volume_info section which holds all the book details
               JsonObject volumeInfo = Onebook.getAsJsonObject("volumeInfo");


               // Getting all book information
               // To get the title
               title = volumeInfo.get("title").getAsString();


               // to get the author
               author = volumeInfo.getAsJsonArray("authors").get(0).getAsString();


               // to get the description
               description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No description"; //  to check if it has a description first and if not, say "no description in the section"


               // to get the year of publication
               yearOfPublication = volumeInfo.get("publishedDate").getAsString();


               // to get the genre
               genre = volumeInfo.has("categories") ? volumeInfo.getAsJsonArray("categories").get(0).getAsString() : "No genre"; //  to check if it has a genre first and if not, say "no description in the section"


               // to get the book cover
               thumbnail = ""; // default value is an empty string because some books may not have covers


               // Check if the book has an image link
               if (volumeInfo.has("imageLinks")) {
                   JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks"); // creating object for image link
                   thumbnail = imageLinks.has("thumbnail") ? imageLinks.get("thumbnail").getAsString() : ""; // is found, get the link and convert to a string(i'm using it in a function that's why)
               }
               coverLabel = getCoverPage(thumbnail);

               // Creates a new Book object with the collected information
                Book book = new Book(title, author, description, yearOfPublication, genre, coverLabel);
                results.add(book); // adds the book object to the results list


               // Print all the collected book info
               System.out.println("Title: " + title);
               System.out.println("Author: " + author);
               System.out.println("Published: " + yearOfPublication);
               System.out.println("Genre: " + genre);
               System.out.println("Description: " + description);
           }
       } else {
           // If no books were found in the API result
           System.out.println("No results found!");
       }
   }










//    // Just for testing
//    public static void main(String[] args) {
//        HttpURLConnection httpURLConnection = connectToApi("dora");
//        if (httpURLConnection != null) {
//            System.out.println("Connected to API");
//        }
//        String json = getResponse(httpURLConnection); // call get response function
//        System.out.println(json); // print raw json
//
//        parseBookInfo(json); // show all data
//
//
//
//    }
}
