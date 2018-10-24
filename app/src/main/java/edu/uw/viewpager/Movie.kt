package edu.uw.viewpager

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A class that represents information about a movie.
 */

@Parcelize
class Movie(
        var title: String,
        var year: String,
        var description: String,
        var url: String
) : Parcelable {

    init {
        year = year.substring(0, 4) //handle year from string
    }

    override fun toString(): String {
        return this.title + " (" + this.year + ")"
    }

}
