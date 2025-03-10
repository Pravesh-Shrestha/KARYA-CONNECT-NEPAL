package com.example.karyaconnectnepal.Model

import android.adservices.adid.AdId
import android.os.Parcel
import android.os.Parcelable

data class UserModel (
    var userId: String = "",
    var userType : String = "",
    var fullName : String = "",
    var email : String = "",
    var contact : String = "",
    var profileImage: String = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(userType)
        parcel.writeString(fullName)
        parcel.writeString(email)
        parcel.writeString(contact)
        parcel.writeString(profileImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}