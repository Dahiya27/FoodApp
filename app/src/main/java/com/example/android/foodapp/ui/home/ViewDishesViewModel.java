package com.example.android.foodapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewDishesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ViewDishesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ViewDish fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
