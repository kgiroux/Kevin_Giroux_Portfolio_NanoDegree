package com.giroux.kevin.androidhttprequestlibrairy;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.giroux.kevin.androidhttprequestlibrairy.constants.MethodDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevin on 17/06/2016. KevinGirouxPortfolio
 */

public class AsyncCursor extends AsyncTask<Void,Void,Object> {

    private Context context;
    private ContentValues contentValues;
    private ContentValues[] contentValuesBulkInsert;
    private String[] value;
    private String whereClause;
    private Uri uri;
    private int method;
    private String[] projection;
    private Map<String,Object> objectUI;
    private String sortOrder;


    public void setObjectUI(Map<String, Object> objectUI) {
        this.objectUI = objectUI;
    }

    public void setSortOrder(String sortOrder) {

        this.sortOrder = sortOrder;
    }

    public Map<String, Object> getObjectUI() {
        return objectUI;
    }

    public void setProjection(String[] projection) {

        this.projection = projection;
    }

    public void setContentValuesBulkInsert(ContentValues[] contentValuesBulkInsert) {
        this.contentValuesBulkInsert = contentValuesBulkInsert;
    }


    public AsyncCursor(Context context){
        this.context = context;
        objectUI = new HashMap<>();
    }


    @Override
    protected Object doInBackground(Void... voids) {
        Object toReturn;
        switch (this.method){
            case MethodDatabase.INSERT :
                toReturn = context.getContentResolver().insert(this.uri,contentValues);
                break;
            case MethodDatabase.UPDATE:
                toReturn = context.getContentResolver().update(this.uri,contentValues,whereClause,value);
                break;
            case MethodDatabase.DELETE:
                toReturn = context.getContentResolver().delete(this.uri,whereClause,value);
                break;
            case MethodDatabase.BULK_INSERT:
                toReturn = context.getContentResolver().bulkInsert(this.uri,contentValuesBulkInsert);
                break;
            case MethodDatabase.QUERY:
                toReturn = context.getContentResolver().query(this.uri,projection,whereClause,value,sortOrder);
                break;
            default:
                toReturn = null;
        }


        return toReturn;
    }

    public void setObject(String key, Object object){
        this.objectUI.put(key,object);
    }


    public int getMethod() {
        return method;
    }

    public Context getContext() {

        return context;
    }

    public void setContentValues(ContentValues contentValues) {
        this.contentValues = contentValues;
    }

    public void setValue(String[] value) {
        this.value = value;
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public void setMethod(int method) {
        this.method = method;
    }
}
