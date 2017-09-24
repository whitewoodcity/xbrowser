package com.whitewoodcity.core.api;

import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.LinkedHashSet;
import java.util.Set;

public class JsApi {

    private Parent parent;

    public JsApi(Parent parent) {
        this.parent = parent;
    }


    private boolean isEmpty(String str){
        return str==null||str.length()==0;
    }

    public Node findView(String selector){
        if(isEmpty(selector)){
            return null;
        }
        return parent.lookup(selector);
    }

    public Set<Node> findViews(String selector){
        if(isEmpty(selector)){
            return new LinkedHashSet<>();
        }
        return parent.lookupAll(selector);
    }
}
