package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.InterfaceBookmark;
import com.example.bookingapproyaljourney.repository.BookmarkRepository;
import com.example.bookingapproyaljourney.response.BookmarkResponse;

public class BookmarkViewModel extends AndroidViewModel {
    private BookmarkRepository bookmarkRepository;

    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<BookmarkResponse> bookmarkResponseMutableLiveData = new MutableLiveData<>();

    public BookmarkViewModel(@NonNull Application application) {
        super(application);
        bookmarkRepository = new BookmarkRepository();
    }

    public void getListBookmarkById(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        bookmarkRepository.getListBookmarkById(id, new InterfaceBookmark() {
            @Override
            public void onResponse(BookmarkResponse bookmarkResponse) {
                mProgressMutableData.postValue(View.GONE);
                bookmarkResponseMutableLiveData.postValue(bookmarkResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }

    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public MutableLiveData<BookmarkResponse> getBookmarkResponseMutableLiveData() {
        return bookmarkResponseMutableLiveData;
    }
}
