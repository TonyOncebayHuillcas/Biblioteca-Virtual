'use strict'

const mongoose = require('mongoose')
const Schema = mongoose.Schema

const PublicidadEsquema = Schema({
    nombre:String,
    direccion:String,
    codUni:{type:Schema.ObjectId, ref:'Universidad'},
    codUsuario:{type:Schema.ObjectId, ref:'Usuario'}
})

module.exports = mongoose.model('Publicidad', PublicidadEsquema)