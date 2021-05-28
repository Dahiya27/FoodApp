package com.example.android.foodapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddDishViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AddDishViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is AddDish fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}