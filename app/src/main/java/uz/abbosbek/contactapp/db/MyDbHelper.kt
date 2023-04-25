package uz.abbosbek.contactapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.abbosbek.contactapp.models.ContactInfo
import uz.abbosbek.contactapp.models.DbConstant.CONTACT_NAME
import uz.abbosbek.contactapp.models.DbConstant.CONTACT_NUMBER
import uz.abbosbek.contactapp.models.DbConstant.CONTACT_SURNAME
import uz.abbosbek.contactapp.models.DbConstant.CONTACT_TABLE
import uz.abbosbek.contactapp.models.DbConstant.DB_NAME
import uz.abbosbek.contactapp.models.DbConstant.ID
import uz.abbosbek.contactapp.models.DbConstant.VERSION

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, VERSION),
    MyDbInterface {
    override fun onCreate(p0: SQLiteDatabase?) {
        val query =
            "create table $CONTACT_TABLE($ID integer not null primary key autoincrement unique, $CONTACT_NAME text not null, $CONTACT_SURNAME text not null, $CONTACT_NUMBER text not null)"

        p0?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    override fun addContact(contactInfo: ContactInfo) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CONTACT_NAME, contactInfo.name)
        contentValues.put(CONTACT_SURNAME, contactInfo.surName)
        contentValues.put(CONTACT_NUMBER, contactInfo.number)
        database.insert(CONTACT_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllContacts(): ArrayList<ContactInfo> {
        val database = this.readableDatabase
        val cursor = database.rawQuery("select *from $CONTACT_TABLE", null)
        val list = ArrayList<ContactInfo>()
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    ContactInfo(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    override fun deleteContact(contactInfo: ContactInfo) {
        val database = this.writableDatabase
        database.delete(CONTACT_TABLE, "id = ?", arrayOf(contactInfo.id.toString()))
        database.close()
    }

    override fun editContact(contactInfo: ContactInfo): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CONTACT_NAME, contactInfo.name)
        contentValues.put(CONTACT_SURNAME, contactInfo.surName)
        contentValues.put(CONTACT_NUMBER, contactInfo.number)

        return database.update(
            CONTACT_TABLE, contentValues, "$ID = ?", arrayOf(contactInfo.id.toString())
        )
    }
}