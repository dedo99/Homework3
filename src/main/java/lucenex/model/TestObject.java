package lucenex.model;

import java.util.List;

public class TestObject {

    private List<String> query;

    private List<String> top3;

    public TestObject(List<String> query, List<String> top3) {
        this.query = query;
        this.top3 = top3;
    }

    public TestObject() {

    }

    public void setQuery(List<String> query){
        this.query = query;
    }

    public void setTop3(List<String> top3) {
        this.top3 = top3;
    }

    public List<String> getQuery() {
        return query;
    }

    public List<String> getTop3() {
        return top3;
    }
}
