package com.freeloaers.hikeathon.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class is used for save and retrieve Shared preference data.
 */
public class AppPreferences {
    public static final String MSG_ILLEGAL_ARGS = "The argument should be the application context!";
    public static final String VALUE_NOT_SET = null;
    public static final String TOTAL_NOTIFICATION = "total_notification";
    public static SharedPreferences sharedPreference;

    /**
     * This method should be called at least once to set the SharedPreferences,
     * preferably at application launch. Once called, it need not be called
     * again by subsequent Activities
     *
     * @param applicationContext : Must be the application context and not an Activity context
     */
    public static void initPreferences(Context applicationContext) {
        if (applicationContext instanceof Activity) {
            throw new IllegalArgumentException(MSG_ILLEGAL_ARGS);
        } else if (sharedPreference == null) {
            sharedPreference = applicationContext.getSharedPreferences(
                    Constants.APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        }
    }

    /**
     * Gets the application preference identified by the argument key. Returns
     * null if the specified preference does not exist
     *
     * @param preference string
     * @return Value
     */
    public synchronized static String getPreference(String preference) {
        if (sharedPreference == null) {
            return null;
        }
        return sharedPreference.getString(preference, VALUE_NOT_SET);
    }

//	public synchronized static String getPreferenceString(String preference,
//			String defaultVal) {
//		if (sharedPreference == null) {
//			return null;
//		}
//		return sharedPreference.getString(preference, defaultVal);
//	}

    /**
     * Saves the specified key value pair in the application preferences
     *
     * @param preference
     * @param value
     */
    public synchronized static void setPreference(String preference,
                                                  String value) {
        sharedPreference.edit().putString(preference, value).commit();

    }

    public synchronized static void setBoolPreference(String preference,
                                                      boolean value) {
        sharedPreference.edit().putBoolean(preference, value).commit();

    }

    //
//	public synchronized static void setIntPreference(String preference,
//			int value) {
//		sharedPreference.edit().putInt(preference, value).commit();
//	}
//
//	public static int getIntPreference(String preference) {
//		if (sharedPreference == null) {
//			return -1;
//		}
//		return sharedPreference.getInt(preference, -1);
//
//	}
//
//	public synchronized static void setLongPreference(String preference,
//			Long value) {
//		sharedPreference.edit().putLong(preference, value).commit();
//
//	}
//
//	public static long getLongPreference(String preference) {
//		if (sharedPreference == null) {
//			return -1;
//		}
//		return sharedPreference.getLong(preference, 0);
//
//	}
//
    public static boolean getBoolPreference(String preference) {
        if (sharedPreference == null) {
            return false;
        }
        return sharedPreference.getBoolean(preference, false);

    }

    /**
     * Returns a Map containing all the Applications preferences
     *
     * @return
     */
//	@SuppressWarnings("unchecked")
//	public static Map<String, String> getAllPreferences() {
//		return (Map<String, String>) sharedPreference.getAll();
//	}

    /**
     * Multiple preferences can be saved by passing the key value pairs in a Map
     *
     * @param preferenceMap
     *            Map containing Keys and Values to be stored.
     */
//	public synchronized static void setPreferences(
//			Map<String, String> preferenceMap) {
//		SharedPreferences.Editor editor = sharedPreference.edit();
//		Set<String> preferenceKeySet = preferenceMap.keySet();
//		for (Iterator<String> index = preferenceKeySet.iterator(); index
//				.hasNext();) {
//			String preference = index.next();
//			String value = preferenceMap.get(preference);
//			editor.putString(preference, value);
//		}
//		editor.commit();
//	}

//	public static void removePreferences(Map<String, String> preferenceMap) {
//		SharedPreferences.Editor editor = sharedPreference.edit();
//		Set<String> preferenceKeySet = preferenceMap.keySet();
//		for (Iterator<String> index = preferenceKeySet.iterator(); index
//				.hasNext();) {
//			String preference = index.next();
//			editor.remove(preference);
//		}
//		editor.commit();
//	}
//
//	public synchronized static void removePreference(String key) {
//		if (sharedPreference.contains(key)) {
//			sharedPreference.edit().remove(key).commit();
//		}
//	}
//
//	public static void removeAllPreferences() {
//		sharedPreference.edit().clear().commit();
//	}
//
//	public synchronized static boolean prefContains(String preferences) {
//		return sharedPreference.contains(preferences);
//	}

}
