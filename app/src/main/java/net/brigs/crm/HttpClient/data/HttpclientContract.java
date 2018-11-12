package net.brigs.crm.HttpClient.data;

import android.provider.BaseColumns;

public final class HttpclientContract {
    public HttpclientContract() {
    }

    public static final class LoginData {
        public final static String TABLE_NAME = "loginUserInfo";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_SUCCESS = "success";
        public final static String COLUMN_HASH = "hash";
        public final static String COLUMN_USER_ID = "userId";
    }
}
