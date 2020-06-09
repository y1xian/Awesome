package com.yyxnb.awesome.data

import com.yyxnb.awesome.bean.MainBean
import com.yyxnb.common.AppConfig.getContext
import com.yyxnb.common.log.LogUtils.list
import com.yyxnb.http.utils.GsonUtils.jsonToList
import com.yyxnb.utils.FileUtils
import java.util.*

object DataConfig {
    //    public static final String BASE_URL = "http://192.168.8.103:7879/";
    const val BASE_URL = " http://www.mocky.io/"

    /**
     * 首页数据
     * @return
     */
    var mainBeans: List<MainBean>? = null
        get() {
            if (field == null) {
                val content = FileUtils.parseFile(getContext(), "main_data.json")
                field = jsonToList(content, MainBean::class.java)
            }
            list(field!!)
            return field
        }
        private set

    val dialogList: List<String>
        get() {
            val list: MutableList<String> = ArrayList()
            list.add("loading")
            list.add("提示")
            list.add("输入框")
            list.add("中间列表")
            list.add("中间列表 带选中")
            list.add("底部列表")
            list.add("底部列表 带选中")
            list.add("全屏")
            list.add("底部弹框 注册")
            list.add("评论列表")
            list.add("底部 + vp")
            return list
        }
}