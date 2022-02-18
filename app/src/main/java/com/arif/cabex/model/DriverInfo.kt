package com.arif.cabex.model

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
    var contactNumber:String? = null
    var carPlate:String? = null
    var carColor:String? =null
    var rating:Double? = null
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
    constructor(profilePicture:Int, departurePoint:String, destinationPoint:String, driverFullName:String, departureTime:String,
                carBrand:String, numberOfSeats:Int, emptySeats:Int, contactNumber:String, carPlate:String, carColor:String, rating:Double) {
        this.carBrand = carBrand
        this.departurePoint = departurePoint
        this.destinationPoint = destinationPoint
        this.driverFullName = driverFullName
        this.emptySeats = emptySeats
        this.departureTime = departureTime
        this. profilePicture = profilePicture
        this. numberOfSeats = numberOfSeats
        this.contactNumber = contactNumber
        this.carPlate = carPlate
        this.carBrand = carBrand
        this.carColor = carColor
        this.rating = rating
        seats = "Oturacaq sayı:$numberOfSeats, Boş yerlər:$emptySeats"
        departurePositionTime = "$departureTime-da $departurePoint adlı məntəqəni tərk edir."
        driverRoute = "$departurePoint - $destinationPoint"
    }



}