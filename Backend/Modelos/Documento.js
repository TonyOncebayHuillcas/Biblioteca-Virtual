'use strict'

const mongoose = require('mongoose')
const Schema = mongoose.Schema

const DocumentoEsquema = Schema({
    nombre:String,
    contador:{type:String,default:'0'},
    fecha:String,
    codTema:{type:Schema.ObjectId, ref:'Tema'},
    codUsuario:{type:Schema.ObjectId,ref:'Usuario'},
    archivo:String
})

module.exports = mongoose.model('Documento', DocumentoEsquema)

