package co.cdmunoz.gbooksviewmodel.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class Book implements Parcelable {

  @SerializedName("volumeInfo") BookVolumeInfo volumeInfo;

  public BookVolumeInfo getVolumeInfo() {
    return volumeInfo;
  }

  public void setVolumeInfo(BookVolumeInfo volumeInfo) {
    this.volumeInfo = volumeInfo;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(this.volumeInfo, flags);
  }

  public Book() {
  }

  protected Book(Parcel in) {
    this.volumeInfo = in.readParcelable(BookVolumeInfo.class.getClassLoader());
  }

  public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
    @Override public Book createFromParcel(Parcel source) {
      return new Book(source);
    }

    @Override public Book[] newArray(int size) {
      return new Book[size];
    }
  };
}
