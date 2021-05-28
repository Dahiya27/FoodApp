package com.example.android.foodapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewOrderViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ViewOrderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ViewOrder fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
