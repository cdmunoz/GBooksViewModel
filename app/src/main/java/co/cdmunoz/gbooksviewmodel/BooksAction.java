package co.cdmunoz.gbooksviewmodel;

import co.cdmunoz.gbooksviewmodel.api.GoogleBooksService;
import co.cdmunoz.gbooksviewmodel.model.BookSearchResult;
import io.reactivex.Observable;

public class BooksAction {

  Observable<BookSearchResult> getBooks(String query) {
    return GoogleBooksService.INSTANCE.search("search+" + query);
  }
}
