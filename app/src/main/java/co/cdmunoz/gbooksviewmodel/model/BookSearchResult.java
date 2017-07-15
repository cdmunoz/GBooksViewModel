package co.cdmunoz.gbooksviewmodel.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BookSearchResult {

  @SerializedName("items")
  private List<Book> books;

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }
}
