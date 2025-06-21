    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.net.HttpURLConnection;


    /**
     * Constructs a search button that triggers a book search using the Google Books API.
     *
     * @param targetPanel The panel where the search results (book cards) will be displayed.
     * @param myCollectionPanel The panel where books can be added to a user's collection.
     * @param searchField The text field where the user types in their search keyword.
     */

    public class SButton extends JButton implements ActionListener {
    private final JPanel targetPanel; // Here the target panel is expected to be the search panel
    private final JPanel myCollectionPanel;
    private final JTextField searchField;

    
    public SButton(JPanel targetPanel, JPanel myCollectionPanel, JTextField searchField) {
        super("Search"); // Text on Button
        this.targetPanel = targetPanel; // Initializing the target panel variable
        this.myCollectionPanel = myCollectionPanel;
            this.searchField = searchField; // Initializing the search field variable
        setBackground(Color.CYAN); // color of button
        addActionListener(this); // to make the button actually work by connecting all functionalities
    }


    // Search Button Function (all these trigger when the search button is clicked)
    /**
     * Action performed when the "Search" button is clicked.
     * 
     * This method:
     * - Validates the user's search input
     * - Sends a request to the Google Books API with the keyword
     * - Parses the JSON response into Book objects
     * -  Generates book cards and displays them in the target panel
     * - Updates the UI to reflect the new results
     * 
     * If no input is entered or an error occurs, it shows a relevant error dialog.
     *
     * @param e The event object triggered by the button click.
     */
    @Override
        public void actionPerformed(ActionEvent e) {
            String keyword = searchField.getText().trim(); // Gets the text from the search field and trims it to remove leading and trailing spaces

            // Validates input
            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a keyword to search.", "Input Error", JOptionPane.WARNING_MESSAGE); // Displays a warning message if the search field is empty
                return;
            }

            try {
                // Fetches and parses books from the API
                BookSearchAPI.keywordd = keyword; // Sets the keyword for the API call based on user input in the search field
                HttpURLConnection httpURLConnection = BookSearchAPI.connectToApi(keyword); // Makes the connection to the API using the keyword
                String json = BookSearchAPI.getResponse(httpURLConnection); // Gets the JSON response from the API
                BookSearchAPI.parseBookInfo(json); // Parses the JSON response to extract book information

                
                targetPanel.removeAll(); // Clears previous search results before showing new ones

                // Loops through all books in the results list and display them
                for (Book book : BookSearchAPI.results) {
                    Book_Info bookCard = new Book_Info(book, myCollectionPanel); // Creates a new Book_Info card for each book found in the search results
                    targetPanel.add(bookCard); // Clears previous search results before showing new ones
                    targetPanel.add(Box.createVerticalStrut(10)); // space between cards
                }

                // Refresh the panel to update UI
                targetPanel.revalidate();
                targetPanel.repaint();

            } catch (Exception ex) { // Catches any exceptions that occur during the search process
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Book not found. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    // when the search button is clicked, the search process begins
            // it checks if there is text in the searchfield and if not, it dispalys and error message. when it has text, it takes the text from the search field and connects to the API, fetching the JSON response which which contains information about books matching the search. then the data is parsed to extract the deatils of the book(title, description etc), and eventually displays it in the target panel(search panel) after the process is complete.
