package com.example.apothecary.views.view.pharmacy;

import com.example.apothecary.Utils;
import com.example.apothecary.models.brands.Brands;
import com.example.apothecary.models.pharmacies.Pharmacy;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PharmacyPresenter {
    PharmacyView view;

    public PharmacyPresenter(PharmacyView view) {
        this.view = view;
    }


    public void getPharmacies(double latitude , double longitude){

        view.showLoading();
        Call<Pharmacy> pharmacyCall = Utils.getApothecaryApi().getPharmacies(latitude,longitude);
        pharmacyCall.enqueue(new Callback<Pharmacy>() {
            @Override
            public void onResponse(@NonNull Call<Pharmacy> call, Response<Pharmacy> response) {
                view.hideLoading();
                if(response.isSuccessful() && response.body() != null){

                    view.setPharmacies(response.body());
                } else {
                    view.onErrorLoading(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<Pharmacy> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

    public void getUpdatedPharmacies(String Url){

        if(Url==null || Url.isEmpty() || Url.trim().isEmpty()){
            return;
        }

        view.showUpdatingLoading();
        Call<Pharmacy> pharmacyCall = Utils.getApothecaryApi().getPharmacy(Url);
        pharmacyCall.enqueue(new Callback<Pharmacy>() {
            @Override
            public void onResponse(@NonNull Call<Pharmacy> call, Response<Pharmacy> response) {
                view.hideUpdatingLoading();
                if(response.isSuccessful() && response.body() != null){
                    view.setUpdatedPharmacies(response.body());
                } else {
                    view.onErrorLoading(response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<Pharmacy> call, Throwable t) {
                view.hideUpdatingLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

}
