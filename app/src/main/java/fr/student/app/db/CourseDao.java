package fr.student.app.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    long insert(CourseEntity entity);

    @Update
    void update(CourseEntity entity);

    @Delete
    void delete(CourseEntity entity);

    @Query("SELECT * FROM courses WHERE semesterId = :semesterId ORDER BY name COLLATE NOCASE ASC")
    List<CourseEntity> getForSemester(long semesterId);

    @Query("SELECT * FROM courses WHERE id = :id LIMIT 1")
    CourseEntity getById(long id);
}
