package org.alextan.android.exits.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DreamFactoryResource<T> {
    @SerializedName("resource") private List<T> data;

    public DreamFactoryResource(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
