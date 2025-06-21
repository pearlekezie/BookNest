/* Created by Louis Wei, Pearl Ekezie, Angelina Cianfarani
* 05/27/2025
* Version = '0.1'
* This program is a simple library management GUI
*/


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.util.Scanner;


public class Main {
   public static void main(String[] args) {


    JFrame frame = new JFrame(); // Creating main frame


       //Welcome message at top of page
       JLabel titleLabel = new JLabel(); // Holds the title text
       titleLabel.setText("Welcome to BookNest"); // Introduction at top of page
       titleLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Set font and size
       titleLabel.setVisible(true); // Make label visible



       //Frame setup
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Enable exit on close
       frame.setSize(2000, 1000); // Set window size
       frame.setResizable(false); // Disable resizing


       //Search tab
       JPanel search = new JPanel(); //creates new panel on tabbed pane
       search.setLayout(new BoxLayout(search, BoxLayout.Y_AXIS)); //creates a vertical layout for everything in the tab

       //Scroll pane on search tab
       JScrollPane scrollPane2 = new JScrollPane(search);// Wrap search  in a scroll pane for overflow
       scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //creates a vertical scroll bar



       //Search field on search tab
       JTextField searchField = new JTextField(20); // Create a text field for the search bar
       searchField.setMaximumSize(searchField.getPreferredSize()); // Prevent it from stretching and taking up the whole screen

       // Results panel on search tab
       // This panel will hold the book cards that are displayed after a search
       JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS)); // to stack book cards

        // Scroll pane for results panel
        JScrollPane scrollPane3 = new JScrollPane(resultsPanel);
        scrollPane3.setPreferredSize(new Dimension(800, 600));


       //Message asking user to enter a book name in search tab
       JLabel PromptLabel = new JLabel("Please enter a book name to search"); // Create a label to display the search result or a message


       //My collection tab
       JPanel myCollection = new JPanel();// Extended My Collection panel
       myCollection.setLayout(new BoxLayout(myCollection, BoxLayout.Y_AXIS)); //creates a vertical layout



       //Scroll pane for my collection tab
       JScrollPane scrollPane = new JScrollPane(myCollection);// Wrap myCollection in a scroll pane for overflow
       scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //creates a vertical scroll bar




       //Search button
       SButton searchButton = new SButton(search, myCollection, searchField); //  search is set as the parameter for SButton because that is the panel it takes in




       // Add components to the search panel
       search.add(searchField);
       search.add(searchButton);
       search.add(PromptLabel);
       search.add(resultsPanel); // Adds the results panel to the search tab for displaying search results
       search.add(Box.createVerticalStrut(10)); // Adds vertical spacing between components in the search tab
       search.add(scrollPane3); // Adds the scroll pane to the search tab for overflow handling









       //Tabbed panel with search and my collection tabs
       JTabbedPane tab = new JTabbedPane(); //creates the tabbed pane
       tab.setBounds(40, 60, 1800, 850); //sets size and location of pane




       //Adds labels to the tabs
       tab.add("Search", scrollPane2);
       tab.add("My Collection", scrollPane);








       //Adds the tabbed pane to the main frame
       frame.add(tab);

       //Adds layouts/labels to main frame and set visible
       frame.setTitle("Simple Library Management GUI"); //names the window
       frame.setLayout(new BorderLayout()); //creates a frame layout
       frame.add(titleLabel, BorderLayout.NORTH); //adds the title label to the top of the page
       frame.setVisible(true); //makes the frame visible
   }








   /**
    * Helper method: Adds a single book entry to the specified parent panel.
    * Each entry shows three lines (Title of book, Description of book, Genre) and a Remove button.
    *
    * @param parent      The panel to which the book entry is added
    * @param title       The book title
    * @param author      Author of the book
    * @param genre       The genre of the book
    */


   public static void addBookEntry(JPanel parent, String title, String author, String genre) {
       // Create a panel for a single book entry, using BorderLayout
       JPanel bookPanel = new JPanel(); //creates the book panel
       bookPanel.setLayout(new BorderLayout()); //creates the border layout
       bookPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adds some padding


       // Left side: info panel with vertical layout for title, description, and genre
       JPanel infoPanel = new JPanel(); //creates the panel
       infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); //vertically stacks it




       //Adds text and information to the info panel
       JLabel titleLabel = new JLabel("Title of book: " + title); // Title of book
       titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font of label
       JLabel authorLabel = new JLabel("<html>Author of book: " + author + "</html>"); //Author of book
       authorLabel.setFont(new Font("Arial", Font.PLAIN, 14)); //Set font of label
       JLabel genreLabel = new JLabel("Genre: " + genre); //Displays genre of book
       genreLabel.setFont(new Font("Arial", Font.ITALIC, 12)); //Set font of label








       //Adds all text and layouts to the info panel
       infoPanel.add(titleLabel);
       infoPanel.add(Box.createVerticalStrut(5)); // Vertical spacing
       infoPanel.add(authorLabel);
       infoPanel.add(Box.createVerticalStrut(5)); // Vertical spacing
       infoPanel.add(genreLabel);





       // Right side: button panel containing the remove button on my collections page
       JPanel buttonPanel = new JPanel(); //creates a panel for the button
       buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0)); //moves the button to the right side of the tab
       JButton removeButton = new JButton("Remove"); //creates the remove button
       removeButton.setFont(new Font("Arial", Font.PLAIN, 12)); //adjusts font/size of button



       // Remove the bookPanel from parent when clicked, then refresh the UI
       /**
         * Action listener for the "Remove" button on a book in the user's collection.
         *
         * this:
         * - Removes the corresponding book panel from the parent container (My Collection)
         * - Refreshes the panel to reflect the change
         * - Re-enables the "Add Item" button in the Book_Info panel (in case the user wants to add it again)
         * - Resets the "Add Item" button text back to "Add Item"
         *
         * @param e The event triggered by clicking the "Remove" button.
         */
       removeButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               parent.remove(bookPanel);
               parent.revalidate();
               parent.repaint();

            //    Book_Info.addItemButton.setEnabled(true); // Re-enables the Add Item button in Book_Info after removing a book
            //    Book_Info.addItemButton.setText("Add Item"); // Resets the button text to "Add Item"
           }
       });


       buttonPanel.add(removeButton); //adds the remove button to the button panel


       // Adds infoPanel to the center and buttonPanel to the right side of bookPanel
       bookPanel.add(infoPanel, BorderLayout.CENTER);
       bookPanel.add(buttonPanel, BorderLayout.EAST);

       // Add a horizontal separator below the bookPanel
       JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);


       // First add the bookPanel, then the separator to the parent
       parent.add(bookPanel);
       parent.add(separator);
   }
}


