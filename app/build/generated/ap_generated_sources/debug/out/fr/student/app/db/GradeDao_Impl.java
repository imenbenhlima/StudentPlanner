package fr.student.app.db;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class GradeDao_Impl implements GradeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<GradeEntity> __insertionAdapterOfGradeEntity;

  private final EntityDeletionOrUpdateAdapter<GradeEntity> __deletionAdapterOfGradeEntity;

  private final EntityDeletionOrUpdateAdapter<GradeEntity> __updateAdapterOfGradeEntity;

  public GradeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGradeEntity = new EntityInsertionAdapter<GradeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `grades` (`id`,`semesterId`,`courseId`,`value`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final GradeEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.semesterId);
        statement.bindLong(3, entity.courseId);
        statement.bindDouble(4, entity.value);
      }
    };
    this.__deletionAdapterOfGradeEntity = new EntityDeletionOrUpdateAdapter<GradeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `grades` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final GradeEntity entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfGradeEntity = new EntityDeletionOrUpdateAdapter<GradeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `grades` SET `id` = ?,`semesterId` = ?,`courseId` = ?,`value` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final GradeEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.semesterId);
        statement.bindLong(3, entity.courseId);
        statement.bindDouble(4, entity.value);
        statement.bindLong(5, entity.id);
      }
    };
  }

  @Override
  public long insert(final GradeEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfGradeEntity.insertAndReturnId(entity);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final GradeEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfGradeEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final GradeEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfGradeEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<GradeEntity> getForSemester(final long semesterId) {
    final String _sql = "SELECT * FROM grades WHERE semesterId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, semesterId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSemesterId = CursorUtil.getColumnIndexOrThrow(_cursor, "semesterId");
      final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
      final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
      final List<GradeEntity> _result = new ArrayList<GradeEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final GradeEntity _item;
        final long _tmpSemesterId;
        _tmpSemesterId = _cursor.getLong(_cursorIndexOfSemesterId);
        final long _tmpCourseId;
        _tmpCourseId = _cursor.getLong(_cursorIndexOfCourseId);
        final float _tmpValue;
        _tmpValue = _cursor.getFloat(_cursorIndexOfValue);
        _item = new GradeEntity(_tmpSemesterId,_tmpCourseId,_tmpValue);
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public GradeEntity getForCourse(final long semesterId, final long courseId) {
    final String _sql = "SELECT * FROM grades WHERE semesterId = ? AND courseId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, semesterId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, courseId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSemesterId = CursorUtil.getColumnIndexOrThrow(_cursor, "semesterId");
      final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
      final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
      final GradeEntity _result;
      if (_cursor.moveToFirst()) {
        final long _tmpSemesterId;
        _tmpSemesterId = _cursor.getLong(_cursorIndexOfSemesterId);
        final long _tmpCourseId;
        _tmpCourseId = _cursor.getLong(_cursorIndexOfCourseId);
        final float _tmpValue;
        _tmpValue = _cursor.getFloat(_cursorIndexOfValue);
        _result = new GradeEntity(_tmpSemesterId,_tmpCourseId,_tmpValue);
        _result.id = _cursor.getLong(_cursorIndexOfId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
