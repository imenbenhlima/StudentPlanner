package fr.student.app.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssignmentDao {
    @Insert
    long insert(AssignmentEntity entity);

    @Update
    void update(AssignmentEntity entity);

    @Delete
    void delete(AssignmentEntity entity);

    @Query("SELECT * FROM assignments WHERE semesterId = :semesterId ORDER BY dueDateMillis ASC")
    List<AssignmentEntity> getForSemester(long semesterId);

    @Query("SELECT * FROM assignments WHERE semesterId = :semesterId AND dueDateMillis >= :fromMillis ORDER BY dueDateMillis ASC LIMIT 1")
    AssignmentEntity getNextDue(long semesterId, long fromMillis);
}
