package com.fc.vedio.base;

import java.io.Serializable;

/**
 * @author 范超 on 2017/9/7
 */

public class BaseModel<T> implements Serializable {
    public int code;
    public String message;
    public T data;

    public boolean success(){
        return code == 0;
    }
}
