package co.cdmunoz.gbooksviewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import co.cdmunoz.gbooksviewmodel.api.GoogleBooksService;
import co.cdmunoz.gbooksviewmodel.model.Book;
import co.cdmunoz.gbooksviewmodel.model.BookSearchResult;
import java.util.List;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BooksViewModel extends ViewModel {

  private MutableLiveData<List<Book>> books;

  LiveData<List<Book>> getBooks() {
    if (null == books) {
      books = new MutableLiveData<>();
    }
    return books;
  }

  public void loadBooks(String query) {
    GoogleBooksService.INSTANCE.search("search+"+query).subscribeOn(Schedulers.io()).observeOn(
        AndroidSchedulers.mainThread()).subscribe(new Subscriber<BookSearchResult>() {
      @Override public void onCompleted() {}

      @Override public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override public void onNext(BookSearchResult bookSearchResult) {
        books.setValue(bookSearchResult.getBooks());
      }
    });
  }
}
