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
) : SQLiteOpenHelper(context, "app.db", factory, 4) {

    override fun onCreate(db: SQLiteDatabase) {
        val users = """
            CREATE TABLE users (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              login TEXT, email TEXT, pass TEXT,
              role TEXT DEFAULT 'user'
            );
        """.trimIndent()
        db.execSQL(users)

        val items = """
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
        """.trimIndent()
        db.execSQL(items)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldV: Int, newV: Int) {
        db.execSQL("DROP TABLE IF EXISTS items")
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

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

    /**
     * Перевести событие в completed + скопировать изображение в папку archive_images
     */
    fun completeItem(itemId: Int) {
        // 1) Получаем старый путь
        var oldImage: String? = null
        getItemById(itemId).use { c ->
            if (c.moveToFirst()) {
                oldImage = c.getString(c.getColumnIndexOrThrow("image"))
            }
        }
        // 2) Копируем файл, если он есть
        val newPath = oldImage?.let { copyFileToArchive(context, it) }
        // 3) Обновляем статус и, возможно, путь
        writableDatabase.use { db ->
            val cv = ContentValues().apply {
                put("status", "completed")
                newPath?.let { put("image", it) }
            }
            db.update("items", cv, "id = ?", arrayOf(itemId.toString()))
        }
    }

    /**
     * Однократно проставляет completed всем просроченным planned-событиям
     */
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

    /** Физически копирует файл в filesDir/archive_images и возвращает новый путь */
    private fun copyFileToArchive(ctx: Context, sourcePath: String): String? {
        val src = File(sourcePath)
        if (!src.exists()) return null
        val archive = File(ctx.filesDir, "archive_images").also { if (!it.exists()) it.mkdirs() }
        val dst = File(archive, src.name)
        return try {
            FileInputStream(src).use { input ->
                FileOutputStream(dst).use { output -> input.copyTo(output) }
            }
            dst.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    /**
     * Возвращает только "planned" события данного организатора
     */
    fun getPlannedItemsByOrganizer(orgId: Int): Cursor =
        readableDatabase.rawQuery(
            "SELECT * FROM items WHERE organizer_id = ? AND status = ?",
            arrayOf(orgId.toString(), "planned")
        )

    /** Только проведённые (для архива) */
    fun getCompletedItemsByOrganizer(orgId: Int): Cursor =
        readableDatabase.rawQuery(
            "SELECT * FROM items WHERE organizer_id = ? AND status = ?",
            arrayOf(orgId.toString(), "completed")
        )
}