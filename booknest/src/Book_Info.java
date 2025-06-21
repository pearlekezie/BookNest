import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/*  * Book class represents a book with its details such as title, author, description, year of publication, genre, and cover image.
 * We are using it to create book objects that can be displayed in the Book_Info panel.
 */
class Book {
   String title; // Title of the book
   String author; // Author of the book
   String description; // Description of the book
   String yearOfPublication; // Year of publication of the book
   String genre; // genre of the book
   JLabel coverLabel; // cover label that contains the cover image

   // Constructor
   public Book(String title, String author, String description, String yearOfPublication, String genre, JLabel coverLabel) {
      this.title = title;
      this.author = author;
      this.description = description;
      this.yearOfPublication = yearOfPublication;
      this.genre = genre;
      this.coverLabel = coverLabel;
   }






}

/**
 * Book_Info creates a detailed panel card for a specific book, showing all retieved book information.
 * It includes an "Add Item" button to allow users to save the book to their collection.
 */



public class Book_Info extends JPanel {

  String titleToBeUsedAsText; // Title of the book to be used as text
  String authorToBeUsedAsText; // Author of the book to be used as text
  String genreToBeUsedAsText; // Genre of the book to be used as text
  String yearToBeUsedAsText;   // Year of publication of the book to be used as text

  static JButton addItemButton; // Button to add the book to the collection

  public Book_Info(Book book, JPanel myCollection) {


  // Book Info Panel (Content on Left, Image on Right)
  JPanel bookInfoPanel = new JPanel();
  bookInfoPanel.setLayout(new BoxLayout(bookInfoPanel, BoxLayout.X_AXIS));
  bookInfoPanel.setPreferredSize(new Dimension(600, 300));
  bookInfoPanel.setMaximumSize(new Dimension(1000, 400));
  bookInfoPanel.setBackground(Color.WHITE);


  // LEFT: Book Text Info
  JPanel contentPanel = new JPanel();
  contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
  contentPanel.setBackground(Color.pink);
  contentPanel.setMaximumSize(new Dimension(800, 400));
  contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding
  contentPanel.setAlignmentY(Component.TOP_ALIGNMENT); // Align to top


  // assigning built-in functions to format the title.
  // Title of Book
  titleToBeUsedAsText = book.title; // title retrieved from api
  JLabel title = new JLabel(titleToBeUsedAsText);
  // Properties
  title.setText(titleToBeUsedAsText);
  title.setFont(new Font("SansSerif", Font.BOLD, 16));
  title.setAlignmentX(Component.LEFT_ALIGNMENT);


  // Author of Book
  authorToBeUsedAsText = book.author; // author retrieved from api
  JLabel author = new JLabel(authorToBeUsedAsText);
  author.setAlignmentX(Component.LEFT_ALIGNMENT);


  // Description of Book
  JTextArea summary = new JTextArea(book.description); // description retrieved from api
  summary.setLineWrap(true); // To make the text not constrained to one line(next line)
  summary.setWrapStyleWord(true);
  summary.setEditable(false); // To prevent text from being editable
  summary.setOpaque(false); // To make sure it is indeed visible
  summary.setBorder(null); // no borders
  summary.setFocusable(false); // to avoid a focus
  summary.setAlignmentX(Component.LEFT_ALIGNMENT); // to align text leftwards


  // Genre Placeholder
  genreToBeUsedAsText = book.genre; // genre retrieved from api
  JLabel genre = new JLabel(genreToBeUsedAsText); // make genre label
  genre.setAlignmentX(Component.LEFT_ALIGNMENT); // position it to the left


  // Year of Publication Placeholder
  yearToBeUsedAsText = book.yearOfPublication; // author retrieved from api
  JLabel year = new JLabel(String.valueOf(yearToBeUsedAsText)); // make genre label
  year.setAlignmentX(Component.LEFT_ALIGNMENT); // make genre label


  // Adding all components to the general parent panel --> contentPanel
  contentPanel.add(title);
  contentPanel.add(Box.createVerticalStrut(5)); // space
  contentPanel.add(author);
  contentPanel.add(Box.createVerticalStrut(5)); // space
  contentPanel.add(summary);
  contentPanel.add(Box.createVerticalStrut(10)); // space
  contentPanel.add(genre);
  contentPanel.add(year);


  // RIGHT: Book Cover + Button
  JPanel imagePanel = new JPanel();
  // Properties
  imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
  imagePanel.setBackground(Color.red);
  imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding
  imagePanel.setMaximumSize(new Dimension(220, 340));
  imagePanel.setPreferredSize(new Dimension(220, 340));
  imagePanel.setAlignmentY(Component.TOP_ALIGNMENT); //  Align to top

  // Book Cover - Image
  JLabel bookCover = book.coverLabel;
  // Properties
  bookCover.setPreferredSize(new Dimension(150, 350));
  bookCover.setMaximumSize(new Dimension(150, 350));
  bookCover.setOpaque(true);
  bookCover.setBackground(Color.LIGHT_GRAY);
  bookCover.setHorizontalAlignment(SwingConstants.CENTER);
  bookCover.setAlignmentX(Component.CENTER_ALIGNMENT);


  // Add Item Button below the book image
  addItemButton = new JButton("Add Item");
  addItemButton.setAlignmentX(Component.CENTER_ALIGNMENT);

  // Functionality of the Add Item Button
  /**
   * Action listener for the "Add Item" button.
   * 
   * When clicked, this adds the current book to the "My Collection" panel,
   * disables the button to prevent duplicate additions, and changes the button
   * text to "Added" to show the user that it has been.
     * @param e the ActionEvent triggered by the button click
   */
  addItemButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      Main.addBookEntry(myCollection, titleToBeUsedAsText, authorToBeUsedAsText, genreToBeUsedAsText); // triggering this function whenever the add item button is clicked
      JOptionPane.showMessageDialog(null, "Book added to My Collection!"); // Message dialog box that triggers whenever book has been added
      addItemButton.setEnabled(false); // Disables the button after clicking
      addItemButton.setText("Added"); // Change button text to "Added" after clicking
    }
  });



  // Adding contents to the image panel
  imagePanel.add(bookCover);
  imagePanel.add(Box.createVerticalStrut(70));
  imagePanel.add(addItemButton);


  // Add both content and image sections to bookInfoPanel
  bookInfoPanel.add(contentPanel);
  bookInfoPanel.add(Box.createHorizontalStrut(20));
  bookInfoPanel.add(imagePanel);


  // Wrap in a bordered container
  JPanel cardContainer = new JPanel();
  // Properties
  cardContainer.setLayout(new BorderLayout());
  cardContainer.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
  cardContainer.add(bookInfoPanel, BorderLayout.CENTER);
  cardContainer.setMaximumSize(new Dimension(1000, 400)); // Set max size for the card
  cardContainer.setPreferredSize(new Dimension(1000, 400)); // Set preferred size for the card

  // This adds the complete, properly formatted panel with border
  setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // or BoxLayout.X_AXIS if you prefer
  add(cardContainer);


  }

}
