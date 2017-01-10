package dev.studentapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import dev.studentapp.model.dataset.login.LoginDataset;

/**
 * Created by Nirav-Dangi on 30/08/16.
 */
public class PrefData {

    private static long userId;
    private static String email;
    private static long roleId;
    private static String accessToken;
    private static String name;

    //== New Params ===//
    private static String username;
    private static String summary;
    private static String profileType;
    private static String lastName;
    private static String firtName;
    private static String avatarUrl;

    private static final String PREFF_NAME = "studentapp";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFF_NAME, Context.MODE_PRIVATE);
    }

    public static void setUserData(Context context, LoginDataset loginDataset) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong("user_id", loginDataset.getUserId());
        editor.putString("email", loginDataset.getEmail());
        editor.putLong("role_id", loginDataset.getRoleId());
        editor.putString("access_token", loginDataset.getAccessToken());
        editor.putString("name", loginDataset.getName());

        editor.putString("username", loginDataset.getUsername());
        editor.putString("summary", loginDataset.getSummary());
        editor.putString("profile_type", loginDataset.getProfileType());
        editor.putString("last_name", loginDataset.getLastName());
        editor.putString("first_name", loginDataset.getFirtName());
        editor.putString("avatar_url", loginDataset.getAvatarUrl());

        editor.commit();

        updatePreferances(context);
    }

    public static void updatePreferances(Context context) {

        SharedPreferences sharedPreferences = getSharedPreferences(context);

        userId = sharedPreferences.getLong("user_id",-1);
        email = sharedPreferences.getString("email","");
        roleId = sharedPreferences.getLong("role_id",-1);
        accessToken = sharedPreferences.getString("access_token",null);
        name = sharedPreferences.getString("name","");

        username = sharedPreferences.getString("username","");
        summary = sharedPreferences.getString("summary","");
        profileType = sharedPreferences.getString("profile_type","");
        lastName = sharedPreferences.getString("last_name","");
        firtName = sharedPreferences.getString("first_name","");
        avatarUrl = sharedPreferences.getString("avatar_url","");
    }

    public static void clearPrefData(Context context){

        getSharedPreferences(context).edit().clear().commit();

        updatePreferances(context);
    }

    public static long getUserId() {
        return userId;
    }

    public static String getEmail() {
        return email;
    }

    public static long getRoleId() {
        return roleId;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static String getName() {
        return name;
    }

    public static String getUsername() {
        return username;
    }

    public static String getSummary() {
        return summary;
    }

    public static String getProfileType() {
        return profileType;
    }

    public static String getLastName() {
        return lastName;
    }

    public static String getFirtName() {
        return firtName;
    }

    public static String getAvatarUrl() {
        return avatarUrl;
    }
}
