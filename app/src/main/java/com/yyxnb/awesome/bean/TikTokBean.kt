package com.yyxnb.awesome.bean

import java.io.Serializable

data class TikTokBean
(
        var id: Int = 0,
        var videoId: Int = 0,
        var userId: Int = 0,
        var itemId: Int = 0,
        var commentCount: Int = 0,
        var coverUrl: String? = null,
        var videoUrl: String? = null,
        var title: String? = null,
        var state: Int = 0,
        var type: Int = 0,
        var description: Any? = null,
        var likeCount: Int = 0,
        var playCount: Int = 0,
        var createTime: String? = null
) : Serializable {
    /**
     * id : 2
     * videoId : 2
     * userId : 1
     * itemId : 2
     * commentCount : 0
     * coverUrl : http://p3-dy.byteimg.com/large/tos-cn-p-0015/6f888eb380fc4a0084c7bf0a824c61fb_1575445410.jpeg?from=2563711402_large
     * videoUrl : ttp://v6-dy.ixigua.com/eb7e02d4284ff496da8a6ab847001da6/5de7d06e/video/tos/hxsy/tos-hxsy-ve-0015/fb4974d1663b41aba1cef7b66f98118c/?a=1128&br=939&cr=0&cs=0&dr=0&ds=6&er=&l=201912042227280100140431330D4C1DCD&lr=&qs=0&rc=anV0eGV1aDZwcTMzOmkzM0ApOThmZGdlOmRoNzg6aWY8NWc0M2psLm1mNGdfLS0vLS9zcy80YC9iYC1jLzY0XmMwMV86Yw%3D%3D
     * title : 百度地图提醒你，拐不进去使劲拐#重庆重庆
     * state : 0
     * type : 1
     * description : null
     * likeCount : 232
     * playCount : 23
     * createTime : 1575445406000
     */

}