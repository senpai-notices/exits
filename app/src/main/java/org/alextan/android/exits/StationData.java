package org.alextan.android.exits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class StationData {

    @SerializedName("resource")
    @Expose
    private List<Resource> resource = new ArrayList<Resource>();

    /**
     *
     * @return
     * The resource
     */
    public List<Resource> getResource() {
        return resource;
    }

    /**
     *
     * @param resource
     * The resource
     */
    public void setResource(List<Resource> resource) {
        this.resource = resource;
    }

}