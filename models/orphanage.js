const mongoose = require('mongoose')

const orphanageSchema = mongoose.Schema({
    nama_panti:{
        type: String,
        required : true
    },
    tentang_panti:{
        type: String,
        required : true
    },
    alamat_panti:{
        type: String,
        required : true
    },
    foto_panti:{
        type: String,
        required : true
    },
    telephone_panti:{
        type: String,
        required : true
    },
}, {
    versionkey: false
})

module.exports = mongoose.model('orphanage',orphanageSchema,'orphanage');