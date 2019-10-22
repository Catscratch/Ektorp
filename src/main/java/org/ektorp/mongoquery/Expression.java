package org.ektorp.mongoquery;

import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Expression implements Serializable {

    private String left;
    private Serializable right;

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public Serializable getRight() {
        return right;
    }

    public void setRight(Serializable right) {
        this.right = right;
    }

    @JsonValue
    public Map<String, Serializable> toJson() {
        Map<String, Serializable> result = new HashMap<>();
        result.put(getLeft(), getRight());
        return result;
    }
}
