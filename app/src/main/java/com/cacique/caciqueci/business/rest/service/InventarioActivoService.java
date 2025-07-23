package com.cacique.caciqueci.business.rest.service;

import com.cacique.caciqueci.business.models.InventarioActivo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InventarioActivoService {
    @GET("v1/inventario/listaactivos/{tipoInventarioId}")
    Call<List<InventarioActivo>> obtenerInventarioActivo(@Path("tipoInventarioId") String tipoInventarioId);
}
