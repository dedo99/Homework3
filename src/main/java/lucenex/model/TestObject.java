package lucenex.model;

import java.util.List;

public class TestObject {

    private List<String> query;

    private List<String> top5;

    public TestObject(List<String> query, List<String> top5) {
        this.query = query;
        this.top5 = top5;
    }

    public TestObject() {

    }

    public void setQuery(List<String> query){
        this.query = query;
    }

    public void setTop5(List<String> top5) {
        this.top5 = top5;
    }

    public List<String> getQuery() {
        return query;
    }

    public List<String> getTop5() {
        return top5;
    }
}
