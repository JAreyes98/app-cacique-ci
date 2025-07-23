package com.cacique.caciqueci.business.rest.service;

import com.cacique.caciqueci.business.models.TipoInventario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TipoInventarioService {
    @GET("v1/inventario/tpo")
    Call<List<TipoInventario>> obtenerTipoInventario();
}
