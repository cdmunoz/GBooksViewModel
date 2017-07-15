package co.cdmunoz.gbooksviewmodel.api;

import co.cdmunoz.gbooksviewmodel.model.BookSearchResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GoogleBooksApi {
  @GET("books/v1/volumes") Observable<BookSearchResult> search(@Query("q") String search);
}
