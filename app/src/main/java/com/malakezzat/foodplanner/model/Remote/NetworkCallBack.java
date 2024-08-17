package com.malakezzat.foodplanner.model.Remote;

import com.malakezzat.foodplanner.model.data.Data;

import java.util.List;

public interface NetworkCallBack {
    void onSuccessResult(List<? extends Data> listOfItems);
    void onFailureResult(String msg);
}
