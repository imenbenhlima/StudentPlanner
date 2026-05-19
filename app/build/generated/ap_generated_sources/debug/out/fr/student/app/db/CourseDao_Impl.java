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
public final class CourseDao_Impl implements CourseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CourseEntity> __insertionAdapterOfCourseEntity;

  private final EntityDeletionOrUpdateAdapter<CourseEntity> __deletionAdapterOfCourseEntity;

  private final EntityDeletionOrUpdateAdapter<CourseEntity> __updateAdapterOfCourseEntity;

  public CourseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCourseEntity = new EntityInsertionAdapter<CourseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `courses` (`id`,`semesterId`,`name`,`coefficient`,`description`,`type`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CourseEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.semesterId);
        if (entity.name == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.name);
        }
        statement.bindDouble(4, entity.coefficient);
        if (entity.description == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.description);
        }
        if (entity.type == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.type);
        }
      }
    };
    this.__deletionAdapterOfCourseEntity = new EntityDeletionOrUpdateAdapter<CourseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `courses` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CourseEntity entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfCourseEntity = new EntityDeletionOrUpdateAdapter<CourseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `courses` SET `id` = ?,`semesterId` = ?,`name` = ?,`coefficient` = ?,`description` = ?,`type` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CourseEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.semesterId);
        if (entity.name == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.name);
        }
        statement.bindDouble(4, entity.coefficient);
        if (entity.description == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.description);
        }
        if (entity.type == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.type);
        }
        statement.bindLong(7, entity.id);
      }
    };
  }

  @Override
  public long insert(final CourseEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfCourseEntity.insertAndReturnId(entity);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final CourseEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCourseEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final CourseEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCourseEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<CourseEntity> getForSemester(final long semesterId) {
    final String _sql = "SELECT * FROM courses WHERE semesterId = ? ORDER BY name COLLATE NOCASE ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, semesterId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSemesterId = CursorUtil.getColumnIndexOrThrow(_cursor, "semesterId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCoefficient = CursorUtil.getColumnIndexOrThrow(_cursor, "coefficient");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final List<CourseEntity> _result = new ArrayList<CourseEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final CourseEntity _item;
        final long _tmpSemesterId;
        _tmpSemesterId = _cursor.getLong(_cursorIndexOfSemesterId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final float _tmpCoefficient;
        _tmpCoefficient = _cursor.getFloat(_cursorIndexOfCoefficient);
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        final String _tmpType;
        if (_cursor.isNull(_cursorIndexOfType)) {
          _tmpType = null;
        } else {
          _tmpType = _cursor.getString(_cursorIndexOfType);
        }
        _item = new CourseEntity(_tmpSemesterId,_tmpName,_tmpCoefficient,_tmpDescription,_tmpType);
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
  public CourseEntity getById(final long id) {
    final String _sql = "SELECT * FROM courses WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSemesterId = CursorUtil.getColumnIndexOrThrow(_cursor, "semesterId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCoefficient = CursorUtil.getColumnIndexOrThrow(_cursor, "coefficient");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final CourseEntity _result;
      if (_cursor.moveToFirst()) {
        final long _tmpSemesterId;
        _tmpSemesterId = _cursor.getLong(_cursorIndexOfSemesterId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final float _tmpCoefficient;
        _tmpCoefficient = _cursor.getFloat(_cursorIndexOfCoefficient);
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        final String _tmpType;
        if (_cursor.isNull(_cursorIndexOfType)) {
          _tmpType = null;
        } else {
          _tmpType = _cursor.getString(_cursorIndexOfType);
        }
        _result = new CourseEntity(_tmpSemesterId,_tmpName,_tmpCoefficient,_tmpDescription,_tmpType);
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
