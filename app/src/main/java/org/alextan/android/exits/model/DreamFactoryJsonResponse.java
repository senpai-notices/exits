package org.alextan.android.exits.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DreamFactoryJsonResponse<T> {
    @SerializedName("resource") private List<T> data;

    public DreamFactoryJsonResponse(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
