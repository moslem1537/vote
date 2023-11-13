package com.example.votingsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String databaseName = "Signup.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Signup.db", null, 12); // Update the version number to 7
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table IF NOT EXISTS civilians(email TEXT primary key UNIQUE, password Text, city Text, name Text)");
        MyDatabase.execSQL("create Table IF NOT EXISTS candidates (email TEXT primary key, password Text,city Text, name Text,achievements Text, manifesto Text)");
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS admins(email TEXT PRIMARY KEY, password TEXT)");
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS approved_candidates (name Text PRIMARY KEY,city Text, achievements Text, manifesto Text)");
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS CandidateVote (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, city TEXT, civilianEmail TEXT, voteCount INTEGER DEFAULT 0, UNIQUE(name, city, civilianEmail))");

        insertAdminData(MyDatabase); // Insert admin data
        insertDummyCandidates(MyDatabase);
        insertDummyCivilians(MyDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int oldVersion, int newVersion) {
        MyDatabase.execSQL("drop Table if exists civilians");
        MyDatabase.execSQL("drop Table if exists candidates"); // Add this line to drop the 'candidates' table
        MyDatabase.execSQL("drop Table if exists approved_candidates");
        MyDatabase.execSQL("drop Table if exists CandidateVote");
        onCreate(MyDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void insertAdminData(SQLiteDatabase MyDatabase) {
        // Define the admin's email and password
        String adminEmail = "admin@gmail.com";
        String adminPassword = "123";

        // Insert the admin's data into the admins table
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", adminEmail);
        contentValues.put("password", adminPassword);

        long result = MyDatabase.insert("admins", null, contentValues);

        if (result == -1) {
            Log.d("DatabaseHelper", "Failed to insert admin data");
        } else {
            Log.d("DatabaseHelper", "Admin data inserted successfully");
        }
    }

    public boolean insertCivilianData(String email, String password, String name, String city) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("name", name);
        contentValues.put("city", city);
        long result = MyDatabase.insert("civilians", null, contentValues);

        return result != -1;
    }

    public Boolean checkCandidateEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from candidates where email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();

        return exists;
    }

    public boolean insertCandidateData(String email, String password, String name, String city, String achievements, String manifesto) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("city",city);
        contentValues.put("name", name);
        contentValues.put("achievements", achievements);
        contentValues.put("manifesto", manifesto);
        long result = MyDatabase.insert("candidates", null, contentValues);

        return result != -1;
    }


    private void insertDummyCandidates(SQLiteDatabase MyDatabase) {
        // Insert dummy candidates data
        insertCandidateData(MyDatabase, "q@example.com", "123", "mohamed", "kairouan", "sssssssss", "mmmmmmmmmmmmmmmmmmmmmm");
        insertCandidateData(MyDatabase, "s@example.com", "456", "oussema", "kairouan", "ffffffffffffffff", "Ammmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
        insertCandidateData(MyDatabase, "d@example.com", "789", "firas", "kairouan", "llllllllllllllllllllllllllllll", "mnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");

    }


    private void insertCandidateData(SQLiteDatabase MyDatabase, String email, String password, String name, String city, String achievements, String manifesto) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("city", city);
        contentValues.put("name", name);
        contentValues.put("achievements", achievements);
        contentValues.put("manifesto", manifesto);
        long result = MyDatabase.insert("candidates", null, contentValues);

        if (result == -1) {
            Log.d("DatabaseHelper", "Failed to insert candidate data");
        } else {
            Log.d("DatabaseHelper", "Candidate data inserted successfully");
        }
    }

    private void insertDummyCivilians(SQLiteDatabase MyDatabase) {
        // Insert dummy civilians data
        insertCivilianData(MyDatabase, "a@example.com", "123", "mohsen", "kairouan");
        insertCivilianData(MyDatabase, "z@example.com", "456", "ahmed", "kairouan");
        insertCivilianData(MyDatabase, "e@example.com", "789", "salim", "kairouan");

    }

    private void insertCivilianData(SQLiteDatabase MyDatabase, String email, String password, String name, String city) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("name", name);
        contentValues.put("city", city);
        long result = MyDatabase.insert("civilians", null, contentValues);

        if (result == -1) {
            Log.d("DatabaseHelper", "Failed to insert civilian data");
        } else {
            Log.d("DatabaseHelper", "civilian data inserted successfully");
        }
    }

    public boolean insertApprovedCandidateData(Candidate candidate) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("city", candidate.getCity());
        contentValues.put("name", candidate.getName());
        contentValues.put("achievements", candidate.getAchievements());
        contentValues.put("manifesto", candidate.getManifesto());
        long result = MyDatabase.insert("approved_candidates", null, contentValues);

        return result != -1;
    }


    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from civilians where email = ?", new String[]{email});

        return cursor.getCount() > 0;
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from civilians where email = ? and password =?", new String[]{email, password});

        return cursor.getCount() > 0;
    }

    public Boolean checkAdminEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM admins WHERE email = ? AND password = ?", new String[]{email, password});
        boolean isAdmin = cursor.getCount() > 0;
        cursor.close();
        return isAdmin;
    }

    public String getAdminEmail() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT email FROM admins LIMIT 1";  // Assuming there's only one admin
        Cursor cursor = db.rawQuery(query, null);

        String email = "";
        if(cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndex("email"));

        }
        cursor.close();

        return email;
    }

    public List<Candidate> getAllCandidates() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM candidates";
        Cursor cursor = db.rawQuery(query, null);

        List<Candidate> candidates = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String city = cursor.getString(cursor.getColumnIndex("city"));
                String achievements = cursor.getString(cursor.getColumnIndex("achievements"));
                String manifesto = cursor.getString(cursor.getColumnIndex("manifesto"));

                Candidate candidate = new Candidate(name, city, achievements, manifesto);
                candidates.add(candidate);

            } while(cursor.moveToNext());
        }
        cursor.close();

        return candidates;
    }



    public List<ApprovedCandidate> getAllApprovedCandidates() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM approved_candidates";
        Cursor cursor = db.rawQuery(query, null);

        List<ApprovedCandidate> approvedCandidates = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String city = cursor.getString(cursor.getColumnIndex("city"));
                String achievements = cursor.getString(cursor.getColumnIndex("achievements"));
                String manifesto = cursor.getString(cursor.getColumnIndex("manifesto"));

                ApprovedCandidate candidate = new ApprovedCandidate(name, city, achievements, manifesto);
                approvedCandidates.add(candidate);

            } while (cursor.moveToNext());
        }
        cursor.close();

        System.out.println("Fetched " + approvedCandidates.size() + " approved candidates from the database"); // Console log for data fetch

        return approvedCandidates;
    }

    public List<CivilianCandidateList> getApprovedCandidatesByCity(String civiliancity) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM approved_candidates WHERE city = ?";
        Cursor cursor = db.rawQuery(query, new String[]{civiliancity});

        List<CivilianCandidateList> civilianCandidateList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String city = cursor.getString(cursor.getColumnIndex("city"));
                String achievements = cursor.getString(cursor.getColumnIndex("achievements"));
                String manifesto = cursor.getString(cursor.getColumnIndex("manifesto"));

                CivilianCandidateList civiliancandidate = new CivilianCandidateList(name, city, achievements, manifesto);
                civilianCandidateList.add(civiliancandidate);

            } while (cursor.moveToNext());
        }
        cursor.close();

        System.out.println("Fetched " + civilianCandidateList.size() + " approved candidates from the database"); // Console log for data fetch

        return civilianCandidateList;
    }


    public String getCivilianCity(String civilianEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT city FROM civilians WHERE email = ?", new String[]{civilianEmail});
        if (cursor.moveToFirst()) {
            String city = cursor.getString(cursor.getColumnIndex("city"));
            cursor.close();
            return city;
        } else {
            cursor.close();
            return null; // or throw an exception
        }
    }

    public boolean insertVoteRecord(String civilianEmail, String candidateName, String city) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", candidateName);
        contentValues.put("city", city);
        contentValues.put("civilianEmail", civilianEmail);
        long result = MyDatabase.insert("CandidateVote", null, contentValues);

        if (result == -1) {
            Log.d("DatabaseHelper", "Failed to insert vote record");
            return false;
        } else {
            Log.d("DatabaseHelper", "Vote record inserted successfully");
            return true;
        }
    }



    public boolean checkCandidateVote(String candidateName, String civilianEmail) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM CandidateVote WHERE name = ? AND EXISTS (SELECT 1 FROM civilians WHERE email = ?)", new String[]{candidateName, civilianEmail});

        boolean alreadyVoted = cursor.getCount() > 0;
        cursor.close();
        return alreadyVoted;
    }


    public void incrementVoteCount(String candidateName) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        MyDatabase.execSQL("UPDATE CandidateVote SET voteCount = voteCount + 1 WHERE name = ?", new String[]{candidateName});
    }

    public boolean hasVoted(String civilianEmail) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM CandidateVote WHERE civilianEmail = ?", new String[]{civilianEmail});

        boolean hasVoted = cursor.getCount() > 0;
        cursor.close();
        return hasVoted;
    }


//Chart logic
public HashMap<String, HashMap<String, Integer>> getCandidateVotes() {
    SQLiteDatabase db = this.getReadableDatabase();
    String query = "SELECT name, city, COUNT(*) as voteCount FROM CandidateVote GROUP BY name, city";
    Cursor cursor = db.rawQuery(query, null);

    HashMap<String, HashMap<String, Integer>> candidateVotes = new HashMap<>();
    if (cursor.moveToFirst()) {
        do {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String city = cursor.getString(cursor.getColumnIndex("city"));
            int voteCount = cursor.getInt(cursor.getColumnIndex("voteCount"));

            HashMap<String, Integer> cityAndVoteCount = new HashMap<>();
            cityAndVoteCount.put(city, voteCount);

            candidateVotes.put(name, cityAndVoteCount);

        } while (cursor.moveToNext());
    }
    cursor.close();

    return candidateVotes;
}


    public int getApprovedCandidatesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM approved_candidates", null);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        } else {
            cursor.close();
            return 0;
        }
    }

    public HashMap<String, String> getWinners() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT name, city, COUNT(*) as voteCount FROM CandidateVote GROUP BY name, city ORDER BY city, voteCount DESC";
        Cursor cursor = db.rawQuery(query, null);

        HashMap<String, String> winners = new HashMap<>();
        if (cursor.moveToFirst()) {
            String currentFaculty = "";
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String city = cursor.getString(cursor.getColumnIndex("city"));

                // If this is a new faculty, then this candidate is the winner for this faculty
                if (!city.equals(currentFaculty)) {
                    winners.put(city, name);
                    currentFaculty = city;
                }

            } while (cursor.moveToNext());
        }
        cursor.close();

        return winners;
    }









}
