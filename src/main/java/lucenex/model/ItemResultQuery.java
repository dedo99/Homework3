package lucenex.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ItemResultQuery {

    private String table;

    private String column;

    public ItemResultQuery(){}

    public ItemResultQuery(String table, String column){
        this.table = table;
        this.column = column;
    }

    public String getColumn() {
        return column;
    }

    public String getTable() {
        return table;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String toJson(){
        Gson gson = new Gson();
        Type fooType = new TypeToken<ItemResultQuery>() {}.getType();
        String json = gson.toJson(this,fooType);
        return json;
    }
}
