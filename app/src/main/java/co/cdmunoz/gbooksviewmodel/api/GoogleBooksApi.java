package co.cdmunoz.gbooksviewmodel.api;

import co.cdmunoz.gbooksviewmodel.model.BookSearchResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleBooksApi {
  @GET("books/v1/volumes") Observable<BookSearchResult> search(@Query("q") String search);
}
