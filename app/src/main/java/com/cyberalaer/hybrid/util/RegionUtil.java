package com.cyberalaer.hybrid.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.alaer.lib.api.bean.Region;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

}
