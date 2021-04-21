package com.cyberalaer.hybrid.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.alaer.lib.api.bean.Province;
import com.alaer.lib.api.bean.Region;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import likly.dollar.$;

public class RegionUtil {

    private final String regionFile = "region.json";

    public List<Region> parseRegions(Context context) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open(regionFile), "UTF-8");
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            inputStreamReader.close();

            final String json = builder.toString();
            if (!TextUtils.isEmpty(json)) {
                return $.json().toList(json, Region.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Province.Entity> parseAreas(Context context) {
        List<Province.Entity> list = new ArrayList<>();
        try {
            AssetManager assetManager = context.getAssets();
            InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open("areas"), "UTF-8");
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
                String[] values = line.split("\t");

                Province.Entity entity = new Province.Entity();
                entity.name = values[0];
//                entity.codeString = values[1];
                entity.code = Integer.valueOf(values[1]);
                list.add(entity);
//                if (Integer.valueOf(values[1]) % 10000 == 0) {
//                    Log.e("Areas", values[0] + " " + values[1] + "\n");
//                }
            }
            br.close();
            inputStreamReader.close();

            final String json = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

//    RegionUtil util = new RegionUtil();
//    List<Province.Entity> list = util.parseAreas(getApplicationContext());
//
//    List<Province> provinces = new ArrayList<>();
//        for (Province.Entity entity : list) {
//        if (entity.code % 10000 == 0) {
//            Province province = new Province();
//            province.name = entity.name;
//            province.code = entity.code;
//            provinces.add(province);
//        }
//    }
//
//        for (Province province : provinces) {
//        for (Province.Entity entity : list) {
//            if ((TextUtils.equals(entity.codeString.substring(0, 2), String.valueOf(province.code).substring(0, 2))
//                    && entity.code % 100 == 0 && entity.code % 10000 != 0)
//                    || (entity.code == 810000 && province.code == 810000)
//                    || (entity.code == 820000 && province.code == 820000)) {
//                Province.City city = new Province.City();
//                city.name = entity.name;
//                city.code = entity.code;
//                province.citys.add(city);
//            }
//        }
//    }
//
//        for (Province.Entity entity : list) {
//        for (Province province : provinces) {
//            for (Province.City city : province.citys) {
//                if (TextUtils.equals(entity.codeString.substring(0, 4), String.valueOf(city.code).substring(0, 4))
//                        && entity.code % 100 != 0) {
//                    Province.Region region = new Province.Region();
//                    region.name = entity.name;
//                    region.code = entity.code;
//                    city.regions.add(region);
//                }
//            }
//        }
//    }
//
//    String json = $.json().toJson(provinces);
//        System.out.println("");

}
