package com.example.bibliotecavirtual.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.bibliotecavirtual.Models.DocumentClass;
import com.example.bibliotecavirtual.Models.TemaClass;
import com.example.bibliotecavirtual.Models.UserClass;
import com.example.bibliotecavirtual.Models.UsersClass;

import java.io.File;
import java.util.ArrayList;

public class SqliteClass {
    public DatabaseHelper databasehelp;
    private static SqliteClass SqliteInstance = null;

    private SqliteClass(Context context) {
        databasehelp = new DatabaseHelper(context);
    }

    public static SqliteClass getInstance(Context context){
        if(SqliteInstance == null){
            SqliteInstance = new SqliteClass(context);
        }
        return SqliteInstance;
    }

    public class DatabaseHelper extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "app_easy_note.db";

        /*TABLE_APP_USER*/
        public static final String TABLE_APP_USER = "user";
        private static final String KEY_USEID = "id";
        private static final String KEY_USEIDDOC = "idUser";
        private static final String KEY_USEUSE = "userName";
        private static final String KEY_USEEMA = "correo";
        private static final String KEY_USEPAS = "contraseña";
        private static final String KEY_USECODUNI = "codUniversidad";
        private static final String KEY_USEACT = "active";

        /*TABLE_APP_USERS*/
        public static final String TABLE_APP_USERS = "users";
        private static final String KEY_USERSIDDOC = "idUser";
        private static final String KEY_USERSUSE = "userName";

        /*TABLE_APP_UTEMA*/
        public static final String TABLE_APP_TEMAS = "temas";
        private static final String KEY_TEMCOD = "idTema";
        private static final String KEY_TEMNAM = "nombre";

        /*TABLE_APP_DOCUMENT*/
        private static final String TABLE_APP_DOCUMENT = "documentClass";
        //private static final String KEY_DOCID ="id";
        private static final String KEY_DOCIDDOC ="idDoc";
        private static final String KEY_DOCNAM ="nombre";
        private static final String KEY_DOCCONT ="contador";
        private static final String KEY_DOCFECH ="fecha";
        private static final String KEY_DOCCODTEM ="codTema";
        private static final String KEY_DOCCODUSE ="codUsuario";
        private static final String KEY_DOCARC ="archivo";


        /*@SQL*/
        public AppUserSql usersql;
        public AppDocumentSql documentsql;
        public AppUsersSql userssql;
        public AppTemaSql temasql;

        public Context context;
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            usersql = new AppUserSql();
            documentsql = new AppDocumentSql();
            userssql = new AppUsersSql();
            temasql = new AppTemaSql();

            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            /*@TABLE_USER*/
            String CREATE_TABLE_USER = " CREATE TABLE " + TABLE_APP_USER + "("
                    + KEY_USEID + " INTEGER PRIMARY KEY," +KEY_USEIDDOC + " TEXT," + KEY_USEUSE + " TEXT,"+ KEY_USEEMA + " TEXT,"
                    + KEY_USEPAS + " TEXT," + KEY_USECODUNI + " TEXT," + KEY_USEACT + " TEXT)";

            /*@TABLE_USERS*/
            String CREATE_TABLE_USERS = " CREATE TABLE " + TABLE_APP_USERS + "("
                    + KEY_USERSIDDOC + " STRING PRIMARY KEY," +KEY_USERSUSE + " TEXT)";

            /*@TABLE_TEMAS*/
            String CREATE_TABLE_TEMA = " CREATE TABLE " + TABLE_APP_TEMAS + "("
                    + KEY_TEMCOD + " STRING PRIMARY KEY," +KEY_TEMNAM + " TEXT)";

            /*@TABLE_DOCUMENT*/
            String CREATE_TABLE_DOCUMENT = " CREATE TABLE " + TABLE_APP_DOCUMENT + "("
                    + KEY_DOCIDDOC + " STRING PRIMARY KEY," +  KEY_DOCNAM + " TEXT," + KEY_DOCCONT + " TEXT,"
                    + KEY_DOCFECH + " TEXT," + KEY_DOCCODTEM + " TEXT," + KEY_DOCCODUSE + " TEXT," + KEY_DOCARC +" TEXT)";

            /*@EXECSQL_CREATE*/
            db.execSQL(CREATE_TABLE_USER);
            db.execSQL(CREATE_TABLE_DOCUMENT);
            db.execSQL(CREATE_TABLE_USERS);
            db.execSQL(CREATE_TABLE_TEMA);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_DOCUMENT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_TEMAS);
            onCreate(db);
        }

        public boolean checkDataBase(){
            File dbFile = new File(context.getDatabasePath(DATABASE_NAME).toString());
            return dbFile.exists();
        }

        public void deleteDataBase(){
            context.deleteDatabase(DATABASE_NAME);
        }

        /*@CLASS_USERSQL*/
        public class AppUserSql{
            public AppUserSql(){}
            public void deleteUser(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_USER,null,null);
                db.close();
            }

            public String getActive() {
                String result = "";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor mCount= db.rawQuery("SELECT active FROM " + TABLE_APP_USER ,null);
                mCount.moveToFirst();
                result= mCount.getString(0);
                db.close();
                return result;
            }

            public void updateActive(String pid) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("active", "0");
                db.update(TABLE_APP_USER, values, KEY_USEID + " = ?",new String[] { pid });
                db.close();
            }

            public void addUser (UserClass user){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_USEID, user.getId());
                values.put(KEY_USEIDDOC, user.getIdUser());
                values.put(KEY_USEUSE, user.getUserName());
                values.put(KEY_USEEMA, user.getCorreo());
                values.put(KEY_USEPAS, user.getContraseña());
                values.put(KEY_USECODUNI, user.getCodUniversidad());
                values.put(KEY_USEACT, "1");
                db.insert(TABLE_APP_USER, null, values);
                db.close();
            }
            public int updateUser(UserClass user) {
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_USEUSE, user.getUserName());
                int value = db.update(TABLE_APP_USER, values, KEY_USEUSE + " = ?",new String[] { String.valueOf(user.getUserName())});
                db.close();
                return value;
            }

            public boolean isRegisterUser(String sName, String sPassword) {
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_USER, new String[] { KEY_USEID,KEY_USEIDDOC , KEY_USEUSE, KEY_USEEMA, KEY_USEPAS
                                ,KEY_USECODUNI}, KEY_USEUSE + "=?" + " and "+ KEY_USEPAS + "=?",
                        new String[] {sName, sPassword}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                if(cursor.getCount() == 1){
                    db.close();
                    return true;
                }else{
                    db.close();
                    return false;
                }
            }

            public UserClass getUser (int id){
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor =  db.query(TABLE_APP_USER, new String[] { KEY_USEID,KEY_USEIDDOC , KEY_USEUSE, KEY_USEEMA, KEY_USEPAS
                                ,KEY_USECODUNI}, KEY_USEID + "=?",
                        new String[] { String.valueOf(id) }, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                UserClass user = new UserClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),cursor.getString(5));
                db.close();
                return user;
            }

            public ArrayList<UserClass> getUserData() {
                ArrayList<UserClass> userList = new ArrayList<UserClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_USER + " LIMIT 1";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        UserClass user = new UserClass();
                        user.setId(cursor.getInt(cursor.getColumnIndex(KEY_USEID)));
                        user.setIdUser(cursor.getString(cursor.getColumnIndex(KEY_USEIDDOC)));
                        user.setUserName(cursor.getString(cursor.getColumnIndex(KEY_USEUSE)));
                        user.setCorreo(cursor.getString(cursor.getColumnIndex(KEY_USEEMA)));
                        user.setContraseña(cursor.getString(cursor.getColumnIndex(KEY_USEPAS)));
                        user.setCodUniversidad(cursor.getString(cursor.getColumnIndex(KEY_USECODUNI)));
                        userList.add(user);
                    } while (cursor.moveToNext());
                }
                db.close();
                return userList;
            }

            public String getId(String sName, String sPassword) {
                String _id;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_USER, new String[] { KEY_USEID,KEY_USEIDDOC , KEY_USEUSE, KEY_USEEMA, KEY_USEPAS
                                ,KEY_USECODUNI}, KEY_USEUSE + "=?" + " and "+ KEY_USEPAS + "=?",
                        new String[] {sName, sPassword}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _id = cursor.getString(0);
                db.close();
                return _id;
            }


            public String getID() {
                String selectQuery = "SELECT idUser FROM " + TABLE_APP_USER ;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                String nombre = "";
                cursor.moveToFirst();
                nombre=cursor.getString(0);
                db.close();
                return nombre;
            }


            public String getData(int nField, String sName) {
                String _date = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_USER, new String[] {KEY_USEID,KEY_USEIDDOC , KEY_USEUSE, KEY_USEEMA, KEY_USEPAS
                                ,KEY_USECODUNI}, KEY_USEUSE + "=?",
                        new String[] {sName}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _date = cursor.getString(nField);
                db.close();
                return _date;
            }
            public String getDate(String sName) {
                String _date = null;
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_USER, new String[] {KEY_USEID,KEY_USEIDDOC , KEY_USEUSE, KEY_USEEMA, KEY_USEPAS
                                ,KEY_USECODUNI}, KEY_USEUSE + "=?",
                        new String[] {sName}, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                _date = cursor.getString(4);
                db.close();
                return _date;
            }

        }
        /*@CLASS_DOCUMENTSQL*/
        public class AppDocumentSql{
            public AppDocumentSql(){ }
            public void deleteOrder(){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                db.delete(TABLE_APP_DOCUMENT,null,null);
                db.close();
            }

            public void addDocument(DocumentClass documentClass){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_DOCIDDOC,documentClass.getIdDoc());
                values.put(KEY_DOCNAM,documentClass.getNombre());
                values.put(KEY_DOCCONT,documentClass.getContador());
                values.put(KEY_DOCFECH,documentClass.getFecha());
                values.put(KEY_DOCCODTEM,documentClass.getCodTema());
                values.put(KEY_DOCCODUSE,documentClass.getCodUsuario());
                values.put(KEY_DOCARC,documentClass.getArchivo());
                db.insert(TABLE_APP_DOCUMENT, null, values);
                db.close();
            }

            public void updateContador(String idDoc, int cont){
                cont= cont+1;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_DOCCONT, cont);
                db.update(TABLE_APP_DOCUMENT, values, KEY_DOCIDDOC + " = ?",new String[] { idDoc});
                db.close();
            }

            public int getContador(String idDoc) {
                String selectQuery = "SELECT contador FROM " + TABLE_APP_DOCUMENT + " WHERE idDoc = '" + idDoc + "'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                int nombre ;
                cursor.moveToFirst();
                nombre=cursor.getInt(0);
                db.close();
                return nombre;
            }

            public ArrayList<DocumentClass> getContadorUser(String codUsuario) {
                ArrayList<DocumentClass> orderClassList = new ArrayList<DocumentClass>();
                String selectQuery = "SELECT * FROM " + TABLE_APP_DOCUMENT + " WHERE codUsuario = '" + codUsuario + "'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()){
                    do {
                        DocumentClass item = new DocumentClass();
                        //item.setId(cursor.getInt(cursor.getColumnIndex(KEY_DOCID)));
                        item.setIdDoc(cursor.getString(cursor.getColumnIndex(KEY_DOCIDDOC)));
                        item.setNombre(cursor.getString(cursor.getColumnIndex(KEY_DOCNAM)));
                        item.setContador(cursor.getInt(cursor.getColumnIndex(KEY_DOCCONT)));
                        item.setFecha(cursor.getString(cursor.getColumnIndex(KEY_DOCFECH)));
                        item.setCodTema(cursor.getString(cursor.getColumnIndex(KEY_DOCCODTEM)));
                        item.setCodUsuario(cursor.getString(cursor.getColumnIndex(KEY_DOCCODUSE)));
                        item.setArchivo(cursor.getString(cursor.getColumnIndex(KEY_DOCARC)));
                        orderClassList.add(item);
                    } while (cursor.moveToNext());
                }
                db.close();
                return orderClassList;
            }

            public ArrayList<DocumentClass> getAllItem(){
                ArrayList<DocumentClass> orderClassList = new ArrayList<DocumentClass>();
                String selectQuery = "SELECT  * FROM " + TABLE_APP_DOCUMENT;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()){
                    do {
                        DocumentClass item = new DocumentClass();
                        //item.setId(cursor.getInt(cursor.getColumnIndex(KEY_DOCID)));
                        item.setIdDoc(cursor.getString(cursor.getColumnIndex(KEY_DOCIDDOC)));
                        item.setNombre(cursor.getString(cursor.getColumnIndex(KEY_DOCNAM)));
                        item.setContador(cursor.getInt(cursor.getColumnIndex(KEY_DOCCONT)));
                        item.setFecha(cursor.getString(cursor.getColumnIndex(KEY_DOCFECH)));
                        item.setCodTema(cursor.getString(cursor.getColumnIndex(KEY_DOCCODTEM)));
                        item.setCodUsuario(cursor.getString(cursor.getColumnIndex(KEY_DOCCODUSE)));
                        item.setArchivo(cursor.getString(cursor.getColumnIndex(KEY_DOCARC)));
                        orderClassList.add(item);
                    } while (cursor.moveToNext());
                }
                db.close();
                return orderClassList;
            }
            public DocumentClass getDocument (String nId){
                SQLiteDatabase db = databasehelp.getReadableDatabase();
                Cursor cursor = db.query(TABLE_APP_DOCUMENT, new String[] {
                                KEY_DOCIDDOC, KEY_DOCNAM, KEY_DOCCONT,KEY_DOCFECH,KEY_DOCCODTEM,KEY_DOCCODUSE
                        ,KEY_DOCARC}, KEY_DOCIDDOC + "=?",
                        new String[] { String.valueOf(nId) }, null, null, null, null);
                if (cursor != null){
                    cursor.moveToFirst();
                }
                DocumentClass documentClass = new DocumentClass(cursor.getString(0), cursor.getString(1), cursor.getInt(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
                db.close();
                return documentClass;
            }
        }
        /*@CLASS_USERSSQL*/
        public class AppUsersSql{
            public void addUsers(UsersClass usersClass){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_USERSIDDOC,usersClass.getIdUser());
                values.put(KEY_USERSUSE,usersClass.getUserName());
                db.insert(TABLE_APP_USERS, null, values);
                db.close();
            }

            public String getNameUser(String idUser) {
                String selectQuery = "SELECT userName FROM " + TABLE_APP_USERS + " WHERE idUser = '" + idUser + "'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                String nombre = "";
                cursor.moveToFirst();
                nombre=cursor.getString(0);
                db.close();
                return nombre;
            }


            public ArrayList<UsersClass> getAllItem(){
                ArrayList<UsersClass> usersClassList = new ArrayList<UsersClass>();
                String selectQuery = "SELECT  * FROM " + TABLE_APP_USERS;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()){
                    do {
                        UsersClass item = new UsersClass();
                        item.setIdUser(cursor.getString(cursor.getColumnIndex(KEY_USERSIDDOC)));
                        item.setUserName(cursor.getString(cursor.getColumnIndex(KEY_USERSUSE)));
                        usersClassList.add(item);
                    } while (cursor.moveToNext());
                }
                db.close();
                return usersClassList;
            }
        }

        /*@CLASS_TEMASSQL*/
        public class AppTemaSql{
            public void addTemas(TemaClass temaClass){
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_TEMCOD,temaClass.getIdTema());
                values.put(KEY_TEMNAM,temaClass.getNombre());
                db.insert(TABLE_APP_TEMAS, null, values);
                db.close();
            }
            public String getNameTem(String idTem) {
                String selectQuery = "SELECT nombre FROM " + TABLE_APP_TEMAS + " WHERE idTema = '" + idTem + "'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                String nombre = "";
                cursor.moveToFirst();
                nombre=cursor.getString(0);
                db.close();
                return nombre;
            }
            public String getIdTema(String nombre) {
                String selectQuery = "SELECT idTema FROM " + TABLE_APP_TEMAS + " WHERE nombre = '" + nombre + "'";
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                String rpta = "";
                cursor.moveToFirst();
                rpta=cursor.getString(0);
                db.close();
                return rpta;
            }
            public ArrayList<TemaClass> getAllItem(){
                ArrayList<TemaClass> orderClassList = new ArrayList<TemaClass>();
                String selectQuery = "SELECT  * FROM " + TABLE_APP_TEMAS;
                SQLiteDatabase db = databasehelp.getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()){
                    do {
                        TemaClass item = new TemaClass();
                        //item.setId(cursor.getInt(cursor.getColumnIndex(KEY_DOCID)));
                        item.setIdTema(cursor.getString(cursor.getColumnIndex(KEY_TEMCOD)));
                        item.setNombre(cursor.getString(cursor.getColumnIndex(KEY_TEMNAM)));
                        orderClassList.add(item);
                    } while (cursor.moveToNext());
                }
                db.close();
                return orderClassList;
            }

        }
    }

}
