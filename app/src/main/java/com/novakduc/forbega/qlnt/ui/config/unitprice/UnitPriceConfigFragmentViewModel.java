package com.novakduc.forbega.qlnt.ui.config.unitprice;

import com.novakduc.forbega.qlnt.data.ProjectRepo;
import com.novakduc.forbega.qlnt.data.database.UnitPrice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Nguyen Quoc Thanh on 1/30/2018.
 */

public class UnitPriceConfigFragmentViewModel extends ViewModel {
    private ProjectRepo mProjectRepo;
    private LiveData<UnitPrice> mUnitPriceLiveData;

    public UnitPriceConfigFragmentViewModel(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
        mUnitPriceLiveData = mProjectRepo.getUnitPrice();
    }

    public LiveData<UnitPrice> getUnitPriceLiveData() {
        return mUnitPriceLiveData;
    }

    public void updateUnitPrice(UnitPrice unitPrice) {
        mProjectRepo.updateUnitPrice(unitPrice);
    }
}
