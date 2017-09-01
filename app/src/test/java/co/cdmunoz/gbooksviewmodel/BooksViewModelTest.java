package co.cdmunoz.gbooksviewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import co.cdmunoz.gbooksviewmodel.model.Book;
import co.cdmunoz.gbooksviewmodel.model.BookSearchResult;
import co.cdmunoz.gbooksviewmodel.model.BookVolumeInfo;
import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class BooksViewModelTest {

  BooksViewModel booksViewModel;
  private String search;

  @Mock BooksAction booksActionMock;
  @Rule public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
  //@Rule public RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();

  @Before public void setUp() {
    RxAndroidPlugins.setInitMainThreadSchedulerHandler(
        schedulerCallable -> Schedulers.trampoline());
    MockitoAnnotations.initMocks(this);
    booksViewModel = new BooksViewModel();
    booksViewModel.booksAction = booksActionMock;
    search = "Mou";
  }

  @Test public void loadBooks() throws InterruptedException {
    MutableLiveData<List<Book>> fakeBooks = getFakeBooks();
    booksViewModel.setBooks(fakeBooks);
    Mockito.when(booksActionMock.getBooks(search)).thenReturn(getObsFakeBooks());

    List<Book> booksReturned = getValue(booksViewModel.getBooks());
    booksActionMock.getBooks(search);
    verify(booksActionMock).getBooks(search);
    assertEquals(1, booksReturned != null ? booksReturned.size() : 0);
    assertEquals("Mou",
        booksReturned != null ? booksReturned.get(0).getVolumeInfo().getTitle() : "Mou");
  }

  @NonNull private MutableLiveData<List<Book>> getFakeBooks() {
    BookVolumeInfo bookInfo = new BookVolumeInfo();
    List<String> authors = new ArrayList<>();
    authors.add("CDMI");
    bookInfo.setTitle("Mou");
    bookInfo.setAuthor(authors);

    Book book = new Book();
    book.setVolumeInfo(bookInfo);

    List<Book> books = new ArrayList<>();
    books.add(book);
    MutableLiveData<List<Book>> fakeBooks = new MutableLiveData<>();
    fakeBooks.setValue(books);
    return fakeBooks;
  }

  @NonNull private Observable<BookSearchResult> getObsFakeBooks() {
    BookVolumeInfo bookInfo = new BookVolumeInfo();
    List<String> authors = new ArrayList<>();
    authors.add("CDMI");
    bookInfo.setTitle("Mou");
    bookInfo.setAuthor(authors);

    Book book = new Book();
    book.setVolumeInfo(bookInfo);

    List<Book> books = new ArrayList<>();
    books.add(book);
    BookSearchResult bookSearchResult = new BookSearchResult();
    bookSearchResult.setBooks(books);
    return Observable.just(bookSearchResult);
  }

  public static <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
    final Object[] data = new Object[1];
    final CountDownLatch latch = new CountDownLatch(1);
    Observer<T> observer = new Observer<T>() {
      @Override public void onChanged(@Nullable T o) {
        data[0] = o;
        latch.countDown();
        liveData.removeObserver(this);
      }
    };
    liveData.observeForever(observer);
    latch.await(2, TimeUnit.SECONDS);
    //noinspection unchecked
    return (T) data[0];
  }

  @After public void tearDownClass() {
    RxAndroidPlugins.reset();
  }
}
