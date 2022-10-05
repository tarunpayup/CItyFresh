package com.fwts.cityfreshapp.Model;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PreviousOrdersModel
{
    String dateTime, ppName, ppPrice, ppQty;

    public String getDateTime() {
        return dateTime;
    }

    public String getPpName() {
        return ppName;
    }

    public String getPpPrice() {
        return ppPrice;
    }

    public String getPpQty() {
        return ppQty;
    }

}

