package org.alextan.android.exits.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Object for the deserialised response to a DreamFactory REST API call.
 */
public class DreamFactoryResource<T> {
    @SerializedName("resource") private List<T> mData;

    public DreamFactoryResource(List<T> data) {
        this.mData = data;
    }

    public List<T> getData() {
        return mData;
    }

    public void setData(List<T> data) {
        this.mData = data;
    }
}
