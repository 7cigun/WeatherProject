package ru.gb.weatherproject

import android.app.Application
import androidx.room.Room
import ru.gb.weatherproject.domain.room.HistoryDao
import ru.gb.weatherproject.domain.room.MyDB

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object{
        private var db: MyDB?=null
        private var appContext:MyApp?=null
        fun getHistoryDao(): HistoryDao {
            if(db==null){
                if(appContext!=null){
                    db = Room.databaseBuilder(appContext!!,MyDB::class.java,"test")
                        .build()
                }else{
                    throw IllegalStateException("что-то пошло не так, и у нас пустое appContext")
                }
            }
            return db!!.historyDao()
        }
    }
}