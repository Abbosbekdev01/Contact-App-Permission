package uz.abbosbek.contactapp.models

import java.io.Serializable

class ContactInfo : Serializable {
    var id: Int? = null
    var name:String? = null
    var surName:String? = null
    var number:String? = null

    constructor(id: Int?, name: String?, surName: String?, number: String?) {
        this.id = id
        this.name = name
        this.surName = surName
        this.number = number
    }

    constructor(name: String?, surName: String?, number: String?) {
        this.name = name
        this.surName = surName
        this.number = number
    }


}