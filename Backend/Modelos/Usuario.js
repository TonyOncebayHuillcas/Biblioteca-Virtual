'use strict'

const mongoose = require('mongoose')
const Schema = mongoose.Schema

const UsuarioEsquema = Schema({
    userName:String,
    correo:String,
    contra:{type:String},
    numCue:{type:String, default:'123456'},
    codUni:{type:Schema.ObjectId, ref:'Universidad', default: '5dd8bd19d099cc00047d2213'}
})

//Cambio

module.exports = mongoose.model('Usuario', UsuarioEsquema)

