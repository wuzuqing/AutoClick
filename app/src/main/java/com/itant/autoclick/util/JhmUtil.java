package com.itant.autoclick.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 吴祖清
 * @version $Rev$
 * @createTime 2018/1/27 10:44
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate 2018/1/27$
 * @updateDes ${TODO}
 */

public class JhmUtil
{

    private static Map<String,JhmModel> sJhmModelMap = new HashMap<>();
    public static void init(){
        List<JhmModel > jmhs = new ArrayList<>();
        jmhs.add(new JhmModel(0,"jmh_wpyx_x9991",30));
        jmhs.add(new JhmModel(1,"jmh_wpyx_x3207",60));
        jmhs.add(new JhmModel(2,"jmh_wpyx_x5330",30));
        jmhs.add(new JhmModel(3,"jmh_wpyx_x6788",30));
        jmhs.add(new JhmModel(4,"jmh_wpyx_x6124",30));
        sJhmModelMap.clear();
        for (JhmModel jhm : jmhs) {
            sJhmModelMap.put(jhm.getNumber(),jhm);
        }
    }

    public static boolean check(String jhm){
        return sJhmModelMap.containsKey(jhm);
    }

    public static JhmModel get(String jmh){
        return sJhmModelMap.get(jmh);
    }

    public static class JhmModel{
        private int id;
        private String number;
        private int liveDay;

        public JhmModel(int id, String number, int liveDay) {
            this.id = id;
            this.number = number;
            this.liveDay = liveDay;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getLiveDay() {
            return liveDay;
        }

        public void setLiveDay(int liveDay) {
            this.liveDay = liveDay;
        }
    }
}
