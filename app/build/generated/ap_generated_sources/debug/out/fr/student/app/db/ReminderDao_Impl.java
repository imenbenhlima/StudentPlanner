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
public final class ReminderDao_Impl implements ReminderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ReminderEntity> __insertionAdapterOfReminderEntity;

  private final EntityDeletionOrUpdateAdapter<ReminderEntity> __deletionAdapterOfReminderEntity;

  private final EntityDeletionOrUpdateAdapter<ReminderEntity> __updateAdapterOfReminderEntity;

  public ReminderDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReminderEntity = new EntityInsertionAdapter<ReminderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `reminders` (`id`,`text`,`triggerMillis`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ReminderEntity entity) {
        statement.bindLong(1, entity.id);
        if (entity.text == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.text);
        }
        statement.bindLong(3, entity.triggerMillis);
      }
    };
    this.__deletionAdapterOfReminderEntity = new EntityDeletionOrUpdateAdapter<ReminderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `reminders` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ReminderEntity entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfReminderEntity = new EntityDeletionOrUpdateAdapter<ReminderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `reminders` SET `id` = ?,`text` = ?,`triggerMillis` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ReminderEntity entity) {
        statement.bindLong(1, entity.id);
        if (entity.text == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.text);
        }
        statement.bindLong(3, entity.triggerMillis);
        statement.bindLong(4, entity.id);
      }
    };
  }

  @Override
  public long insert(final ReminderEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfReminderEntity.insertAndReturnId(entity);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final ReminderEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfReminderEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final ReminderEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfReminderEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public ReminderEntity getById(final long id) {
    final String _sql = "SELECT * FROM reminders WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final int _cursorIndexOfTriggerMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "triggerMillis");
      final ReminderEntity _result;
      if (_cursor.moveToFirst()) {
        final String _tmpText;
        if (_cursor.isNull(_cursorIndexOfText)) {
          _tmpText = null;
        } else {
          _tmpText = _cursor.getString(_cursorIndexOfText);
        }
        final long _tmpTriggerMillis;
        _tmpTriggerMillis = _cursor.getLong(_cursorIndexOfTriggerMillis);
        _result = new ReminderEntity(_tmpText,_tmpTriggerMillis);
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

  @Override
  public List<ReminderEntity> getAll() {
    final String _sql = "SELECT * FROM reminders ORDER BY triggerMillis ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final int _cursorIndexOfTriggerMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "triggerMillis");
      final List<ReminderEntity> _result = new ArrayList<ReminderEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ReminderEntity _item;
        final String _tmpText;
        if (_cursor.isNull(_cursorIndexOfText)) {
          _tmpText = null;
        } else {
          _tmpText = _cursor.getString(_cursorIndexOfText);
        }
        final long _tmpTriggerMillis;
        _tmpTriggerMillis = _cursor.getLong(_cursorIndexOfTriggerMillis);
        _item = new ReminderEntity(_tmpText,_tmpTriggerMillis);
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
  public List<ReminderEntity> getFuture(final long now) {
    final String _sql = "SELECT * FROM reminders WHERE triggerMillis > ? ORDER BY triggerMillis ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, now);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final int _cursorIndexOfTriggerMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "triggerMillis");
      final List<ReminderEntity> _result = new ArrayList<ReminderEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ReminderEntity _item;
        final String _tmpText;
        if (_cursor.isNull(_cursorIndexOfText)) {
          _tmpText = null;
        } else {
          _tmpText = _cursor.getString(_cursorIndexOfText);
        }
        final long _tmpTriggerMillis;
        _tmpTriggerMillis = _cursor.getLong(_cursorIndexOfTriggerMillis);
        _item = new ReminderEntity(_tmpText,_tmpTriggerMillis);
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _result.add(_item);
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
