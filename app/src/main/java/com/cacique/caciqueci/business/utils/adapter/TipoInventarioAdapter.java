package com.cacique.caciqueci.business.utils.adapter;

import android.content.Context;

import com.cacique.caciqueci.business.models.TipoInventario;

import java.util.List;

public class TipoInventarioAdapter extends AbstractAdapter<TipoInventario> {

  public TipoInventarioAdapter(
      Context context, int textViewResourceId, List<TipoInventario> data) {
    super(context, textViewResourceId, data);
  }

  @Override
  public String getPlaceHolder() {
    return "SELECCIONE TIPO INVENTARIO";
  }

  @Override
  public String onSelectShowText(TipoInventario obj) {
    return obj.getTipo_inventario();
  }
}
