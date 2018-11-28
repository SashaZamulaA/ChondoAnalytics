package com.example.aleksandr.myapplication.model

sealed class CityUkraine(val id: Long,
                         val method: Method) {

    class KYIV(id: Long, readableName: String?) : CityUkraine(id, Method.KYIV)
    class KHARKIV(id: Long, readableName: String?) : CityUkraine(id, Method.KHARKIV)
    class LVIV(id: Long, readableName: String?) : CityUkraine(id, Method.LVIV)
    class DNEPR(id: Long, readableName: String?) : CityUkraine(id, Method.DNEPR)
    class ZHYTOMYR(id: Long, readableName: String?) : CityUkraine(id, Method.ZHYTOMYR)
    class ODESSA(id: Long, readableName: String?) : CityUkraine(id, Method.ODESSA)
    class CHERNIGOV(id: Long, readableName: String?) : CityUkraine(id, Method.CHERNIGOV)


    enum class Method(val method: String) {
        KYIV("KYIV"),
        KHARKIV("KHARKIV"),
        LVIV("LVIV"),
        DNEPR("DNEPR"),
        ZHYTOMYR("ZHYTOMYR"),
        ODESSA("ODESSA"),
        CHERNIGOV("CHERNIGOV"),
    }
}