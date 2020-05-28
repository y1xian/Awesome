package com.yyxnb.awesome.bean;

import java.io.Serializable;
import java.util.List;

public class BaseListData<T> extends BaseData<T> implements Serializable {

    public List<T> list;

}
