package com.example.sportivo.Services;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import com.example.sportivo.Models.Reservation;
import com.example.sportivo.Models.Sport;
import com.example.sportivo.start_screen.SportsListFragmentDataStorage;

import java.util.Calendar;

import androidx.core.content.ContextCompat;

public class CalendarService {
    public static void addEventToCalendar(Context context) {


        long calID = 3;
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(ReservationService.getYear(), ReservationService.getMonth()-1, ReservationService.getDay(), ReservationService.getHour(), ReservationService.getMinute());
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(ReservationService.getYear(), ReservationService.getMonth()-1, ReservationService.getDay(), ReservationService.getHour() + ReservationService.getLength(), ReservationService.getMinute());
        endMillis = endTime.getTimeInMillis();
        Sport sport = SportsListFragmentDataStorage.getSportById(ReservationService.sportId);

        String eventDescription = sport == null ? "description" : sport.getName();

        ContentResolver cr = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, "Sportivo");
        values.put(CalendarContract.Events.DESCRIPTION, eventDescription);
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            try {
                cr.insert(CalendarContract.Events.CONTENT_URI, values);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void removeEvent(Context context, Reservation reservation) {

        final String[] INSTANCE_PROJECTION = new String[] {
                CalendarContract.Instances.EVENT_ID,      // 0
                CalendarContract.Instances.DESCRIPTION,         // 1
                CalendarContract.Instances.TITLE          // 2
        };
        final int PROJECTION_ID_INDEX = 0;

        // Specify the date range you want to search for recurring event instances
        Calendar beginTime = Calendar.getInstance();
        beginTime.setTime(reservation.getStartTime());
        long startMillis = beginTime.getTimeInMillis();

        Calendar endTime = Calendar.getInstance();
        endTime.setTime(reservation.getEndTime());
        long endMillis = endTime.getTimeInMillis();

        Sport sport = SportsListFragmentDataStorage.getSportById(reservation.getCourt().getSportId());
        String sportName = sport == null ? "" : sport.getName();

        // The ID of the recurring event whose instances you are searching for in the Instances table
        String selection = CalendarContract.Instances.TITLE + " = ?" + " AND " + CalendarContract.Instances.DESCRIPTION + " = ?";
        String[] selectionArgs = new String[] {"Sportivo", sportName};

        // Construct the query with the desired date range.
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        // Submit the query
        Cursor cur = context.getContentResolver().query(builder.build(), INSTANCE_PROJECTION, selection, selectionArgs, null);

        if(cur != null && cur.moveToFirst()) {
            long eventID = cur.getLong(PROJECTION_ID_INDEX);

            Uri deleteUri;
            deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
            context.getContentResolver().delete(deleteUri, null, null);
        }

        if(cur != null){
            cur.close();
        }
    }
}
