package com.zl.shyhttp.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AnalyticalJSON {
    /** 解析得到HashMap。{"":"","":""}*/
    public static HashMap<String, String> getHashMap(String json) {
        HashMap<String, String> item = new HashMap<String, String>();
        if (json!=null){
            try {
                JSONObject json_data = new JSONObject(json);
                Iterator<String> keysIterator = json_data.keys();
                while (keysIterator.hasNext()) {
                    String key = (String) keysIterator.next();
                    item.put(key, json_data.getString(key));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("zhou", "e==" + e.getMessage());
                return null;
            }
        }
        return item;
    }

    /** 解析得到HashMap。{"":"","":""}*/
    public static HashMap<String, Object> getObjHashMap(String json) {
        HashMap<String, Object> item = new HashMap<String, Object>();
        if (json!=null){
            try {
                JSONObject json_data = new JSONObject(json);
                Iterator<String> keysIterator = json_data.keys();
                while (keysIterator.hasNext()) {
                    String key = (String) keysIterator.next();
                    item.put(key, json_data.get(key));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("zhou", "e==" + e.getMessage());
                return null;
            }
        }
        return item;
    }

    /** 解析得到List。{[{"":""},{"":""}]}*/
    public static ArrayList<HashMap<String, String>> getList(String json) {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObj = new JSONObject(json);
            JSONArray result = jsonObj.getJSONArray("commentList");
            if (result == null) {
                return null;
            }
            for (int i = 0; i < result.length(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject json_data = result.getJSONObject(i);
                Iterator<String> keysIterator = json_data.keys();
                while (keysIterator.hasNext()) {
                    String key = (String) keysIterator.next();
                    map.put(key, json_data.getString(key));
                }
                data.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("zhou", "e==" + e.getMessage());
            return null;
        }
        return data;
    }

    /** 解析得到List。[{"":""},{"":""}]*/
    public static ArrayList<HashMap<String, String>> getList_zj(String json) {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        if (json!=null){

            String s=json.replace("\uFEFF","");
            try {
                JSONArray result = new JSONArray(s);
                if (result == null) {
                    Log.i("hrr","解析result="+result.toString());
                    return null;
                }
                for (int i = 0; i < result.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject json_data = result.getJSONObject(i);
                    Log.i("hrr","解析json_datat="+json_data.toString());
                    Iterator<String> keysIterator = json_data.keys();
                    Log.i("hrr","解析keysIterator="+keysIterator.toString());
                    while (keysIterator.hasNext()) {
                        String key = (String) keysIterator.next();
                        Log.i("hrr","解析key="+key);
                        map.put(key, json_data.getString(key));
                    }
                    data.add(map);
                }
                Log.i("hrr","解析data="+data.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("hrr", "e=" + e.getMessage());
                return null;
            }
            return data;
        }else {
            return null;
        }

    }

    /** 解析得到List。["",""]*/
    public static ArrayList<String> getList_string(String json) {
        if ((json!=null)&&(!json.equals(""))){
            ArrayList<String> data = new ArrayList<String>();
            try {
//                JSONObject jsonObject = new JSONObject(json);
                JSONArray result = new JSONArray(json);
                if (result == null) {
                    return null;
                }
                for (int i = 0; i < result.length(); i++) {
                    data.add(result.getString(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("zhou", "e==" + e.getMessage());
                return null;
            }
            return data;
        }else {
            return null;
        }

    }

    /** 解析得到List。["",""]*/
    public static ArrayList<String> getScrollList_string(String json) {
        if ((json!=null)||(!json.equals(""))){
            ArrayList<String> data = new ArrayList<String>();
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray result = jsonObject.getJSONArray("send_packet");
                if (result == null) {
                    return null;
                }
                for (int i = 0; i < result.length(); i++) {
                    data.add(result.getString(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("zhou", "e==" + e.getMessage());
                return null;
            }
            return data;
        }else {
            return null;
        }

    }

    /** 解析得到List。["",""]*/
    public static ArrayList<Integer> getList_int(String json) {
        if ((json!=null)||(!json.equals(""))){
            ArrayList<Integer> data = new ArrayList<Integer>();
            try {
                JSONArray result = new JSONArray(json);
                if (result == null) {
                    return null;
                }
                for (int i = 0; i < result.length(); i++) {
                    data.add(result.getInt(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("zhou", "e==" + e.getMessage());
                return null;
            }
            return data;
        }else {
            return null;
        }

    }

    public static ArrayList<String> getRedList(String json,String ArrayString) {
        if ((json!=null)||(!json.equals(""))){
            ArrayList<String> redList = new ArrayList<String>();
            try {
                JSONObject redObject = new JSONObject(json);
                JSONArray ArrayList = redObject.getJSONArray(ArrayString);
                for (int i = 0; i < ArrayList.length(); i++) {
                    redList.add(ArrayList.get(i).toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("zhou", "e==" + e.getMessage());
                return null;
            }
            return redList;
        }else {
            return null;
        }

    }

    public static ArrayList<String> getDeleteIds(String json) {
        ArrayList<String> ids= new ArrayList<>();
        if (json != null && !json.equals("")){
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = new JSONArray(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ids;
    }
}
