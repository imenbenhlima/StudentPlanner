package fr.student.app.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReminderDao {
    @Insert
    long insert(ReminderEntity entity);

    @Update
    void update(ReminderEntity entity);

    @Delete
    void delete(ReminderEntity entity);

    @Query("SELECT * FROM reminders WHERE id = :id LIMIT 1")
    ReminderEntity getById(long id);

    @Query("SELECT * FROM reminders ORDER BY triggerMillis ASC")
    List<ReminderEntity> getAll();

    @Query("SELECT * FROM reminders WHERE triggerMillis > :now ORDER BY triggerMillis ASC")
    List<ReminderEntity> getFuture(long now);
}
