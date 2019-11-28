'use strict'

const mongoose = require('mongoose')
const Schema = mongoose.Schema

const UniversidadEsquema = Schema({
    nombre:String,
    imagen:String
})

module.exports = mongoose.model('Universidad', UniversidadEsquema)