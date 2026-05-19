package fr.student.app.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GradeDao {
    @Insert
    long insert(GradeEntity entity);

    @Update
    void update(GradeEntity entity);

    @Delete
    void delete(GradeEntity entity);

    @Query("SELECT * FROM grades WHERE semesterId = :semesterId")
    List<GradeEntity> getForSemester(long semesterId);

    @Query("SELECT * FROM grades WHERE semesterId = :semesterId AND courseId = :courseId LIMIT 1")
    GradeEntity getForCourse(long semesterId, long courseId);
}
