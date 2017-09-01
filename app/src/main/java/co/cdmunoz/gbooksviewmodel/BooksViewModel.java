package co.cdmunoz.gbooksviewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import co.cdmunoz.gbooksviewmodel.model.Book;
import co.cdmunoz.gbooksviewmodel.model.BookSearchResult;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class BooksViewModel extends ViewModel {

  private MutableLiveData<List<Book>> books;
  BooksAction booksAction;

  public BooksViewModel() {
    booksAction = new BooksAction();
  }

  LiveData<List<Book>> getBooks() {
    if (null == books) {
      books = new MutableLiveData<>();
    }
    return books;
  }

  public void setBooks(MutableLiveData<List<Book>> books) {
    this.books = books;
  }

  public void loadBooks(String query) {
    booksAction.getBooks(query)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<BookSearchResult>() {
          @Override public void onSubscribe(@NonNull Disposable d) {

          }

          @Override public void onNext(@NonNull BookSearchResult bookSearchResult) {
            books.setValue(bookSearchResult.getBooks());
          }

          @Override public void onError(@NonNull Throwable e) {
            e.printStackTrace();
          }

          @Override public void onComplete() {

          }
        });
  }
}
