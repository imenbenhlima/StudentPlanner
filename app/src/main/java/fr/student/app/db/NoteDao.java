package fr.student.app.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    long insert(CourseNoteEntity entity);

    @Update
    void update(CourseNoteEntity entity);

    @Delete
    void delete(CourseNoteEntity entity);

    @Query("SELECT * FROM course_notes WHERE semesterId = :semesterId ORDER BY createdAtMillis DESC")
    List<CourseNoteEntity> getForSemester(long semesterId);
}
