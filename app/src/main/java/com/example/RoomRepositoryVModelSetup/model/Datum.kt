package com.example.RoomRepositoryVModelSetup.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_data_table")
class Datum {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @ColumnInfo(name = "email")
    @SerializedName("email")
    @Expose
    var email: String? = null

    @ColumnInfo(name = "first_name")
    @SerializedName("first_name")
    @Expose
    var firstName: String? = null

    @ColumnInfo(name = "last_name")
    @SerializedName("last_name")
    @Expose
    var lastName: String? = null

    @ColumnInfo(name = "avatar")
    @SerializedName("avatar")
    @Expose
    var avatar: String? = null

}