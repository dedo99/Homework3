package lucenex.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListResultQuery {

    private List<ResultQuery> list_result;

    public ListResultQuery(){
        this.list_result = new ArrayList<>();
    }

    public ListResultQuery(List<ResultQuery> list_result){
        this.list_result = list_result;
    }

    public List<ResultQuery> getList_result() {
        return list_result;
    }

    public void setList_result(List<ResultQuery> list_result) {
        this.list_result = list_result;
    }

    public String toJson(){
        Gson gson = new Gson();
        Type fooType = new TypeToken<ListResultQuery>() {}.getType();
        String json = gson.toJson(this,fooType);
        return json;
    }
}
