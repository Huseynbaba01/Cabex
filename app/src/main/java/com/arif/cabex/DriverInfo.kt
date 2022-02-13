package com.arif.cabex

class DriverInfo {
    var profilePicture:Int? = null
    var driverRoute: String? = null
    var driverFullName:String? = null
    var departurePositionTime:String? = null
    var carBrand:String? = null
    var numberOfSeats:Int? = null
    var emptySeats:Int? = null
    var seats:String? = null
    var departureTime:String? = null
    var departurePoint:String? = null
    var destinationPoint:String? = null
    var isOpened = false
    constructor(profilePicture:Int, departurePoint:String, destinationPoint:String, driverFullName:String, departureTime:String,
                carBrand:String, numberOfSeats:Int, emptySeats:Int) {
        this.carBrand = carBrand
        this.departurePoint = departurePoint
        this.destinationPoint = destinationPoint
        this.driverFullName = driverFullName
        this.emptySeats = emptySeats
        this.departureTime = departureTime
        this. profilePicture = profilePicture
        this. numberOfSeats = numberOfSeats
        seats = "Oturacaq sayı:$numberOfSeats, Boş yerlər:$emptySeats"
        departurePositionTime = "$departureTime-da $departurePoint adlı məntəqəni tərk edir."
        driverRoute = "$departurePoint - $destinationPoint"
    }
}