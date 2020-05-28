package com.yyxnb.awesome.bean;

import java.io.Serializable;
import java.util.List;

public class TikTokBean1 implements Serializable {

//
//    /**
//     * status : 200
//     * message : 成功
//     * data : {"list":[{"id":2,"videoId":2,"userId":1,"itemId":2,"commentCount":0,"coverUrl":"http://p3-dy.byteimg.com/large/tos-cn-p-0015/6f888eb380fc4a0084c7bf0a824c61fb_1575445410.jpeg?from=2563711402_large","videoUrl":"ttp://v6-dy.ixigua.com/eb7e02d4284ff496da8a6ab847001da6/5de7d06e/video/tos/hxsy/tos-hxsy-ve-0015/fb4974d1663b41aba1cef7b66f98118c/?a=1128&br=939&cr=0&cs=0&dr=0&ds=6&er=&l=201912042227280100140431330D4C1DCD&lr=&qs=0&rc=anV0eGV1aDZwcTMzOmkzM0ApOThmZGdlOmRoNzg6aWY8NWc0M2psLm1mNGdfLS0vLS9zcy80YC9iYC1jLzY0XmMwMV86Yw%3D%3D","title":"百度地图提醒你，拐不进去使劲拐#重庆重庆","state":0,"type":1,"description":null,"likeCount":232,"playCount":23,"createTime":"1575445406000"},{"id":1,"videoId":1,"userId":1,"itemId":1,"commentCount":0,"coverUrl":"https://pic.vjshi.com/2019-09-30/d5339fa89e17fb16a58befb05fdb835c/online/puzzle.jpg?x-oss-process=style/resize_w_720","videoUrl":"https://mp4.vjshi.com/2019-09-30/d5339fa89e17fb16a58befb05fdb835c.mp4","title":"测试","state":0,"type":1,"description":"测试测 ","likeCount":412,"playCount":231,"createTime":"1575456795002"}]}
//     */
//
//    public int status;
//    public String message;
//    public DataBean data;
//
//    public static class DataBean {
        public List<ListBean> list;

        public static class ListBean {
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

            public int id;
            public int videoId;
            public int userId;
            public int itemId;
            public int commentCount;
            public String coverUrl;
            public String videoUrl;
            public String title;
            public int state;
            public int type;
            public Object description;
            public int likeCount;
            public int playCount;
            public String createTime;
        }
//    }
}
