package webserviceapi.model.response;

import webserviceapi.shared.dto.Node;

import java.util.List;

public class NodeCoordResponseModel {
    private List<Node> lst;

    public List<Node> getLst() {
        return lst;
    }

    public void setLst(List<Node> lst) {
        this.lst = lst;
    }
}
