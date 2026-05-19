package fr.student.app.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimetableDao {
    @Insert
    long insert(TimetableSlotEntity entity);

    @Update
    void update(TimetableSlotEntity entity);

    @Delete
    void delete(TimetableSlotEntity entity);

    @Query("SELECT * FROM timetable_slots WHERE semesterId = :semesterId ORDER BY dayOfWeek, startMinuteOfDay")
    List<TimetableSlotEntity> getForSemester(long semesterId);

    @Query("SELECT * FROM timetable_slots WHERE semesterId = :semesterId AND dayOfWeek = :dayOfWeek ORDER BY startMinuteOfDay ASC")
    List<TimetableSlotEntity> getForDay(long semesterId, int dayOfWeek);
}
