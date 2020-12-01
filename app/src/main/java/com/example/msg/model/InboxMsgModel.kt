package com.example.msg.model

data class InboxMsgModel(
    var _id: String? = null,
    var _address: String? = null,
    var _msg: String? = null,
    var _readState: String? = null,
    var _time: String? = null,
    var _folderName: String? = null,
    var isHeader: Boolean = false
)