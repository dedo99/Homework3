package lucenex.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ResultQuery {

    private String name_query;

    private List<ItemResultQuery> list_item;

    public ResultQuery(){
        this.list_item = new ArrayList<>();
    }

    public ResultQuery(String name_query){
        this.name_query = name_query;
        this.list_item = new ArrayList<>();
    }

    public ResultQuery(String name_query, List<ItemResultQuery> list_item){
        this.name_query = name_query;
        this.list_item = list_item;
    }

    public void setName_query(String name_query) {
        this.name_query = name_query;
    }

    public String getName_query() {
        return name_query;
    }

    public void setList_item(List<ItemResultQuery> list_item) {
        this.list_item = list_item;
    }

    public List<ItemResultQuery> getList_item() {
        return list_item;
    }

    public String toJson(){
        Gson gson = new Gson();
        Type fooType = new TypeToken<ResultQuery>() {}.getType();
        String json = gson.toJson(this,fooType);
        return json;
    }
}
