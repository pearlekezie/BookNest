# BookNest
Simple Library Management Java GUI📚
this was a little project i worked on for my ics3u final project:)
it works using java swing and api responses from google books api

Core Features:
-  Allow users to search for books by title or genre
-  Display book details including:
   - Title
   - Author
   - Year of Publication
   - Brief summary of book
   - Genre
   - Cover Page
- Add selected books to a "My Collection" section.
- Display the list of added books.
- Remove a book from a collection.

  Methods:
→ Search Button Function: To search for a book when a keyword is inputted
→ Add Item Button Function = addBookEntry() : To add a book to the My Collections Tab
→ Remove Button Function = removeButton.addActionListener() : To remove a book from the My Collections list
→ Connect to API function = connectToApi() : To connect to the google books api so as to be able to use it
→ Read Json response function = getResponse() : To read the response from the API after making a connection
→ Get cover page function = getCoverPage() : To retrieve the cover of a book(if it has any) from image link gotten from api and display in gui section
→ Parse Book Info function = parseBookInfo() : To parse the json data and extract book information such as title, description, author, year of publication, genre and cover image 




DISCLAIMER
- minor bugs may be present depending on your screen.

