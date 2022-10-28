package com.example.bookingapproyaljourney.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository categoryRepository;

    private LiveData<List<Category>> categoryResponseLiveData;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository();
        categoryResponseLiveData = categoryRepository.getCategory();
    }

    public LiveData<List<Category>> getListCategory() {
        return categoryResponseLiveData;
    }

}
