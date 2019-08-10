package com.hackathon.tyroler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<Integer> curr;

    public LiveData<Integer> getCurr() {
        if (curr == null) {
            curr = new MutableLiveData<>();
            initCurr();
        }
        return curr;
    }

    private void initCurr() {
        curr.setValue(1);
    }

    public void increaseCurr() {
        if (curr != null && curr.getValue() != null && curr.getValue() < 3)
            curr.setValue(curr.getValue() + 1);
    }
}
