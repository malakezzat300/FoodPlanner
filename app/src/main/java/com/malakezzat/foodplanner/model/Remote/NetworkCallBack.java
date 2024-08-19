package com.malakezzat.foodplanner.model.Remote;

import com.malakezzat.foodplanner.model.data.Data;

import java.util.List;

public interface NetworkCallBack {
    void onSuccessResult(List<? extends Data> listOfItems);
    void onSuccessResult(List<? extends Data> listOfItems,int saveMode);
    void onFailureResult(String msg);
}
