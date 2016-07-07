package com.laith.babylontest.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.laith.babylontest.model.Address;
import com.laith.babylontest.model.Comment;
import com.laith.babylontest.model.Company;
import com.laith.babylontest.model.GeoCoords;
import com.laith.babylontest.model.Post;
import com.laith.babylontest.model.User;

import java.util.ArrayList;

public class BlogDBHelper extends SQLiteOpenHelper implements DBHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BabylonBlog.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_USER =
            "CREATE TABLE " + UserTable.TABLE_NAME + " (" +
                    UserTable._ID + " INTEGER PRIMARY KEY," +
                    UserTable.NAME + TEXT_TYPE + COMMA_SEP +
                    UserTable.USERNAME + TEXT_TYPE + COMMA_SEP +
                    UserTable.EMAIL + TEXT_TYPE + COMMA_SEP +
                    UserTable.STREET + TEXT_TYPE + COMMA_SEP +
                    UserTable.SUITE + TEXT_TYPE + COMMA_SEP +
                    UserTable.CITY + TEXT_TYPE + COMMA_SEP +
                    UserTable.ZIPCODE + TEXT_TYPE + COMMA_SEP +
                    UserTable.LATITUDE + TEXT_TYPE + COMMA_SEP +
                    UserTable.LONGITUDE + TEXT_TYPE + COMMA_SEP +
                    UserTable.PHONE + TEXT_TYPE + COMMA_SEP +
                    UserTable.WEBSITE + TEXT_TYPE + COMMA_SEP +
                    UserTable.COMPANY_NAME + TEXT_TYPE + COMMA_SEP +
                    UserTable.COMPANY_CATCH_PHRASE + TEXT_TYPE + COMMA_SEP +
                    UserTable.COMPANY_BUSINESS + TEXT_TYPE + " )";

    private static final String SQL_DELETE_USER =
            "DROP TABLE IF EXISTS " + UserTable.TABLE_NAME;

    private static final String SQL_CREATE_POST =
            "CREATE TABLE " + PostTable.TABLE_NAME + " (" +
                    PostTable._POST_ID + " INTEGER PRIMARY KEY," +
                    PostTable.USER_ID + " INTEGER," +
                    PostTable.TITLE + TEXT_TYPE + COMMA_SEP +
                    PostTable.BODY + TEXT_TYPE + COMMA_SEP +
                    "FOREIGN KEY(" + PostTable.USER_ID + ") REFERENCES " + UserTable.TABLE_NAME
                    + "(" + UserTable._ID + "))";

    private static final String SQL_DELETE_POST =
            "DROP TABLE IF EXISTS " + PostTable.TABLE_NAME;

    private static final String SQL_CREATE_COMMENT =
            "CREATE TABLE " + CommentTable.TABLE_NAME + " (" +
                    CommentTable._COMMENT_ID + " INTEGER PRIMARY KEY," +
                    CommentTable.POST_ID + " INTEGER," +
                    CommentTable.NAME + TEXT_TYPE + COMMA_SEP +
                    CommentTable.EMAIL + TEXT_TYPE + COMMA_SEP +
                    CommentTable.BODY + TEXT_TYPE + COMMA_SEP +
                    "FOREIGN KEY(" + CommentTable.POST_ID + ") REFERENCES " + PostTable.TABLE_NAME
                    + "(" + PostTable._POST_ID + "))";

    private static final String SQL_DELETE_COMMENT =
            "DROP TABLE IF EXISTS " + CommentTable.TABLE_NAME;


    public BlogDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_POST);
        db.execSQL(SQL_CREATE_COMMENT);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_USER);
        db.execSQL(SQL_DELETE_POST);
        db.execSQL(SQL_DELETE_COMMENT);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void updateUsers(ArrayList<User> users) {
        for (User user : users) {
            addUser(user);
        }
    }

    private void addUser(User user) {

        if (!isUserAdded(user.getId())) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(UserTable._ID, user.getId());
            values.put(UserTable.USERNAME, user.getUsername());
            values.put(UserTable.NAME, user.getName());
            values.put(UserTable.EMAIL, user.getEmail());

            values.put(UserTable.PHONE, user.getPhone());
            values.put(UserTable.WEBSITE, user.getWebsite());

            if (user.getAddress() != null) {
                Address address = user.getAddress();
                values.put(UserTable.STREET, address.getStreet());
                values.put(UserTable.SUITE, address.getSuite());
                values.put(UserTable.CITY, address.getCity());

                if (address.getGeo() != null) {
                    GeoCoords geoCoords = address.getGeo();
                    values.put(UserTable.LATITUDE, geoCoords.getLat());
                    values.put(UserTable.LONGITUDE, geoCoords.getLng());
                }
            }

            if (user.getCompany() != null) {
                Company company = user.getCompany();
                values.put(UserTable.COMPANY_NAME, company.getName());
                values.put(UserTable.COMPANY_CATCH_PHRASE, company.getCatchPhrase());
                values.put(UserTable.COMPANY_BUSINESS, company.getBusiness());
            }

            db.insert(UserTable.TABLE_NAME, null, values);
            db.close();
        }
    }

    private boolean isUserAdded(int userID) {
        String sql = "SELECT " + UserTable._ID + " FROM " + UserTable.TABLE_NAME + " WHERE " + UserTable._ID + "=" + userID;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public void updatePosts(ArrayList<Post> posts) {
        for (Post post : posts) {
            addPost(post);
        }
    }

    private void addPost(Post post) {
        if (!isPostAdded(post.getId())) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PostTable._POST_ID, post.getId());
            values.put(PostTable.USER_ID, post.getUserId());
            values.put(PostTable.TITLE, post.getTitle());
            values.put(PostTable.BODY, post.getBody());

            db.insert(PostTable.TABLE_NAME, null, values);
            db.close();
        }
    }

    private boolean isPostAdded(int postID) {
        String sql = "SELECT " + PostTable._POST_ID + " FROM " + PostTable.TABLE_NAME + " WHERE " + PostTable._POST_ID + "=" + postID;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public ArrayList<Post> getAllPosts() {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                PostTable._POST_ID,
                PostTable.USER_ID,
                PostTable.TITLE,
                PostTable.BODY
        };

        String sortOrder =
                PostTable._POST_ID + " ASC";

        Cursor c = db.query(
                PostTable.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        ArrayList<Post> posts = new ArrayList<>();
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Post post = new Post();
            post.setId(c.getInt(0));
            post.setUserId(c.getInt(1));
            post.setTitle(c.getString(2));
            post.setBody(c.getString(3));

            posts.add(post);
            c.moveToNext();
        }
        c.close();
        return posts;
    }

    @Override
    public ArrayList<Comment> getCommentsByPostId(int postId) {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                CommentTable.NAME,
                CommentTable.EMAIL,
                CommentTable.BODY,
        };

        String sortOrder = CommentTable._COMMENT_ID + " DESC";
        String whereClause = CommentTable.POST_ID + " = ?";

        Cursor c = db.query(
                CommentTable.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                whereClause,                              // The columns for the WHERE clause
                new String[]{Integer.toString(postId)},   // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        ArrayList<Comment> comments = new ArrayList<>();
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Comment comment = new Comment();
            comment.setName(c.getString(0));
            comment.setEmail(c.getString(1));
            comment.setBody(c.getString(2));

            comments.add(comment);
            c.moveToNext();
        }
        c.close();
        return comments;
    }

    @Override
    public void updateComments(ArrayList<Comment> comments) {
        for (Comment comment : comments) {
            addComment(comment);
        }
    }

    @Override
    public User getBriefUserInfo(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                UserTable.NAME,
                UserTable.EMAIL
        };

        String whereClause = UserTable._ID + " = ?";

        Cursor c = db.query(
                UserTable.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                whereClause,                              // The columns for the WHERE clause
                new String[]{Integer.toString(userID)},  // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        User user = null;
        if (c != null) {
            c.moveToFirst();
            user = new User();
            user.setName(c.getString(0));
            user.setEmail(c.getString(1));
            c.close();
        }

        return user;
    }

    private void addComment(Comment comment) {
        if (!isCommentAdded(comment.getId())) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CommentTable._COMMENT_ID, comment.getId());
            values.put(CommentTable.POST_ID, comment.getPostId());
            values.put(CommentTable.NAME, comment.getName());
            values.put(CommentTable.EMAIL, comment.getEmail());
            values.put(CommentTable.BODY, comment.getBody());

            db.insert(CommentTable.TABLE_NAME, null, values);
            db.close();
        }
    }

    private boolean isCommentAdded(int commentID) {
        String sql = "SELECT " + CommentTable._COMMENT_ID + " FROM " + CommentTable.TABLE_NAME + " WHERE " + CommentTable._COMMENT_ID + "=" + commentID;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

}
