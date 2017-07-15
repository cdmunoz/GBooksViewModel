package co.cdmunoz.gbooksviewmodel.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BookVolumeInfo implements Parcelable {

  @SerializedName("authors") private List<String> author;
  @SerializedName("title") private String title;

  public List<String> getAuthor() {
    return author;
  }

  public void setAuthor(List<String> author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeStringList(this.author);
    dest.writeString(this.title);
  }

  public BookVolumeInfo() {
  }

  protected BookVolumeInfo(Parcel in) {
    this.author = in.createStringArrayList();
    this.title = in.readString();
  }

  public static final Parcelable.Creator<BookVolumeInfo> CREATOR =
      new Parcelable.Creator<BookVolumeInfo>() {
        @Override public BookVolumeInfo createFromParcel(Parcel source) {
          return new BookVolumeInfo(source);
        }

        @Override public BookVolumeInfo[] newArray(int size) {
          return new BookVolumeInfo[size];
        }
      };
}
