package com.cacique.caciqueci.business.rest.service;

import com.cacique.caciqueci.business.models.BulkRequest;
import com.cacique.caciqueci.business.models.wrapper.DataWrapper;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InventoryService {

        @POST("v1/inventory/bulk")
        Call<DataWrapper<String>> bulkData(@Body BulkRequest data);
}
