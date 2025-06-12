package com.example.eco_activities

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class DbHelper(
    private val context: Context,
    factory: SQLiteDatabase.CursorFactory?
) : SQLiteOpenHelper(context, "app.db", factory, 5) {

    override fun onCreate(db: SQLiteDatabase) {
        // 1) Пользователи
        db.execSQL("""
            CREATE TABLE users (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              login TEXT,
              email TEXT,
              pass TEXT,
              role TEXT DEFAULT 'user'
            );
        """.trimIndent())

        // 2) Мероприятия
        db.execSQL("""
            CREATE TABLE items (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              image TEXT,
              title TEXT,
              description TEXT,
              text TEXT,
              price INTEGER,
              date TEXT,
              status TEXT DEFAULT 'planned',
              organizer_id INTEGER,
              FOREIGN KEY (organizer_id) REFERENCES users(id)
            );
        """.trimIndent())

        // 3) Записи пользователей на мероприятия
        db.execSQL("""
            CREATE TABLE signups (
              id      INTEGER PRIMARY KEY AUTOINCREMENT,
              user_id INTEGER NOT NULL,
              item_id INTEGER NOT NULL,
              FOREIGN KEY (user_id) REFERENCES users(id),
              FOREIGN KEY (item_id) REFERENCES items(id)
            );
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldV: Int, newV: Int) {
        // Удаляем все таблицы и пересоздаём
        db.execSQL("DROP TABLE IF EXISTS signups")
        db.execSQL("DROP TABLE IF EXISTS items")
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    // --- User methods ---
    fun addUser(user: User) {
        writableDatabase.use { db ->
            val cv = ContentValues().apply {
                put("login", user.login)
                put("email", user.email)
                put("pass", user.pass)
                put("role", user.role)
            }
            db.insert("users", null, cv)
        }
    }

    fun getUserWithRole(login: String, pass: String): Cursor =
        readableDatabase.rawQuery(
            "SELECT * FROM users WHERE login = ? AND pass = ?",
            arrayOf(login, pass)
        )

    // --- Item methods ---
    fun addItem(item: Item) {
        writableDatabase.use { db ->
            val cv = ContentValues().apply {
                put("image", item.image)
                put("title", item.title)
                put("description", item.desc)
                put("text", item.text)
                put("price", item.price)
                put("date", item.date)
                put("status", item.status)
                put("organizer_id", item.organizerId)
            }
            db.insert("items", null, cv)
        }
    }

    fun updateItem(item: Item) {
        writableDatabase.use { db ->
            val cv = ContentValues().apply {
                put("image", item.image)
                put("title", item.title)
                put("description", item.desc)
                put("text", item.text)
                put("price", item.price)
                put("date", item.date)
                put("status", item.status)
            }
            db.update("items", cv, "id = ?", arrayOf(item.id.toString()))
        }
    }

    fun deleteItem(itemId: Int) {
        writableDatabase.use { db ->
            db.delete("items", "id = ?", arrayOf(itemId.toString()))
        }
    }

    fun getItemById(itemId: Int): Cursor =
        readableDatabase.rawQuery(
            "SELECT * FROM items WHERE id = ?",
            arrayOf(itemId.toString())
        )

    fun getItemsByOrganizer(orgId: Int): Cursor =
        readableDatabase.rawQuery(
            "SELECT * FROM items WHERE organizer_id = ?",
            arrayOf(orgId.toString())
        )

    fun getPlannedItemsByOrganizer(orgId: Int): Cursor =
        readableDatabase.rawQuery(
            "SELECT * FROM items WHERE organizer_id = ? AND status = ?",
            arrayOf(orgId.toString(), "planned")
        )

    fun getCompletedItemsByOrganizer(orgId: Int): Cursor =
        readableDatabase.rawQuery(
            "SELECT * FROM items WHERE organizer_id = ? AND status = ?",
            arrayOf(orgId.toString(), "completed")
        )

    fun getActiveItems(): Cursor =
        readableDatabase.rawQuery(
            "SELECT * FROM items WHERE status = ?",
            arrayOf("planned")
        )

    fun getArchivedItems(): Cursor =
        readableDatabase.rawQuery(
            "SELECT * FROM items WHERE status = ?",
            arrayOf("completed")
        )

    // Перевести event в completed + скопировать изображение в архив
    fun completeItem(itemId: Int) {
        var oldImage: String? = null
        getItemById(itemId).use { c ->
            if (c.moveToFirst()) {
                oldImage = c.getString(c.getColumnIndexOrThrow("image"))
            }
        }
        val newPath = oldImage?.let { copyFileToArchive(context, it) }
        writableDatabase.use { db ->
            val cv = ContentValues().apply {
                put("status", "completed")
                newPath?.let { put("image", it) }
            }
            db.update("items", cv, "id = ?", arrayOf(itemId.toString()))
        }
    }

    // Архивировать все просроченные (по дате) planned-события
    fun archiveExpiredEvents() {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val expiredIds = mutableListOf<Int>()
        readableDatabase.rawQuery(
            "SELECT id FROM items WHERE date < ? AND status = ?",
            arrayOf(today, "planned")
        ).use { c ->
            while (c.moveToNext()) {
                expiredIds += c.getInt(c.getColumnIndexOrThrow("id"))
            }
        }
        expiredIds.forEach { completeItem(it) }
    }

    // --- Signup methods ---
    /** Добавить запись пользователя на мероприятие */
    fun addSignUp(userId: Int, itemId: Int): Boolean {
        writableDatabase.use { db ->
            val cv = ContentValues().apply {
                put("user_id", userId)
                put("item_id", itemId)
            }
            val id = db.insert("signups", null, cv)
            return id != -1L
        }
    }

    /** Проверить, записан ли пользователь на мероприятие */
    fun isUserSigned(userId: Int, itemId: Int): Boolean {
        readableDatabase.use { db ->
            db.rawQuery(
                "SELECT 1 FROM signups WHERE user_id = ? AND item_id = ? LIMIT 1",
                arrayOf(userId.toString(), itemId.toString())
            ).use { c ->
                return c.moveToFirst()
            }
        }
    }

    /** Количество записавшихся на мероприятие */
    fun getSignUpCount(itemId: Int): Int {
        readableDatabase.use { db ->
            db.rawQuery(
                "SELECT COUNT(*) FROM signups WHERE item_id = ?",
                arrayOf(itemId.toString())
            ).use { c ->
                if (c.moveToFirst()) return c.getInt(0)
            }
        }
        return 0
    }

    /** Активные мероприятия, на которые записан пользователь */
    fun getUserSignedActiveEvents(userId: Int): Cursor =
        readableDatabase.rawQuery("""
            SELECT i.* FROM items i
              JOIN signups s ON i.id = s.item_id
             WHERE s.user_id = ? AND i.status = ?
        """.trimIndent(), arrayOf(userId.toString(), "planned"))

    /** Архивные мероприятия, на которые записан пользователь */
    fun getUserSignedArchivedEvents(userId: Int): Cursor =
        readableDatabase.rawQuery("""
            SELECT i.* FROM items i
              JOIN signups s ON i.id = s.item_id
             WHERE s.user_id = ? AND i.status = ?
        """.trimIndent(), arrayOf(userId.toString(), "completed"))

    // --- Вспомогательный метод для копирования картинок ---
    private fun copyFileToArchive(ctx: Context, sourcePath: String): String? {
        val src = File(sourcePath)
        if (!src.exists()) return null
        val archiveDir = File(ctx.filesDir, "archive_images").apply { if (!exists()) mkdirs() }
        val dest = File(archiveDir, src.name)
        return try {
            FileInputStream(src).use { input ->
                FileOutputStream(dest).use { output -> input.copyTo(output) }
            }
            dest.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}