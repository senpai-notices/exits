package org.alextan.android.exits.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Object for the deserialised response to a DreamFactory REST API call.
 */
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
