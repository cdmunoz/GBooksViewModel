package co.cdmunoz.gbooksviewmodel;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.cdmunoz.gbooksviewmodel.model.Book;
import java.util.List;

public class MainActivity extends LifecycleActivity {

  @BindView(R.id.searchText) EditText searchText;
  @BindView(R.id.searchButton) ImageButton searchButton;
  @BindView(R.id.bookList) RecyclerView bookList;

  private BooksListAdapter adapter;
  private BooksViewModel model;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    adapter = new BooksListAdapter();
    bookList.setLayoutManager(
        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    bookList.setAdapter(adapter);
    searchButton.setOnClickListener(v -> {
      if (isConnectionAvailable()) {
        performSearch();
      } else {
        Toast.makeText(MainActivity.this, R.string.error_no_internet, Toast.LENGTH_SHORT).show();
      }
    });

    model = ViewModelProviders.of(this).get(BooksViewModel.class);
    model.getBooks().observe(this, books -> updateUi(books));
  }

  private void updateUi(List<Book> books) {
    /*if (books.isEmpty()) {
      // if no books found, show a message
      textNoDataFound.setVisibility(View.VISIBLE);
    } else {
      textNoDataFound.setVisibility(View.GONE);
    }*/
    adapter.setBooksList(books);
  }

  private void performSearch() {
    String formatUserInput = getUserInput().trim().replaceAll("\\s+", "+");
    // Just call the method on the GoogleBooksApi
    model.loadBooks(formatUserInput);
  }

  private String getUserInput() {
    return searchText.getText().toString();
  }

  private boolean isConnectionAvailable() {
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    return activeNetwork.isConnectedOrConnecting();
  }
}
