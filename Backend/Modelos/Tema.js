'use strict'

const mongoose = require('mongoose')
const Schema = mongoose.Schema

const TemaEsquema = Schema({
    nombre:String
})

module.exports = mongoose.model('Tema', TemaEsquema)
