package fr.student.app.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SemesterDao {
    @Insert
    long insert(SemesterEntity entity);

    @Update
    void update(SemesterEntity entity);

    @Query("SELECT * FROM semesters ORDER BY id ASC")
    List<SemesterEntity> getAll();

    @Query("SELECT * FROM semesters WHERE id = :id LIMIT 1")
    SemesterEntity getById(long id);
}
