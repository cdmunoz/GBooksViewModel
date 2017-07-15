package co.cdmunoz.gbooksviewmodel;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.cdmunoz.gbooksviewmodel.model.Book;
import co.cdmunoz.gbooksviewmodel.model.BookVolumeInfo;
import java.util.ArrayList;
import java.util.List;

public class BooksListAdapter extends RecyclerView.Adapter<BooksListAdapter.BooksHolder> {

  private List<Book> booksList = new ArrayList<>();

  @Override public BooksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
    return new BooksHolder(itemView);
  }

  @Override public void onBindViewHolder(BooksHolder holder, int position) {
    holder.bindBook(booksList.get(position));
  }

  @Override public int getItemCount() {
    return booksList.size();
  }

  public void setBooksList(List<Book> booksList) {
    this.booksList = booksList;
    notifyDataSetChanged();
  }

  static class BooksHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title) TextView bookTitle;
    @BindView(R.id.author) TextView bookAuthor;

    public BooksHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bindBook(Book book) {
      BookVolumeInfo bookVolumeInfo = book.getVolumeInfo();
      if (null != bookVolumeInfo) {
        bookTitle.setText(bookVolumeInfo.getTitle());
        if (null != bookVolumeInfo.getAuthor()) {
          StringBuilder builder = new StringBuilder();
          for (String author : bookVolumeInfo.getAuthor()) {
            if (builder.length() > 0) {
              builder.append(", ");
            }
            builder.append(author);
          }
          bookAuthor.setText(builder.toString());
        }
      }
    }
  }
}
