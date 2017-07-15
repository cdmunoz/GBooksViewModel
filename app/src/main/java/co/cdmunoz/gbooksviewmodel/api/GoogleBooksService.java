package co.cdmunoz.gbooksviewmodel.api;

import co.cdmunoz.gbooksviewmodel.BuildConfig;
import co.cdmunoz.gbooksviewmodel.model.BookSearchResult;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public enum GoogleBooksService {

  INSTANCE;
  private final GoogleBooksApi googleBooksApi;

  GoogleBooksService() {
    OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
    Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    googleBooksApi = retrofit.create(GoogleBooksApi.class);
  }

  public Observable<BookSearchResult> search(String search) {
    return googleBooksApi.search(search);
  }
}
