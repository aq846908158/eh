package cn.lcvc.uitl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonResult {

    private String errorCode;

    private String message;
    private Map<Object, Object> item = new HashMap<Object, Object>();
    private List list=new ArrayList();
    public JsonResult(){

    }
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<Object, Object> getItem() {
        return item;
    }

    public void setItem(Map<Object, Object> item) {
        this.item = item;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
